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
		//������� ����
		btok.setDisable(true);
		birthInit();
	}
	//������� �޺��ڽ�����
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
	
	
	//���̵� �ߺ�Ȯ�� �׼��̺�Ʈ
	public void IDcheckAction(ActionEvent event) {
		try {
			String mdo = AdminDAO.getAdminIDCheck(txid.getText());
			if(txid.getText().equals("")) {
				CommonDialog.alertDisplay(1, "���̵� �Է� ����", "���̵� ���Է�", "���̵� �Է��ϼ���");
			}else if(mdo.equals(txid.getText())) {
				CommonDialog.alertDisplay(1, "���̵� �Է� ����", "�ߺ��� ���̵�", "�̹� ������� ���̵��Դϴ�");
			}
		}catch(Exception e) {
			CommonDialog.alertDisplay(3, "���̵� ���� ����", "��� ������ ���̵�", "ȸ�������� ��� �������ּ���");
			btok.setDisable(false);
			txid.setDisable(true);
		}
		
	}
	
	//�н����� ��ġ ���� Ű�̺�Ʈ
	public void pwcheckAction(KeyEvent event) {
		if(txpw2.getText().equals(txpw1.getText())) {
			lbpwcheck.setText("��й�ȣ�� ��ġ�մϴ�");
			lbpwcheck.setStyle("-fx-text-fill : green");
		}else if(!txpw2.getText().equals(txpw1.getText())) {
			lbpwcheck.setText("������ ��й�ȣ�� �Է����ּ���");
			lbpwcheck.setStyle("-fx-text-fill : red");
		}
	}
	
	//Ȯ�ι�ư �׼��̺�Ʈ
	public void OkAction(ActionEvent event) throws SQLException, IOException{
//		LocalDate localDate = LocalDate.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
//		String formattedString = localDate.format(formatter);
//		
		if(txid.getText().isEmpty() || txpw1.getText().isEmpty() || txpw2.getText().isEmpty() || txname.getText().isEmpty()
				||txphone.getText().isEmpty()||cbyear.getValue().isEmpty() || cbmonth.getValue().isEmpty() || cbday.getValue().isEmpty()) {
			//���Է��� �κ��� �ִٸ� ���â
			CommonDialog.alertDisplay(1, "���Է�", "���Է�", "������ ��� �Է��ϼ���");
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
					CommonDialog.alertDisplay(1, "SUC����", "SUC����", "SUC����");
				}
				//ȸ�������� �Ϸ�Ǹ� �Ϸ� �˸�â
				CommonDialog.alertDisplay(3, "�Ϸ�", "ȸ������ �Ϸ�", "ȸ�������� �Ϸ�Ǿ����ϴ�.");
				//Ȯ�ι�ư ������ ȸ������â ����
				((Stage) btok.getScene().getWindow()).close();
			}else if(!txpw2.getText().equals(txpw1.getText())) {
				CommonDialog.alertDisplay(1, "�߸��� �Է�", "�߸��� �Է�", "������ ��й�ȣ�� �Է��ϼ���");
			}
		}
	}
	//��ҹ�ư �׼��̺�Ʈ
	public void CancelAction(ActionEvent event){
		//��� ������ ȸ������â ����
		((Stage) btcancel.getScene().getWindow()).close();
	}
}
