package sample;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.Caret;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

	class ManagerDetails
	{
		String fname;
		String lname;
		String email;
		String password;
		String ID;
	};

public class AddManager extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField MFName;
	private JTextField MLName;
	private JTextField MEmail;
	private JTextField MID;
	private JPasswordField MPass;
	private JTable ManagerTable;
	private JButton btnConfirm;
	private JButton btnAdd;
	JLabel status;
	int col,editingMan;
	ButtonGroup group = new ButtonGroup();
	boolean edit=false,delete=false;
	DefaultTableModel dm = new DefaultTableModel();
	List<ManagerDetails> managerList=new ArrayList<ManagerDetails>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddManager frame = new AddManager();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
					      public void windowClosing(WindowEvent e) {
					        System.exit(0);
					      }
					    });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 50, 763, 659);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(0, 39, 832, 8);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.GRAY);
		separator_1.setBounds(373, 39, 10, 581);
		contentPane.add(separator_1);
		
		JLabel lblAddManager = new JLabel("ADD MANAGER");
		lblAddManager.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddManager.setBounds(143, 58, 148, 20);
		contentPane.add(lblAddManager);
		
		JLabel lblRockWallManagment = new JLabel("Rock Wall Management");
		lblRockWallManagment.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRockWallManagment.setBounds(10, 0, 221, 38);
		contentPane.add(lblRockWallManagment);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial", Font.BOLD, 14));
		lblFirstName.setBounds(47, 127, 102, 14);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Arial", Font.BOLD, 14));
		lblLastName.setBounds(47, 174, 102, 14);
		contentPane.add(lblLastName);
		
		JLabel lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setFont(new Font("Arial", Font.BOLD, 14));
		lblEmailAddress.setBounds(47, 221, 102, 14);
		contentPane.add(lblEmailAddress);
		
		JLabel lblManagerId = new JLabel("Manager ID");
		lblManagerId.setFont(new Font("Arial", Font.BOLD, 14));
		lblManagerId.setBounds(47, 267, 102, 14);
		contentPane.add(lblManagerId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
		lblPassword.setBounds(47, 310, 102, 14);
		contentPane.add(lblPassword);
		
		MFName = new JTextField();
		MFName.setBounds(181, 125, 133, 20);
		contentPane.add(MFName);
		MFName.setColumns(10);
		
		MLName = new JTextField();
		MLName.setColumns(10);
		MLName.setBounds(181, 172, 133, 20);
		contentPane.add(MLName);
		
		MEmail = new JTextField();
		MEmail.setColumns(10);
		MEmail.setBounds(181, 215, 133, 20);
		contentPane.add(MEmail);
		
		MID = new JTextField();
		MID.setColumns(10);
		MID.setBounds(181, 261, 133, 20);
		contentPane.add(MID);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(this);
		btnConfirm.setBounds(532, 517, 89, 23);
		contentPane.add(btnConfirm);
		
		btnAdd = new JButton("Add Manager");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(181, 358, 133, 23);
		contentPane.add(btnAdd);
		MPass = new JPasswordField();
		MPass.setBounds(181, 304, 133, 20);
		contentPane.add(MPass);
		
		UIDefaults ui = UIManager.getLookAndFeel().getDefaults();
	    UIManager.put("RadioButton.focus", ui.getColor("control"));

	   
		
		ManagerTable = new JTable(dm){
			public void tableChanged(TableModelEvent e) {
		        super.tableChanged(e);
		        repaint();
		      }
		};
		
	    ManagerTable.setRowHeight( 32 );
		ManagerTable.setBounds(414, 127, 308, 346);
		contentPane.add(ManagerTable);
		
		dm.setDataVector(new Object[][] { {  },}, new Object[] {"Manager Name", "Edit Option","Delete Option" });
	    group.add((JRadioButton) dm.getValueAt(0, 1));
	    group.add((JRadioButton) dm.getValueAt(0, 2));
	    dm.removeRow(0);
	    ManagerTable.getColumn("Edit Option").setCellRenderer( new RadioButtonRenderer());
	    ManagerTable.getColumn("Edit Option").setCellEditor(new RadioButtonEditor(new JCheckBox()));
	    ManagerTable.getColumn("Delete Option").setCellRenderer(new RadioButtonRenderer());
		ManagerTable.getColumn("Delete Option").setCellEditor(new RadioButtonEditor(new JCheckBox()));
		
        JScrollPane scroll1 = new JScrollPane(ManagerTable); 
        scroll1.setEnabled(false);
		scroll1.setBounds(414, 127, 308, 346);
		contentPane.add(scroll1);
		
		JLabel lblDeleteManager = new JLabel("DELETE MANAGER");
		lblDeleteManager.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDeleteManager.setBounds(489, 58, 189, 20);
		contentPane.add(lblDeleteManager);
		
		status = new JLabel("");
		status.setFont(new Font("Tahoma", Font.PLAIN, 14));
		status.setBounds(101, 466, 205, 14);
		contentPane.add(status);
			
	}
	
