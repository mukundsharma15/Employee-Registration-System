package javaProject;

import javax.swing.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.nio.file.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EmployeeDetails implements ActionListener, Runnable, FocusListener {

	JFrame f;
	JPanel p;
	JButton bsave, bdelete, bupdate, bexit, bclear,badd,bshowall;
	JLabel lid, lname, laddress, lphone, lheading, ldate, ltime, lmsg,lphoto;
	JTextField tid, tname, taddress, tphone, tftime;
	JComboBox cb;

	Connection con;
	ResultSet rs;
	PreparedStatement ps;

	public void run() {
		while (true) {
			Date d = new Date();
			if (d.getHours() < 12)
				tftime.setText(d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + " AM");
			else
				tftime.setText(d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + " PM");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
	}

	EmployeeDetails() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		f = new JFrame("Employee Registration Form");
		p = new JPanel();
		p.setLayout(null);
		f.add(p);

		// label
		lid = new JLabel("Employee Id");
		lname = new JLabel("Employee Name");
		laddress = new JLabel("Employee Address");
		lphone = new JLabel("Employee Phone");
		lheading = new JLabel("Registration Form");
		lheading.setForeground(Color.BLACK);
		lheading.setFont(new Font("Lucida Handwriting", Font.BOLD, 15));
		lphoto = new JLabel(" *---- Image ----* ");
		//lphoto.setEditable(false);
		
		Date d = new Date();
		ldate = new JLabel("Date:");
		ldate.setText("Date: " + d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900));
		ldate.setFont(new Font("Lucida Handwriting", Font.PLAIN, 10));
		ltime = new JLabel("Time: ");
		lmsg = new JLabel("Message:");

		// textview
		tid = new JTextField(30);
		tid.addFocusListener(this);
		// tid.setFont(new Font("Coronet",Font.BOLD,15));
		tname = new JTextField(30);
		// tname.setFont(new Font("Coronet",Font.BOLD,15));
		taddress = new JTextField(30);
		// taddress.setFont(new Font("Coronet",Font.BOLD,15));
		tphone = new JTextField(30);
		// tphone.setFont(new Font("Coronet",Font.BOLD,15));
		tftime = new JTextField(20);
		tftime.setEditable(false);
		
		

		// ComboBox
		cb = new JComboBox();
		cb.addActionListener(this);

		// button
		bsave = new JButton("Save");
		bsave.addActionListener(this);
		bupdate = new JButton("Update");
		bupdate.addActionListener(this);
		bdelete = new JButton("Delete");
		bdelete.addActionListener(this);
		bclear = new JButton("Clear");
		bclear.addActionListener(this);
		bexit = new JButton("Exit");
		bexit.addActionListener(this);
		badd = new JButton("Browse");
		badd.addActionListener(this);
		bshowall = new JButton("Show All Data");
		bshowall.addActionListener(this);

		// setBounds
		lheading.setBounds(130, 10, 300, 25);
		badd.setBounds(420,20,150,25);
		lphoto.setBounds(420,65,150,125);
		ldate.setBounds(10, 10, 110, 30);

		lid.setBounds(20, 50, 80, 20);
		cb.setBounds(180, 50, 100, 20);
		tid.setBounds(120, 50, 60, 20);
		ltime.setBounds(300, 50, 80, 20);
		tftime.setBounds(340, 50, 80, 20);

		lname.setBounds(20, 80, 80, 20);
		tname.setBounds(120, 80, 150, 20);
		laddress.setBounds(20, 110, 80, 20);
		taddress.setBounds(120, 110, 150, 20);
		lphone.setBounds(20, 140, 80, 20);
		tphone.setBounds(120, 140, 150, 20);
		bsave.setBounds(20, 190, 80, 20);
		bupdate.setBounds(110, 190, 80, 20);
		bdelete.setBounds(200, 190, 80, 20);
		bclear.setBounds(290, 190, 80, 20);
		bexit.setBounds(380, 190, 80, 20);
		bshowall.setBounds(480,240,100,20);
		lmsg.setBounds(20, 300, 1000, 20);

		p.add(lid);
		p.add(badd);
		p.add(lphoto);
		p.add(tid);
		p.add(lname);
		p.add(tname);
		p.add(laddress);
		p.add(taddress);
		p.add(lphone);
		p.add(tphone);
		p.add(cb);
		p.add(bsave);
		p.add(bupdate);
		p.add(bdelete);
		p.add(bexit);
		p.add(bclear);
		p.add(bshowall);
		p.add(lheading);
		p.add(ldate);
		p.add(ltime);
		p.add(tftime);
		p.add(lmsg);
		updateCombo();
		clear();
		f.setSize(600, 400);
		f.setLocation(200, 100);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void focusLost(FocusEvent fe)
	{
		String eid = tid.getText();
		try
		{
			con = getConnection();
			ps = con.prepareStatement("Select * from employees");
			rs = ps.executeQuery();
			while(rs.next())
			{
				if(eid.equals(rs.getString("id")))
				{
					tid.setText(rs.getString("id"));
					tname.setText(rs.getString("name"));
					taddress.setText(rs.getString("address"));
					tphone.setText(rs.getString("phone"));
				}
			}
			lmsg.setText("Message: " +eid+ "Successfully shown");
		}catch(Exception e)
		{
			
		}
	}
	
	public void focusGained(FocusEvent fe)
	{
		
	}

	public void clear() {
		tid.setText("");
		tname.setText("");
		taddress.setText("");
		tphone.setText("");
		lmsg.setText("Message: " + " ");
	}

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/java", "root", "root");
		} catch (Exception e) {
		}
		return con;
	}
	
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(lphoto.getWidth(), lphoto.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	public void updateCombo() {
		try {
			con = getConnection();
			ps = con.prepareStatement("select id from employees");
			rs = ps.executeQuery();
			cb.removeAllItems();
			while (rs.next()) {
				cb.addItem(rs.getString("id"));
			}
		} catch (Exception e) {
			lmsg.setText("Message :" + e);
		}

	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == bsave) {
			boolean value = false;
			String id = tid.getText();
			String name = tname.getText();
			String address = taddress.getText();
			String phone = tphone.getText();

			try {

				con = getConnection();
				ps = con.prepareStatement("select id from employees");
				rs = ps.executeQuery();

				while (rs.next()) {
					if (id.equals(rs.getString("id"))) {
						value = true;
					}
				}

				if (value) {
					lmsg.setText("Message: " + id + "already exist");

				}

				else {
					ps = con.prepareStatement("insert into employees values (?, ?, ?, ?)");
					ps.setString(1, id);
					ps.setString(2, name);
					ps.setString(3, address);
					ps.setInt(4, Integer.parseInt(phone));
					ps.executeUpdate();
					updateCombo();
					clear();
					lmsg.setText("Message:" + id + " inserted successfully");
				}
			} catch (Exception e) {
				// System.out.println(e);
				lmsg.setText("Message :  " + e);
			}
		}

		else if (ae.getSource() == cb) {

			String eid = (String) cb.getSelectedItem();
			int i = cb.getSelectedIndex();

			try {
				con = getConnection();
				ps = con.prepareStatement("select * from employees");
				rs = ps.executeQuery();

				while (rs.next()) {
					if (eid.equals(rs.getString("id"))) {
						tid.setText(rs.getString("id"));
						tname.setText(rs.getString("name"));
						taddress.setText(rs.getString("address"));
						tphone.setText(rs.getString("phone"));
					}
				}
				updateCombo();
				cb.setSelectedIndex(i);
				lmsg.setText("Message :" + eid + " data successfully shown");
			} catch (Exception e) {
				lmsg.setText("Message :" + e);
			}

		}
		
		
		else if(ae.getSource()==bdelete)
		{
			String eid = (String) cb.getSelectedItem();
			try {
				con = getConnection();
				ps = con.prepareStatement("delete from employees where id =?");
				ps.setString(1, eid);
				ps.executeUpdate();

				updateCombo();
				clear();
				lmsg.setText("Message :" + eid + "Data successfully Deleted");
		}catch(Exception e)
			{
				lmsg.setText("Message :" +e);
			}
		}
		
		else if(ae.getSource()==bupdate)
		{
			String eid = (String) cb.getSelectedItem();
			String id = tid.getText();
			String name = tname.getText();
			String address = taddress.getText();
			String phone = tphone.getText();
			boolean value = false;
			
			try 
			{
				con = getConnection();
				ps = con.prepareStatement("select * from employees");
				rs = ps.executeQuery();
				
				if(value)
				{
					
				}else {
				ps = con.prepareStatement("update employees set name =?, address =?, phone =? where id=?");
				ps.setString(1, id);
				ps.setString(2, name);
				ps.setString(3, address);
				ps.setInt(4, Integer.parseInt(phone));
				ps.executeUpdate();
				}
				
				//clear();
				//updateCombo();
				lmsg.setText("Message :" +id+ "Update successfully");
				
			}catch(Exception e)
			{
				lmsg.setText("Message :" +e );
			}
		}

		else if (ae.getSource() == bclear) {
			clear();

		}

		else if (ae.getSource() == bexit) {
			System.exit(0);
		}

		else if (ae.getSource()== badd)
		{
			JFileChooser file = new JFileChooser();
			file.setCurrentDirectory(new File(System.getProperty("user.home")));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images","jpg","gif","png");
			file.addChoosableFileFilter(filter);
			int result = file.showSaveDialog(null);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = file.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				lphoto.setIcon(ResizeImage(path));
				lmsg.setText("Message :" + "Image Successfully shown");
			}
		
			else if(result == JFileChooser.CANCEL_OPTION) {
				lmsg.setText("Message: " + "No Image File Selected");
			}
		}
		
		else if(ae.getSource()==bshowall)
		{
			GetTableData data = new GetTableData();
		}
		
	}
	
	

	public static void main(String[] args) {

		EmployeeDetails e = new EmployeeDetails();
		Thread t = new Thread(e);
		t.start();
	}

}
