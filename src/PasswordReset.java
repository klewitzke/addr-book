import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.Date;

import org.mindrot.BCrypt;
public class PasswordReset extends JFrame {
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	JFrame jf = new JFrame();
	JLabel usernameLabel = new JLabel(" Username");
	JLabel currentPasswordLabel = new JLabel(" Current Password");
	JLabel newPasswordLabel = new JLabel(" New Password");
	JLabel confirmPasswordLabel = new JLabel(" Confirm New Password");
	JTextField usernameField = new JTextField();
	JPasswordField currentPasswordField = new JPasswordField();
	JPasswordField newPasswordField = new JPasswordField();
	JPasswordField confirmPasswordField = new JPasswordField();
	JButton submit = new JButton("Save");
	JButton close = new JButton("Cancel");
	int usernameCount = 0;
	public PasswordReset() {
		usernameLabel.setForeground(fcolor);
		usernameLabel.setBackground(bgcolor);
		usernameLabel.setOpaque(true);
		usernameLabel.setPreferredSize(new Dimension(150, 20));
		currentPasswordLabel.setForeground(fcolor);
		currentPasswordLabel.setBackground(bgcolor);
		currentPasswordLabel.setOpaque(true);
		currentPasswordLabel.setPreferredSize(new Dimension(150, 20));
		newPasswordLabel.setForeground(fcolor);
		newPasswordLabel.setBackground(bgcolor);
		newPasswordLabel.setOpaque(true);
		newPasswordLabel.setPreferredSize(new Dimension(150, 20));
		confirmPasswordLabel.setForeground(fcolor);
		confirmPasswordLabel.setBackground(bgcolor);
		confirmPasswordLabel.setOpaque(true);
		confirmPasswordLabel.setPreferredSize(new Dimension(150, 20));
		usernameField.setPreferredSize(new Dimension(125, 20));
		currentPasswordField.setPreferredSize(new Dimension(125, 20));
		newPasswordField.setPreferredSize(new Dimension(125, 20));
		confirmPasswordField.setPreferredSize(new Dimension(125, 20));
		submit.setPreferredSize(new Dimension(125, 35));
		submit.addActionListener(new LoginListener());
		close.setPreferredSize(new Dimension(125, 35));
		close.addActionListener(new CloseListener());
		Container c1 = new Container();
		Container c2 = new Container();
		jf.add(c1, BorderLayout.CENTER);
		jf.add(c2, BorderLayout.PAGE_END);
		c1.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.ipadx = 5;
		c2.setLayout(new FlowLayout());
		c.gridx = 0; c.gridy = 0;
		c1.add(usernameLabel, c);
		c.gridx = 1; c.gridy = 0;
		c1.add(usernameField, c);
		c.gridx = 0; c.gridy = 1;
		c1.add(currentPasswordLabel, c);
		c.gridx = 1; c.gridy = 1;
		c1.add(currentPasswordField, c);
		c.gridx = 0; c.gridy = 2;
		c1.add(newPasswordLabel, c);
		c.gridx = 1; c.gridy = 2;
		c1.add(newPasswordField, c);
		c.gridx = 0; c.gridy = 3;
		c1.add(confirmPasswordLabel, c);
		c.gridx = 1; c.gridy = 3;
		c1.add(confirmPasswordField, c);
		c2.add(submit);
		c2.add(close);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.pack();
		jf.setLocationRelativeTo(null);
	}

	class LoginListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	    	Connection 	conn;
			Statement	stmt;
			ResultSet	rs;
			try{
				String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("Select Count (*) as usernameCount from Users where Username = '"+usernameField.getText()+"'");
				while(rs.next()){
					usernameCount = (rs.getInt("usernameCount"));
				}
				
				if (usernameCount == 0){
					JOptionPane.showMessageDialog(null, "Invalid username/password combination", null, JOptionPane.ERROR_MESSAGE);
				} else {
					rs = stmt.executeQuery("Select * from Users where Username = '"+usernameField.getText()+"'");
					
					while(rs.next()){
						if(rs.getBoolean("Active")==(true)){
							if(rs.getInt("Attempts_Remaining")>0){
								if(BCrypt.checkpw(currentPasswordField.getText().toString(), rs.getString("Password_Hash"))){
									if(!(currentPasswordField.getText().toString()).equals(newPasswordField.getText().toString())){
										if(newPasswordField.getText().toString().equals(confirmPasswordField.getText().toString())){
											String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
											if(newPasswordField.getText().toString().matches(pattern)){
												String hashed = BCrypt.hashpw(newPasswordField.getText().toString(), BCrypt.gensalt(10));
												stmt.execute("Update Users set Password_Hash = '" + hashed + "', Password_Expiry = DATE_ADD(NOW(),INTERVAL 90 DAY) where Username = '" + usernameField.getText() + "'");
												JOptionPane.showMessageDialog(null, "Your password has been reset.", null, JOptionPane.INFORMATION_MESSAGE);
												jf.dispose();
											} else {
												JOptionPane.showMessageDialog(null, "Your new password does not meet the minimum complexity requirements.", null, JOptionPane.WARNING_MESSAGE);
											}
										} else {
											JOptionPane.showMessageDialog(null, "New passwords do not match.", null, JOptionPane.ERROR_MESSAGE);
										}
									} else {
										JOptionPane.showMessageDialog(null, "Your new password must be different from your current password.", null, JOptionPane.WARNING_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null, "Invalid username/password combination", null, JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "Your account has been disabled.\nPlease contact a system administrator for assistance.", null, JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Your account has been disabled.\nPlease contact a system administrator for assistance.", null, JOptionPane.ERROR_MESSAGE);
						}
					}
				
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
										
		}
	}
	class CloseListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	jf.dispose();
	    }
	}
}