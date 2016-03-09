import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.*;
public class Settings extends JFrame {
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	JFrame jf = new JFrame();
	Container c1 = new Container();
	Container c2 = new Container();
	Container cUsers = new Container();
	Container cGroups = new Container();
	JLabel usersLabel = new JLabel(" Users");
	JLabel groupsLabel = new JLabel(" Groups");
	JLabel blankLabel1 = new JLabel(" ");
	JLabel blankLabel2 = new JLabel(" ");
	ArrayList<JTextField> usersField = new ArrayList<>();
	ArrayList<JTextField> groupsField = new ArrayList<>();
	JPasswordField currentPasswordField = new JPasswordField();
	JPasswordField newPasswordField = new JPasswordField();
	JPasswordField confirmPasswordField = new JPasswordField();
	JButton userAdd = new JButton("Add");
	ArrayList<JButton> userEdit = new ArrayList<>();
	JButton groupAdd = new JButton("Add");
	ArrayList<JButton> groupEdit = new ArrayList<>();
	ArrayList<JButton> groupRemove = new ArrayList<>();
	JButton save = new JButton("Change Password");
	JButton close = new JButton("Close");
	ArrayList<String> fullUsername = new ArrayList<>();
	int usersCount = 0;
	int ContactGroupsCount = 0;
	ArrayList<Integer> userID = new ArrayList<>();
	ArrayList<Integer> groupID = new ArrayList<>();
	public Settings() {
		usersLabel.setForeground(fcolor);
		usersLabel.setBackground(bgcolor);
		usersLabel.setOpaque(true);
		usersLabel.setPreferredSize(new Dimension(90, 20));
		groupsLabel.setForeground(fcolor);
		groupsLabel.setBackground(bgcolor);
		groupsLabel.setOpaque(true);
		groupsLabel.setPreferredSize(new Dimension(90, 20));
		userAdd.setPreferredSize(new Dimension(80, 19));
		userAdd.addActionListener(new addUserListener());
		groupAdd.setPreferredSize(new Dimension(80, 19));
		groupAdd.addActionListener(new addGroupListener());
		currentPasswordField.setColumns(20);
		newPasswordField.setColumns(20);
		confirmPasswordField.setColumns(20);
		save.setPreferredSize(new Dimension(150, 35));
		save.addActionListener(new ChangePasswordListener());
		close.addActionListener(new CloseListener());
		close.setPreferredSize(new Dimension(150, 35));
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs1, rs2;
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select Count (*) as usersCount from Users");
			rs2 = stmt.executeQuery("Select Count (*) as groupsCount from Groups");
			while(rs1.next()){
				usersCount = (rs1.getInt("usersCount"));
			}
			while(rs2.next()){
				ContactGroupsCount = (rs2.getInt("groupsCount"));
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		jf.add(c1, BorderLayout.CENTER);
		jf.add(c2, BorderLayout.PAGE_END);
		c1.setLayout(new GridBagLayout());
		c2.setLayout(new FlowLayout());
		cUsers.setLayout(new GridBagLayout());
		cGroups.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST; c.ipadx = 0; c.ipady = 0;
		if(Login.LoggedInUserRole.equals("A")){
			//start of Users loop
			int i = 0;
			while (i < usersCount) {
				try{
					String database = Application.DBlocation;
					conn = DriverManager.getConnection(database);
					stmt = conn.createStatement();		
					rs1 = stmt.executeQuery("Select User_ID, First_Name, Last_Name from Users");
					while(rs1.next()){
						userID.add(i, rs1.getInt("User_ID"));
						usersField.add(i, new JTextField());
						usersField.get(i).setText((rs1.getString("Last_Name"))+", "+(rs1.getString("First_Name")));
						usersField.get(i).setEditable(false);
					    usersField.get(i).setColumns(20);
					    userEdit.add(i, new JButton());
						userEdit.get(i).setPreferredSize(new Dimension(80, 19));
						userEdit.get(i).setText("Edit");
						userEdit.get(i).addActionListener(new editUserListener());
						c.gridx = 0; c.gridy = i; cUsers.add(usersField.get(i), c);
						c.gridx = 1; c.gridy = i; cUsers.add(userEdit.get(i), c);
						i++;
					}
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}
			//End of Users loop
		}
		
		if(Login.LoggedInUserRole.equals("A")||Login.LoggedInUserRole.equals("C")){
			//start of Groups loop
			int j = 0;
			while (j < ContactGroupsCount) {
				try{
					String database = Application.DBlocation;
					conn = DriverManager.getConnection(database);
					stmt = conn.createStatement();		
					rs1 = stmt.executeQuery("Select Group_ID, Group_Name from Groups order by Group_Name asc");
					while(rs1.next()){
						groupID.add(j, rs1.getInt("Group_ID"));
						groupsField.add(j, new JTextField());
						groupsField.get(j).setText((rs1.getString("Group_Name")));
						groupsField.get(j).setEditable(false);
					    groupsField.get(j).setColumns(20);
					    groupEdit.add(j, new JButton());
						groupEdit.get(j).setPreferredSize(new Dimension(80, 19));
						groupEdit.get(j).setText("Edit");
						groupEdit.get(j).addActionListener(new editGroupListener());
					    groupRemove.add(j, new JButton());
						groupRemove.get(j).setPreferredSize(new Dimension(80, 19));
						groupRemove.get(j).setText("Remove");
						groupRemove.get(j).addActionListener(new removeGroupListener());
						c.gridx = 0; c.gridy = j; cGroups.add(groupsField.get(j), c);
						c.gridx = 1; c.gridy = j; cGroups.add(groupEdit.get(j), c);
						c.gridx = 2; c.gridy = j; cGroups.add(groupRemove.get(j), c);
						j++;
					}
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}
			//End of Groups loop
		}
		
		if(Login.LoggedInUserRole.equals("A")){
			c.gridx = 0; c.gridy = 0; c1.add(usersLabel, c);
			c.gridx = 1; c.gridy = 0; c.gridwidth=2; c1.add(cUsers, c); c.gridwidth=1;
			c.gridx = 1; c.gridy = 1; c1.add(userAdd, c);
			c.gridx = 0; c.gridy = 2; c1.add(blankLabel1, c);
		}
		
		if(Login.LoggedInUserRole.equals("A")||Login.LoggedInUserRole.equals("C")){
			c.gridx = 0; c.gridy = 3; c1.add(groupsLabel, c);
			c.gridx = 1; c.gridy = 3; c.gridwidth=2; c1.add(cGroups, c); c.gridwidth=1;
			c.gridx = 1; c.gridy = 4; c1.add(groupAdd, c);
		}
		
		c2.add(save); c2.add(close);
		
		jf.setResizable(false);
		jf.setVisible(true);
		jf.pack();
		jf.setLocationRelativeTo(null);
	}

	class addUserListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	CreateEditUser.editMode = 2;
	    	CreateEditUser.User_ID = 0;
	    	jf.dispose();
	    	new CreateEditUser();
	    }
	}
	
	class editUserListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			Connection 	conn;
			Statement	stmt;
			ResultSet	rs1;
			while (i<usersCount){
				if (e.getSource() == userEdit.get(i)) {
					CreateEditUser.editMode = 1;
					CreateEditUser.User_ID = userID.get(i);
					jf.dispose();
					new CreateEditUser();
		        }
		    i++;
			}
			jf.pack();
	    }
	}
	
	class addGroupListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	CreateEditGroup.editMode = 2;
	    	CreateEditGroup.Group_ID = 0;
	    	jf.dispose();
	    	new CreateEditGroup();
	    }
	}
	
	class editGroupListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			Connection 	conn;
			Statement	stmt;
			ResultSet	rs1;
			while (i<ContactGroupsCount){
				if (e.getSource() == groupEdit.get(i)) {
					CreateEditGroup.editMode = 1;
					CreateEditGroup.Group_ID = groupID.get(i);
					jf.dispose();
					new CreateEditGroup();
		        }
		    i++;
			}
			jf.pack();
	    }
	}
	
	class removeGroupListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			int groupIDtoDelete = 0;
			Connection 	conn;
			Statement	stmt;
			ResultSet	rs1;
			while (i<ContactGroupsCount){
				if (e.getSource() == groupRemove.get(i)) {
					Object[] options = {"Cancel","Delete Group"};
		    		int b = JOptionPane.showOptionDialog(null, "Are you sure you want to delete group "+groupsField.get(i).getText()+"?\nThis action cannot be undone.", "Delete Group", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		    		if (b==1) {
		    			groupIDtoDelete = groupID.get(i);
		    			try{
		    				String database = Application.DBlocation;
		    				conn = DriverManager.getConnection(database);
		    				stmt = conn.createStatement();		
		    				stmt.execute("Delete from Groups where Group_ID = "+groupIDtoDelete);
		    			}catch(Exception ex){
		    				System.out.println(ex.getMessage());
		    			}
						int z = i;
						int y = (i+1);
						while(z<(ContactGroupsCount-1)) {
							groupsField.get(z).setText(groupsField.get(y).getText());
							z++; y++;
				        }
						cGroups.remove(groupsField.get(ContactGroupsCount-1));
				        cGroups.remove(groupEdit.get(ContactGroupsCount-1));
				        cGroups.remove(groupRemove.get(ContactGroupsCount-1));
				        ContactGroupsCount -=1;
		    		}
		        }
		    i++;
			}
			jf.pack();
	    }
	}
	
	class ChangePasswordListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	new PasswordReset();
	    }
	}
	
	class CloseListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	jf.dispose();
	    }
	}
}