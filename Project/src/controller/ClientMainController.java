package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ClientMainController implements Initializable{
	@FXML
	private Button btorder;
	@FXML
	private Label lbid;
	@FXML
	private Label lbseatno;
	@FXML
	private Label lbprepaidmoney;
	@FXML
	private Label lbavailabletime;
	@FXML 
	private Button btlogout;
	
	//������Ű���
	private int time;
	ClientLoginController clc = new ClientLoginController();
	int currentSeatNo = clc.getSeatno(); //�α���â���� ����� �޺��ڽ��� �¼���ȣ ��
	String currentid = clc.getTxid(); //�α���â���� �Է��ߴ� id ��
	private boolean logout = true;
	private int countdownseconds;
	private int prepaidmoney;
	private int i; //Ÿ�̸� 1�ʾ� �� ����
	
	private Socket socket;
	private String id;
	private String count;
	private PrintWriter os;
	private BufferedReader in;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connect(); //�����ڿ� ��Ʈ��ũ ����
		lbseatno.setText(String.valueOf(currentSeatNo)); //�����¼�
		lbid.setText(currentid);//���� �����
		
	}
	
	
	//�����ڿ� ��Ʈ��ũ ����
	public void connect() {
		ChargeDAO chargeDAO = new ChargeDAO();
		prepaidmoney = chargeDAO.selectNameTime(currentid); //id�Է¹޾� ���ҿ�� int�� ��ȯ
		
		countdownseconds = chargeDAO.selectTime(currentid);
		
		try {
			socket = new Socket("localhost", 5000);
			os = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("������ ���� ����");
		}catch(IOException e) {
			System.out.println("������ ���� ����");
			e.printStackTrace();
		}
		//�����ð� ǥ��
		
		//���̵� �Է¹޾� ���ҿ�ݰ� �����ð��� ǥ�����ִ� ������
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					//outputStream�� ����Ͽ� "ID ������̵�"�� ������
					os.println("ID " + currentid);
					os.flush();
					while(logout) {
						if(countdownseconds == 0) {//�����ð� ���ٸ�
							CommonDialog.alertDisplay(1, "����", "����", "����");
						}else {//�����ð��� �ְ�
							if(currentid == null) {//������̵� ���ٸ�
								CommonDialog.alertDisplay(1, "����", "����", "����");
							}else {//������̵� �ִٸ�
								for(i = countdownseconds; i >= 0; i--) {
									
									Thread.sleep(1000);
									
									Platform.runLater(()->{
										lbavailabletime.setText(String.valueOf(i/3600 + ":" + i%3600/60 + ":" + i%3600%60));
										lbprepaidmoney.setText(prepaidmoney+" ��");
									});
								}
							}
						}
					}//while(logout) end
				}catch(InterruptedException e) {
					System.out.println("InterruptedException");
					e.printStackTrace();
				}
			}
		};
		t1.start();
	}//connect() end
	
	
	//��ǰ�ֹ�
	public void OrderAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/ClientOrderLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("��ǰ�ֹ�");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	// �������
	public void LogoutAction(ActionEvent event) throws Exception{
		//�ð� ����
		ChargeDAO cdo = new ChargeDAO();
		cdo.saveTime(i, currentid);
		
		os.println("EXIT " + currentid);
		os.flush();
		logout = false;
		if(logout ==false) {
			try {
				os.close();
				socket.close();
				
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				logout = true;
			}
		}
		
		Stage stage = (Stage) btlogout.getScene().getWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/ClientLoginLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		stage.close();
	}
	
	
	
}
