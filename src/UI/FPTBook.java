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
import Validation.FormValidator;
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
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerNumberModel;
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
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableRowSorter;

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

	private JTextField textField_Search;
	private JComboBox<?> comboBox_SCategory;
	private JLabel lblTitle_Search;
	private JLabel lblCategory_Search;
	private JButton btnSearchByCategory;
	private JLabel lbl_SearchBook;
	private JLabel Label_BookManagement;
	private JButton btnSearch;

	ArrayList<Book> myBookList = new ArrayList<>();
	String category[] = { "Arts & Music", "Biographies", "Business", "Comics", "Computer & Tech", "Cooking",
			"Edu & Reference", "Entertainment", "Health & Fitness", "History", "Hobbies & Crafts", "Home & Garden",
			"Horror", "Kids", "Literature & Fiction", "Medical", "Mysteries", "Parenting", "Religion", "Romance",
			"Scifi & Fantasy", "Science & Math", "Self-Help", "Social Sciences", "Sports", "Teen", "Travel",
			"True Crime", "Westerns" };

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
		String category = tableModel.getValueAt(selectedRowIndex, 5).toString();
		String description = tableModel.getValueAt(selectedRowIndex, 6).toString();
		Book selectedBook = new Book(id, title, author, quantity, price, category, description);
		return selectedBook;
	}

	public void NewBook() {
		Book newBook = new Book();
		newBook.setId(GenerateID());
		newBook.setTitle(textField_Title.getText());
		newBook.setAuthor(textField_Author.getText());
		newBook.setQuantity((int) spinner_Quantity.getValue());
		newBook.setPrice(Integer.parseInt(textField_Price.getText()));
		newBook.setCategory(comboBox_Category.getSelectedItem().toString());
		newBook.setDescription(textArea_Description.getText());
		myBookList.add(newBook);
		rowData[0] = newBook.getId();
		rowData[1] = newBook.getTitle();
		rowData[2] = newBook.getAuthor();
		rowData[3] = newBook.getQuantity();
		rowData[4] = newBook.getPrice();
		rowData[5] = newBook.getCategory();
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
		updateBook.setCategory(comboBox_Category.getSelectedItem().toString());
		updateBook.setDescription(textArea_Description.getText());
		myBookList.set(selectedRowIndex, updateBook);
		tableModel.setValueAt(updateBook.getTitle(), selectedRowIndex, 1);
		tableModel.setValueAt(updateBook.getAuthor(), selectedRowIndex, 2);
		tableModel.setValueAt(updateBook.getQuantity(), selectedRowIndex, 3);
		tableModel.setValueAt(updateBook.getPrice(), selectedRowIndex, 4);
		tableModel.setValueAt(updateBook.getDescription(), selectedRowIndex, 6);
		tableModel.setValueAt(updateBook.getCategory(), selectedRowIndex, 5);
		return myBookList;
	}

	public void ResetAllField() {
		textField_Title.setText("");
		textField_Author.setText("");
		spinner_Quantity.setValue(0);
		textField_Price.setText("");
		comboBox_Category.setSelectedIndex(0);
		textArea_Description.setText("");
		Button_Delete.setEnabled(false);
		Button_Update.setEnabled(false);
	}

	public void RefreshTable() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
	}

	private void SearchByCategory(String category) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(tr);
		if (category != "") {
			tr.setRowFilter(RowFilter.regexFilter(category));
		} else {
			table.setRowSorter(tr);
		}
	}

	private void Search(String searchString) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(tr);
		if (searchString != "") {
			tr.setRowFilter(RowFilter.regexFilter(searchString));
		} else {
			table.setRowSorter(tr);
		}
	}

	public void OpenFile() {
		// Clear list and table before open file
		tableModel.setRowCount(0);
		myBookList.clear();
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
						rowData[5] = words[5];
						rowData[6] = words[6];
						tableModel.addRow(rowData);
						Book b = new Book(Integer.parseInt(words[0]), words[1], words[2], Integer.parseInt(words[3]),
								Integer.parseInt(words[4]), words[5], words[6]);
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
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(category);

		setIconImage(Toolkit.getDefaultToolkit().getImage(FPTBook.class.getResource("/icon/logo-fpt.jpg")));
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
		Menu_File.setFont(new Font("Calibri", Font.PLAIN, 13));
		menuBar.add(Menu_File);

		MenuItem_OpenFile = new JMenuItem("Open File");
		MenuItem_OpenFile.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_File.add(MenuItem_OpenFile);

		MenuItem_SaveFile = new JMenuItem("Save File");
		MenuItem_SaveFile.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_File.add(MenuItem_SaveFile);

		Menu_System = new JMenu("System");
		Menu_System.setFont(new Font("Calibri", Font.PLAIN, 13));
		menuBar.add(Menu_System);

		MenuItem_Exit = new JMenuItem("Exit");
		MenuItem_Exit.setFont(new Font("Calibri", Font.PLAIN, 12));
		Menu_System.add(MenuItem_Exit);

		Menu_Help = new JMenu("Help");
		Menu_Help.setFont(new Font("Calibri", Font.PLAIN, 13));
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
		panel_CRUD.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("CRUD", null, panel_CRUD, null);
		panel_CRUD.setLayout(null);

		panel_Search = new JPanel();
		panel_Search.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Search", null, panel_Search, null);
		panel_Search.setLayout(null);

		comboBox_SCategory = new JComboBox<>(comboModel);
		comboBox_SCategory.setSelectedIndex(0);
		comboBox_SCategory.setFont(new Font("Calibri", Font.PLAIN, 11));
		comboBox_SCategory.setBounds(92, 117, 204, 22);
		panel_Search.add(comboBox_SCategory);

		lblTitle_Search = new JLabel("Title / Author");
		lblTitle_Search.setFont(new Font("Calibri", Font.BOLD, 13));
		lblTitle_Search.setBounds(10, 55, 92, 14);
		panel_Search.add(lblTitle_Search);

		lblCategory_Search = new JLabel("Category");
		lblCategory_Search.setFont(new Font("Calibri", Font.BOLD, 13));
		lblCategory_Search.setBounds(10, 121, 67, 14);
		panel_Search.add(lblCategory_Search);

		btnSearchByCategory = new JButton("Search By Category");

		btnSearchByCategory.setForeground(new Color(255, 255, 255));
		btnSearchByCategory.setBackground(new Color(13, 110, 253));
		btnSearchByCategory.setFont(new Font("Calibri", Font.BOLD, 14));
		btnSearchByCategory.setBounds(141, 150, 155, 23);
		panel_Search.add(btnSearchByCategory);

		lbl_SearchBook = new JLabel("Search Book ");
		lbl_SearchBook.setFont(new Font("Calibri Light", Font.BOLD, 21));
		lbl_SearchBook.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_SearchBook.setBounds(10, 11, 286, 35);
		panel_Search.add(lbl_SearchBook);

		textField_Search = new JTextField();
		textField_Search.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField_Search.setBounds(92, 52, 204, 20);
		panel_Search.add(textField_Search);
		textField_Search.setColumns(10);

		btnSearch = new JButton("Search ");
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Calibri", Font.BOLD, 14));
		btnSearch.setBackground(new Color(13, 110, 253));
		btnSearch.setBounds(141, 83, 155, 23);
		panel_Search.add(btnSearch);

		Label_Title = new JLabel("Title");
		Label_Title.setForeground(new Color(0, 0, 0));
		Label_Title.setFont(new Font("Calibri", Font.BOLD, 13));
		Label_Title.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Title.setBounds(10, 56, 46, 14);
		panel_CRUD.add(Label_Title);

		textField_Title = new JTextField();
		textField_Title.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField_Title.setBounds(76, 51, 221, 20);
		panel_CRUD.add(textField_Title);
		textField_Title.setColumns(10);

		Label_Author = new JLabel("Author");
		Label_Author.setForeground(new Color(0, 0, 0));
		Label_Author.setFont(new Font("Calibri", Font.BOLD, 13));
		Label_Author.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Author.setBounds(10, 84, 46, 14);
		panel_CRUD.add(Label_Author);

		textField_Author = new JTextField();
		textField_Author.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField_Author.setColumns(10);
		textField_Author.setBounds(76, 79, 221, 20);
		panel_CRUD.add(textField_Author);

		Label_Quantity = new JLabel("Quantity");
		Label_Quantity.setForeground(new Color(0, 0, 0));
		Label_Quantity.setFont(new Font("Calibri", Font.BOLD, 13));
		Label_Quantity.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Quantity.setBounds(10, 115, 56, 14);
		panel_CRUD.add(Label_Quantity);

		Label_Price = new JLabel("Price");
		Label_Price.setForeground(new Color(0, 0, 0));
		Label_Price.setFont(new Font("Calibri", Font.BOLD, 13));
		Label_Price.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Price.setBounds(10, 146, 46, 14);
		panel_CRUD.add(Label_Price);

		textField_Price = new JTextField();
		textField_Price.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField_Price.setColumns(10);
		textField_Price.setBounds(76, 141, 221, 20);
		panel_CRUD.add(textField_Price);

		Label_Category = new JLabel("Category");
		Label_Category.setForeground(new Color(0, 0, 0));
		Label_Category.setFont(new Font("Calibri", Font.BOLD, 13));
		Label_Category.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Category.setBounds(10, 178, 56, 14);
		panel_CRUD.add(Label_Category);

		Label_Description = new JLabel("Description");
		Label_Description.setForeground(new Color(0, 0, 0));
		Label_Description.setFont(new Font("Calibri", Font.BOLD, 13));
		Label_Description.setHorizontalAlignment(SwingConstants.LEFT);
		Label_Description.setBounds(10, 212, 70, 14);
		panel_CRUD.add(Label_Description);

		textArea_Description = new JTextArea();
		textArea_Description.setFont(new Font("Calibri", Font.PLAIN, 12));
		textArea_Description.setBounds(98, 205, 199, 81);
		panel_CRUD.add(textArea_Description);

		Button_New = new JButton("New");
		Button_New.setForeground(Color.WHITE);
		Button_New.setBackground(new Color(13, 110, 253));
		Button_New.setFont(new Font("Calibri", Font.BOLD, 14));
		Button_New.setBounds(56, 297, 89, 23);
		panel_CRUD.add(Button_New);

		Button_Delete = new JButton("Delete");
		Button_Delete.setForeground(Color.WHITE);
		Button_Delete.setBackground(new Color(220, 53, 69));
		Button_Delete.setEnabled(false);
		Button_Delete.setFont(new Font("Calibri", Font.BOLD, 14));
		Button_Delete.setBounds(155, 297, 89, 23);
		panel_CRUD.add(Button_Delete);

		Button_Update = new JButton("Update");
		Button_Update.setForeground(Color.WHITE);
		Button_Update.setBackground(new Color(108, 117, 125));
		Button_Update.setEnabled(false);
		Button_Update.setFont(new Font("Calibri", Font.BOLD, 14));
		Button_Update.setBounds(56, 342, 89, 23);
		panel_CRUD.add(Button_Update);

		comboBox_Category = new JComboBox<>(comboModel);
		comboBox_Category.setFont(new Font("Calibri", Font.PLAIN, 12));
		comboBox_Category.setBounds(76, 172, 221, 22);
		panel_CRUD.add(comboBox_Category);

		spinner_Quantity = new JSpinner();
		spinner_Quantity.setFont(new Font("Calibri", Font.PLAIN, 12));
		spinner_Quantity.setBounds(76, 110, 221, 20);
		panel_CRUD.add(spinner_Quantity);

		Button_Reset = new JButton("Reset");
		Button_Reset.setForeground(Color.WHITE);
		Button_Reset.setBackground(new Color(25, 135, 84));
		Button_Reset.setFont(new Font("Calibri", Font.BOLD, 14));
		Button_Reset.setBounds(155, 342, 89, 23);
		panel_CRUD.add(Button_Reset);

		Label_BookManagement = new JLabel("Book Management");
		Label_BookManagement.setForeground(Color.BLACK);
		Label_BookManagement.setBackground(Color.WHITE);
		Label_BookManagement.setFont(new Font("Calibri Light", Font.BOLD, 21));
		Label_BookManagement.setHorizontalAlignment(SwingConstants.CENTER);
		Label_BookManagement.setBounds(10, 11, 287, 34);
		panel_CRUD.add(Label_BookManagement);

		JButton btnNewButton = new JButton("Reload Data");
		btnNewButton.setBounds(56, 376, 188, 23);
		panel_CRUD.add(btnNewButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(314, 26, 693, 445);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table.setFont(new Font("Calibri", Font.PLAIN, 14));
		table.setBackground(Color.LIGHT_GRAY);
		tableModel.setColumnIdentifiers(column);
		table.setModel(tableModel);
		scrollPane.setViewportView(table);

		// 1. Add validation to all field in CRUD Panel
		textField_Title.setName("string");
		textField_Title.setInputVerifier(FormValidator.getVerifier());

		textField_Author.setName("string");
		textField_Author.setInputVerifier(FormValidator.getVerifier());

		textField_Price.setName("integer");
		textField_Price.setInputVerifier(FormValidator.getVerifier());

		spinner_Quantity.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spinner_Quantity.setInputVerifier(FormValidator.getVerifier());

		// 2. Add validation to all field in Search Panel

		// 3. Open File
		MenuItem_OpenFile.addActionListener(e -> OpenFile());

		// 4. Save File
		MenuItem_SaveFile.addActionListener(e -> SaveFile());

		// 5. About
		MenuItem_About.addActionListener(e -> {
		    JOptionPane.showMessageDialog(contentPane,
		            "FPT Book Management App v1.0 - By Le Nguyen Hoang Long",
		            "System Info", JOptionPane.INFORMATION_MESSAGE);
		});

		// 6. Exit Application
		MenuItem_Exit.addActionListener(e -> System.exit(0));

		// 6. Reset
		Button_Reset.addActionListener(e -> ResetAllField());

		// 7. Button new - Use to create new book
		Button_New.addActionListener((ActionEvent e) -> {
		    if (FormValidator.areAllComponentsValid(panel_CRUD)) {
		        NewBook();
		        ResetAllField();
		        JOptionPane.showMessageDialog(contentPane, "You have added successfully", "Success!",
		                JOptionPane.INFORMATION_MESSAGE);
		    } else {
		        JOptionPane.showMessageDialog(contentPane, "Error when adding new book", "Error",
		                JOptionPane.ERROR_MESSAGE);
		    }
		});

		// 8. Button delete - Use to delete a book in list
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

		// 9. Button update - Use to update a book in list
		Button_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateBook();
				JOptionPane.showMessageDialog(contentPane, "You have updated successfully", "Success!",
						JOptionPane.INFORMATION_MESSAGE);
				ResetAllField();
			}
		});

		// 10.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Book book = GetDataFromTable();
				textField_Title.setText(book.getTitle());
				textField_Author.setText(book.getAuthor());
				spinner_Quantity.setValue(book.getQuantity());
				textField_Price.setText(Integer.toString(book.getPrice()));
				comboBox_Category.setSelectedItem(book.getCategory());
				textArea_Description.setText(book.getDescription());
				Button_Delete.setEnabled(true);
				Button_Update.setEnabled(true);
			}
		});

		// 11.
		btnSearchByCategory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String category = comboBox_SCategory.getSelectedItem().toString();
				SearchByCategory(category);
			}
		});

		// 12.
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String str = textField_Search.getText();
				if (str.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Your title field is Empty");
				} else {
					Search(str);
				}
				textField_Search.setText("");
			}
		});

		// 13.
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RefreshTable();
			}
		});
	}
}
