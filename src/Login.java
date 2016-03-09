import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.mindrot.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
public class Login extends JFrame {
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	JFrame jf = new JFrame();
	JLabel usernameLabel = new JLabel(" Username");
	JLabel passwordLabel = new JLabel(" Password");
	JTextField usernameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton login = new JButton("Login");
	int usernameCount = 0;
	static String LoggedInUserID;
	static String LoggedInUserRole;
	public Login() {
		usernameLabel.setForeground(fcolor);
		usernameLabel.setBackground(bgcolor);
		usernameLabel.setOpaque(true);
		usernameLabel.setPreferredSize(new Dimension(75, 20));
		passwordLabel.setForeground(fcolor);
		passwordLabel.setBackground(bgcolor);
		passwordLabel.setOpaque(true);
		passwordLabel.setPreferredSize(new Dimension(75, 20));
		usernameField.setPreferredSize(new Dimension(125, 20));
		passwordField.setPreferredSize(new Dimension(125, 20));
		login.setPreferredSize(new Dimension(150, 35));
		login.addActionListener(new LoginListener());
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
		c1.add(passwordLabel, c);
		c.gridx = 1; c.gridy = 1;
		c1.add(passwordField, c);
		c2.add(login);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
						int Attempts = rs.getInt("Attempts_Remaining");
						if(rs.getBoolean("Active")==(true)){
							if(rs.getInt("Attempts_Remaining")>0){
								//if(BCrypt.checkpw(passwordField.getText().toString(), rs.getString("Password_Hash"))){
									Date date = new Date();
									if(date.before(rs.getDate("Password_Expiry")) ){
										stmt.execute("Update Users set Attempts_Remaining = 5 where Username = '" + usernameField.getText() + "'");
										LoggedInUserID = rs.getString("User_ID");
										LoggedInUserRole = rs.getString("Role");
										jf.dispose();
								    	new Main();
									}else{
										JOptionPane.showMessageDialog(null, "Your password has expired and must be reset.", null, JOptionPane.INFORMATION_MESSAGE);
										new PasswordReset();
									}
								//} else {
								//	Attempts--;
								//	stmt.execute("Update Users set Attempts_Remaining = " + Attempts + " where Username = '" + usernameField.getText() + "'");
								//	JOptionPane.showMessageDialog(null, "Invalid username/password combination", null, JOptionPane.ERROR_MESSAGE);
								//}
							}else{
								JOptionPane.showMessageDialog(null, "Your account has been disabled.\nPlease contact a system administrator for assistance.", null, JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(null, "Your account has been disabled.\nPlease contact a system administrator for assistance.", null, JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
	    }
	}
}