package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class AdminloginController {
	@FXML TextField IdTextField;
	@FXML PasswordField PwPasswordField;
	@FXML Button ClearButton, CloseButton, LoginButton;
	
	@FXML
	private void ClearButttonAction(ActionEvent event) {
		IdTextField.setText("");
		PwPasswordField.setText("");
	}
	
	
	@FXML
	private void CloseButtonAction(ActionEvent event) {
		Stage stage = new Stage();
		stage = (Stage)CloseButton.getScene().getWindow();
		stage.close();	
	}
	
	
	
	@FXML
	private void LoginButtonAction(ActionEvent event) {
		if(IdTextField.getText().isEmpty()||PwPasswordField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("아이디와 비번을 둘다 입력");
			alert.show();
		}else {
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConnection();
			
			String sql = "select admin_id, admin_password"
					+ " from admin_accounts"
					+ " where admin_id = ?"
					+ " and admin_password = ?";
			
			try {
				PreparedStatement ps = conn2.prepareStatement(sql);
				
				ps.setString(1, IdTextField.getText());
				ps.setString(2, PwPasswordField.getText());
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					try {
						Parent root = FXMLLoader.load(getClass().getResource("Admindb.fxml"));
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("로그인 실패");
					alert.show();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
