package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Connection.DBConnection;

import Model.User;


public class UserDao {
	public static User checkEmail(String email) {
		User u = null;
		try {
			Connection connection = DBConnection.getConnection();
			String sqlString="select * from student where email=?";
			PreparedStatement pst = connection.prepareStatement(sqlString);
			pst.setString(1, email);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				u = new User();
				u.setUserid(rs.getInt("userid"));
				System.out.println("userid = "+rs.getInt("userid"));
				u.setFirstname(rs.getString("firstname"));
				u.setLastname(rs.getString("lastname"));
				u.setEmail(rs.getString("email"));
				u.setMobile(rs.getLong("mobile"));
				u.setAddress(rs.getString("address"));
				u.setGender(rs.getString("gender"));
				u.setPassword(rs.getString("password"));
				
			}
			System.out.println("user"+u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	public static void insertUser(User u) {
		
		try {
			Connection connection = DBConnection.getConnection();
			String sqlString = "insert into student(firstname,lastname,email,mobile,address,gender,password) values(?,?,?,?,?,?,?)";
		    PreparedStatement pst = connection.prepareStatement(sqlString);
			pst.setString(1, u.getFirstname());
			pst.setString(2, u.getLastname());
			pst.setString(3, u.getEmail());
			pst.setLong(4, u.getMobile());
			pst.setString(5, u.getAddress());
			pst.setString(6, u.getGender());
			pst.setString(7, u.getPassword());
		    pst.executeUpdate();
			System.out.println("data inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static User loginUser(User u) {
		User u1=null;
		try {
			Connection connection = DBConnection.getConnection();
			String sqlString = "select * from student where email=? and password=?";
			PreparedStatement pst = connection.prepareStatement(sqlString);
			pst.setString(1, u.getEmail());
			pst.setString(2, u.getPassword());
			ResultSet rSet= pst.executeQuery();
			if(rSet.next()) {
				u1=new User();
				u1.setUserid(rSet.getInt("userid"));
				u1.setFirstname(rSet.getString("firstname"));
				u1.setLastname(rSet.getString("lastname"));
				 u1.setEmail(rSet.getString("email"));
				u1.setMobile(rSet.getLong("mobile"));
				u1.setAddress(rSet.getString("address"));
				u1.setGender(rSet.getString("gender"));
				u1.setPassword(rSet.getString("password"));
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u1;
		}
	
public static boolean checkOldPassword(int userid,String op) {
	boolean flag = false;
	try {
		Connection connection = DBConnection.getConnection();
		String sqlString="select * from student where userid=? and password=?";
		PreparedStatement pst = connection.prepareStatement(sqlString);
		pst.setInt(1, userid);
		pst.setString(2, op);
		ResultSet rSet = pst.executeQuery();
		if(rSet.next()) {
			flag = true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
}
public static void changePasswrod(int userid,String np) {
	try {
		Connection connection = DBConnection.getConnection();
		String sqlString="update student set password=? where userid=?";
		PreparedStatement pst = connection.prepareStatement(sqlString);
		pst.setString(1, np);
		pst.setInt(2, userid);
		pst.executeUpdate();
		System.out.println("password changed");
	} catch (Exception e) {
		e.printStackTrace();
	}
}
public static boolean checkEmailfromDB(String email) {
	boolean flag=false;
	try {
		Connection connection = DBConnection.getConnection();
		String sqlString="select * from student where email=?";
		PreparedStatement pst = connection.prepareStatement(sqlString);
		pst.setString(1, email);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			flag=true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return flag;
}

public static void changeNewPassword(String email, String newp) {
	try {
		Connection connection = DBConnection.getConnection();
		String sqlString="update student set password=? where email=?";
		PreparedStatement pst = connection.prepareStatement(sqlString);
		pst.setString(1, newp);
		pst.setString(2, email);
		pst.executeUpdate();
		System.out.println("password changed");
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}

