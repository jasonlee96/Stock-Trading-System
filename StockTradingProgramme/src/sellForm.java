import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class sellForm {
	public JFrame frame;
	public JTextField textField;
	public JTextField textField_1;
	public JLabel lblConfirmationPanel;
	private JLabel lblClock;
	private JLabel lblDate;
	public String name;
	public float price;
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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sellForm window = new sellForm("Microsoft");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param name 
	 */
	public sellForm(String name) {
		this.name = name;
		initialize();
		clock();
		date();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Sell Order");
		frame.setSize(588, 210);
		frame.setBounds(100, 100, 590, 210);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(0, 10, 80, 40);
		frame.getContentPane().add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrice.setBounds(180, 10, 80, 40);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblUnits = new JLabel("Unit");
		lblUnits.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnits.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnits.setBounds(360, 10, 80, 40);
		frame.getContentPane().add(lblUnits);
		
		//price
		textField = new JTextField();
		textField.addKeyListener(new priceKeyAdapter2(this));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(250, 15, 100, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//quantity
		textField_1 = new JTextField();
		textField_1.addKeyListener(new priceKeyAdapter2(this));
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setBounds(440, 15, 100, 30);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(this.name);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(75, 10, 100, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnSell = new JButton("Sell");
		btnSell.setBounds(130, 143, 89, 23);
		frame.getContentPane().add(btnSell);		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(231, 143, 89, 23);
		frame.getContentPane().add(btnCancel);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(330, 143, 89, 23);
		frame.getContentPane().add(btnClear);
		
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
	    
	    lblConfirmationPanel = new JLabel("Sell " + name + " at price " + this.price + ", and at " + this.quantity + " units?");
	    lblConfirmationPanel.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    panel.add(lblConfirmationPanel);
	    
	    JLabel lblUnit = new JLabel("1 unit = 100 lots");
	    lblUnit.setBounds(388, 48, 108, 14);
	    frame.getContentPane().add(lblUnit);
	}
}

class priceKeyAdapter2 extends KeyAdapter{
	public sellForm sell;
	public sellForm quantity;
	public priceKeyAdapter2(sellForm form) {
		this.sell = form;
		this.quantity = form;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		try {
			String text = sell.textField.getText().isEmpty() ? "0" : sell.textField.getText();
			sell.price = Integer.parseInt(text);
			String text2 = quantity.textField_1.getText().isEmpty() ? "0" : quantity.textField_1.getText();
			sell.quantity = Integer.parseInt(text2);
			sell.lblConfirmationPanel.setText("Sell " + sell.name + " at price " + text + ", and at " + text2 + " units?");
			
		} catch (NumberFormatException e1)
		{
			sell.lblConfirmationPanel.setText("Invalid price input or quantity!");
		}
		
		
		
	}
}