//////////////////////////////////////////////////////////////
	
	public void resetInputFields()
	{
		MFName.setText("");
		MLName.setText("");
		MEmail.setText("");
		MID.setText("");
		MPass.setText("");
	}

/////////////////////////////////////////////////////////////////
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getSource() == btnAdd)
		 {
			 if(btnAdd.getText()=="Save")
			 {
				 btnAdd.setText("Add Manager");
				 managerList.get(editingMan).fname=MFName.getText().toString();
				 System.out.println( managerList.get(editingMan).fname);
				 managerList.get(editingMan).lname=MLName.getText().toString();
				 managerList.get(editingMan).email=MEmail.getText().toString();
				 managerList.get(editingMan).ID=MID.getText().toString();
				 managerList.get(editingMan).password=MPass.getText().toString();
				 dm.setValueAt( managerList.get(editingMan).fname+" " +managerList.get(editingMan).lname, editingMan, 0);
				 status.setText("Data Updated!");
				 resetInputFields();
			 }
			 else
			 {	 
				 ManagerDetails manager=new ManagerDetails();
				 manager.fname=MFName.getText().toString();
				 manager.lname=MLName.getText().toString();
				 manager.email=MEmail.getText().toString();
				 manager.ID=MID.getText().toString();
				 manager.password=MPass.getText().toString();
				 managerList.add(manager);
				 status.setText("Data Added!");
				 dm.addRow(new Object[]{managerList.get(dm.getRowCount()).fname+" "+managerList.get(dm.getRowCount()).lname, new JRadioButton("Edit"),new JRadioButton("Delete")});
				 group.add((JRadioButton) dm.getValueAt(dm.getRowCount()-1, 1));
				 group.add((JRadioButton) dm.getValueAt(dm.getRowCount()-1, 2));
		
				 resetInputFields();
			 }
		 }
		 else if(e.getSource()==btnConfirm)
		 {
			 if(managerList.size()==0)
			 {
				 status.setText("No Record Found!");
				 return;
			 }
			    int selectedManager=ManagerTable.getSelectedRow();
			    
				if(delete)
				{
					if(editingMan==selectedManager)
					{
						resetInputFields();
						btnAdd.setText("Add Manager");
					}
					
					dm.removeRow(selectedManager);
					managerList.remove(selectedManager);
					System.out.println(managerList.size());	
					status.setText("Data Deleted!");
				}
				else
				{
					status.setText("");
					editingMan=selectedManager;
					MFName.setText(managerList.get(selectedManager).fname);
					MLName.setText(managerList.get(selectedManager).lname);
					MEmail.setText(managerList.get(selectedManager).email);
					MID.setText(managerList.get(selectedManager).ID);
					MPass.setText(managerList.get(selectedManager).password);
					btnAdd.setText("Save");
				}   
		 }
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////
	class RadioButtonRenderer implements TableCellRenderer
	{
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
		{
			if (value == null)
				return null;
			return (Component) value;
		}
	}	

/////////////////////////////////////////////////////////////////////////
	
	class RadioButtonEditor extends DefaultCellEditor implements ItemListener 
	{
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) 
		{
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column)
		{
			col=column;
				if (value == null)
					return null;
			button = (JRadioButton) value;
			button.addItemListener(this);
			return (Component) value;
		}

		public Object getCellEditorValue()
		{
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e)
		{
			if(col==1)
			{ 
				edit=true;
				delete=false;
			}
			else if(col==2)
			{
				edit=false;
				delete=true;
			}
			super.fireEditingStopped();
		}
	}
///////////////////////////////////////////////////////////////////////////////
}