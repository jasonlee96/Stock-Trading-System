import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.ParseException;
import java.time.Clock;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

public class buyForm {

	public StockTradingInterface main;
	public JFrame frame;
	public JTextField textField;
	public JTextField textField_1;
	public JLabel lblConfirmationPanel;
	private JLabel lblClock;
	private JLabel lblDate;
	protected ServerConnection server;
	public String symbol;
	public String title;
	public float price;
	public JButton btnBuy;
	public int quantity;
	private KeyListener listener; 
	
	public void clock()
	{
		Thread clock1 = new Thread()
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
		clock1.start();
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
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					buyForm window = new buyForm("Microsoft");
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public buyForm(ServerConnection server, StockTradingInterface main, String symbol, String title) {
		this.symbol = symbol;
		this.server = server;
		this.title = title;
		this.main = main;
		initialize();
		clock();
		date();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Buy Order");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(588, 210);
		frame.setBounds(100, 100, 590, 210);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(0, 10, 80, 40);
		frame.getContentPane().add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrice.setBounds(190, 10, 80, 40);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblUnits = new JLabel("Unit");
		lblUnits.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnits.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnits.setBounds(367, 10, 65, 40);
		frame.getContentPane().add(lblUnits);
		
		//price
		textField = new JTextField();
		textField.addKeyListener(new priceKeyAdapter(this));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(278, 15, 100, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//quantity
		textField_1 = new JTextField();
		textField_1.addKeyListener(new priceKeyAdapter(this));
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setBounds(440, 15, 100, 30);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(this.title);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(75, 10, 155, 40);
		frame.getContentPane().add(lblNewLabel);
		
		btnBuy = new JButton("Buy");
		btnBuy.setBounds(175, 143, 89, 23);
		btnBuy.setEnabled(false);
		frame.getContentPane().add(btnBuy);
		btnBuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(server.buyStk(symbol,price,quantity)) {
						JOptionPane.showMessageDialog(null, "Purchase Complete");
						StockTradingInterface reload = new StockTradingInterface(main.login, main.server, main.uid, main.username, main.proc);
						reload.tradingPage.setVisible(true);
						main.tradingPage.dispose();
						myOrder ord = new myOrder(server);
						ord.frame.setVisible(true);
						frame.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Purchase Incomplete");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(276, 143, 89, 23);
		frame.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
			
		});		
		
		JPanel clockPanel = new JPanel();
		clockPanel.setBounds(10, 55, 155, 32);
		frame.getContentPane().add(clockPanel);
		clockPanel.setLayout(null);
		
		lblClock = new JLabel("Clock");
		lblClock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClock.setBounds(10, 11, 114, 21);
		clockPanel.add(lblClock);
		
		JPanel datePanel = new JPanel();
	    datePanel.setBounds(190, 55, 160, 32);
	    frame.getContentPane().add(datePanel);
	    datePanel.setLayout(null);
	    
	    lblDate = new JLabel("Date");
	    lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    lblDate.setBounds(10, 11, 149, 21);
	    datePanel.add(lblDate);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(10, 100, 556, 32);
	    frame.getContentPane().add(panel);
	    
	    lblConfirmationPanel = new JLabel("Buy " + this.title + " at price " + this.price + ", and at " + this.quantity + " units?");
	    lblConfirmationPanel.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    panel.add(lblConfirmationPanel);
	    
	    JLabel lblUnit = new JLabel("1 unit = 100 lots");
	    lblUnit.setBounds(432, 52, 108, 14);
	    frame.getContentPane().add(lblUnit);
	    	
	}
}

class priceKeyAdapter extends KeyAdapter{
	public buyForm buy;
	public buyForm quantity;
	public priceKeyAdapter(buyForm form) {
		this.buy = form;
		this.quantity = form;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		try {
			String text = buy.textField.getText().isEmpty() ? "0.00" : buy.textField.getText();
			buy.price = Float.parseFloat(text);
			String text2 = quantity.textField_1.getText().isEmpty() ? "0" : quantity.textField_1.getText();
			buy.quantity = Integer.parseInt(text2);
			buy.lblConfirmationPanel.setText("Buy " + buy.title + " at price " + text + ", and at " + text2 + " units?");
			if(!buy.textField.getText().isEmpty() && !buy.textField_1.getText().isEmpty()) {
				buy.btnBuy.setEnabled(true);
			}else {
				buy.btnBuy.setEnabled(false);
			}
		} catch (NumberFormatException e1)
		{
			buy.lblConfirmationPanel.setText("Invalid price input or quantity!");
			buy.btnBuy.setEnabled(false);
		}
		
		
		
	}
}


