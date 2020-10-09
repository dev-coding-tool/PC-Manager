package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MemberVO;

public class MemberController implements Initializable {
	@FXML
	private TableView<MemberVO> tableview;
	@FXML
	private Button btcharge;
	@FXML
	private Button btdelete;
	@FXML
	private Button btsearch;
	@FXML
	private TextField txsearch;
	@FXML
	private ComboBox<String> cbcharge;
	@FXML
	private TableColumn<MemberVO, String> colid;
	@FXML
	private TableColumn<MemberVO, String> colname;
	@FXML
	private TableColumn<MemberVO, String> colgender;
	@FXML
	private TableColumn<MemberVO, String> colphone;
	@FXML
	private TableColumn<MemberVO, String> colbirth;
	@FXML
	private TableColumn<MemberVO, Boolean> colstatus;

	private ObservableList<MemberVO> memberdata;
	private int selectedindex;
	
	private ObservableList<MemberVO> selectedmember;

	private boolean editdelete = false;
	private LocalTime t;
	private int prepaidmoney;
	private int countdownseconds;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		memberTableViewSetting();
		cbcharge.setItems(FXCollections.observableArrayList("1000�� 1�ð�", "2000�� 2�ð�", "3000�� 3�ð�", "5000�� 6�ð�", "10000�� 12�ð�"));
	}

	// ȸ�����̺� ����
	public void memberTableViewSetting() {
		memberdata = FXCollections.observableArrayList();
		tableview.setEditable(true);

		colid.setCellValueFactory(new PropertyValueFactory<>("id"));
		colname.setCellValueFactory(new PropertyValueFactory<>("name"));
		colgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		colphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colbirth.setCellValueFactory(new PropertyValueFactory<>("birth"));
		colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		tableview.setItems(memberdata);
		// tableview.getColumns().addAll(colid, colname, colgender, colphone, colbirth,
		// colstatus);
		totalMemberList();
	}

	// ��ü ȸ�� �����ͺ��̽� �ҷ��ͼ� ǥ��
	public void totalMemberList() {
		ArrayList<MemberVO> list = null;
		MemberDAO memberDAO = new MemberDAO();
		MemberVO mvo = null;
		list = memberDAO.getMemberTotal();
		System.out.println(list);
		if (list == null) {
			CommonDialog.alertDisplay(1, "���", "db�������� ����", "db�������� ����");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) { // list.size() -> list�� ����ִ� ������ ����
				mvo = list.get(i);
				memberdata.add(mvo);
			}
		}
	}

	// ȸ���˻�
	public void MemberSearchAction(ActionEvent event) {
		try {
			ArrayList<MemberVO> list = new ArrayList<MemberVO>();
			MemberDAO mdo = new MemberDAO();
			list = mdo.getMemberSearch(txsearch.getText());
			if (list == null) {
				CommonDialog.alertDisplay(1, "�˻�����", "�˻�����", "�˻�����");
			}
			memberdata.removeAll(memberdata);
			for (MemberVO mvo : list) {
				memberdata.add(mvo);
			}
		} catch (Exception e) {
			CommonDialog.alertDisplay(1, "�˻�����", "�˻� ��� ����", "�ٽ� Ȯ�����ּ���");
		}
	}

	// ȸ������
	public void MemberDeleteAction(ActionEvent event) {
		try {
			editdelete = true;
			selectedindex = tableview.getSelectionModel().getSelectedIndex();
			selectedmember = tableview.getSelectionModel().getSelectedItems();

			ChargeDAO chargeDAO = new ChargeDAO();
			chargeDAO.getChargeDelete(selectedmember.get(0).getId());
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.getMemberDelete(selectedmember.get(0).getId());
			memberdata.removeAll(memberdata);
			totalMemberList();
		} catch (Exception e) {
			CommonDialog.alertDisplay(1, "��������", "ȸ������ ����", "ȸ���� �������ּ���");
		}
		editdelete = false;

	}

	// �������
	public void ChargeAction(ActionEvent event) {
		int prepaidmoney=0;
		int countdownseconds=0;
		try {
			editdelete = true;
			//���̺�信�� ����
			selectedindex = tableview.getSelectionModel().getSelectedIndex();
			selectedmember = tableview.getSelectionModel().getSelectedItems();
			String cb = cbcharge.getValue();
			int index = cb.indexOf("��");
			
			prepaidmoney = Integer.valueOf(cb.substring(0,index));
			
			System.out.println("���õ� ���̵� = " + selectedmember.get(0).getId());
			System.out.println("���õ� ����� = " + prepaidmoney);
			
			if(prepaidmoney == 1000) { //1000�� 1�ð�
				countdownseconds = 3600; 
			}else if(prepaidmoney == 2000) { //2000�� 2�ð�
				countdownseconds = 7200;
			}else if(prepaidmoney == 3000) { //3000�� 3�ð�
				countdownseconds = 10800;
			}else if(prepaidmoney == 5000) { //5000�� 6�ð�
				countdownseconds = 21600;
			}else if(prepaidmoney == 10000) { //10000�� 12�ð�
				countdownseconds = 43200;
			}
			ChargeDAO chargeDAO = new ChargeDAO();
			chargeDAO.memberCharge(prepaidmoney, countdownseconds, selectedmember.get(0).getId());
			CommonDialog.alertDisplay(3, "����", "���� �Ϸ�", cbcharge.getValue() + "�� �����Ǿ����ϴ�.");
			
		} catch (Exception e) {
			CommonDialog.alertDisplay(1, "��������", "���� ����", "���� ����");
		}
		editdelete = false;
	}
}
