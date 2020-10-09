package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class CommonDialog {
	//경고장 처리 메소드
	public static void alertDisplay(int type, String title, String header, String content){
		Alert alert = null;
		switch(type) {
			case 1: //경고창
				alert = new Alert(AlertType.WARNING);
				break;
			case 2: //확인창
				alert = new Alert(AlertType.CONFIRMATION);
				break;
			case 3: //정보창
				alert = new Alert(AlertType.INFORMATION);
				break;
			case 4: //오류창
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
