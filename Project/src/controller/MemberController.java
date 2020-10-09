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
		cbcharge.setItems(FXCollections.observableArrayList("1000원 1시간", "2000원 2시간", "3000원 3시간", "5000원 6시간", "10000원 12시간"));
	}

	// 회원테이블 관리
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

	// 전체 회원 데이터베이스 불러와서 표시
	public void totalMemberList() {
		ArrayList<MemberVO> list = null;
		MemberDAO memberDAO = new MemberDAO();
		MemberVO mvo = null;
		list = memberDAO.getMemberTotal();
		System.out.println(list);
		if (list == null) {
			CommonDialog.alertDisplay(1, "경고", "db가져오기 오류", "db가져오기 오류");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) { // list.size() -> list의 들어있는 원소의 개수
				mvo = list.get(i);
				memberdata.add(mvo);
			}
		}
	}

	// 회원검색
	public void MemberSearchAction(ActionEvent event) {
		try {
			ArrayList<MemberVO> list = new ArrayList<MemberVO>();
			MemberDAO mdo = new MemberDAO();
			list = mdo.getMemberSearch(txsearch.getText());
			if (list == null) {
				CommonDialog.alertDisplay(1, "검색오류", "검색오류", "검색오류");
			}
			memberdata.removeAll(memberdata);
			for (MemberVO mvo : list) {
				memberdata.add(mvo);
			}
		} catch (Exception e) {
			CommonDialog.alertDisplay(1, "검색실패", "검색 결과 없음", "다시 확인해주세요");
		}
	}

	// 회원삭제
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
			CommonDialog.alertDisplay(1, "삭제실패", "회원삭제 오류", "회원을 선택해주세요");
		}
		editdelete = false;

	}

	// 요금충전
	public void ChargeAction(ActionEvent event) {
		int prepaidmoney=0;
		int countdownseconds=0;
		try {
			editdelete = true;
			//테이블뷰에서 선택
			selectedindex = tableview.getSelectionModel().getSelectedIndex();
			selectedmember = tableview.getSelectionModel().getSelectedItems();
			String cb = cbcharge.getValue();
			int index = cb.indexOf("원");
			
			prepaidmoney = Integer.valueOf(cb.substring(0,index));
			
			System.out.println("선택된 아이디 = " + selectedmember.get(0).getId());
			System.out.println("선택된 요금제 = " + prepaidmoney);
			
			if(prepaidmoney == 1000) { //1000원 1시간
				countdownseconds = 3600; 
			}else if(prepaidmoney == 2000) { //2000원 2시간
				countdownseconds = 7200;
			}else if(prepaidmoney == 3000) { //3000원 3시간
				countdownseconds = 10800;
			}else if(prepaidmoney == 5000) { //5000원 6시간
				countdownseconds = 21600;
			}else if(prepaidmoney == 10000) { //10000원 12시간
				countdownseconds = 43200;
			}
			ChargeDAO chargeDAO = new ChargeDAO();
			chargeDAO.memberCharge(prepaidmoney, countdownseconds, selectedmember.get(0).getId());
			CommonDialog.alertDisplay(3, "충전", "충전 완료", cbcharge.getValue() + "이 충전되었습니다.");
			
		} catch (Exception e) {
			CommonDialog.alertDisplay(1, "충전실패", "충전 오류", "충전 오류");
		}
		editdelete = false;
	}
}
