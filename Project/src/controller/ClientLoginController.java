package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientLoginController implements Initializable{
	@FXML
	private TextField txid;
	@FXML
	private PasswordField txpw;
	@FXML
	private Button btlogin;
	@FXML
	private Button btsignup;
	@FXML
	private Button btexit;
	@FXML
	private ComboBox<String> cbseatno;
	
	//�����id ����
	public static String getCurrentID;
	String currentid;
	public String getTxid() {
		currentid = getCurrentID;
		return currentid;
	}
	//�¼���ȣ ����
	public static String getCurrentSeatNo;
	String seatno;
	
	public int getSeatno() {
		seatno = getCurrentSeatNo;
		return Integer.parseInt(seatno);
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbseatno.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8"));
		
	}
	
	
	//�α����ϱ�
	public void LoginAction(ActionEvent event) throws Exception{
		//�¼���ȣ �޺��ڽ����� ���� �ҷ��ͼ� ����
		getCurrentSeatNo = cbseatno.getValue();
		//�α����� ���̵� �ؽ�Ʈ���� �ҷ��ͼ� ����
		getCurrentID = txid.getText();
		System.out.println("getCurrentSeatNo = " + getCurrentSeatNo);
		if(getCurrentSeatNo == null) {
			CommonDialog.alertDisplay(1, "�α��ν���", "�¼���ȣ ���� ����", "�¼���ȣ�� ������ �ּ���.");
			return;
		}
		String id = txid.getText();
		String pw = txpw.getText();
		String cb = cbseatno.getValue();
		
		int existmember = MemberDAO.memberCheck(id, pw);
//		System.out.println("ȸ��üũ�Ϸ�");
//		System.out.println("id = " + id);
//		System.out.println("pw = " + pw);
//		System.out.println("cb = " + cb);
		
		//existmember=1 ID, PW���� �����ϸ� Ŭ���̾�Ʈ ����â�� ���
		if(existmember == 1) {
			ChargeDAO cdo = new ChargeDAO();
			cdo.updateCharge(cb, id);
			
			Stage stage = (Stage) btlogin.getScene().getWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/javafx/ClientMainLayout.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("PC Manager_��");
			primaryStage.setResizable(false);
			primaryStage.show();
			stage.close();
			
			
		}
		//existmember=0 ���̵� ����
		else if(existmember == 0) {
			CommonDialog.alertDisplay(1, "���̵� ����", "���̵� ����", "���̵� Ȯ�����ּ���");
		}
		//existmember=-1 ��й�ȣ ����
		else if(existmember == -1) {
			CommonDialog.alertDisplay(1, "��й�ȣ ����", "��й�ȣ ����", "��й�ȣ�� Ȯ�����ּ���");
		}
	}
	
	// ȸ������â ����
	public void SignUpAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/SignUpLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ȸ������");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	// ID PWã�� â ����
	public void IDPWSearchAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/IDPWSearchLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ID/PW ã��");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	// ����
	public void ExitAction(ActionEvent event) {
		System.exit(0);
	}
	
}
