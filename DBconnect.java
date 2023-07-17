package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnect {

	public Connection DBconn;
	
	public Connection getConnection() {
		
		
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="cafe2";
		String password="cafe2";
		
		try {
			Class.forName(driver);
			DBconn=DriverManager.getConnection(url, user, password);
			System.out.println("디비 연결 성공-20230509");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("디비 연결 실패-20230509");
		}
		
		
		
		
		return DBconn;
		
		
		
	}
}
