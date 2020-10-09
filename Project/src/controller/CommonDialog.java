package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class CommonDialog {
	//����� ó�� �޼ҵ�
	public static void alertDisplay(int type, String title, String header, String content){
		Alert alert = null;
		switch(type) {
			case 1: //���â
				alert = new Alert(AlertType.WARNING);
				break;
			case 2: //Ȯ��â
				alert = new Alert(AlertType.CONFIRMATION);
				break;
			case 3: //����â
				alert = new Alert(AlertType.INFORMATION);
				break;
			case 4: //����â
				alert = new Alert(AlertType.ERROR);
				break;
		}
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.setResizable(false);
		alert.showAndWait();
		return;
	}
}
