package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemVO;

public class OrderController implements Initializable {
	@FXML
	private TableView<ItemVO> tableviewmenu;
	@FXML
	private TableView<ItemVO> tablevieworder;
	@FXML
	private TableColumn<ItemVO, String> colitemname;
	@FXML
	private TableColumn<ItemVO, String> colitemname2;
	@FXML
	private TableColumn<ItemVO, Integer> colprice;
	@FXML
	private TableColumn<ItemVO, Integer> colprice2;
	@FXML
	private TableColumn<ItemVO, Integer> colitemno;
	@FXML
	private TableColumn<ItemVO, Integer> colstock;
	//아이템테이블의 정보
	private ObservableList<ItemVO> itemdata;
	//장바구니
	private ObservableList<ItemVO> orderdata;
	
	private int selectedindex;
	private ObservableList<ItemVO> selecteditem;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		menuTableviewSetting();
	}
	
	//메뉴테이블
	public void menuTableviewSetting() {
		itemdata = FXCollections.observableArrayList();
		tableviewmenu.setEditable(true);
		
		colitemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
		colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		tableviewmenu.setItems(itemdata);
		itempriceList();
	}
	
	//상품명, 가격 불러와서 표시
	public void itempriceList() {
		ArrayList<ItemVO> list = null;
		ItemDAO itemDAO = new ItemDAO();
		ItemVO ivo = null;
		list = itemDAO.getItemNamePrice();
		System.out.println(list);
		
		if(list == null) {
			CommonDialog.alertDisplay(1, "경고", "db가져오기 오류", "db가져오기 오류");
			return;
		}else {
			for(int i=0; i<list.size(); i++) {
				ivo = list.get(i);
				itemdata.add(ivo);
			}
		}
	}
	
	/*
	//장바구니 추가버튼
	public void ItemAddAction(ActionEvent event) {
		try {
			selectedindex = tableviewmenu.getSelectionModel().getSelectedIndex();
			selecteditem = tableviewmenu.getSelectionModel().getSelectedItems();
			
			
			
		}catch(Exception e) {
			CommonDialog.alertDisplay(1, "장바구니 오류", "장바구니 오류", "장바구니 오류");
		}
	}
	*/
	
	
}
