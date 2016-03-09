import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.*;
import javax.swing.event.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Main extends JFrame {
	DefaultTableModel model = new DefaultTableModel() {
	   @Override
	   public boolean isCellEditable(int row, int column) {
	       return false;
	   }
	};
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	JFrame jf = new JFrame();
	JTable summaryTable = new JTable(model);
	JButton view = new JButton("View");
	JButton edit = new JButton("Edit");
	JButton delete = new JButton("Delete");
	JButton create = new JButton("New");
	JButton settings = new JButton("Settings");
	JButton exit = new JButton("Logout");
	JLabel nameLabel = new JLabel(" Name");
	JLabel addressLabel1 = new JLabel(" Address");
	JLabel addressLabel2 = new JLabel(" ");
	JLabel phoneLabel = new JLabel(" Phone");
	JLabel emailLabel = new JLabel(" Email");
	JLabel fullNameLabel = new JLabel();
	JLabel fullAddressLabel1 = new JLabel();
	JLabel fullAddressLabel2 = new JLabel();
	JLabel fullPhoneLabel = new JLabel();
	JLabel fullEmailLabel = new JLabel();
	static String fullName;
	static String fullAddress1;
	static String fullAddress2;
	static String phoneNum;
	static String email;
	static String groupList;
	static Object selectedContactID;
	static Integer selectedContactIDint;
	static Object selectedCreatedBy;
	static Object Public;
	int groupCount;
	int Contact_ID;
	String filterGroup;
	JComboBox groupSelector;
	ArrayList<Integer> groupID = new ArrayList<>();
	ArrayList<Integer> groupIDcount = new ArrayList<>();
	public Main() {
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs1, rs2, rs3;
		model.addColumn("Contact_ID");
		model.addColumn("Created_By");
		model.addColumn("Public");
		model.addColumn("First Name");
	    model.addColumn("Last Name");
	    model.addColumn("Telephone");
	    model.addColumn("Groups");
	    summaryTable.getColumn("Contact_ID").setPreferredWidth(0);
	    summaryTable.getColumn("Contact_ID").setMinWidth(0);
	    summaryTable.getColumn("Contact_ID").setWidth(0);
	    summaryTable.getColumn("Contact_ID").setMaxWidth(0);
	    summaryTable.getColumn("Created_By").setPreferredWidth(0);
	    summaryTable.getColumn("Created_By").setMinWidth(0);
	    summaryTable.getColumn("Created_By").setWidth(0);
	    summaryTable.getColumn("Created_By").setMaxWidth(0);
	    summaryTable.getColumn("Public").setPreferredWidth(0);
	    summaryTable.getColumn("Public").setMinWidth(0);
	    summaryTable.getColumn("Public").setWidth(0);
	    summaryTable.getColumn("Public").setMaxWidth(0);
	    summaryTable.getColumn("Groups").setPreferredWidth(0);
	    summaryTable.getColumn("Groups").setMinWidth(0);
	    summaryTable.getColumn("Groups").setWidth(0);
	    summaryTable.getColumn("Groups").setMaxWidth(0);
	    view.setEnabled(false);
	    edit.setEnabled(false);
	    delete.setEnabled(false);
	    try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select Contact_ID, First_Name, Last_Name, Public, Created_By from Contacts where Public = 1 or Created_By = '"+Login.LoggedInUserID+"'");
			while(rs1.next()){
				groupList = ",";
				Contact_ID = rs1.getInt("Contact_ID");
				rs2 = stmt.executeQuery("Select top 1 Phone_Num as PhoneNum from Phone Where Contact_ID = "+Contact_ID);
				rs3 = stmt.executeQuery("Select Group_ID from ContactGroups Where Contact_ID = "+Contact_ID);
				while(rs2.next()){
					phoneNum = rs2.getString("PhoneNum");
				}
				while(rs3.next()){
					groupList += rs3.getString("Group_ID")+",";
				}
				model.addRow(new Object[] {rs1.getString("Contact_ID"),rs1.getString("Created_By"),rs1.getString("Public"),rs1.getString("First_Name"),rs1.getString("Last_Name"), phoneNum, groupList});
				phoneNum = null;
			}
		
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		nameLabel.setForeground(fcolor);
		nameLabel.setBackground(bgcolor);
		nameLabel.setOpaque(true);
		nameLabel.setPreferredSize(new Dimension(90, 20));
		addressLabel1.setForeground(fcolor);
		addressLabel1.setBackground(bgcolor);
		addressLabel1.setOpaque(true);
		addressLabel1.setPreferredSize(new Dimension(90, 20));
		addressLabel2.setForeground(fcolor);
		addressLabel2.setBackground(bgcolor);
		addressLabel2.setOpaque(true);
		addressLabel2.setPreferredSize(new Dimension(90, 20));
		phoneLabel.setForeground(fcolor);
		phoneLabel.setBackground(bgcolor);
		phoneLabel.setOpaque(true);
		phoneLabel.setPreferredSize(new Dimension(90, 20));
		emailLabel.setForeground(fcolor);
		emailLabel.setBackground(bgcolor);
		emailLabel.setOpaque(true);
		emailLabel.setPreferredSize(new Dimension(90, 20));
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select Count (*) as groupCount from Groups");
			while(rs1.next()){
				groupCount = rs1.getInt("groupCount");
			}
			groupCount++; //Add 1 more item to array for the "Display All" option.
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		String[] groups = new String[groupCount];
		final String[] groupIDindex = new String[groupCount];
		
		groups[0] = "Display All (";
		groupIDindex[0] = "-1";
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select Count(*) as ContactsCount from Contacts where Public = 1 or Created_By = '"+Login.LoggedInUserID+"'");
			while(rs1.next()){
				groups[0] += rs1.getString("ContactsCount");
			}
			groups[0] += ")";
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		try{
			int i = 1;
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select * from Groups order by Group_Name asc");
			while(rs1.next()){
				groups[i] = rs1.getString("Group_Name");
				groupIDindex[i] = rs1.getString("Group_ID");
				groupID.add(i-1, rs1.getInt("Group_ID"));
				rs2 = stmt.executeQuery("SELECT Count(*) as groupIDcount FROM ContactGroups INNER JOIN Contacts ON ContactGroups.Contact_ID = Contacts.Contact_ID Where (Contacts.Public = TRUE OR Contacts.Created_By = " +Login.LoggedInUserID+ ") AND Group_ID = "+groupID.get(i-1));
				while(rs2.next()){
					groupIDcount.add(i-1, rs2.getInt("groupIDcount"));
					groups[i] += " (" + rs2.getInt("groupIDcount") + ")";
				}
				i++;
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		final JComboBox groupSelector = new JComboBox(groups);
		groupSelector.addItemListener(new ItemListener(){
			public void itemStateChanged( ItemEvent event ) {
			    if( event.getStateChange() == ItemEvent.SELECTED ) {
		            if(groupIDindex[groupSelector.getSelectedIndex()].equals("-1")){
		            	filterGroup = "";
		            } else {
		            filterGroup = ","+groupIDindex[groupSelector.getSelectedIndex()]+",";
		            }
		            sorter.setRowFilter(RowFilter.regexFilter(filterGroup, 6));
			    }
			}
	    });
		summaryTable.setAutoCreateRowSorter(true);
		summaryTable.setRowSorter(sorter);
		view.setPreferredSize(new Dimension(100, 25));
		view.addActionListener(new ViewListener());
		edit.setPreferredSize(new Dimension(100, 25));
		edit.addActionListener(new EditListener());
		delete.setPreferredSize(new Dimension(100, 25));
		delete.addActionListener(new DeleteListener());
		create.setPreferredSize(new Dimension(100, 25));
		create.addActionListener(new CreateListener());
		settings.setPreferredSize(new Dimension(100, 25));
		settings.addActionListener(new SettingsListener());
		exit.setPreferredSize(new Dimension(100, 25));
		exit.addActionListener(new CloseListener());
		summaryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener());
		summaryTable.addMouseListener(new DoubleClickListener());
		groupSelector.setPreferredSize(new Dimension(200, 25));
		Container c1 = new Container();
		Container c2 = new Container();
		Container c3 = new Container();
		jf.add(c1, BorderLayout.CENTER);
		jf.add(c2, BorderLayout.PAGE_END);
		jf.add(c3, BorderLayout.PAGE_START);
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		c1.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.ipadx = 5;
		c.gridx = 0; c.gridy = 0;
		jp1.add(new JScrollPane(summaryTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		c1.add(jp1,c);
		c.gridx = 0; c.gridy = 1;
		c1.add(jp2,c);
		jp2.setLayout(new GridBagLayout());
		c.gridx = 0; c.gridy = 0;
		jp2.add(nameLabel,c);
		c.gridx = 1; c.gridy = 0;
		jp2.add(fullNameLabel,c);
		c.gridx = 0; c.gridy = 1;
		jp2.add(addressLabel1,c);
		c.gridx = 1; c.gridy = 1;
		jp2.add(fullAddressLabel1,c);
		c.gridx = 0; c.gridy = 2;
		jp2.add(addressLabel2,c);
		c.gridx = 1; c.gridy = 2;
		jp2.add(fullAddressLabel2,c);
		c.gridx = 0; c.gridy = 3;
		jp2.add(phoneLabel,c);
		c.gridx = 1; c.gridy = 3;
		jp2.add(fullPhoneLabel, c);
		c.gridx = 0; c.gridy = 4;
		jp2.add(emailLabel,c);
		c.gridx = 1; c.gridy = 4;
		jp2.add(fullEmailLabel,c);
		c2.setLayout(new FlowLayout());
		c3.setLayout(new FlowLayout());		
		c2.add(view);
		c2.add(edit);
		c2.add(delete);
		c2.add(create);
		c3.add(groupSelector);
		c3.add(settings);
		c3.add(exit);
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class ListSelectionListener implements javax.swing.event.ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			edit.setEnabled(false);
			delete.setEnabled(false);
			selectedContactID = (summaryTable.getValueAt(summaryTable.getSelectedRow(), 0));
			selectedCreatedBy = (String) (summaryTable.getValueAt(summaryTable.getSelectedRow(), 1));
			Public = (summaryTable.getValueAt(summaryTable.getSelectedRow(), 2));
			view.setEnabled(true);
			if(selectedCreatedBy.equals(Login.LoggedInUserID)){
				edit.setEnabled(true);
				delete.setEnabled(true);
			} else if(Login.LoggedInUserRole.equals("A")||Login.LoggedInUserRole.equals("C")){
				edit.setEnabled(true);
				delete.setEnabled(true);
			}
			try{
				String database = Application.DBlocation;
				Connection conn = DriverManager.getConnection(database);
				Statement stmt = conn.createStatement();
				ResultSet rs1 = stmt.executeQuery("Select Contact_ID, First_Name, Middle_Name, Last_Name, Street, City, State, Postal_Code, Country from Contacts Where Contact_ID = "+selectedContactID);
				ResultSet rs2 = stmt.executeQuery("Select top 1 Phone_Num as PhoneNum from Phone Where Contact_ID = "+selectedContactID);
				ResultSet rs3 = stmt.executeQuery("Select top 1 Email_Address as EmailAddr from Email Where Contact_ID = "+selectedContactID);
				while(rs1.next()){
					fullName = rs1.getString("First_Name")+" "+rs1.getString("Middle_Name")+" "+rs1.getString("Last_Name");
					fullAddress1 = rs1.getString("Street");
					fullAddress2 = rs1.getString("City")+", "+rs1.getString("State")+" "+rs1.getString("Postal_Code")+" "+rs1.getString("Country");
					fullNameLabel.setText(fullName);
					fullAddressLabel1.setText(fullAddress1);
					fullAddressLabel2.setText(fullAddress2);
					selectedContactIDint = rs1.getInt("Contact_ID");
				}
				while(rs2.next()){
					phoneNum = rs2.getString("PhoneNum");
					fullPhoneLabel.setText(phoneNum);
				}
				while(rs3.next()){
					email = rs3.getString("EmailAddr");
					fullEmailLabel.setText(email);
				}
			
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}

		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class DoubleClickListener implements javax.swing.event.MouseInputListener{
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount()==2){
				jf.dispose();
				Contact.editMode = 0;
				Contact.Contact_ID = selectedContactIDint;
				new Contact();
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}
	}
	
	class ViewListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			jf.dispose();
			Contact.editMode = 0;
			Contact.Contact_ID = selectedContactIDint;
			new Contact();
	    }
	}
	
	class EditListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			jf.dispose();
			Contact.editMode = 1;
			Contact.Contact_ID = selectedContactIDint;
	    	new Contact();
	    }
	}
	
	
	class CreateListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			jf.dispose();
			Contact.editMode = 2;
			Contact.Contact_ID = 0;
	    	new Contact();
	    }
	}
	
	class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object[] options = {"Cancel","Delete Contact"};
    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this contact?\nThis action cannot be undone.", "Delete Contact", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    		if (i==1) {
    			Connection 	conn;
	    		Statement	stmt;
	    		String database = Application.DBlocation;
				try {
					conn = DriverManager.getConnection(database);
					stmt = conn.createStatement();	
	    			stmt.execute("Delete from Phone where Contact_ID = " + selectedContactID);
	    			stmt.execute("Delete from Email where Contact_ID = " + selectedContactID);
	    			stmt.execute("Delete from Workplace where Contact_ID = " + selectedContactID);
	    			stmt.execute("Delete from ContactGroups where Contact_ID = " + selectedContactID);
	    			stmt.execute("Delete from Contacts where Contact_ID = " + selectedContactID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				jf.dispose();
				new Main();
    		}
		}
	}
	
	class SettingsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Login.LoggedInUserRole.equals("V")){
				new PasswordReset();
			} else {
				new Settings();
			}
	    }
	}
	
	class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object[] options = {"Go Back","Log Out"};
    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to log out of the application?", "Log Out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    		if (i==1) {
    			fullNameLabel.setText("");
				fullAddressLabel1.setText("");
				fullAddressLabel2.setText("");
    			jf.dispose();
    			new Login();
    		}
	    }
	}
}