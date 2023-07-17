package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdmindbController implements Initializable{

	int mcount1=0;
	int mcount2=0;
	int mcount3=0;
	int rs_sum=0;
	
	@FXML Button searchButton,datesearchButton,datesearch2Button,countButton, sumButton;
	
	@FXML DatePicker dateDatePicker,startDatePicker,endDatePicker; 	
	
	@FXML PieChart rsPieChart;
	
	@FXML TextArea resultTextArea;
	
	@FXML TableView<Order> orderlistTableView;
	
	@FXML TableColumn<Order, String> idxTableColumn;
	@FXML TableColumn<Order, String> dateTableColumn;
	@FXML TableColumn<Order, String> count1TableColumn;
	@FXML TableColumn<Order, String> count2TableColumn;
	@FXML TableColumn<Order, String> count3TableColumn;
	@FXML TableColumn<Order, String> sumTableColumn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idxTableColumn.setCellValueFactory(new PropertyValueFactory<>("idx"));
		dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		count1TableColumn.setCellValueFactory(new PropertyValueFactory<>("count1"));
		count2TableColumn.setCellValueFactory(new PropertyValueFactory<>("count2"));
		count3TableColumn.setCellValueFactory(new PropertyValueFactory<>("count3"));
		sumTableColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
	}
	
	@FXML
	public void datesearchButtonAction(ActionEvent event) {
		if(dateDatePicker.getValue()==null) {Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("날짜를 선택해주세요.");
		alert.show();
			
		}else {
			
			resultTextArea.setText(null);
			mcount1=0;
			mcount2=0;
			mcount3=0;
			rs_sum=0;
			
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConnection();	
			
			String sql ="select idx, order_time, count1, count2, count3, total"
					+ " from orderlist_accounts"
					+ " where to_char(order_time, 'yyyy-mm-dd')=?";
			
			try {
				PreparedStatement ps = conn2.prepareStatement(sql);
				
				ps.setString(1, (dateDatePicker.getValue()).toString());
				
				ResultSet rs = ps.executeQuery();
				
				ObservableList<Order> abcd = FXCollections.observableArrayList();
				
				while(rs.next()) {
					abcd.add(
							new Order(
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getString(6)
									)
							);
					mcount1 = mcount1 + Integer.parseInt(rs.getString(3));
					mcount2 = mcount2 + Integer.parseInt(rs.getString(4));
					mcount3 = mcount3 + Integer.parseInt(rs.getString(5));
					rs_sum= rs_sum + Integer.parseInt(rs.getString(6));
				}
				orderlistTableView.setItems(abcd);
				
				resultTextArea.setText("");
				resultTextArea.appendText("아메리카노: "+mcount1+"잔\n");
				resultTextArea.appendText("카푸치노: "+mcount2+"잔\n");
				resultTextArea.appendText("카페라떼: "+mcount3+"잔\n");
				resultTextArea.appendText("총합: "+rs_sum+"잔");
				
				conn2.close();
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	
	
	@FXML
	public void searchButtonAction(ActionEvent event) {
		
		resultTextArea.setText(null);
		mcount1=0;
		mcount2=0;
		mcount3=0;
		rs_sum=0;
		
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConnection();
		
		String sql="select idx, to_char(order_time, 'yyyy-mm-dd hh24:mi:ss'), count1, count2, count3, total"
				+ " from orderlist_accounts"
				+ " order by idx";
		
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			ObservableList<Order> dataList = FXCollections.observableArrayList(); 
			
			while(rs.next()) {
				dataList.add(
						new Order(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6)
								)
						);
				mcount1 = mcount1 + Integer.parseInt(rs.getString(3));
				mcount2 = mcount2 + Integer.parseInt(rs.getString(4));
				mcount3 = mcount3 + Integer.parseInt(rs.getString(5));
				rs_sum= rs_sum + Integer.parseInt(rs.getString(6));
			}
			orderlistTableView.setItems(dataList);
			
			resultTextArea.setText("");
			resultTextArea.appendText("아메리카노: "+mcount1+"잔\n");
			resultTextArea.appendText("카푸치노: "+mcount2+"잔\n");
			resultTextArea.appendText("카페라떼: "+mcount3+"잔\n");
			resultTextArea.appendText("총합: "+rs_sum+"잔");
			
			conn2.close();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void datesearch2ButtonAction(ActionEvent event) {
		//시작 날짜 또는 종료 날짜가 비어있으면 ==> 경고매세지
		//그 외에는 ==> 디비 접속 ==> sql문 작성 ==> 쿼리 실행
		if(startDatePicker.getValue()==null || endDatePicker.getValue()==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ecyce");
			alert.setContentText("날짜를 둘다 선택하고 조회하세요");
			alert.show();
		}else {
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConnection();
		String sql="select idx, order_time, count1, count2, count3, total"
				+ " from orderlist_accounts"
				+ " where order_time between ? and ?";
		PreparedStatement ps;
		try {
			ps = conn2.prepareStatement(sql);
			ps.setString(1, startDatePicker.getValue().toString());
			ps.setString(2, endDatePicker.getValue().plusDays(1).toString());
			
			ResultSet rs = ps.executeQuery();
			
			ObservableList<Order> aadd = FXCollections.observableArrayList();
			mcount1=0;
			mcount2=0;
			mcount3=0;
			rs_sum=0;
			resultTextArea.setText("");
			while(rs.next()) {
				aadd.add(
						new Order(
								rs.getString(1),rs.getString(2),rs.getString(3),
								rs.getString(4),rs.getString(5),rs.getString(6)
								)
						);
				mcount1 = mcount1 + Integer.parseInt(rs.getString(3));
				mcount2 = mcount2 + Integer.parseInt(rs.getString(4));
				mcount3 = mcount3 + Integer.parseInt(rs.getString(5));
				rs_sum = rs_sum + Integer.parseInt(rs.getString(6));
			}
			
			orderlistTableView.setItems(aadd);
			resultTextArea.appendText("아메리카노 : "+mcount1+"잔\n");
			resultTextArea.appendText("카푸치노 : "+mcount2+"잔\n");
			resultTextArea.appendText("카페라떼 : "+mcount3+"잔\n");
			resultTextArea.appendText("총가격 : "+rs_sum+"원");
			
				ps.close();
				rs.close();
				conn2.close();
			
			}
			
		 catch (SQLException e) {
			e.printStackTrace();
		 }
		}
		
	}
	
}
