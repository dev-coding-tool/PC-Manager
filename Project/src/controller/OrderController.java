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
	//���������̺��� ����
	private ObservableList<ItemVO> itemdata;
	//��ٱ���
	private ObservableList<ItemVO> orderdata;
	
	private int selectedindex;
	private ObservableList<ItemVO> selecteditem;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		menuTableviewSetting();
	}
	
	//�޴����̺�
	public void menuTableviewSetting() {
		itemdata = FXCollections.observableArrayList();
		tableviewmenu.setEditable(true);
		
		colitemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
		colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		tableviewmenu.setItems(itemdata);
		itempriceList();
	}
	
	//��ǰ��, ���� �ҷ��ͼ� ǥ��
	public void itempriceList() {
		ArrayList<ItemVO> list = null;
		ItemDAO itemDAO = new ItemDAO();
		ItemVO ivo = null;
		list = itemDAO.getItemNamePrice();
		System.out.println(list);
		
		if(list == null) {
			CommonDialog.alertDisplay(1, "���", "db�������� ����", "db�������� ����");
			return;
		}else {
			for(int i=0; i<list.size(); i++) {
				ivo = list.get(i);
				itemdata.add(ivo);
			}
		}
	}
	
	/*
	//��ٱ��� �߰���ư
	public void ItemAddAction(ActionEvent event) {
		try {
			selectedindex = tableviewmenu.getSelectionModel().getSelectedIndex();
			selecteditem = tableviewmenu.getSelectionModel().getSelectedItems();
			
			
			
		}catch(Exception e) {
			CommonDialog.alertDisplay(1, "��ٱ��� ����", "��ٱ��� ����", "��ٱ��� ����");
		}
	}
	*/
	
	
}
