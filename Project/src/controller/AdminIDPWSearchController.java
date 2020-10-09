package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminIDPWSearchController implements Initializable{
	@FXML
	private TextField txinputname1;
	@FXML
	private TextField txinputphone1;
	@FXML
	private TextField txinputid;
	@FXML
	private TextField txinputname2;
	@FXML
	private TextField txinputphone2;
	@FXML
	private Button btidsearch;
	@FXML
	private Button btpwsearch;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	//ID찾기 버튼
	public void IDSearchAction(ActionEvent event) throws Exception{
		String foundid = null;
		String name = txinputname1.getText();
		String phone = txinputphone1.getText();
		try {
			foundid = AdminDAO.getFoundID(name, phone);
			if(foundid == null) {
				CommonDialog.alertDisplay(1, "ID찾기 실패", "아이디가 존재하지 않습니다", "다시 입력해주세요");
			}else{
				CommonDialog.alertDisplay(3,  "ID찾기 성공", "ID를 찾았습니다.", name + "님의 아이디는 '" + foundid  + "' 입니다");
			}
			}catch(Exception e) {}
		//CommonDialog.alertDisplay(1, "db연결 오류", "db연결 오류", "db연결 오류");
	}
	//PW찾기 버튼
	public void PWSearchAction(ActionEvent event) throws Exception{
		String foundpw = null;
		String id = txinputid.getText();
		String name = txinputname2.getText();
		String phone = txinputphone2.getText();
		try {
			foundpw = AdminDAO.getFoundPW(id ,name, phone);
			if(foundpw == null) {
				CommonDialog.alertDisplay(1, "PW찾기 실패", "잘못된 입력", "다시 입력해주세요");
			}else{
				CommonDialog.alertDisplay(3,  "PW찾기 성공", "PW를 찾았습니다", "비밀번호는 '" + foundpw  + "' 입니다");
			}
		}catch(Exception e) {
		//CommonDialog.alertDisplay(1, "db연결 오류", "db연결 오류", "db연결 오류");
		}
	}
}
