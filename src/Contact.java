import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.imageio.*;
public class Contact extends JFrame {
	static int editMode; //0:View Only, 1:Edit Existing, 2:Create New
	static int Contact_ID; //Contact_ID of contact to load
	String Created_By = "0";
	int phoneCount = 1;
	int emailCount = 1;
	int workplaceCount = 1;
	Color bgcolor = new Color(189,215,238);
	Color fcolor = new Color(68,84,106);
	Color bgcolorEmpty = new Color(238,232,170);
	JFrame jf = new JFrame();
	Container c1 = new Container();
	Container c2 = new Container();
	Container cp = new Container();
	Container ce = new Container();
	Container cwp = new Container();
	JLabel firstNameLabel = new JLabel(" First Name");
	JLabel middleNameLabel = new JLabel(" Middle Name");
	JLabel lastNameLabel = new JLabel(" Last Name");
	JLabel nicknameLabel = new JLabel(" Nickname");
	JLabel birthdateLabel = new JLabel(" Birthdate");
	JLabel notesLabel = new JLabel(" Notes");
	JLabel streetLabel = new JLabel(" Street");
	JLabel cityLabel = new JLabel(" City");
	JLabel stateRegionLabel = new JLabel(" State/Region");
	JLabel postalCodeLabel = new JLabel(" Postal Code");
	JLabel countryLabel = new JLabel(" Country");
	JLabel phoneLabel = new JLabel(" Phone");
	JLabel emailLabel = new JLabel(" Email");
	JLabel workplaceLabel = new JLabel(" Workplace");
	JLabel groupsLabel = new JLabel(" Groups");
	JLabel contactTypeLabel = new JLabel(" Contact Type");
	JLabel blankLabel1 = new JLabel(" ");
	JLabel blankLabel2 = new JLabel(" ");
	JLabel blankLabel3 = new JLabel(" ");
	JLabel blankLabel4 = new JLabel(" ");
	JLabel blankLabel5 = new JLabel(" ");
	JLabel blankLabel6 = new JLabel(" ");
	JTextField firstNameField = new JTextField();
	JTextField middleNameField = new JTextField();
	JTextField lastNameField = new JTextField();
	JTextField nicknameField = new JTextField();
	JFormattedTextField birthdateField = new JFormattedTextField();
	JTextArea notes = new JTextArea();
	JScrollPane notesSP = new JScrollPane(notes);
	JTextField streetField = new JTextField();
	JTextField cityField = new JTextField();
	JTextField stateRegionField = new JTextField();
	JTextField postalCodeField = new JTextField();
	JTextField countryField = new JTextField();
	ArrayList<JTextField> emailField = new ArrayList<>();
	ArrayList<JComboBox> emailType = new ArrayList<>();
	ArrayList<JTextField> workplaceField = new ArrayList<>();
	JTextField groupsField = new JTextField();
	JRadioButton publicContactRadioButton = new JRadioButton("Public");
	JRadioButton personalContactRadioButton = new JRadioButton("Personal");
	ArrayList<JFormattedTextField> phoneField = new ArrayList<>();
	ArrayList<JComboBox> phoneType = new ArrayList<>();
	ArrayList<JButton> phoneRemove = new ArrayList<>();
	JButton phoneAdd = new JButton("Add");
	JButton emailAdd = new JButton("Add");
	ArrayList<JButton> emailRemove = new ArrayList<>();
	JButton workplaceAdd = new JButton("Add");
	ArrayList<JButton> workplaceRemove = new ArrayList<>();
	JButton groupsEdit = new JButton("Edit");
	JButton edit = new JButton("Edit");
	JButton close = new JButton("Close");
	JButton delete = new JButton("Delete");
	JButton upload = new JButton("Select Photo");
	JFileChooser fileChooser = new JFileChooser();
	Image image;
	JLabel picLabel;
	String selectedFile;
	String groupsString = "";
	String Date;
	String formattedDate;
	MaskFormatter formatter;
	int ContactGroupsCount = 0;
	int existingImage = 0;
	public Contact() {
		firstNameField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(firstNameField.getText().equals("")){
                	firstNameField.setBackground(bgcolorEmpty);
                } else {
                	firstNameField.setBackground(Color.WHITE);
                }
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				firstNameField.setBackground(Color.WHITE);				
			}
        });
		lastNameField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(lastNameField.getText().equals("")){
                	lastNameField.setBackground(bgcolorEmpty);
                } else {
                	lastNameField.setBackground(Color.WHITE);
                }
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				lastNameField.setBackground(Color.WHITE);
			}
        });
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
		nicknameLabel.setForeground(fcolor);
		nicknameLabel.setBackground(bgcolor);
		nicknameLabel.setOpaque(true);
		nicknameLabel.setPreferredSize(new Dimension(90, 20));
		birthdateLabel.setForeground(fcolor);
		birthdateLabel.setBackground(bgcolor);
		birthdateLabel.setOpaque(true);
		birthdateLabel.setPreferredSize(new Dimension(90, 20));
		notesLabel.setForeground(fcolor);
		notesLabel.setBackground(bgcolor);
		notesLabel.setOpaque(true);
		notesLabel.setPreferredSize(new Dimension(90, 20));
		streetLabel.setForeground(fcolor);
		streetLabel.setBackground(bgcolor);
		streetLabel.setOpaque(true);
		streetLabel.setPreferredSize(new Dimension(90, 20));
		cityLabel.setForeground(fcolor);
		cityLabel.setBackground(bgcolor);
		cityLabel.setOpaque(true);
		cityLabel.setPreferredSize(new Dimension(90, 20));
		stateRegionLabel.setForeground(fcolor);
		stateRegionLabel.setBackground(bgcolor);
		stateRegionLabel.setOpaque(true);
		stateRegionLabel.setPreferredSize(new Dimension(90, 20));
		postalCodeLabel.setForeground(fcolor);
		postalCodeLabel.setBackground(bgcolor);
		postalCodeLabel.setOpaque(true);
		postalCodeLabel.setPreferredSize(new Dimension(90, 20));
		countryLabel.setForeground(fcolor);
		countryLabel.setBackground(bgcolor);
		countryLabel.setOpaque(true);
		countryLabel.setPreferredSize(new Dimension(90, 20));
		phoneLabel.setForeground(fcolor);
		phoneLabel.setBackground(bgcolor);
		phoneLabel.setOpaque(true);
		phoneLabel.setPreferredSize(new Dimension(90, 20));
		emailLabel.setForeground(fcolor);
		emailLabel.setBackground(bgcolor);
		emailLabel.setOpaque(true);
		emailLabel.setPreferredSize(new Dimension(90, 20));
		workplaceLabel.setForeground(fcolor);
		workplaceLabel.setBackground(bgcolor);
		workplaceLabel.setOpaque(true);
		workplaceLabel.setPreferredSize(new Dimension(90, 20));
		groupsLabel.setForeground(fcolor);
		groupsLabel.setBackground(bgcolor);
		groupsLabel.setOpaque(true);
		groupsLabel.setPreferredSize(new Dimension(90, 20));
		contactTypeLabel.setForeground(fcolor);
		contactTypeLabel.setBackground(bgcolor);
		contactTypeLabel.setOpaque(true);
		contactTypeLabel.setPreferredSize(new Dimension(90, 20));
		firstNameField.setColumns(25);
		firstNameField.setNextFocusableComponent(middleNameField);
		middleNameField.setColumns(25);
		lastNameField.setColumns(25);
		nicknameField.setColumns(25);
		birthdateField.setColumns(25);
		notes.setColumns(35);
        notes.setRows(4);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setBackground(new Color(255,255,255));
		streetField.setColumns(25);
		streetField.setNextFocusableComponent(postalCodeField);
		cityField.setColumns(25);
		stateRegionField.setColumns(25);
		stateRegionField.setNextFocusableComponent(countryField);
		postalCodeField.setColumns(25);
		postalCodeField.setNextFocusableComponent(cityField);
		postalCodeField.addFocusListener(new FocusListener() {
		        public void focusLost(FocusEvent e) {
		        	Connection 	conn;
		    		Statement	stmt;
		    		ResultSet	rs;
		    		try{
		    			String database = Application.DBlocation;
		    			conn = DriverManager.getConnection(database);
		    			stmt = conn.createStatement();
		    			rs = stmt.executeQuery("Select City, State from Zip_Code where ZIP_Code = " + postalCodeField.getText());
		    			while(rs.next()){
		    				cityField.setText(rs.getString("City"));
		    				stateRegionField.setText(rs.getString("State"));
		    			}
		    		}catch(Exception ex){
		    			System.out.println(ex.getMessage());
		    		}
		        }

				public void focusGained(FocusEvent e) {
				}
		});
		countryField.setColumns(25);
		groupsField.setColumns(25);
		phoneAdd.setPreferredSize(new Dimension(80, 19));
		phoneAdd.addActionListener(new addPhoneListener());
		emailAdd.setPreferredSize(new Dimension(80, 19));
		emailAdd.addActionListener(new addEmailListener());
		workplaceAdd.setPreferredSize(new Dimension(80, 19));
		workplaceAdd.addActionListener(new addWorkplaceListener());
		groupsEdit.setPreferredSize(new Dimension(80, 19));
		groupsEdit.addActionListener(new GroupsEditListener());
		upload.addActionListener(new PhotoListener());
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		edit.setPreferredSize(new Dimension(145, 35));
		edit.addActionListener(new EditListener());
		edit.setEnabled(false);
		close.addActionListener(new CloseListener());
		close.setPreferredSize(new Dimension(145, 35));
		delete.addActionListener(new DeleteListener());
		delete.setPreferredSize(new Dimension(145, 35));
		delete.setEnabled(false);
		groupsField.setEditable(false);
		Connection 	conn;
		Statement	stmt;
		ResultSet	rs1, rs2, rs3;
		try{
			String database = Application.DBlocation;
			conn = DriverManager.getConnection(database);
			stmt = conn.createStatement();
			rs1 = stmt.executeQuery("Select * from Contacts where Contact_ID = " + Contact_ID);
			MaskFormatter DOBformatter = new MaskFormatter();
			DOBformatter = new MaskFormatter("##'/##'/#####");
			DOBformatter.install(birthdateField);
			rs2 = stmt.executeQuery("Select Group_Name FROM Groups INNER JOIN ContactGroups ON ContactGroups.Group_ID=Groups.Group_ID where ContactGroups.Contact_ID = " + Contact_ID + " order by Group_Name asc");
			rs3 = stmt.executeQuery("Select Count (*) as ContactGroupsCount from ContactGroups where Contact_ID = " + Contact_ID);
			while(rs3.next()){
				ContactGroupsCount = (rs3.getInt("ContactGroupsCount"));
			}
			while(rs1.next()){
				Date = rs1.getString("Birthdate");
				Created_By = rs1.getString("Created_By");
				if(Date != null) {
					formattedDate = Date.substring(5,7)+"/"+Date.substring(8,10)+"/"+Date.substring(0,4);
				} else {
					formattedDate = "";
				}
				firstNameField.setText(rs1.getString("First_Name"));
				middleNameField.setText(rs1.getString("Middle_Name"));
				lastNameField.setText(rs1.getString("Last_Name"));
				nicknameField.setText(rs1.getString("Nickname"));
				birthdateField.setText(formattedDate);
				notes.setText(rs1.getString("Notes"));
				streetField.setText(rs1.getString("Street"));
				cityField.setText(rs1.getString("City"));
				stateRegionField.setText(rs1.getString("State"));
				postalCodeField.setText(rs1.getString("Postal_Code"));
				countryField.setText(rs1.getString("Country"));
				while(rs2.next()){
					groupsString += (rs2.getString("Group_Name"));
					ContactGroupsCount--;
					if(ContactGroupsCount>0){
						groupsString += (", ");
					}
				}
				groupsField.setText(groupsString);
				if(rs1.getBoolean("Public") == true){
					publicContactRadioButton.setSelected(true);
				} else {
					personalContactRadioButton.setSelected(true);
				}
				//TODO read image
				InputStream imageBlob = rs1.getBinaryStream("Photo");
				if(rs1.getBinaryStream("Photo") != null){
					image = ImageIO.read(imageBlob);
					existingImage = 1;
				}
			}
			
			rs1 = stmt.executeQuery("Select Count (*) as phoneCount from Phone where [Contact_ID] = " + Contact_ID);
			while(rs1.next()){
				phoneCount = (rs1.getInt("phoneCount"));
			}
			rs1 = stmt.executeQuery("Select Count (*) as emailCount from Email where [Contact_ID] = " + Contact_ID);
			while(rs1.next()){
				emailCount = (rs1.getInt("emailCount"));
			}
			rs1 = stmt.executeQuery("Select Count (*) as workplaceCount from Workplace where [Contact_ID] = " + Contact_ID);
			while(rs1.next()){
				workplaceCount = (rs1.getInt("workplaceCount"));
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		if(Created_By.equals(Login.LoggedInUserID)){
			edit.setEnabled(true);
			delete.setEnabled(true);
		} else if(Login.LoggedInUserRole.equals("A")||Login.LoggedInUserRole.equals("C")||editMode==2){
			edit.setEnabled(true);
			delete.setEnabled(true);
		}
		
		if(editMode==0){
			firstNameField.setEditable(false);
			middleNameField.setEditable(false);
			lastNameField.setEditable(false);
			nicknameField.setEditable(false);
			birthdateField.setEditable(false);
			notes.setEditable(false);
			streetField.setEditable(false);
			cityField.setEditable(false);
			stateRegionField.setEditable(false);
			postalCodeField.setEditable(false);
			countryField.setEditable(false);
			phoneAdd.setVisible(false);
			emailAdd.setVisible(false);
			workplaceAdd.setVisible(false);
			groupsEdit.setVisible(false);
			upload.setVisible(false);
			publicContactRadioButton.setEnabled(false);
			personalContactRadioButton.setEnabled(false);
			edit.setText("Edit");
			close.setText("Close");
			delete.setVisible(false);
			streetLabel.setText(" Address");
	        cityLabel.setText("");
	        stateRegionLabel.setText("");
	        postalCodeLabel.setText("");
	        countryLabel.setText("");
		} else {
			edit.setText("Save");
			close.setText("Cancel");
		}
		if(editMode==2) {
			delete.setVisible(false);
			groupsEdit.setEnabled(false);
		}
		if(Login.LoggedInUserRole.equals("V")) {
			publicContactRadioButton.setEnabled(false);
			personalContactRadioButton.setSelected(true);
		}
		jf.add(c1, BorderLayout.CENTER);
		jf.add(c2, BorderLayout.PAGE_END);
		c1.setLayout(new GridBagLayout());
		c2.setLayout(new FlowLayout());
		cp.setLayout(new GridBagLayout());
		ce.setLayout(new GridBagLayout());
		cwp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST; c.ipadx = 0; c.ipady = 0;
		//start of Phone loop
		try {
			formatter = new MaskFormatter("'(###')' ###'-####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int i = 0;
		while (i < phoneCount) {
			try{
				String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();		
				rs1 = stmt.executeQuery("Select (Phone_Num), (Phone_Type) from Phone where [Contact_ID] = " + Contact_ID);
				while(rs1.next()){
					phoneField.add(i, new JFormattedTextField(formatter));
					phoneField.get(i).setText(rs1.getString("Phone_Num"));
					String[] phoneTypes = new String[] {"Home", "Mobile", "Work", "Home Fax", "Work Fax", "Other"};
					phoneType.add(i, new JComboBox(phoneTypes));
					phoneType.get(i).setSelectedItem(rs1.getString("Phone_Type"));
					phoneRemove.add(i, new JButton());
				    phoneField.get(i).setColumns(25);
					phoneType.get(i).setPreferredSize(new Dimension(100, 19));
					phoneRemove.get(i).setPreferredSize(new Dimension(80, 19));
					phoneRemove.get(i).setText("Remove");
					phoneRemove.get(i).addActionListener(new removePhoneListener());
					if(editMode==0){
						phoneField.get(i).setEditable(false);
						phoneType.get(i).setEnabled(false);
						phoneRemove.get(i).setVisible(false);
					}
					c.gridx = 0; c.gridy = i; cp.add(phoneField.get(i), c);
					c.gridx = 1; c.gridy = i; cp.add(phoneType.get(i), c);
					c.gridx = 2; c.gridy = i; cp.add(phoneRemove.get(i), c);
					i++;
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		//end of Phone loop //start of Email loop
		int j = 0;
		while (j < emailCount) {
			try{
				String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();		
				rs1 = stmt.executeQuery("Select (Email_Address), (Email_Type) from Email where [Contact_ID] = " + Contact_ID);
				while(rs1.next()){
					emailField.add(j, new JTextField());
					emailField.get(j).setText(rs1.getString("Email_Address"));
					String[] emailTypes = new String[] {"Home", "Work", "Other"};
					emailType.add(j, new JComboBox(emailTypes));
					emailType.get(j).setSelectedItem(rs1.getString("Email_Type"));
					emailRemove.add(j, new JButton());
				    emailField.get(j).setColumns(25);
					emailType.get(j).setPreferredSize(new Dimension(100, 19));
					emailRemove.get(j).setPreferredSize(new Dimension(80, 19));
					emailRemove.get(j).setText("Remove");
					emailRemove.get(j).addActionListener(new removeEmailListener());
					if(editMode==0){
						emailField.get(j).setEditable(false);
						emailType.get(j).setEnabled(false);
						emailRemove.get(j).setVisible(false);
					}
					c.gridx = 0; c.gridy = j; ce.add(emailField.get(j), c);
					c.gridx = 1; c.gridy = j; ce.add(emailType.get(j), c);
					c.gridx = 2; c.gridy = j; ce.add(emailRemove.get(j), c);
				    j++;
				}
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		//end of Email loop //start of Workplace loop
		int w = 0;
		while (w < workplaceCount) {
			try{
				String database = Application.DBlocation;
				conn = DriverManager.getConnection(database);
				stmt = conn.createStatement();		
				rs1 = stmt.executeQuery("Select (Workplace_Name) from Workplace where [Contact_ID] = " + Contact_ID);
				while(rs1.next()){
					workplaceField.add(w, new JTextField());
					workplaceField.get(w).setText((rs1.getString("Workplace_Name")));
					workplaceRemove.add(w, new JButton());
				    workplaceField.get(w).setColumns(25);
				    workplaceRemove.get(w).setPreferredSize(new Dimension(80, 19));
				    workplaceRemove.get(w).setText("Remove");
				    workplaceRemove.get(w).addActionListener(new removeWorkplaceListener());
					if(editMode==0){
						workplaceField.get(w).setEditable(false);
						workplaceRemove.get(w).setVisible(false);
					}
					c.gridx = 0; c.gridy = w; cwp.add(workplaceField.get(w), c);
					c.gridx = 1; c.gridy = w; cwp.add(workplaceRemove.get(w), c);
				    w++;
				}
				stmt.close();
				rs1.close();
				conn.close();
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		//end of Workplace loop
		c.gridx = 0; c.gridy = 0; c1.add(firstNameLabel, c);
		c.gridx = 1; c.gridy = 0; c1.add(firstNameField, c);
		c.gridx = 0; c.gridy = 1; c1.add(middleNameLabel, c);
		c.gridx = 1; c.gridy = 1; c1.add(middleNameField, c);
		c.gridx = 0; c.gridy = 2; c1.add(lastNameLabel, c);
		c.gridx = 1; c.gridy = 2; c1.add(lastNameField, c);
		c.gridx = 0; c.gridy = 3; c1.add(nicknameLabel, c);
		c.gridx = 1; c.gridy = 3; c1.add(nicknameField, c);
		c.gridx = 0; c.gridy = 4; c1.add(birthdateLabel, c);
		c.gridx = 1; c.gridy = 4; c1.add(birthdateField, c);
		c.gridx = 0; c.gridy = 5; c1.add(blankLabel1, c);
		c.gridx = 0; c.gridy = 6; c1.add(streetLabel, c);
		c.gridx = 1; c.gridy = 6; c1.add(streetField, c);
		c.gridx = 0; c.gridy = 7; c1.add(cityLabel, c);
		c.gridx = 1; c.gridy = 7; c1.add(cityField, c);
		c.gridx = 0; c.gridy = 8; c1.add(stateRegionLabel, c);
		c.gridx = 1; c.gridy = 8; c1.add(stateRegionField, c);
		c.gridx = 0; c.gridy = 9; c1.add(postalCodeLabel, c);
		c.gridx = 1; c.gridy = 9; c1.add(postalCodeField, c);
		c.gridx = 0; c.gridy = 10; c1.add(countryLabel, c);
		c.gridx = 1; c.gridy = 10; c1.add(countryField, c);
		c.gridx = 0; c.gridy = 11; c1.add(blankLabel2, c);
		c.gridwidth = 2;
		c.gridx = 0; c.gridy = 12; c1.add(phoneLabel, c);
		c.gridx = 1; c.gridy = 12; c1.add(cp, c);
		c.gridx = 1; c.gridy = 13; c1.add(phoneAdd, c);
		c.gridx = 0; c.gridy = 14; c1.add(blankLabel3, c);
		c.gridx = 0; c.gridy = 15; c1.add(emailLabel, c);
		c.gridx = 1; c.gridy = 15; c1.add(ce, c);
		c.gridx = 1; c.gridy = 16; c1.add(emailAdd, c);
		c.gridx = 0; c.gridy = 17; c1.add(blankLabel4, c);
		c.gridx = 0; c.gridy = 18; c1.add(workplaceLabel, c);
		c.gridx = 1; c.gridy = 18; c1.add(cwp, c);
		c.gridx = 1; c.gridy = 19; c1.add(workplaceAdd, c);
		c.gridx = 0; c.gridy = 20; c1.add(blankLabel5, c);
		c.gridx = 0; c.gridy = 21; c1.add(groupsLabel, c);
		c.gridx = 1; c.gridy = 21; c1.add(groupsField, c);
		c.gridx = 1; c.gridy = 22; c1.add(groupsEdit, c);
		c.gridx = 0; c.gridy = 23; c1.add(blankLabel6, c);
		c.gridx = 0; c.gridy = 24; c1.add(notesLabel, c);
		c.gridx = 1; c.gridy = 24; c1.add(notesSP, c);
		c.gridx = 0; c.gridy = 25; c1.add(contactTypeLabel, c);
		ButtonGroup roleType = new ButtonGroup();
		roleType.add(publicContactRadioButton);
		roleType.add(personalContactRadioButton);
		c.gridx = 1; c.gridy = 25; c1.add(publicContactRadioButton, c);
		c.gridx = 1; c.gridy = 26; c1.add(personalContactRadioButton, c);
		c2.add(edit); c2.add(close); c2.add(delete);
		c.gridx = 2; c.gridy = 0;
		c.gridheight = 8;
		c.anchor = GridBagConstraints.NORTH;
		if (image != null){
			Image scaledImage = image.getScaledInstance(150,150,Image.SCALE_SMOOTH);
			picLabel = new JLabel(new ImageIcon(scaledImage));
			picLabel.setPreferredSize(new Dimension(150,150));
			c1.add(picLabel,c);
		}
		c.gridx = 2; c.gridy = 8;
		c.gridheight = 2;
		c1.add(upload, c);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridheight = 0;
		jf.setResizable(false);
		jf.setVisible(true);
		jf.pack();
		jf.setLocationRelativeTo(null);
	}
	
	class PhotoListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	//Handle open button action.
	        if (e.getSource() == upload) {
	            int returnVal = fileChooser.showOpenDialog(Contact.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
	            	File file = fileChooser.getSelectedFile();
	            	try {
						image = ImageIO.read(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	Image scaledImage = image.getScaledInstance(150,150,Image.SCALE_SMOOTH);
	            	if(existingImage==1){
	            		c1.remove(picLabel);
	            	}
	            	picLabel = new JLabel(new ImageIcon(scaledImage));
	    			picLabel.setPreferredSize(new Dimension(150,150));
	            	GridBagConstraints c = new GridBagConstraints();
	            	c.gridx = 2; c.gridy = 0;
	        		c.gridheight = 8;
	        		c.anchor = GridBagConstraints.NORTH;
	        		c1.add(picLabel,c);
	            	c1.revalidate();
	            	c1.repaint();
	            	jf.pack();
	            } else {
	            	//Do this.
	            }
	        }
	    }
	}

	class EditListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	if (editMode==0) {
	    		editMode = 1;
		        firstNameField.setEditable(true);
		        middleNameField.setEditable(true);
		        lastNameField.setEditable(true);
		        nicknameField.setEditable(true);
		        birthdateField.setEditable(true);
		        notes.setEditable(true);
		        streetField.setEditable(true);
		        cityField.setEditable(true);
		        stateRegionField.setEditable(true);
		        postalCodeField.setEditable(true);
		        countryField.setEditable(true);
		        personalContactRadioButton.setEnabled(true);
		        phoneAdd.setVisible(true);
				emailAdd.setVisible(true);
				workplaceAdd.setVisible(true);
				groupsEdit.setVisible(true);
				upload.setVisible(true);
				delete.setVisible(true);
		        edit.setText("Save");
		        close.setText("Cancel");
		        streetLabel.setText(" Street");
		        cityLabel.setText(" City");
		        stateRegionLabel.setText(" State/Region");
		        postalCodeLabel.setText(" Postal Code");
		        countryLabel.setText(" Country");
		        if(Login.LoggedInUserRole.equals("A")||Login.LoggedInUserRole.equals("C")) {
					publicContactRadioButton.setEnabled(true);
				}
		        int i = 0;
				while (i < phoneCount) {
					phoneField.get(i).setEditable(true);
			        phoneType.get(i).setEnabled(true);
			        phoneRemove.get(i).setVisible(true);
				    i++;
				}
				int j = 0;
				while (j < emailCount) {
					emailField.get(j).setEditable(true);
			        emailType.get(j).setEnabled(true);
			        emailRemove.get(j).setVisible(true);
				    j++;
				}
				int w = 0;
				while (w < workplaceCount) {
					workplaceField.get(w).setEditable(true);
					workplaceRemove.get(w).setVisible(true);
				    w++;
				}
				jf.setLocationRelativeTo(null);
		        jf.pack();
	    	} else {
	    		if ((firstNameField.getText()).equals("")||(lastNameField.getText()).equals("")) {
	    			JOptionPane.showMessageDialog(null, "First and Last Name are required.", null, JOptionPane.WARNING_MESSAGE);
	    		} else {
		    		if (editMode==1) {
		    			try{
			    		Connection 	conn;
				    		Statement	stmt;
				    		String database = Application.DBlocation;
							conn = DriverManager.getConnection(database);
							stmt = conn.createStatement();
							Date = birthdateField.getText();
							if(Date.matches(".*[0-9].*")) {
								formattedDate = Date.substring(6,10)+"-"+Date.substring(0,2)+"-"+Date.substring(3,5)+" 00:00:00.000000";
								stmt.execute("Update Contacts set First_Name='"+firstNameField.getText()+"', Middle_Name='"+middleNameField.getText()+"', Last_Name='"+lastNameField.getText()+"', Nickname='"+nicknameField.getText()+"', Birthdate='"+formattedDate+"', Street='"+streetField.getText()+"', City='"+cityField.getText()+"', State='"+stateRegionField.getText()+"', Postal_Code='"+postalCodeField.getText()+"', Country='"+countryField.getText()+"', Notes='"+notes.getText()+"' Where Contact_ID="+Contact_ID);
							} else {						
								stmt.execute("Update Contacts set First_Name='"+firstNameField.getText()+"', Middle_Name='"+middleNameField.getText()+"', Last_Name='"+lastNameField.getText()+"', Nickname='"+nicknameField.getText()+"', Birthdate=NULL, Street='"+streetField.getText()+"', City='"+cityField.getText()+"', State='"+stateRegionField.getText()+"', Postal_Code='"+postalCodeField.getText()+"', Country='"+countryField.getText()+"', Notes='"+notes.getText()+"' Where Contact_ID="+Contact_ID);
							}
							
							//TODO write image
							PreparedStatement statement = null;
							FileInputStream inputStream = null;
							if(selectedFile != null){
								try {
									File image = new File(selectedFile);
									inputStream = new FileInputStream(image);
									statement = conn.prepareStatement("Update Contacts Set Photo = (?) Where Contact_ID = "+Contact_ID);
									statement.setBinaryStream(1, (InputStream) inputStream,	(int) (image.length()));
									statement.executeUpdate();
								} catch (FileNotFoundException e1) {
									System.out.println("FileNotFoundException: - " + e1);
								} catch (SQLException e1) {
									System.out.println("SQLException: - " + e1);
								} finally {
									try {
										//conn.close();
										statement.close();
									} catch (SQLException e1) {
										System.out.println("SQLException Finally: - " + e1);
									}
								}
							}				
							if(publicContactRadioButton.isSelected()){
								stmt.execute("Update Contacts set Public=1 Where Contact_ID="+Contact_ID);
							} else {
								stmt.execute("Update Contacts set Public=0 Where Contact_ID="+Contact_ID);
							}
							
							stmt.execute("Delete from Phone where Contact_ID = " + Contact_ID);
							int i = 0;
							while (i < phoneCount) {
								if(phoneField.get(i).getText().matches("^[(]\\d{3}[)][\\s]\\d{3}[-]\\d{4}$")){
									stmt.execute("Insert into Phone values ("+Contact_ID+", '"+phoneField.get(i).getText()+"', '"+phoneType.get(i).getSelectedItem().toString()+"')");
								}
							i++;
							}
							stmt.execute("Delete from Email where Contact_ID = " + Contact_ID);
							i = 0;
							while (i < emailCount) {
								if(emailField.get(i).getText().length()>0){
									stmt.execute("Insert into Email values ("+Contact_ID+", '"+emailField.get(i).getText()+"', '"+emailType.get(i).getSelectedItem().toString()+"')");
								}
							i++;
							}
							stmt.execute("Delete from Workplace where Contact_ID = " + Contact_ID);
							i = 0;
							while (i < workplaceCount) {
								if(workplaceField.get(i).getText().length()>0){
									stmt.execute("Insert into Workplace values ("+Contact_ID+", '"+workplaceField.get(i).getText()+"')");
								}
							i++;
							}
							
							JOptionPane.showMessageDialog(null, "Contact "+firstNameField.getText()+" "+lastNameField.getText()+" updated successfully.");
							jf.dispose();
							new Main();
				    	}catch(Exception ex){
				    		System.out.println(ex.getMessage());
						}
			    	} else if (editMode==2) {
			    		try{
				    		Connection 	conn;
				    		Statement	stmt;
				    		ResultSet	rs;
				    		String database = Application.DBlocation;
							conn = DriverManager.getConnection(database);
							stmt = conn.createStatement();
							Date = birthdateField.getText();
							if(publicContactRadioButton.isSelected()){
								if(Date.matches(".*[0-9].*")) {
									formattedDate = Date.substring(6,10)+"-"+Date.substring(0,2)+"-"+Date.substring(3,5)+" 00:00:00.000000";
									stmt.execute("Insert into Contacts (First_Name, Middle_Name, Last_Name, Nickname, Birthdate, Street, City, State, Postal_Code, Country, Public, Created_By, Notes) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', '"+nicknameField.getText()+"', '"+formattedDate+"', '"+streetField.getText()+"', '"+cityField.getText()+"', '"+stateRegionField.getText()+"', '"+postalCodeField.getText()+"', '"+countryField.getText()+"', 1, "+Login.LoggedInUserID+", '"+notes.getText()+"')");
								} else {
									stmt.execute("Insert into Contacts (First_Name, Middle_Name, Last_Name, Nickname, Street, City, State, Postal_Code, Country, Public, Created_By, Notes) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', '"+nicknameField.getText()+"', '"+streetField.getText()+"', '"+cityField.getText()+"', '"+stateRegionField.getText()+"', '"+postalCodeField.getText()+"', '"+countryField.getText()+"', 1, "+Login.LoggedInUserID+", '"+notes.getText()+"')");
								}
							} else {
								if(Date.matches(".*[0-9].*")) {
									formattedDate = Date.substring(6,10)+"-"+Date.substring(0,2)+"-"+Date.substring(3,5)+" 00:00:00.000000";
									stmt.execute("Insert into Contacts (First_Name, Middle_Name, Last_Name, Nickname, Birthdate, Street, City, State, Postal_Code, Country, Public, Created_By, Notes) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', '"+nicknameField.getText()+"', '"+formattedDate+"', '"+streetField.getText()+"', '"+cityField.getText()+"', '"+stateRegionField.getText()+"', '"+postalCodeField.getText()+"', '"+countryField.getText()+"', 0, "+Login.LoggedInUserID+", '"+notes.getText()+"')");							
								} else {
									stmt.execute("Insert into Contacts (First_Name, Middle_Name, Last_Name, Nickname, Street, City, State, Postal_Code, Country, Public, Created_By, Notes) values ('"+firstNameField.getText()+"', '"+middleNameField.getText()+"', '"+lastNameField.getText()+"', '"+nicknameField.getText()+"', '"+streetField.getText()+"', '"+cityField.getText()+"', '"+stateRegionField.getText()+"', '"+postalCodeField.getText()+"', '"+countryField.getText()+"', 0, "+Login.LoggedInUserID+", '"+notes.getText()+"')");
								}
							}
							rs = stmt.executeQuery("SELECT @@IDENTITY as Contact_ID");
							while(rs.next()){
								Contact_ID = (rs.getInt("Contact_ID"));
							}
							int i = 0;
							while (i < phoneCount) {
								if(phoneField.get(i).getText().matches("^[(]\\d{3}[)][\\s]\\d{3}[-]\\d{4}$")){
									stmt.execute("Insert into Phone values ("+Contact_ID+", '"+phoneField.get(i).getText()+"', '"+phoneType.get(i).getSelectedItem().toString()+"')");
								}
							i++;
							}
							i = 0;
							while (i < emailCount) {
								if(emailField.get(i).getText().length()>0){
									stmt.execute("Insert into Email values ("+Contact_ID+", '"+emailField.get(i).getText()+"', '"+emailType.get(i).getSelectedItem().toString()+"')");
								}
							i++;
							}
							i = 0;
							while (i < workplaceCount) {
								if(workplaceField.get(i).getText().length()>0){
									stmt.execute("Insert into Workplace values ("+Contact_ID+", '"+workplaceField.get(i).getText()+"')");
								}
							i++;
							}
							
							//TODO write image
							PreparedStatement statement = null;
							FileInputStream inputStream = null;
							if(selectedFile != null){
								try {
									File image = new File(selectedFile);
									inputStream = new FileInputStream(image);
									statement = conn.prepareStatement("Update Contacts Set Photo = (?) Where Contact_ID = "+Contact_ID);
									statement.setBinaryStream(1, (InputStream) inputStream,	(int) (image.length()));
									statement.executeUpdate();
								} catch (FileNotFoundException e1) {
									System.out.println("FileNotFoundException: - " + e1);
								} catch (SQLException e1) {
									System.out.println("SQLException: - " + e1);
								} finally {
									try {
										//conn.close();
										statement.close();
									} catch (SQLException e1) {
										System.out.println("SQLException Finally: - " + e1);
									}
								}
							}		
							
							
							JOptionPane.showMessageDialog(null, "Contact "+firstNameField.getText()+" "+lastNameField.getText()+" created successfully.");
							jf.dispose();
							new Main();
				    	}catch(Exception ex){
				    		System.out.println(ex.getMessage());
						}
			    	}
	    		}
	    	}
	    }
	}
	class addPhoneListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	int i = phoneCount;
	    	phoneField.add(i, new JFormattedTextField(formatter));
	    	String[] phoneTypes = new String[] {"Home", "Mobile", "Work", "Home Fax", "Work Fax", "Other"};
			phoneType.add(i, new JComboBox(phoneTypes));
			phoneRemove.add(i, new JButton());
			phoneField.get(i).setColumns(25);
			phoneType.get(i).setPreferredSize(new Dimension(100, 19));
			phoneRemove.get(i).setPreferredSize(new Dimension(80, 19));
			phoneRemove.get(i).setText("Remove");
			phoneRemove.get(i).addActionListener(new removePhoneListener());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0; c.gridy = i; cp.add(phoneField.get(i), c);
			c.gridx = 1; c.gridy = i; cp.add(phoneType.get(i), c);
			c.gridx = 2; c.gridy = i; cp.add(phoneRemove.get(i), c);
	    	phoneCount += 1;
	    	jf.pack();
	    }
	}
	class removePhoneListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			while (i<phoneCount){
				if (e.getSource() == phoneRemove.get(i)) {
					int z = i;
					int y = (i+1);

					while(z<(phoneCount-1)) {
						phoneField.get(z).setText(phoneField.get(y).getText());
						phoneType.get(z).setSelectedItem(phoneType.get(y).getSelectedItem());
						z++; y++;
			        }
			        
		        }
		    i++;
			}
			cp.remove(phoneField.get(phoneCount-1));
	        cp.remove(phoneType.get(phoneCount-1));
	        cp.remove(phoneRemove.get(phoneCount-1));
	        phoneCount -=1;
	        jf.pack();
	    }
	}
	class addEmailListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	int j = emailCount;
	    	emailField.add(j, new JTextField());
	    	String[] emailTypes = new String[] {"Home", "Work", "Other"};
			emailType.add(j, new JComboBox(emailTypes));
			emailRemove.add(j, new JButton());
			emailField.get(j).setColumns(25);
			emailType.get(j).setPreferredSize(new Dimension(100, 19));
			emailRemove.get(j).setPreferredSize(new Dimension(80, 19));
			emailRemove.get(j).setText("Remove");
			emailRemove.get(j).addActionListener(new removeEmailListener());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0; c.gridy = j; ce.add(emailField.get(j), c);
			c.gridx = 1; c.gridy = j; ce.add(emailType.get(j), c);
			c.gridx = 2; c.gridy = j; ce.add(emailRemove.get(j), c);
	    	jf.pack();
	    	emailCount += 1;
	    }
	}
	class removeEmailListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			while (i<emailCount){
				if (e.getSource() == emailRemove.get(i)) {
					int z = i;
					int y = (i+1);

					while(z<(emailCount-1)) {
						emailField.get(z).setText(emailField.get(y).getText());
						emailType.get(z).setSelectedItem(emailType.get(y).getSelectedItem());
						z++; y++;
			        }
			        
		        }
		    i++;
			}
			ce.remove(emailField.get(emailCount-1));
	        ce.remove(emailType.get(emailCount-1));
	        ce.remove(emailRemove.get(emailCount-1));
	        emailCount -=1;
	        jf.pack();
	    }
	}
	class addWorkplaceListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	int w = workplaceCount;
	    	workplaceField.add(w, new JTextField());
	    	workplaceRemove.add(w, new JButton());
	    	workplaceField.get(w).setColumns(25);
	    	workplaceRemove.get(w).setPreferredSize(new Dimension(80, 19));
	    	workplaceRemove.get(w).setText("Remove");
	    	workplaceRemove.get(w).addActionListener(new removeWorkplaceListener());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0; c.gridy = w; cwp.add(workplaceField.get(w), c);
			c.gridx = 1; c.gridy = w; cwp.add(workplaceRemove.get(w), c);
			workplaceCount += 1;
	    	jf.pack();
	    }
	}
	class removeWorkplaceListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			while (i<workplaceCount){
				if (e.getSource() == workplaceRemove.get(i)) {
					int z = i;
					int y = (i+1);

					while(z<(workplaceCount-1)) {
						workplaceField.get(z).setText(workplaceField.get(y).getText());
						z++; y++;
			        }
			        
		        }
		    i++;
			}
			cwp.remove(workplaceField.get(workplaceCount-1));
	        cwp.remove(workplaceRemove.get(workplaceCount-1));
	        workplaceCount -=1;
	        jf.pack();
	    }
	}
	class GroupsEditListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	jf.dispose();
	    	new SelectGroups();
	    }
	}
	
    

	class CloseListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	if (editMode==0) {
	    		jf.dispose();
	    		new Main();
	    	}
	    	if (editMode==1) {
	    		Object[] options = {"Go Back","Cancel Without Saving"};
	    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to cancel?\nYour changes will not be saved.", "Cancel", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	    		if (i==1) {
	    			jf.dispose();
	    			new Main();
	    		}
	    	}
	    	if (editMode==2) {
	    		Object[] options = {"Go Back","Cancel Without Saving"};
	    		int i = JOptionPane.showOptionDialog(null, "Are you sure you want to cancel?\nThis contact will not be saved.", "Cancel", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	    		if (i==1) {
	    			jf.dispose();
	    			new Main();
	    		}
	    	}
	    	
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
	    			stmt.execute("Delete from Phone where Contact_ID = " + Contact_ID);
	    			stmt.execute("Delete from Email where Contact_ID = " + Contact_ID);
	    			stmt.execute("Delete from Workplace where Contact_ID = " + Contact_ID);
	    			stmt.execute("Delete from ContactGroups where Contact_ID = " + Contact_ID);
	    			stmt.execute("Delete from Contacts where Contact_ID = " + Contact_ID);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
    			jf.dispose();
    			new Main();
    		}
	    }
	}
}