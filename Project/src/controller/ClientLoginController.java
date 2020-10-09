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
	
	//사용자id 저장
	public static String getCurrentID;
	String currentid;
	public String getTxid() {
		currentid = getCurrentID;
		return currentid;
	}
	//좌석번호 저장
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
	
	
	//로그인하기
	public void LoginAction(ActionEvent event) throws Exception{
		//좌석번호 콤보박스에서 값을 불러와서 넣음
		getCurrentSeatNo = cbseatno.getValue();
		//로그인한 아이디 텍스트값을 불러와서 넣음
		getCurrentID = txid.getText();
		System.out.println("getCurrentSeatNo = " + getCurrentSeatNo);
		if(getCurrentSeatNo == null) {
			CommonDialog.alertDisplay(1, "로그인실패", "좌석번호 선택 오류", "좌석번호를 선택해 주세요.");
			return;
		}
		String id = txid.getText();
		String pw = txpw.getText();
		String cb = cbseatno.getValue();
		
		int existmember = MemberDAO.memberCheck(id, pw);
//		System.out.println("회원체크완료");
//		System.out.println("id = " + id);
//		System.out.println("pw = " + pw);
//		System.out.println("cb = " + cb);
		
		//existmember=1 ID, PW인증 성공하면 클라이언트 메인창을 띄움
		if(existmember == 1) {
			ChargeDAO cdo = new ChargeDAO();
			cdo.updateCharge(cb, id);
			
			Stage stage = (Stage) btlogin.getScene().getWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/javafx/ClientMainLayout.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("PC Manager_고객");
			primaryStage.setResizable(false);
			primaryStage.show();
			stage.close();
			
			
		}
		//existmember=0 아이디 오류
		else if(existmember == 0) {
			CommonDialog.alertDisplay(1, "아이디 오류", "아이디 오류", "아이디를 확인해주세요");
		}
		//existmember=-1 비밀번호 오류
		else if(existmember == -1) {
			CommonDialog.alertDisplay(1, "비밀번호 오류", "비밀번호 오류", "비밀번호를 확인해주세요");
		}
	}
	
	// 회원가입창 열기
	public void SignUpAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/SignUpLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("회원가입");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	// ID PW찾기 창 열기
	public void IDPWSearchAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/IDPWSearchLayout.fxml"));
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
