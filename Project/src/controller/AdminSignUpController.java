package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.AdminVO;

public class AdminSignUpController implements Initializable{
	@FXML
	private TextField txid;
	@FXML
	private PasswordField txpw1;
	@FXML
	private PasswordField txpw2;
	@FXML
	private Button btpwcheck;
	@FXML
	private Label lbpwcheck;
	@FXML
	private TextField txname;
	@FXML
	private RadioButton rbman;
	@FXML
	private RadioButton rbwoman;
	@FXML
	private TextField txphone;
	@FXML
	private ComboBox<String> cbyear;
	@FXML
	private ComboBox<String> cbmonth;
	@FXML
	private ComboBox<String> cbday;
	@FXML
	private Button btidcheck;
	@FXML
	private Button btcancel;
	@FXML
	private Button btok;
	@FXML
	private ToggleGroup genderGP;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//생년월일 세팅
		btok.setDisable(true);
		birthInit();
	}
	//생년월일 콤보박스세팅
	private void birthInit() {
		cbday.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14"
				,"15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"));
		cbmonth.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12"));
		ArrayList<String> yearss = new ArrayList<String>();
		for(int years = Calendar.getInstance().get(Calendar.YEAR); years >= 1900; years--) {
			yearss.add(years + "");
		}
		cbyear.setItems(FXCollections.observableArrayList(yearss));
	}
	
	
	//아이디 중복확인 액션이벤트
	public void IDcheckAction(ActionEvent event) {
		try {
			String mdo = AdminDAO.getAdminIDCheck(txid.getText());
			if(txid.getText().equals("")) {
				CommonDialog.alertDisplay(1, "아이디 입력 오류", "아이디 미입력", "아이디를 입력하세요");
			}else if(mdo.equals(txid.getText())) {
				CommonDialog.alertDisplay(1, "아이디 입력 오류", "중복된 아이디", "이미 사용중인 아이디입니다");
			}
		}catch(Exception e) {
			CommonDialog.alertDisplay(3, "아이디 생성 가능", "사용 가능한 아이디", "회원가입을 계속 진행해주세요");
			btok.setDisable(false);
			txid.setDisable(true);
		}
		
	}
	
	//패스워드 일치 여부 키이벤트
	public void pwcheckAction(KeyEvent event) {
		if(txpw2.getText().equals(txpw1.getText())) {
			lbpwcheck.setText("비밀번호가 일치합니다");
			lbpwcheck.setStyle("-fx-text-fill : green");
		}else if(!txpw2.getText().equals(txpw1.getText())) {
			lbpwcheck.setText("동일한 비밀번호를 입력해주세요");
			lbpwcheck.setStyle("-fx-text-fill : red");
		}
	}
	
	//확인버튼 액션이벤트
	public void OkAction(ActionEvent event) throws SQLException, IOException{
//		LocalDate localDate = LocalDate.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
//		String formattedString = localDate.format(formatter);
//		
		if(txid.getText().isEmpty() || txpw1.getText().isEmpty() || txpw2.getText().isEmpty() || txname.getText().isEmpty()
				||txphone.getText().isEmpty()||cbyear.getValue().isEmpty() || cbmonth.getValue().isEmpty() || cbday.getValue().isEmpty()) {
			//미입력한 부분이 있다면 경고창
			CommonDialog.alertDisplay(1, "미입력", "미입력", "내용을 모두 입력하세요");
		}else {
			//
			String NameInfo = txname.getText();
			String IDInfo = txid.getText();
			String PW1Info = txpw1.getText();
			String PW2Info = txpw2.getText();
			String GenderInfo = "";
			if(rbman.isSelected()) {
				GenderInfo = rbman.getText();
			}else if(rbwoman.isSelected()) {
				GenderInfo = rbwoman.getText();
			}
			String PhoneInfo = txphone.getText();
			String BirthInfo = cbyear.getValue() + "-" + cbmonth.getValue() + "-" +cbday.getValue();
			
			
			if(txpw2.getText().equals(txpw1.getText())) {
				AdminVO mvo = new AdminVO(IDInfo, PW1Info, NameInfo, GenderInfo, PhoneInfo, BirthInfo);
				AdminDAO adminDAO = new AdminDAO();
				int count = 0;
				try {
					count = AdminDAO.getAdminAdd(mvo);
				}catch(Exception e1) {
					CommonDialog.alertDisplay(1, "SUC오류", "SUC오류", "SUC오류");
				}
				//회원가입이 완료되면 완료 알림창
				CommonDialog.alertDisplay(3, "완료", "회원가입 완료", "회원가입이 완료되었습니다.");
				//확인버튼 누르면 회원가입창 닫음
				((Stage) btok.getScene().getWindow()).close();
			}else if(!txpw2.getText().equals(txpw1.getText())) {
				CommonDialog.alertDisplay(1, "잘못된 입력", "잘못된 입력", "동일한 비밀번호를 입력하세요");
			}
		}
	}
	//취소버튼 액션이벤트
	public void CancelAction(ActionEvent event){
		//취소 누르면 회원가입창 닫음
		((Stage) btcancel.getScene().getWindow()).close();
	}
}
