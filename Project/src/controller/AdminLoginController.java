package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController implements Initializable{
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
	
	public static String currentadminid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	//�α����ϱ�
	public void LoginAction(ActionEvent event) throws Exception{
		currentadminid = txid.getText();
		String id = txid.getText();
		String pw = txpw.getText();
		
		int existadmin = AdminDAO.adminCheck(id, pw);
		//existadmin=1 ��������
		if(existadmin == 1) {
			Stage stage = (Stage) btlogin.getScene().getWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminMainLayout.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("PC Manager_������");
			primaryStage.setResizable(false);
			primaryStage.show();
			stage.close();
		}
		//existadmin=0 ���̵� ����
		else if(existadmin == 0) {
			CommonDialog.alertDisplay(1, "���̵� ����", "���̵� ����", "���̵� Ȯ�����ּ���");
		}
		//existadmin=-1 ��й�ȣ ����
		else if(existadmin == -1) {
			CommonDialog.alertDisplay(1, "��й�ȣ ����", "��й�ȣ ����", "��й�ȣ�� Ȯ�����ּ���");
		}
	}
	
	// ȸ������â ����
	public void SignUpAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminSignUpLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ȸ������");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
		
	// ID PWã�� â ����
	public void IDPWSearchAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminIDPWSearchLayout.fxml"));
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
