package javaProject;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class GetTableData {

	private boolean status;
	JFrame frame;
	JPanel tableJPanel;
	JTable employeeTable;
		
	Connection con;
	ResultSet rs;
	PreparedStatement ps;

	public GetTableData()
	{
		//Creating window using JFrame
	     frame = new JFrame();
	     frame.setTitle("Employee Details Table");
	     frame.setSize(800,500);
	     
	     //Adding Table View
	     frame.add(getTablePanel());
	     
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setVisible(true);
	}
	
	public JPanel getTablePanel()
	{
		 tableJPanel = new JPanel();
		 tableJPanel.setLayout(new BorderLayout());
		 
		 //column Header
		 String[] column = { "Employee ID", "Employee Name", "Employee Address", "Employee Phone"};
		 
		 //getting Data for table from database
		 Object [][] data = getEmployeeDetails();
		 
		 //creating JTable object passing data and header
		 
		 
		  employeeTable = new JTable(data, column);
		 
		  tableJPanel.add(employeeTable.getTableHeader(), BorderLayout.NORTH);
		  tableJPanel.add(employeeTable, BorderLayout.CENTER);
		  
		  return tableJPanel;
	}
	
	public Object[][] getEmployeeDetails()
	{
		Object[][] data = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/java", "root", "root");
			ps = con.prepareStatement("select id, name, address, phone from employees");
			rs = ps.executeQuery();
			
			int rowCount = getRowCount(rs); //Row Count
			int columnCount = getColumnCount(rs); //Column Count
			
			data = new Object[rowCount][columnCount];
			
			//Starting from first row for Interation
			
			rs.beforeFirst();
			
			int i=0;
			
			while(rs.next()) {
				
				int j=0;
				
				data[i][j++] = rs.getInt("id");
				data[i][j++] = rs.getString("name");
				data[i][j++] = rs.getString("address");
				data[i][j++] = rs.getInt("phone");
				
				i++;
			}
			
			status = true;
			
			//Closing the Resources;
			
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	//Method to get Row Count From ResultSet Object
	
	public int getRowCount(ResultSet rs) {
		
		try {
			
			if(rs !=null) {
				rs.last();
				return rs.getRow();
			}
			
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// Method to get Column Count from ResultSet Object
	
	public int getColumnCount(ResultSet rs) {
		try {
				if(rs!=null)
					return rs.getMetaData().getColumnCount();
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	
	public String toString() {
		return (status) ? "Data Listed Successfully" : "Application Error Occured";
	}
	
	public static void main(String[] args) {
		
		
		GetTableData getData = new GetTableData();
		System.out.println(getData);
	}

}
