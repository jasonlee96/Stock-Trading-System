import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.data.xy.OHLCDataItem;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.util.*;
import java.util.logging.Handler;

public class StockTradingInterface {

	public String username;
	public String uid;
	public JFrame tradingPage;
	public int selectedRow = -1;
	private DefaultTableModel model;
	private JMenuBar menu;
	private JTable table;
	private JTextField textField;
	private JLabel lblClock;
	private JLabel lblDate;
	private JButton buy;
	private JButton sell;
	private JButton report;
	private JButton aPortfolio;
	private JButton vPortfolio;
	private JButton order;
	private JButton wallet;
	public LoginForm login;
	public JDialog proc;
	protected ServerConnection server;
	public StockTradingInterface main;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//
//		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StockTradingInterface tradingPage = new StockTradingInterface();
//					tradingPage.tradingPage.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public void clock()
	{
		Thread clock = new Thread()
		{
			public void run()
			{
				while (true)
				{
					try {
						while(true)
						{
							Calendar cal = new GregorianCalendar();
							
							int second = cal.get(Calendar.SECOND);
							int minute = cal.get(Calendar.MINUTE);
							int hour = cal.get(Calendar.HOUR);
							
							lblClock.setText("Time: " + hour + ":" + minute + ":" + second);
							sleep(1000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		clock.start();
		
	}
	
	public void getTableData() throws IOException, ParseException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		dataItems = server.getCompanyData();
		if(dataItems == null) {
			JOptionPane.showMessageDialog(null, "Error is occured when retrieving data");
		}else {	
			for(Object[] temp: dataItems) {
				Object[] row = new Object[9];
				row[0] = temp[0];
				row[1] = temp[1];
				row[2] = temp[2];
				row[3] = String.format("%.2f", temp[3]);
				row[4] = temp[4];
				row[5] = String.format("%.2f", temp[5]);
				row[6] = temp[6];
				row[7] = String.format("%.2f", temp[7]);
				row[8] = String.format("%.2f", temp[8]);
				((DefaultTableModel)table.getModel()).addRow(row);
			}
			this.proc.dispose();
		}
	}
	
	public void filterTable(String keyword) {
		TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<DefaultTableModel>((DefaultTableModel) table.getModel());
		table.setRowSorter(rowSorter);
		
		rowSorter.setRowFilter(RowFilter.regexFilter(keyword));
	}
	
	public void date()
	{
		Calendar cal2 = new GregorianCalendar();
		int day = cal2.get(Calendar.DAY_OF_MONTH);
		int month = cal2.get(Calendar.MONTH);
		int year = cal2.get(Calendar.YEAR);
		
		lblDate.setText("Date: " + year + "/" + month + "/" + day);
	}
	
	/**
	 * Create the application.
	 */
	public StockTradingInterface(LoginForm login, ServerConnection server, String uid, String username, JDialog proc) {
		this.login = login;
		this.server = server;
		this.uid = uid;
		this.username = username;
		this.proc = proc;
		try {
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clock();
		date();
		this.main = this;
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	
	private void initialize() throws IOException, ParseException {
		tradingPage = new JFrame("TD Pro");
		tradingPage.setSize(960, 720);
		tradingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tradingPage.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				try {
					String res = server.Logout(uid);
					if(res.startsWith("Success")) {
						tradingPage.dispose();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		   
			public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
				
		    }
		});
		
		JMenuBar menuBar = new JMenuBar();
		tradingPage.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnProgramSettings = new JMenu("Program settings");
		mnFile.add(mnProgramSettings);
		
		JMenuItem mntmCheckForUpdates = new JMenuItem("Check for updates");
		mnFile.add(mntmCheckForUpdates);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String res = server.Logout(uid);
					if(res.startsWith("Success")) {
						tradingPage.dispose();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		mnSettings.add(mntmLogout);
		tradingPage.getContentPane().setLayout(null);
		
		JPanel functionsContainer = new JPanel();
		functionsContainer.setBounds(23, 0, 750, 37);
		tradingPage.getContentPane().add(functionsContainer);
		functionsContainer.setLayout(null);
	
		JButton buy = new JButton("Buy");
		buy.setBounds(10, 11, 90, 23);
		functionsContainer.add(buy);
		buy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedRow >= 0) {
					
					buyForm buy = new buyForm(server, main, table.getValueAt(selectedRow, 0).toString(), table.getValueAt(selectedRow, 1).toString());
					
					buy.frame.setVisible(true);
					
//					myOrder order = new myOrder("user1", 1);
//					order.frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null,"Please Select A Company From Table");
				}
			}


		});
		
	    JButton sell = new JButton("Sell");
	    sell.setBounds(100, 11, 90, 23);
	    functionsContainer.add(sell);
	    sell.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedRow >= 0) {
					sellForm sell = new sellForm(server, main, table.getValueAt(selectedRow, 0).toString(), table.getValueAt(selectedRow, 1).toString());
					sell.frame.setVisible(true);

				}else {
					JOptionPane.showMessageDialog(null,"Please Select A Company From Table");
				}
			}
	    	
	    	
	    });
	    
