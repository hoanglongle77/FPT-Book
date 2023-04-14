package UI;

import java.awt.EventQueue;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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

	private JMenuBar menuBar;
	private JMenu Menu_File;
	private JMenuItem MenuItem_OpenFile;
	private JMenuItem MenuItem_SaveFile;
	private JMenu Menu_System;
	private JMenuItem MenuItem_Exit;
	private JMenu Menu_Help;
	private JMenuItem MenuItem_About;

	private JTabbedPane tabbedPane;
	private JPanel panel_CRUD;
	private JPanel panel_Search;

	private JLabel Label_Title;
	private JLabel Label_Author;
	private JLabel Label_Quantity;
	private JLabel Label_Price;
	private JLabel Label_Category;
	private JLabel Label_Description;
	private JScrollPane scrollPane;

	private JTextField textField_Title;
	private JTextField textField_Author;
	private JTextField textField_Price;
	private JSpinner spinner_Quantity;
	private JComboBox<?> comboBox_Category;
	private JTextArea textArea_Description;

	private JButton Button_New;
	private JButton Button_Delete;
	private JButton Button_Update;
	private JButton Button_Reset;

	private JTable table;
	private DefaultTableModel tableModel;
	private Object[] column = { "ID", "Title", "Author", "Quantity", "Price", "Category", "Description" };
	private Object[] rowData = new Object[7];

	ArrayList<Book> myBookList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
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
	 * All logical method of the application.
	 */
	public int GenerateID() {
		int max = 1;
		if (myBookList != null && myBookList.size() > 0) {
			max = myBookList.get(0).getId();
			for (Book myBook : myBookList) {
				if (max < myBook.getId()) {
					max = myBook.getId();
				}
			}
			max++;
		}
		return max;
	}

	public Book GetDataFromTable() {
		int selectedRowIndex = table.getSelectedRow();
		int id = (int) tableModel.getValueAt(selectedRowIndex, 0);
		int quantity = (int) tableModel.getValueAt(selectedRowIndex, 3);
		int price = (int) tableModel.getValueAt(selectedRowIndex, 4);
		String title = tableModel.getValueAt(selectedRowIndex, 1).toString();
		String author = tableModel.getValueAt(selectedRowIndex, 2).toString();
		// String category = tableModel.getValueAt(selectedRowIndex, 5).toString();
		String description = tableModel.getValueAt(selectedRowIndex, 6).toString();
		Book selectedBook = new Book(id, title, author, quantity, price, description);
		return selectedBook;
	}


	public void NewBook() {
		Book newBook = new Book();
		newBook.setId(GenerateID());
		newBook.setTitle(textField_Title.getText());
		newBook.setAuthor(textField_Author.getText());
		newBook.setQuantity((int) spinner_Quantity.getValue());
		newBook.setPrice(Integer.parseInt(textField_Price.getText()));
		// newBook.setQuantity();
		newBook.setDescription(textArea_Description.getText());
		myBookList.add(newBook);
		rowData[0] = newBook.getId();
		rowData[1] = newBook.getTitle();
		rowData[2] = newBook.getAuthor();
		rowData[3] = newBook.getQuantity();
		rowData[4] = newBook.getPrice();
		// rowData[5] = b.getCategory();
		rowData[6] = newBook.getDescription();
		tableModel.addRow(rowData);
	}

	public ArrayList<Book> DeleteBook() {
		Book deleteBook = GetDataFromTable();
		myBookList.remove(deleteBook);
		tableModel.removeRow(table.getSelectedRow());
		return myBookList;
	}

	public ArrayList<Book> UpdateBook() {
		int selectedRowIndex = table.getSelectedRow();
		Book updateBook = GetDataFromTable();
		updateBook.setTitle(textField_Title.getText());
		updateBook.setAuthor(textField_Author.getText());
		updateBook.setQuantity((int) spinner_Quantity.getValue());
		updateBook.setPrice(Integer.parseInt(textField_Price.getText()));
		updateBook.setDescription(textArea_Description.getText());
		myBookList.set(selectedRowIndex, updateBook);
		tableModel.setValueAt(updateBook.getTitle(),selectedRowIndex , 1);
		tableModel.setValueAt(updateBook.getAuthor(), selectedRowIndex, 2);
		tableModel.setValueAt(updateBook.getQuantity(), selectedRowIndex, 3);
		tableModel.setValueAt(updateBook.getPrice(), selectedRowIndex, 4);
		tableModel.setValueAt(updateBook.getDescription(), selectedRowIndex, 6);
		//tableModel.setValueAt(title, selectedRowIndex, 5);
		return myBookList;
	}

	public void ResetAllField() {
		textField_Title.setText("");
		textField_Author.setText("");
		spinner_Quantity.setValue(0);
		textField_Price.setText("");
		comboBox_Category.setSelectedIndex(-1);
		textArea_Description.setText("");
		Button_Delete.setEnabled(false);
		Button_Update.setEnabled(false);
	}

	public void OpenFile() {
		JFileChooser myFileChooser = new JFileChooser(new File("c:\\"));
		myFileChooser.setDialogTitle("Open a file");
		myFileChooser.setFileFilter(new FileTypeFilter(".txt", "Text File"));
		int result = myFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File fi = myFileChooser.getSelectedFile();
			try (BufferedReader br = new BufferedReader(new FileReader(fi.getPath()));) {
				String line;
				try {
					while ((line = br.readLine()) != null) {
						String[] words = line.split(", ");
						rowData[0] = Integer.parseInt(words[0]);
						rowData[1] = words[1];
						rowData[2] = words[2];
						rowData[3] = Integer.parseInt(words[3]);
						rowData[4] = Integer.parseInt(words[4]);
						rowData[6] = words[5];
						// row[5] = words[5]; Add category cell and field
						// row[6] = words[6];
						tableModel.addRow(rowData);
						Book b = new Book(Integer.parseInt(words[0]), words[1], words[2], Integer.parseInt(words[3]),
								Integer.parseInt(words[4]), words[5]);
						myBookList.add(b);
					}
					if (br != null) {
						br.close();
					}
				} catch (ClassCastException e1) {
					JOptionPane.showMessageDialog(contentPane, e1.getMessage());
				}
			} catch (FileNotFoundException e3) {
				JOptionPane.showMessageDialog(contentPane, e3.getMessage());
			} catch (IOException e4) {
				JOptionPane.showMessageDialog(contentPane, e4.getMessage());
			}
		}
	}

	public void SaveFile() {
		JFileChooser myFileChooser = new JFileChooser(new File("c:\\"));
		myFileChooser.setDialogTitle("Save a file");
		myFileChooser.setFileFilter(new FileTypeFilter(".txt", "Text File"));
		int result = myFileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File fi = myFileChooser.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(fi.getPath());
				for (Book b : myBookList) {
					fw.write(b.toString());
					fw.flush();
				}
				fw.close();
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}
		}
	}

	/**
	 * Create the frame.
	 */

	public FPTBook() {
		setTitle("University of Greenwich - Book Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1023, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(0, 0, 1007, 28);
		contentPane.add(menuBar);

		Menu_File = new JMenu("File");
		Menu_File.setFont(new Font("Calibri", Font.PLAIN, 12));
		menuBar.add(Menu_File);

		MenuItem_OpenFile = new JMenuItem("Open File");
		MenuItem_OpenFile.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_File.add(MenuItem_OpenFile);

		MenuItem_SaveFile = new JMenuItem("Save File");
		MenuItem_SaveFile.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_File.add(MenuItem_SaveFile);

		Menu_System = new JMenu("System");
		Menu_System.setFont(new Font("Calibri", Font.PLAIN, 12));
		menuBar.add(Menu_System);

		MenuItem_Exit = new JMenuItem("Exit");
		MenuItem_Exit.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_System.add(MenuItem_Exit);

		Menu_Help = new JMenu("Help");
		Menu_Help.setFont(new Font("Calibri", Font.PLAIN, 12));
		menuBar.add(Menu_Help);

		MenuItem_About = new JMenuItem("About");
		MenuItem_About.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_Help.add(MenuItem_About);

		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setBounds(0, 26, 311, 445);
		contentPane.add(tabbedPane);

		panel_CRUD = new JPanel();
		panel_CRUD.setForeground(new Color(255, 255, 255));
		panel_CRUD.setBackground(new Color(0, 0, 255));
		tabbedPane.addTab("CRUD", null, panel_CRUD, null);
		panel_CRUD.setLayout(null);

		panel_Search = new JPanel();
		panel_Search.setBackground(new Color(0, 0, 255));
		tabbedPane.addTab("Search", null, panel_Search, null);
		panel_Search.setLayout(null);

		Label_Title = new JLabel("Title");
		Label_Title.setForeground(new Color(255, 255, 255));
		Label_Title.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Title.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Title.setBounds(10, 11, 46, 14);
		panel_CRUD.add(Label_Title);

		textField_Title = new JTextField();
		textField_Title.setBounds(76, 8, 221, 20);
		panel_CRUD.add(textField_Title);
		textField_Title.setColumns(10);

		Label_Author = new JLabel("Author");
		Label_Author.setForeground(new Color(255, 255, 255));
		Label_Author.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Author.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Author.setBounds(10, 39, 46, 14);
		panel_CRUD.add(Label_Author);

		textField_Author = new JTextField();
		textField_Author.setColumns(10);
		textField_Author.setBounds(76, 36, 221, 20);
		panel_CRUD.add(textField_Author);

		Label_Quantity = new JLabel("Quantity");
		Label_Quantity.setForeground(new Color(255, 255, 255));
		Label_Quantity.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Quantity.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Quantity.setBounds(10, 67, 46, 14);
		panel_CRUD.add(Label_Quantity);

		Label_Price = new JLabel("Price");
		Label_Price.setForeground(new Color(255, 255, 255));
		Label_Price.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Price.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Price.setBounds(10, 95, 46, 14);
		panel_CRUD.add(Label_Price);

		textField_Price = new JTextField();
		textField_Price.setColumns(10);
		textField_Price.setBounds(76, 92, 221, 20);
		panel_CRUD.add(textField_Price);

		Label_Category = new JLabel("Category");
		Label_Category.setForeground(new Color(255, 255, 255));
		Label_Category.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Category.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Category.setBounds(10, 123, 46, 14);
		panel_CRUD.add(Label_Category);

		Label_Description = new JLabel("Description");
		Label_Description.setForeground(new Color(255, 255, 255));
		Label_Description.setFont(new Font("Calibri", Font.PLAIN, 11));
		Label_Description.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Description.setBounds(10, 158, 58, 14);
		panel_CRUD.add(Label_Description);

		textArea_Description = new JTextArea();
		textArea_Description.setBounds(76, 158, 221, 81);
		panel_CRUD.add(textArea_Description);

		Button_New = new JButton("New");
		Button_New.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_New.setBounds(56, 250, 89, 23);
		panel_CRUD.add(Button_New);

		Button_Delete = new JButton("Delete");
		Button_Delete.setEnabled(false);
		Button_Delete.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_Delete.setBounds(155, 250, 89, 23);
		panel_CRUD.add(Button_Delete);

		Button_Update = new JButton("Update");
		Button_Update.setEnabled(false);
		Button_Update.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_Update.setBounds(56, 295, 89, 23);
		panel_CRUD.add(Button_Update);

		comboBox_Category = new JComboBox<Object>();
		comboBox_Category.setBounds(76, 119, 221, 22);
		panel_CRUD.add(comboBox_Category);

		spinner_Quantity = new JSpinner();
		spinner_Quantity.setBounds(76, 67, 221, 20);
		panel_CRUD.add(spinner_Quantity);

		Button_Reset = new JButton("Reset");
		Button_Reset.setFont(new Font("Calibri", Font.BOLD, 11));
		Button_Reset.setBounds(155, 295, 89, 23);
		panel_CRUD.add(Button_Reset);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(314, 26, 693, 434);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		tableModel.setColumnIdentifiers(column);
		table.setModel(tableModel);
		scrollPane.setViewportView(table);

		// 1. Open file
		MenuItem_OpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenFile();
			}
		});

		// 2. Save File
		MenuItem_SaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveFile();
			}
		});

		// 3. About
		MenuItem_About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "FPT Book Management App v1.0 - By Le Nguyen Hoang Long",
						" System Info!", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// 4. Exit Application
		MenuItem_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// 5. Reset
		Button_Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetAllField();
			}
		});

		// 6. Button new - Use to create new book
		Button_New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewBook();
				ResetAllField();
				JOptionPane.showMessageDialog(contentPane, "You have add successfully", "Success!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// 7. Button delete - Use to delete a book in list
		Button_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBook();
				JOptionPane.showMessageDialog(contentPane, "You have deleted successfully", "Success!",
						JOptionPane.INFORMATION_MESSAGE);
				ResetAllField();
				Button_Delete.setEnabled(false);
				Button_Update.setEnabled(false);
			}
		});

		// 8. Button update - Use to update a book in list
		Button_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateBook();
				JOptionPane.showMessageDialog(contentPane, "You have updated successfully", "Success!",
						JOptionPane.INFORMATION_MESSAGE);
				ResetAllField();
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Book book = GetDataFromTable();
				textField_Title.setText(book.getTitle());
				textField_Author.setText(book.getAuthor());
				spinner_Quantity.setValue(book.getQuantity());
				textField_Price.setText(Integer.toString(book.getPrice()));
				textArea_Description.setText(book.getDescription());
				Button_Delete.setEnabled(true);
				Button_Update.setEnabled(true);
			}
		});

	}

}
