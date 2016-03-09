import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.*;
public class CreateEditGroup extends JFrame {
	static int editMode; //1:Edit Existing, 2:Create New
	static int Group_ID; //Group_ID of group to load
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	JFrame jf = new JFrame();
	Container c1 = new Container();
	Container c2 = new Container();
	JLabel groupNameLabel = new JLabel(" Group Name");
	JTextField groupNameField = new JTextField();
	JButton save = new JButton("Save");
	JButton close = new JButton("Cancel");
	public CreateEditGroup() {
		groupNameLabel.setForeground(fcolor);
		groupNameLabel.setBackground(bgcolor);
		groupNameLabel.setOpaque(true);
		groupNameLabel.setPreferredSize(new Dimension(90, 20));
		groupNameField.setColumns(20);
		save.setPreferredSize(new Dimension(125, 35));
		save.addActionListener(new SaveListener());
		close.addActionListener(new CloseListener());
		close.setPreferredSize(new Dimension(125, 35));
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs;
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Select * from Groups where Group_ID = " + Group_ID);
			while(rs.next()){
				groupNameField.setText(rs.getString("Group_Name"));
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		jf.add(c1, BorderLayout.CENTER);
		jf.add(c2, BorderLayout.PAGE_END);
		c1.setLayout(new GridBagLayout());
		c2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST; c.ipadx = 0; c.ipady = 0;
		c.gridx = 0; c.gridy = 0; c1.add(groupNameLabel, c);
		c.gridx = 1; c.gridy = 0; c1.add(groupNameField, c);
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c2.add(save, c);
		c.gridx = 1; c.gridy = 0; c2.add(close, c);
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
					stmt.execute("Update Groups set Group_Name='"+groupNameField.getText()+"' Where Group_ID="+Group_ID);
					JOptionPane.showMessageDialog(null, "Group "+groupNameField.getText()+" updated successfully.");
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
					stmt.execute("Insert into Groups (Group_Name) values ('"+groupNameField.getText()+"')");
					JOptionPane.showMessageDialog(null, "Group "+groupNameField.getText()+" created successfully.");
					jf.dispose();
		    	}catch(Exception ex){
		    		System.out.println(ex.getMessage());
				}
	    	}
	    	jf.dispose();
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
	    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to cancel?\nThis group will not be saved.", "Cancel", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	    		if (i==1) {
	    			jf.dispose();
	    			new Settings();
	    		}
	    	}
	    }
	}
}