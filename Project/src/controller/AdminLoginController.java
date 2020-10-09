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
	//로그인하기
	public void LoginAction(ActionEvent event) throws Exception{
		currentadminid = txid.getText();
		String id = txid.getText();
		String pw = txpw.getText();
		
		int existadmin = AdminDAO.adminCheck(id, pw);
		//existadmin=1 인증성공
		if(existadmin == 1) {
			Stage stage = (Stage) btlogin.getScene().getWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminMainLayout.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("PC Manager_관리자");
			primaryStage.setResizable(false);
			primaryStage.show();
			stage.close();
		}
		//existadmin=0 아이디 오류
		else if(existadmin == 0) {
			CommonDialog.alertDisplay(1, "아이디 오류", "아이디 오류", "아이디를 확인해주세요");
		}
		//existadmin=-1 비밀번호 오류
		else if(existadmin == -1) {
			CommonDialog.alertDisplay(1, "비밀번호 오류", "비밀번호 오류", "비밀번호를 확인해주세요");
		}
	}
	
	// 회원가입창 열기
	public void SignUpAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminSignUpLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("회원가입");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
		
	// ID PW찾기 창 열기
	public void IDPWSearchAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminIDPWSearchLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ID/PW 찾기");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	// 종료
	public void ExitAction(ActionEvent event) {
		System.exit(0);
	}

}
