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
	//IDã�� ��ư
	public void IDSearchAction(ActionEvent event) throws Exception{
		String foundid = null;
		String name = txinputname1.getText();
		String phone = txinputphone1.getText();
		try {
			foundid = AdminDAO.getFoundID(name, phone);
			if(foundid == null) {
				CommonDialog.alertDisplay(1, "IDã�� ����", "���̵� �������� �ʽ��ϴ�", "�ٽ� �Է����ּ���");
			}else{
				CommonDialog.alertDisplay(3,  "IDã�� ����", "ID�� ã�ҽ��ϴ�.", name + "���� ���̵�� '" + foundid  + "' �Դϴ�");
			}
			}catch(Exception e) {}
		//CommonDialog.alertDisplay(1, "db���� ����", "db���� ����", "db���� ����");
	}
	//PWã�� ��ư
	public void PWSearchAction(ActionEvent event) throws Exception{
		String foundpw = null;
		String id = txinputid.getText();
		String name = txinputname2.getText();
		String phone = txinputphone2.getText();
		try {
			foundpw = AdminDAO.getFoundPW(id ,name, phone);
			if(foundpw == null) {
				CommonDialog.alertDisplay(1, "PWã�� ����", "�߸��� �Է�", "�ٽ� �Է����ּ���");
			}else{
				CommonDialog.alertDisplay(3,  "PWã�� ����", "PW�� ã�ҽ��ϴ�", "��й�ȣ�� '" + foundpw  + "' �Դϴ�");
			}
		}catch(Exception e) {
		//CommonDialog.alertDisplay(1, "db���� ����", "db���� ����", "db���� ����");
		}
	}
}
