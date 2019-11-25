import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.util.*;
import java.util.logging.Handler;

public class StockTradingInterface {

	public JFrame tradingPage;
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockTradingInterface tradingPage = new StockTradingInterface();
					tradingPage.tradingPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
	public StockTradingInterface() {
		initialize();
		clock();
		date();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		tradingPage = new JFrame("TD Pro");
		tradingPage.setSize(960, 720);
		tradingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		tradingPage.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnProgramSettings = new JMenu("Program settings");
		mnFile.add(mnProgramSettings);
		
		JMenu mnNewMenu = new JMenu("Check for Updates");
		mnFile.add(mnNewMenu);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
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
				// TODO Auto-generated method stub
				buyForm buy = new buyForm();
				tradingPage.setVisible(false);
				buy.frame.setVisible(true);
			}
			
		});
		
	    JButton sell = new JButton("Sell");
	    sell.setBounds(100, 11, 90, 23);
	    functionsContainer.add(sell);
	    
	    JButton report = new JButton("View Report");
	    report.setBounds(190, 11, 100, 23);
	    functionsContainer.add(report);
	    
	    JButton vPortfolio = new JButton("View Portfolio");
	    vPortfolio.setBounds(290, 11, 100, 23);
	    functionsContainer.add(vPortfolio);
	    
	    JButton aPortfolio = new JButton("Add company into Portfolio");
	    aPortfolio.setBounds(390, 11, 170, 23);
	    functionsContainer.add(aPortfolio);
	    
	    JButton orders = new JButton("My Orders");
	    orders.setBounds(560, 11, 90, 23);
	    functionsContainer.add(orders);
	    
	    JButton wallet = new JButton("My Wallet");
	    wallet.setBounds(650, 11, 90, 23);
	    functionsContainer.add(wallet);
	    
	    JButton history = new JButton("View Trade History");
	    functionsContainer.add(history);
	    
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
	    
	    String[] column_header = {"ID", "Name", "Volume", "Buy", "Buy Volume", "Sell", "Sell Volume", "High", "Low"};
	    String[][] row = {};
	    table = new JTable();
	    table.setBounds(23, 70, 899, 555);
	    tradingPage.getContentPane().add(table);
	    
	    JPanel searchPanel = new JPanel();
	    searchPanel.setBounds(23, 37, 282, 28);
	    tradingPage.getContentPane().add(searchPanel);
	    searchPanel.setLayout(null);
	    
	    textField = new JTextField();
	    textField.setBounds(77, 0, 205, 28);
	    searchPanel.add(textField);
	    textField.setColumns(10);
	    
	    JLabel lblSearch = new JLabel("Search:");
	    lblSearch.setBounds(21, 7, 46, 14);
	    searchPanel.add(lblSearch);
	    
	    JPanel viewMarket = new JPanel();
	    viewMarket.setBounds(309, 37, 424, 28);
	    tradingPage.getContentPane().add(viewMarket);
	    viewMarket.setLayout(null);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 0, 404, 28);
	    viewMarket.add(scrollPane);
	    
	    
	    
	    
	}
}