	    JButton report = new JButton("Refresh Table");
	    report.setBounds(190, 11, 120, 23);
	    functionsContainer.add(report);
	    report.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StockTradingInterface sti = new StockTradingInterface(login, server, uid, username, proc);
				sti.tradingPage.setVisible(true);
				main.tradingPage.dispose();
				
			}
	    	
	    });
	    
	    JButton vPortfolio = new JButton("View Portfolio");
	    vPortfolio.setBounds(310, 11, 126, 23);
	    functionsContainer.add(vPortfolio);
	    vPortfolio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PortfolioView pv = new PortfolioView(server, main);
				pv.frame.setVisible(true);
			}
	    	
	    });
	    
	    JButton orders = new JButton("My Orders");
	    orders.setBounds(437, 11, 110, 23);
	    functionsContainer.add(orders);
	    orders.addActionListener(new ActionListener() {
	    	
			@Override
			public void actionPerformed(ActionEvent e) {
					myOrder order;
					try {
						order = new myOrder(server);
						order.frame.setVisible(true);
					} catch (IOException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			}
	    	
	    });
	    
	    JButton wallet = new JButton("My Wallet");
	    wallet.setBounds(548, 11, 110, 23);
	    functionsContainer.add(wallet);
	    wallet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myWallet mw;
				try {
					mw = new myWallet(server);
					mw.frame.setVisible(true);
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
	    	
	    });
	    
	    //JButton history = new JButton("View Trade History");
	    //functionsContainer.add(history);
	    
	    
	    //Time
	    JPanel clockPanel = new JPanel();
	    clockPanel.setBounds(773, 0, 161, 28);
	    tradingPage.getContentPane().add(clockPanel);
	    clockPanel.setLayout(null);
	    
	    lblClock = new JLabel("Clock");
	    lblClock.setBounds(10, 11, 149, 14);
	    clockPanel.add(lblClock);
	    
	    //Date
	    JPanel datePanel = new JPanel();
	    datePanel.setBounds(773, 27, 161, 32);
	    tradingPage.getContentPane().add(datePanel);
	    datePanel.setLayout(null);
	    
	    lblDate = new JLabel("Date");
	    lblDate.setBounds(10, 11, 149, 14);
	    datePanel.add(lblDate);
	    
	    
	    //Table display
	    String[] column_header = {"ID", "Name", "Volume", "Buy", "Buy Volume", "Sell", "Sell Volume", "High", "Low"};

	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(23, 70, 899, 555);
	    tradingPage.getContentPane().add(scrollPane_1);
	    model = new DefaultTableModel(column_header, 0);
	    table = new JTable(model){
	    	public boolean isCellEditable(int row, int column) {
	    		
                return false;           
	    	}
	    };
	    table.getColumnModel().getColumn(0).setPreferredWidth(55);
	    table.getColumnModel().getColumn(1).setPreferredWidth(175);
	    getTableData();
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	    for(int i = 2; i < table.getColumnCount(); i++) {
	    	table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
	    }
	    table.setRowHeight(40);
	    
	    table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				selectedRow = table.getSelectedRow();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
	    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    scrollPane_1.setViewportView(table);
	    

	    JPanel searchPanel = new JPanel();
	    searchPanel.setBounds(23, 37, 282, 28);
	    tradingPage.getContentPane().add(searchPanel);
	    searchPanel.setLayout(null);
	    
	    textField = new JTextField();
	    textField.setBounds(77, 0, 205, 28);
	    searchPanel.add(textField);
	    textField.setColumns(10);
	    textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				filterTable(textField.getText().toString());
			}
	    	
	    });
	    
	    JLabel lblSearch = new JLabel("Search:");
	    lblSearch.setBounds(21, 7, 46, 14);
	    searchPanel.add(lblSearch);
	    
	    JButton btnViewCompanyDetail = new JButton("View Company Detail");
	    btnViewCompanyDetail.setBounds(315, 40, 174, 23);
	    tradingPage.getContentPane().add(btnViewCompanyDetail);
	    
	    JButton aPortfolio_1 = new JButton("Add company into Portfolio");
	    aPortfolio_1.setBounds(493, 40, 188, 23);
	    tradingPage.getContentPane().add(aPortfolio_1);
	    aPortfolio_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(selectedRow >= 0) {
					try {
						if(server.addPortfolio(table.getValueAt(selectedRow, 0).toString())){
							PortfolioView pv = new PortfolioView(server, main);
							pv.frame.setVisible(true);
						}else {
							JOptionPane.showMessageDialog(null,"This company already in your Portfolio");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}else {
					JOptionPane.showMessageDialog(null,"Please Select A Company From Table");
				}
			}
	    	
	    });
	    btnViewCompanyDetail.addActionListener(new ActionListener() {
	    	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedRow >= 0) {
					// TODO Auto-generated method stub
//					buyForm buy = new buyForm("Microsoft");
//					//tradingPage.setVisible(false);
//					buy.frame.setVisible(true);
					
					StockDetail stk_detail;
					try {
						stk_detail = new StockDetail(server, main, table.getValueAt(selectedRow, 0), table.getValueAt(selectedRow, 1));
						stk_detail.frame.setVisible(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Please Select Company from Table");
				}
			}
	    	
	    });

	}
}

class searchBar extends KeyAdapter{
	public searchBar(StockTradingInterface stockTradingInterface) {
		// TODO Auto-generated constructor stub
		
	}

}