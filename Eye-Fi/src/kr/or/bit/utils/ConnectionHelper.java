package kr.or.bit.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionHelper {
	private static Connection conn = null;
	
	public static Connection getConnection(String dbname) {
		
		if(dbname.equals("oracle")) {
			
			try {
				Context context = new InitialContext();// InitialContext : 현재 Web 프로젝트에서 이름 기반으로 검색
				DataSource ds =  (DataSource)context.lookup("java:comp/env/jdbc/oracle"); // java:comp/env/ + name : 자바의 설정환경에서 name을 찾겠다.
				conn = ds.getConnection();	
			} catch (Exception e) {
				System.out.println("connHelp : " + e.getMessage());
			}
			
		}else if(dbname.equals("mysql")) {
			
			try {
				Context context = new InitialContext();// InitialContext : 현재 Web 프로젝트에서 이름 기반으로 검색
				DataSource ds =  (DataSource)context.lookup("java:comp/env/jdbc/mysql"); // java:comp/env/ + name : 자바의 설정환경에서 name을 찾겠다.
				conn = ds.getConnection();
			} catch (Exception e) {
				System.out.println("connHelp : " + e.getMessage());
			}
		}  
		return conn;
	}
	
}
