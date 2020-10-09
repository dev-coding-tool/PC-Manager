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
	
	//소켓통신관련
	private int time;
	ClientLoginController clc = new ClientLoginController();
	int currentSeatNo = clc.getSeatno(); //로그인창에서 골랐던 콤보박스의 좌석번호 값
	String currentid = clc.getTxid(); //로그인창에서 입력했던 id 값
	private boolean logout = true;
	private int countdownseconds;
	private int prepaidmoney;
	private int i; //타이머 1초씩 뺄 변수
	
	private Socket socket;
	private String id;
	private String count;
	private PrintWriter os;
	private BufferedReader in;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connect(); //관리자와 네트워크 연결
		lbseatno.setText(String.valueOf(currentSeatNo)); //현재좌석
		lbid.setText(currentid);//현재 사용자
		
	}
	
	
	//관리자와 네트워크 연결
	public void connect() {
		ChargeDAO chargeDAO = new ChargeDAO();
		prepaidmoney = chargeDAO.selectNameTime(currentid); //id입력받아 선불요금 int로 반환
		
		countdownseconds = chargeDAO.selectTime(currentid);
		
		try {
			socket = new Socket("localhost", 5000);
			os = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("서버에 연결 성공");
		}catch(IOException e) {
			System.out.println("서버에 연결 오류");
			e.printStackTrace();
		}
		//남은시간 표시
		
		//아이디를 입력받아 선불요금과 남은시간을 표시해주는 쓰레드
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					//outputStream을 사용하여 "ID 현재아이디"를 보내줌
					os.println("ID " + currentid);
					os.flush();
					while(logout) {
						if(countdownseconds == 0) {//남은시간 없다면
							CommonDialog.alertDisplay(1, "오류", "오류", "오류");
						}else {//남은시간이 있고
							if(currentid == null) {//현재아이디 없다면
								CommonDialog.alertDisplay(1, "오류", "오류", "오류");
							}else {//현재아이디 있다면
								for(i = countdownseconds; i >= 0; i--) {
									
									Thread.sleep(1000);
									
									Platform.runLater(()->{
										lbavailabletime.setText(String.valueOf(i/3600 + ":" + i%3600/60 + ":" + i%3600%60));
										lbprepaidmoney.setText(prepaidmoney+" 원");
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
	
	
	//상품주문
	public void OrderAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/ClientOrderLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("상품주문");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	// 사용종료
	public void LogoutAction(ActionEvent event) throws Exception{
		//시간 저장
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
