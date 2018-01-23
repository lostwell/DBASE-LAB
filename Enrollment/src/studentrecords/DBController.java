package studentrecords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBController {
	private Connection connection;
	private Statement statement;
	private PreparedStatement ps;
	private ResultSet resultSet;
	private String sql;

	public void dbaseConnect(String url, String user, String pass) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, user, pass);
	}

	public void createAccount(int idno, 
			String lastname, 
			String firstname, 
			String midInitial, 
			String gender, 
			String contactno, 
			String email) throws Exception {

		sql = "INSERT INTO students "
				+ "(idno, lastname, firstname, midInitial, gender, contactno, email) "
				+ "values (?,?,?,?,?,?,?)";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, idno);
		ps.setString(2, lastname);
		ps.setString(3, firstname);
		ps.setString(4, midInitial);
		ps.setString(5, gender);
		ps.setString(6, contactno);
		ps.setString(7, email);
		ps.executeUpdate();
	}

	public void createSubject(String subjid,
			String title,
			int units) throws Exception {

		sql = "INSERT INTO subject "
				+ "(subjid, title, units) "
				+ "values (?,?,?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, subjid);
		ps.setString(2, title);
		ps.setInt(3, units);
		ps.executeUpdate();
	}

	public void createClass(String classcode,
			String time,
			String day,
			String subjid) throws Exception {
		sql = "INSERT INTO class "
				+ "(classcode, time, day, subjid) "
				+ "values (?,?,?,?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, classcode);
		ps.setString(2, time);
		ps.setString(3, day);
		ps.setString(4, subjid);
		ps.executeUpdate();
	}

	public void createEnroll(String classcode,
			int idno, String datesubmitted) throws Exception {

		sql = "INSERT INTO enroll "
				+ "(classcode, idno, datesubmitted) "
				+ "values (?,?,?)";
		ps = connection.prepareStatement(sql);
		ps.setString(1, classcode);
		ps.setInt(2, idno);
		ps.setString(3, datesubmitted);
		ps.executeUpdate();
	}


	//-------------------READ FUNCTIONALITY-----------------------------------------
	public ResultSet getSubjects() throws Exception {
		statement = connection.createStatement();
		sql = "SELECT * FROM subject;";
		return statement.executeQuery(sql);
	}

	public ResultSet getStudents() throws Exception {
		statement = connection.createStatement();
		sql = "SELECT * FROM students;";
		return statement.executeQuery(sql);
	}
	public ResultSet getClasses() throws Exception {
		statement = connection.createStatement();
		sql = "SELECT * FROM class;";
		return statement.executeQuery(sql);
	}
	public ResultSet getEnroll() throws Exception {
		statement = connection.createStatement();
		sql = "SELECT * FROM enroll;";
		return statement.executeQuery(sql);
	}

	//---------------------------UPDATE FUNCTIONALITY---------------------------------------------------
	// Gerichelle
	public void updateAccount(int idno,
			String lname,
			String fname,
			String midInitial,
			String gender,
			String contactNumber,
			String email) throws Exception {

		//execute a query (update preparedStatement)
		sql = "UPDATE students SET "
			+ "lastname = '"+ lname +"', "
			+ "firstname = '"+ fname +"', "
			+ "midInitial = '"+ midInitial +"', "
			+ "gender = '"+ gender +"', "
			+ "contactno = '"+ contactNumber +"', "
			+ "email = '"+ email +"' "
			+ "WHERE idno = "+ idno;
		statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	// Gerichelle
	public void updateSubject(String subjid,
			String title,
			int units) throws Exception {
		sql = "UPDATE subject SET "
				+ "title = '"+ title +"', "
				+ "units = "+ units +" "
				+ "WHERE subjid = '"+ subjid + "'";
		statement = connection.createStatement();
		statement.executeUpdate(sql);
	}  
	//Gerichelle, Shaira
	public void updateClass(String classcode, 
			String time,
			String days,
			String subjid) throws Exception {
		sql = "UPDATE class SET "
				+ "time = '"+ time +"', "
				+ "days = '"+ days +"', "
				+ "subjid = '"+ subjid +"' "
				+ "WHERE classcode = '"+ classcode +"'";
		statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	// Gerichelle
	public void updateEnroll(String classcode1, 
			String classcode2, 
			int idno, 
			String date) throws Exception {
		sql = "UPDATE enroll SET "
				+ "classcode = '"+ classcode2 +"' "
				+ "date = '"+ date +"' "
				+ "WHERE classcode = '"+ classcode1 +"' AND idno = "+ idno +"";
		statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	//-------------------------------------------Delete------------------------------------------------//
	public void deleteAccount(int idno) throws Exception { // Albert did this part
		sql = "delete FROM account WHERE idno = ?; ";
		ps = connection.prepareStatement(sql);
		ps.setInt(1, idno);
		ps.executeUpdate();
	}

	public void deleteSubject(String subjid)throws Exception { // Mitz Did this part
		sql = "delete FROM subjects WHERE subjid = ?; ";
		ps = connection.prepareStatement(sql);
		ps.setString(1, subjid);
		ps.executeUpdate(sql);
	}

	public void deleteClass(String classcode) throws Exception { // Albert did this part
		sql = "delete FROM class where classcode = ?; ";
		ps = connection.prepareStatement(sql);
		ps.setString(1, classcode);
		ps.executeUpdate();
	}

	//-------------------------Extra Feature---------------------------------------------//
	//retrieves the information of each student enrolled in a given class (Henry)
	public ResultSet getClassStudent(int classCode) throws Exception {
		statement = connection.createStatement();//creates a connection to the database in the server
		sql = "select * from students inner join enroll using(idno) where classcode="+(classCode)+";";//sql statement for query
		return statement.executeQuery(sql);//executes the given sql statement to the database
	}
	//retrieves the information of each class in a given subject (Henry)
	public ResultSet getSubjClass(int classCode) throws Exception{
		statement = connection.createStatement();//creates a connection to the database in the server
		sql = "select * from class inner join subject using(subjid);";//sql statement for query
		return statement.executeQuery(sql);//executes the given sql statement to the database
	}
	
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
