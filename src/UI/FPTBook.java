package UI;

import java.awt.EventQueue;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import FileController.FileTypeFilter;
import Model.Book;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FPTBook extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_Title;
	private JTextField textField_Author;
	private JTextField textField_Price;

	/**
	 * Launch the application.
	 */
	ArrayList<Book> myBookList = new ArrayList<>();
	private JTable table;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPTBook frame = new FPTBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public int GenerateID() {
		int max = 1;
		if(myBookList != null && myBookList.size() > 0) {
			max = myBookList.get(0).getId();
			for(Book myBook: myBookList) {
				if(max < myBook.getId()) {
					max = myBook.getId();
				}
			}
			max++;
		}
		return max;
	}
	
	
	
	public FPTBook() {
		setTitle("University of Greenwich - Book Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1023, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(0, 0, 1007, 28);
		contentPane.add(menuBar);
		
		JMenu Menu_File = new JMenu("File");
		Menu_File.setFont(new Font("Calibri", Font.PLAIN, 12));
		menuBar.add(Menu_File);
		
		JMenuItem MenuItem_OpenFile = new JMenuItem("Open File");
		MenuItem_OpenFile.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_File.add(MenuItem_OpenFile);
		
		JMenuItem MenuItem_SaveFile = new JMenuItem("Save File");
		MenuItem_SaveFile.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_File.add(MenuItem_SaveFile);
		
		JMenu Menu_System = new JMenu("System");
		Menu_System.setFont(new Font("Calibri", Font.PLAIN, 12));
		menuBar.add(Menu_System);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mntmNewMenuItem.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_System.add(mntmNewMenuItem);
		
		JMenu Menu_Help = new JMenu("Help");
		Menu_Help.setFont(new Font("Calibri", Font.PLAIN, 12));
		menuBar.add(Menu_Help);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_Help.add(mntmAbout);
		
	
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setBounds(0, 26, 311, 445);
		contentPane.add(tabbedPane);
		
		JPanel panel_CRUD = new JPanel();
		panel_CRUD.setForeground(new Color(255, 255, 255));
		panel_CRUD.setBackground(new Color(0, 0, 255));
		tabbedPane.addTab("CRUD", null, panel_CRUD, null);
		panel_CRUD.setLayout(null);
		
		JPanel panel_Search = new JPanel();
		panel_Search.setBackground(new Color(0, 0, 255));
		tabbedPane.addTab("Search", null, panel_Search, null);
		panel_Search.setLayout(null);
		
		JLabel Label_Title = new JLabel("Title");
		Label_Title.setForeground(new Color(255, 255, 255));
		Label_Title.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Title.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Title.setBounds(10, 11, 46, 14);
		panel_CRUD.add(Label_Title);
		
		textField_Title = new JTextField();
		textField_Title.setBounds(76, 8, 221, 20);
		panel_CRUD.add(textField_Title);
		textField_Title.setColumns(10);
		
		JLabel Label_Author = new JLabel("Author");
		Label_Author.setForeground(new Color(255, 255, 255));
		Label_Author.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Author.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Author.setBounds(10, 39, 46, 14);
		panel_CRUD.add(Label_Author);
		
		textField_Author = new JTextField();
		textField_Author.setColumns(10);
		textField_Author.setBounds(76, 36, 221, 20);
		panel_CRUD.add(textField_Author);
		
		JLabel Label_Quantity = new JLabel("Quantity");
		Label_Quantity.setForeground(new Color(255, 255, 255));
		Label_Quantity.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Quantity.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Quantity.setBounds(10, 67, 46, 14);
		panel_CRUD.add(Label_Quantity);
		
		JLabel Label_Price = new JLabel("Price");
		Label_Price.setForeground(new Color(255, 255, 255));
		Label_Price.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Price.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Price.setBounds(10, 95, 46, 14);
		panel_CRUD.add(Label_Price);
		
		textField_Price = new JTextField();
		textField_Price.setColumns(10);
		textField_Price.setBounds(76, 92, 221, 20);
		panel_CRUD.add(textField_Price);
		
		JLabel Label_Category = new JLabel("Category");
		Label_Category.setForeground(new Color(255, 255, 255));
		Label_Category.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Category.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Category.setBounds(10, 123, 46, 14);
		panel_CRUD.add(Label_Category);
		
		JLabel Label_Description = new JLabel("Description");
		Label_Description.setForeground(new Color(255, 255, 255));
		Label_Description.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Description.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Description.setBounds(10, 158, 58, 14);
		panel_CRUD.add(Label_Description);
		
		JTextArea textArea_Description = new JTextArea();
		textArea_Description.setBounds(76, 158, 221, 81);
		panel_CRUD.add(textArea_Description);
		
		JButton Button_New = new JButton("New");
		Button_New.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_New.setBounds(56, 250, 89, 23);
		panel_CRUD.add(Button_New);
		
		JButton Button_Delete = new JButton("Delete");
		Button_Delete.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_Delete.setBounds(155, 250, 89, 23);
		panel_CRUD.add(Button_Delete);
		
		JButton Button_Update = new JButton("Update");
		Button_Update.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_Update.setBounds(56, 295, 89, 23);
		panel_CRUD.add(Button_Update);
		
		JComboBox<Object> comboBox_Category = new JComboBox<Object>();
		comboBox_Category.setBounds(76, 119, 221, 22);
		panel_CRUD.add(comboBox_Category);
		
		JSpinner spinner_Quantity = new JSpinner();
		spinner_Quantity.setBounds(76, 67, 221, 20);
		panel_CRUD.add(spinner_Quantity);
		
		JButton Button_Reset = new JButton("Reset");
		Button_Reset.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_Reset.setBounds(155, 295, 89, 23);
		panel_CRUD.add(Button_Reset);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(314, 26, 693, 434);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		Object[] column = {"ID", "Title", "Author", "Quantity","Price","Category","Description","Created At"};
		Object[] row = new Object[8];
		tableModel.setColumnIdentifiers(column);
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		
	
		
		// 1. Open File 
		MenuItem_OpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// 2. Save File
		MenuItem_SaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser myFileChooser = new JFileChooser(new File("c:\\"));
				myFileChooser.setDialogTitle("Save a file");
				myFileChooser.setFileFilter(new FileTypeFilter(".txt", "Text File"));
				int result = myFileChooser.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					
				}
				
			}
		});
		
		// 3. About
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "FPT Book Management App v1.0 - By Le Nguyen Hoang Long");
			}
		});
		
		// 4. Exit Application
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// 5. Reset
		Button_Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Title.setText("");
				textField_Author.setText("");
				spinner_Quantity.setValue(0);
				textField_Price.setText("");
				comboBox_Category.setSelectedIndex(-1);
				textArea_Description.setText("");
			}
		});
		
		// 6. Button new - Use to create new book
		Button_New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book myBook = new Book();
				myBook.setId(GenerateID());
				myBook.setTitle(textField_Title.getText());
				myBook.setAuthor(textField_Author.getText());
				myBook.setQuantity((int)spinner_Quantity.getValue());
				myBook.setPrice(Integer.parseInt(textField_Price.getText()));
				myBook.setDescription(textArea_Description.getText());
				myBookList.add(myBook);
				row[0] = myBook.getId();
				row[1] = myBook.getTitle();
				row[2] = myBook.getAuthor();
				row[3] = myBook.getQuantity();
				row[4] = myBook.getPrice();
				//row[5] = myBook.getCategory();
				row[6] = myBook.getDescription();
				tableModel.addRow(row);
				textField_Title.setText("");
				textField_Author.setText("");
				spinner_Quantity.setValue(0);
				textField_Price.setText("");
				comboBox_Category.setSelectedIndex(-1);
				textArea_Description.setText("");
			}
		});
		
		// 7. Button delete - Use to delete a book in list
		Button_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// 8. Select Data from table - Use to get data from table
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				TableModel model = table.getModel();
				textField_Title.setText(model.getValueAt(i, 1).toString());
				//..........
			}
		});
		
		// 8. Button update - Use to update a book in list
		Button_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if() {
//					
//				}else {
//					JOptionPane.showMessageDialog(contentPane, "Please select data from table");
//				}
			}
		});
	}
	
	
}
