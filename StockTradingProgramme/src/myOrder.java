import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class myOrder {

	public String username;
	public int uid;
	public JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myOrder window = new myOrder("user 1", 1);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public myOrder(String name, int id) {
		this.username = name;
		this.uid = id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblUsername = new JLabel("Username");
		frame.getContentPane().add(lblUsername, BorderLayout.NORTH);
		
		String[] column_header = {"Name", "Price", "Quantity", "Action", "Status"};
		String[][] row = {{"1", "2", "3", "4", "5"}, {"1", "2", "3", "4", "5"}};
		table = new JTable(row, column_header);
		JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(23, 70, 899, 555);
	    frame.getContentPane().add(scrollPane_1);
	    table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
	    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    scrollPane_1.setViewportView(table);
	    
	    parsing parse = new parsing(this.username, uid);
		
	    
	}

}

class parsing extends buyForm{

	public parsing(String name, int id) {
		super(name);
		// TODO Auto-generated constructor stub
		System.out.println(this.name);
		System.out.println(price);
		System.out.println(quantity);
	}
	
	
}