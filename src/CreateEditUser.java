import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.security.*;

import javax.swing.*;

import org.mindrot.BCrypt;
public class CreateEditUser extends JFrame {
	static int editMode; //1:Edit Existing, 2:Create New
	static int User_ID; //User_ID of user to load
	int Attempts_Remaining;
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	Color red = new Color(255,0,0);
	JFrame jf = new JFrame();
	Container c1 = new Container();
	Container c2 = new Container();
	JLabel firstNameLabel = new JLabel(" First Name");
	JLabel middleNameLabel = new JLabel(" Middle Name");
	JLabel lastNameLabel = new JLabel(" Last Name");
	JLabel usernameLabel = new JLabel(" Username");
	JLabel roleLabel = new JLabel(" Role");
	JLabel blankLabel1 = new JLabel(" ");
	JLabel blankLabel2 = new JLabel(" ");
	JLabel blankLabel3 = new JLabel(" ");
	JLabel blankLabel4 = new JLabel(" ");
	JLabel lockedOut = new JLabel("USER IS LOCKED OUT");
	JLabel status = new JLabel(" Status");
	JTextField firstNameField = new JTextField();
	JTextField middleNameField = new JTextField();
	JTextField lastNameField = new JTextField();
	JTextField usernameField = new JTextField();
	JRadioButton administratorRadioButton = new JRadioButton("Administrator");
	JRadioButton createEditRadioButton = new JRadioButton("Create and Edit");
	JRadioButton viewOnlyRadioButton = new JRadioButton("View Only");
	JCheckBox active = new JCheckBox("Active");
	JButton passwordReset = new JButton("Reset Password");
	JButton save = new JButton("Save");
	JButton close = new JButton("Cancel");
	public CreateEditUser() {
		firstNameLabel.setForeground(fcolor);
		firstNameLabel.setBackground(bgcolor);
		firstNameLabel.setOpaque(true);
		firstNameLabel.setPreferredSize(new Dimension(90, 20));
		middleNameLabel.setForeground(fcolor);
		middleNameLabel.setBackground(bgcolor);
		middleNameLabel.setOpaque(true);
		middleNameLabel.setPreferredSize(new Dimension(90, 20));
		lastNameLabel.setForeground(fcolor);
		lastNameLabel.setBackground(bgcolor);
		lastNameLabel.setOpaque(true);
		lastNameLabel.setPreferredSize(new Dimension(90, 20));
		usernameLabel.setForeground(fcolor);
		usernameLabel.setBackground(bgcolor);
		usernameLabel.setOpaque(true);
		usernameLabel.setPreferredSize(new Dimension(90, 20));
		roleLabel.setForeground(fcolor);
		roleLabel.setBackground(bgcolor);
		roleLabel.setOpaque(true);
		roleLabel.setPreferredSize(new Dimension(90, 20));
		lockedOut.setForeground(red);
		status.setForeground(fcolor);
		status.setBackground(bgcolor);
		status.setOpaque(true);
		status.setPreferredSize(new Dimension(90, 20));
		firstNameField.setColumns(20);
		middleNameField.setColumns(20);
		lastNameField.setColumns(20);
		usernameField.setColumns(20);
		save.setPreferredSize(new Dimension(125, 35));
		save.addActionListener(new SaveListener());
		close.addActionListener(new CloseListener());
		close.setPreferredSize(new Dimension(125, 35));
		passwordReset.addActionListener(new PasswordResetListener());
		passwordReset.setPreferredSize(new Dimension(250, 35));
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs;
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Select * from Users where User_ID = " + User_ID);
			
			while(rs.next()){
				firstNameField.setText(rs.getString("First_Name"));
				middleNameField.setText(rs.getString("Middle_Name"));
				lastNameField.setText(rs.getString("Last_Name"));
				usernameField.setText(rs.getString("Username"));
				Attempts_Remaining = rs.getInt("Attempts_Remaining");
				if(rs.getString("Role").equals("A")){
					administratorRadioButton.setSelected(true);
				} else if (rs.getString("Role").equals("C")){
					createEditRadioButton.setSelected(true);
				} else if (rs.getString("Role").equals("V")){
					viewOnlyRadioButton.setSelected(true);
				}
				
				if(rs.getBoolean("Active")==(true)){
					active.setSelected(true);
				} else {
					active.setSelected(false);
				}
				
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		if(editMode==2) {
			passwordReset.setVisible(false);
			active.setSelected(true);
		}
		jf.add(c1, BorderLayout.CENTER);
		jf.add(c2, BorderLayout.PAGE_END);
		c1.setLayout(new GridBagLayout());
		c2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST; c.ipadx = 0; c.ipady = 0;
		c.gridx = 0; c.gridy = 0; c1.add(firstNameLabel, c);
		c.gridx = 1; c.gridy = 0; c1.add(firstNameField, c);
		c.gridx = 0; c.gridy = 1; c1.add(middleNameLabel, c);
		c.gridx = 1; c.gridy = 1; c1.add(middleNameField, c);
		c.gridx = 0; c.gridy = 2; c1.add(lastNameLabel, c);
		c.gridx = 1; c.gridy = 2; c1.add(lastNameField, c);
		c.gridx = 0; c.gridy = 3; c1.add(blankLabel1, c);
		c.gridx = 0; c.gridy = 4; c1.add(usernameLabel, c);
		c.gridx = 1; c.gridy = 4; c1.add(usernameField, c);
		c.gridx = 0; c.gridy = 5; c1.add(blankLabel2, c);
		c.gridx = 0; c.gridy = 6; c1.add(roleLabel, c);
		ButtonGroup roleType = new ButtonGroup();
		roleType.add(administratorRadioButton);
		roleType.add(createEditRadioButton);
		roleType.add(viewOnlyRadioButton);
		c.gridx = 1; c.gridy = 6; c1.add(administratorRadioButton, c);
		c.gridx = 1; c.gridy = 7; c1.add(createEditRadioButton, c);
		c.gridx = 1; c.gridy = 8; c1.add(viewOnlyRadioButton, c);
		c.gridx = 0; c.gridy = 9; c1.add(blankLabel3, c);
		c.gridx = 0; c.gridy = 10; c1.add(status, c);
		c.gridx = 1; c.gridy = 10; c1.add(active, c);
		c.gridx = 0; c.gridy = 11; c1.add(blankLabel4, c);
		c.anchor = GridBagConstraints.NORTH;
		if((Attempts_Remaining==0)&&(editMode==1)){
			c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c2.add(lockedOut, c);
		}
		c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c2.add(passwordReset, c);
		c.gridx = 0; c.gridy = 2; c.gridwidth = 1; c2.add(save, c);
		c.gridx = 1; c.gridy = 2; c2.add(close, c);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.pack();
		jf.setLocationRelativeTo(null);
	}

	class SaveListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	if (editMode==1) {
	    		try{
		    		Connection 	conn;
		    		Statement	stmt;
		    		String database = Application.DBlocation;
					conn = DriverManager.getConnection(database);
					stmt = conn.createStatement();		
					stmt.execute("Update Users set First_Name='"+firstNameField.getText()+"', Middle_Name='"+middleNameField.getText()+"', Last_Name='"+lastNameField.getText()+"', Username='"+usernameField.getText()+"' Where User_ID="+User_ID);
					if(administratorRadioButton.isSelected()){
						stmt.execute("Update Users set Role='A' Where User_ID="+User_ID);
					} else if(createEditRadioButton.isSelected()){
						stmt.execute("Update Users set Role='C' Where User_ID="+User_ID);
					} else if(viewOnlyRadioButton.isSelected()){
						stmt.execute("Update Users set Role='V' Where User_ID="+User_ID);
					}
					if(active.isSelected()){
						stmt.execute("Update Users set Active=1 Where User_ID="+User_ID);
					} else {
						stmt.execute("Update Users set Active=0 Where User_ID="+User_ID);
					}
					JOptionPane.showMessageDialog(null, "User "+firstNameField.getText()+" "+lastNameField.getText()+" updated successfully.");
					jf.dispose();
		    	}catch(Exception ex){
		    		System.out.println(ex.getMessage());
				}
	    	}
	    	else if (editMode==2) {
	    		try{
		    		Connection 	conn;
		    		Statement	stmt;
		    		ResultSet	rs;
		    		String database = Application.DBlocation;
					conn = DriverManager.getConnection(database);
					stmt = conn.createStatement();		
					if(administratorRadioButton.isSelected()){
						stmt.execute("Insert into Users (First_Name, Middle_Name, Last_Name, Role, Username, Active) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', 'A', '"+usernameField.getText()+"', 1)");
					} else if(createEditRadioButton.isSelected()) {
						stmt.execute("Insert into Users (First_Name, Middle_Name, Last_Name, Role, Username, Active) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', 'C', '"+usernameField.getText()+"', 1)");
					} else {
						stmt.execute("Insert into Users (First_Name, Middle_Name, Last_Name, Role, Username, Active) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', 'V', '"+usernameField.getText()+"', 1)");
					}
					
					rs = stmt.executeQuery("SELECT @@IDENTITY as User_ID");
					while(rs.next()){
						User_ID = (rs.getInt("User_ID"));
					}
					jf.dispose();
					new PasswordResetProcess();
					
		    	}catch(Exception ex){
		    		System.out.println(ex.getMessage());
				}
	    	}
	    	new Settings();
	    }
	}

	class CloseListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	if (editMode==1) {
	    		Object[] options = {"Go Back","Cancel Without Saving"};
	    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to cancel?\nYour changes will not be saved.", "Cancel", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	    		if (i==1) {
	    			jf.dispose();
	    			new Settings();
	    		}
	    	}
	    	if (editMode==2) {
	    		Object[] options = {"Go Back","Cancel Without Saving"};
	    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to cancel?\nThis user will not be saved.", "Cancel", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	    		if (i==1) {
	    			jf.dispose();
	    			new Settings();
	    		}
	    	}
	    }
	}
	
	class PasswordResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object[] options = {"Cancel","Reset Password"};
			int i = JOptionPane.showOptionDialog(null, "Are you sure you want to reset the password for "+firstNameField.getText()+" "+lastNameField.getText()+"?", "Reset Password", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			if (i==1) {
				new PasswordResetProcess();
				jf.dispose();
				new Settings();
    		}
		}
	}
	
	class PasswordResetProcess {
		String uuid = UUID.randomUUID().toString();
		String upper = uuid.toUpperCase();
		String shortUpper = upper.substring(0,5);
		String hashed = BCrypt.hashpw(shortUpper, BCrypt.gensalt(10));
		
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs; {
			try{
				String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();
				stmt.execute("Update Users set Password_Hash = '" + hashed + "', Password_Expiry = '2000-01-01 00:00:00.000000', Attempts_Remaining = 5 where User_ID = " + User_ID);
			}catch(Exception ex){
	    		System.out.println(ex.getMessage());
			}
			
			JOptionPane.showMessageDialog(null, "User "+firstNameField.getText()+" "+lastNameField.getText()+" has been issued temporary password: "+shortUpper);
		}
	}
}