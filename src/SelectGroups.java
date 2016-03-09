import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;
public class SelectGroups extends JFrame {
	int groupsCount = 0;
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	JFrame jf = new JFrame();
	JLabel groupsLabel = new JLabel("Groups");
	JButton save = new JButton("Save");
	JButton close = new JButton("Cancel");
	Checkbox group[] = new Checkbox[10];
	int groupID[] = new int[10];
	public SelectGroups() {
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs1, rs2;
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select Count (*) as groupsCount from Groups");
				while(rs1.next()){
					groupsCount = (rs1.getInt("groupsCount"));
				}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		groupsLabel.setForeground(fcolor);
		groupsLabel.setBackground(bgcolor);
		groupsLabel.setOpaque(true);
		groupsLabel.setPreferredSize(new Dimension(150, 20));
		save.setPreferredSize(new Dimension(150, 35));
		save.addActionListener(new SaveListener());
		close.addActionListener(new CloseListener());
		close.setPreferredSize(new Dimension(150, 35));
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
		c1.add(groupsLabel, c);
		int i = 0;
		int ContactGroupsCount = 0;
		while (i < groupsCount) {
			try{
				String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();		
				rs1 = stmt.executeQuery("Select * from Groups order by (Group_Name) Asc");
				while(rs1.next()){
					rs2 = stmt.executeQuery("Select Count (*) as ContactGroupsCount from ContactGroups where Contact_ID = " + Contact.Contact_ID + " and Group_ID = " + rs1.getInt("Group_ID"));
					group[i] = new Checkbox(rs1.getString("Group_Name"));
					groupID[i] = (rs1.getInt("Group_ID"));
					while(rs2.next()){
						ContactGroupsCount = (rs2.getInt("ContactGroupsCount"));
					}
					if(ContactGroupsCount>0){
						group[i].setState(true);
					}
					c.gridx = 1; c.gridy = i;
					c1.add(group[i], c);
					group[i].setPreferredSize(new Dimension(150, 20));
					i++;
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		c2.add(save); c2.add(close);
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
	}

	class SaveListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	try{
	    		Connection 	conn;
	    		Statement	stmt;
	    		String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();		
				stmt.executeUpdate("Delete from ContactGroups where Contact_ID = " + Contact.Contact_ID);
				int i = 0;
				while (i < groupsCount) {
					if (group[i].getState()){
						stmt.executeUpdate("Insert into ContactGroups values (" + Contact.Contact_ID + "," + groupID[i] + ")");
					}
				i++;
				}
	    	}catch(Exception ex){
	    		System.out.println(ex.getMessage());
			}
	    	JOptionPane.showMessageDialog(null, "Contact groups updated successfully", null, JOptionPane.INFORMATION_MESSAGE);
	    	jf.dispose();
	    	new Contact();
	    }
	}

	class CloseListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	jf.dispose();
	    	new Contact();
	    }
	}
}