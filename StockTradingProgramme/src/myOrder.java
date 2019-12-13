import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class myOrder {

	protected ServerConnection server;
	public JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					myOrder window = new myOrder(server);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public myOrder(ServerConnection server) throws IOException, ParseException {
		this.server = server;
		initialize();
	}
	
	public void setData() throws IOException, ParseException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		dataItems = server.getOrderData(server.uid);
		if(dataItems == null) {
			JOptionPane.showMessageDialog(null, "Error is occured when retrieving data");
		}else {	
			for(Object[] temp: dataItems) {
				Object[] row = new Object[9];
				row[0] = temp[0];
				row[1] = Integer.parseInt(temp[1].toString()) == 0 ? "Buy" : "Sell";
				row[2] = temp[2];
				row[3] = temp[3];
				((DefaultTableModel)table.getModel()).addRow(row);
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	private void initialize() throws IOException, ParseException {
		frame = new JFrame();
		frame.setBounds(100, 100, 644, 547);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		panel.setBounds(20, 11, 598, 58);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnBackToInterface = new JButton("Back To Interface");
		btnBackToInterface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBackToInterface.setBounds(451, 11, 137, 35);
		panel.add(btnBackToInterface);
		
//		JButton btnAddComp = new JButton("Add New Company");
//		btnAddComp.setBounds(282, 11, 159, 35);
//		panel.add(btnAddComp);
		
		JLabel lblListOfComp = new JLabel("View Orders");
		lblListOfComp.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblListOfComp.setBounds(0, 21, 192, 37);
		panel.add(lblListOfComp);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 608, 420);
		frame.getContentPane().add(scrollPane);
		
		String[] column_header = {"Company Name", "Action", "Price", "Quantity"};
	    String[][] row = {
	    		{"User 1", "Microsoft Corp.", "Buy", "$43.99", "1200"},
	    		{"User 2", "Apple Corp.", "Sell", "$39.88", "900"},
	    		{"User 3", "Company 3", "Sell", "$24.88", "500"},
	    		{"User 4", "Company 4", "Buy", "$109.75", " 1300"},
	    		{"User 5", "Company 5", "Buy", "$129.14", "299"}
	    	};
	    
	    DefaultTableModel model = new DefaultTableModel(column_header, 0);
	    table = new JTable(model) {
	    	public boolean isCellEditable(int row, int column) {
	    		
                return false;           
	    	}
	    };
	   
	    
	    

	    table.getColumnModel().getColumn(0).setPreferredWidth(150);
	    table.getColumnModel().getColumn(1).setPreferredWidth(75);
	    table.getColumnModel().getColumn(2).setPreferredWidth(70);
	    table.getColumnModel().getColumn(3).setPreferredWidth(70);
	    
	    setData();
	    
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	    for(int i = 1; i < table.getColumnCount(); i++) {
	    	table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
	    }
	    table.setRowHeight(40);
	    table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
	    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    scrollPane.setViewportView(table);
		
	    
	}

}

//class parsing extends buyForm{
//
//	public parsing(String name, int id) {
//		super(name);
//		// TODO Auto-generated constructor stub
//		System.out.println(this.name);
//		System.out.println(price);
//		System.out.println(quantity);
//	}
//	
//	
//}