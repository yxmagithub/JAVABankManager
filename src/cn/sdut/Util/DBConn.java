package cn.sdut.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	public static Connection getConnnection() {
		  Connection conn = null;
		  @SuppressWarnings("unused")
		Statement stmt = null;
		  @SuppressWarnings("unused")
		ResultSet rs = null;
		  String url = null;
		  String user = null;
		  String password = null;
		  @SuppressWarnings("unused")
		String sql = null;
		try {
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			Class.forName("com.mysql.jdbc.Driver"); //����mysq����
			  } 
		catch (ClassNotFoundException e) {
			   System.out.println("�������ش���");
			   e.printStackTrace();//��ӡ������ϸ��Ϣ
			  }
			  try {
			   url = 
					   "jdbc:mysql://localhost:3306/myfirstsqldb?user=root&password=root&useUnicode=true&&characterEncoding=latin1&autoReconnect=true&relaxAutoCommit=true&zeroDateTimeBehavior=convertToNull";
					   //"jdbc:mysql://192.168.3.110/bank?user=bank&password=admin&useUnicode=true&&characterEncoding=gb2312&autoReconnect=true&relaxAutoCommit=true&zeroDateTimeBehavior=convertToNull";//��д����url = "jdbc:myqsl://localhost:3306/test(���ݿ���)? user=root(�û�)&password=yqs2602555(����)";
			   user = "root";
			   password = "root";
			   conn = DriverManager.getConnection(url,user,password);
			   System.out.println("���ݿ������ӣ�����");
			  } catch (SQLException e) {
			   System.out.println("���ݿ����Ӵ���");
			   e.printStackTrace();
			  }
		
		return conn;
	}
}
