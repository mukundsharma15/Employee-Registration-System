package javaProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Employees implements ActionListener, FocusListener {
	JLabel ltitle, ldate, ltime, leid, lename, leadd, lphone, lmsg;
	JTextField tftime, tfeid, tfename, tfeadd, tfephone;
	JComboBox cb;
	JButton binsert, bupdate, bdelete, bclear, bexit, bshow;
	JFrame f;
	JPanel p;
	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	Employees() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		f = new JFrame("employee Details");
		p = new JPanel();
		f.add(p);
		p.setLayout(null);
		// label
		ltitle = new JLabel("Employee Details");
		ldate = new JLabel("Date: ");
		ltime = new JLabel("Time: ");
		leid = new JLabel("Employee Id");
		lename = new JLabel("Employee Name");
		leadd = new JLabel("Employee Address");
		lphone = new JLabel("Employee Phone");
		lmsg = new JLabel("Message: ");
		// text field
		tftime = new JTextField(20);
		tfeid = new JTextField(20);
		tfeid.addFocusListener(this);
		tfename = new JTextField(20);
		tfeadd = new JTextField(20);
		tfephone = new JTextField(20);
		// combo box
		cb = new JComboBox();
		cb.addActionListener(this);
		// button
		binsert = new JButton("Save");
		binsert.addActionListener(this);
		bupdate = new JButton("Update");
		bupdate.addActionListener(this);
		bdelete = new JButton("delete");
		bdelete.addActionListener(this);
		bclear = new JButton("clear");
		bclear.addActionListener(this);
		bexit = new JButton("exit");
		bexit.addActionListener(this);
		bshow = new JButton("All Records");
		bshow.addActionListener(this);
		// layouts
		ltitle.setBounds(200, 20, 200, 20);
		ldate.setBounds(20, 50, 100, 20);
		ltime.setBounds(200, 50, 60, 20);
		tftime.setBounds(270, 50, 100, 20);
		leid.setBounds(20, 80, 100, 20);
		tfeid.setBounds(130, 80, 70, 20);
		cb.setBounds(210, 80, 70, 20);
		lename.setBounds(20, 110, 100, 20);
		tfename.setBounds(130, 110, 150, 20);
		leadd.setBounds(20, 140, 100, 20);
		tfeadd.setBounds(130, 140, 150, 20);
		lphone.setBounds(20, 170, 100, 20);
		tfephone.setBounds(130, 170, 150, 20);
		binsert.setBounds(20, 220, 80, 20);
		bupdate.setBounds(110, 220, 80, 20);
		bdelete.setBounds(200, 220, 80, 20);
		bclear.setBounds(290, 220, 80, 20);
		bexit.setBounds(380, 220, 80, 20);
		bshow.setBounds(380, 40, 80, 20);
		lmsg.setBounds(20, 260, 400, 20);
		p.add(ltitle);
		p.add(ldate);
		p.add(ltime);
		p.add(tftime);
		p.add(leid);
		p.add(tfeid);
		p.add(cb);
		p.add(lename);
		p.add(tfename);
		p.add(leadd);
		p.add(tfeadd);
		p.add(lphone);
		p.add(tfephone);
		p.add(binsert);
		p.add(bupdate);
		p.add(bdelete);
		p.add(bclear);
		p.add(bexit);
		p.add(bshow);
		p.add(lmsg);
		updateCombo();
		clear();
		f.setSize(500, 400);
		f.setLocation(200, 100);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void focusLost(FocusEvent fe) {
		String id = tfeid.getText();
		try {
			con = getConn();
			ps = con.prepareStatement("select * from employees");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (id.equals(rs.getString("id"))) {
					tfeid.setText(rs.getString("id"));
					tfename.setText(rs.getString("name"));
					tfeadd.setText(rs.getString("address"));
					tfephone.setText(rs.getString("phone"));
				}
			}
			lmsg.setText("Message :" + id + " successfully shown");
		} catch (Exception e) {

		}
	}

	public void focusGained(FocusEvent fe) {

	}

	public Connection getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/java", "root", "root");
		} catch (Exception e) {

		}
		return con;
	}

	public void updateCombo() {
		try {
			con = getConn();
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

	public void clear() {
		tfeid.setText("");
		tfename.setText("");
		tfeadd.setText("");
		tfephone.setText("");
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == binsert) {
			boolean valid = false;
			String id = tfeid.getText();
			String name = tfename.getText();
			String address = tfeadd.getText();
			String ph = tfephone.getText();
			if (id.equals("")) {
				JOptionPane.showMessageDialog(f, "plz enter Employee Id");
			} else if (name.equals("")) {
				JOptionPane.showMessageDialog(f, "plz enter Employee name");
			} else if (address.equals("")) {
				JOptionPane.showMessageDialog(f, "plz enter Employee Address");
			} else if (ph.equals("")) {
				JOptionPane.showMessageDialog(f, "plz enter Employee Phone");
			} else {
				try {
					con = getConn();
					ps = con.prepareStatement("select id from employees");
					rs = ps.executeQuery();
					while (rs.next()) {
						if (id.equals(rs.getString("id"))) {
							valid = true;
						}
					}
					if (valid) {
						clear();
						lmsg.setText("Message :" + id + " already exist");
					} else {
						ps = con.prepareStatement("insert into employees values(?, ?, ?, ?)");
						ps.setString(1, id);
						ps.setString(2, name);
						ps.setString(3, address);
						ps.setInt(4, Integer.parseInt(ph));
						ps.executeUpdate();
						updateCombo();
						clear();
						JOptionPane.showMessageDialog(f, "" + id + " successfully inserted");
						lmsg.setText("Message :" + id + " successfully inserted");
					}
				}

				catch (Exception e) {
					lmsg.setText("Message :" + e);

				}
			}
		} else if (ae.getSource() == cb) {
			String eid = (String) cb.getSelectedItem();
			int i = cb.getSelectedIndex();

			try {
				con = getConn();
				ps = con.prepareStatement("select * from employees");
				rs = ps.executeQuery();

				while (rs.next()) {
					if (eid.equals(rs.getString("id"))) {
						tfeid.setText(rs.getString("id"));
						tfename.setText(rs.getString("name"));
						tfeadd.setText(rs.getString("address"));
						tfephone.setText(rs.getString("phone"));
					}
				}
				updateCombo();
				cb.setSelectedIndex(i);
				lmsg.setText("Message :" + eid + " data successfully shown");
			} catch (Exception e) {
				lmsg.setText("Message :" + e);
			}
		} else if (ae.getSource() == bdelete) {
			String eid = (String) cb.getSelectedItem();
			try {
				con = getConn();
				ps = con.prepareStatement("delete from employees where id=?");
				ps.setString(1, eid);
				ps.executeUpdate();

				updateCombo();
				clear();
				lmsg.setText("Message :" + eid + " data successfully deleted");
			} catch (Exception e) {
				lmsg.setText("Message :" + e);
			}
		} else if (ae.getSource() == bshow) {
			// new TableExample();
		} else if (ae.getSource() == bclear) {
			clear();
		} else if (ae.getSource() == bexit) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {

		new Employees();

	}

}
