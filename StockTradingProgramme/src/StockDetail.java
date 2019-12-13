import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.Icon;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;

public class StockDetail {
	private String symbol;
	private String name;
	public StockTradingInterface main;
	public JFrame frame;

	protected ServerConnection server;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StockDetail window = new StockDetail();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public StockDetail(ServerConnection server, StockTradingInterface main, Object id, Object name) throws IOException, ParseException {
		this.server = server;
		this.symbol = id.toString();
		this.name = name.toString();
		this.main = main;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private void initialize() throws IOException, ParseException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(960, 720);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCompanyPortfolio = new JLabel(name);
		lblCompanyPortfolio.setHorizontalAlignment(SwingConstants.LEFT);
		lblCompanyPortfolio.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblCompanyPortfolio.setBounds(10, 11, 450, 43);
		Font font = lblCompanyPortfolio.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblCompanyPortfolio.setFont(font.deriveFont(attributes));
		frame.getContentPane().add(lblCompanyPortfolio);
		
		JLabel lblPrice = new JLabel("$ 150.08");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblPrice.setBounds(20, 66, 198, 37);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblNewLabel = new JLabel("0.49     0.33%");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(new Color(51, 153, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(260, 77, 198, 25);
		frame.getContentPane().add(lblNewLabel);
	    
	    ImageIcon icon = new ImageIcon("images/up.png");
	    Image scaledImg = icon.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT);
	    ImageIcon icon2 = new ImageIcon("images/down.png");
	    Image scaledImg2 = icon2.getImage().getScaledInstance(20, 20,Image.SCALE_DEFAULT);
	    JLabel lblNewLabel_1 = new JLabel(new ImageIcon(scaledImg));
	    lblNewLabel_1.setBounds(235, 77, 25, 25);
	    frame.getContentPane().add(lblNewLabel_1);
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
	    panel_1.setBounds(23, 430, 437, 53);
	    frame.getContentPane().add(panel_1);
	    panel_1.setLayout(null);
	    
	    JLabel lblDetail = new JLabel("Detail");
	    lblDetail.setBounds(10, 25, 78, 31);
	    panel_1.add(lblDetail);
	    lblDetail.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    
	    JButton btnNewButton = new JButton("Buy");
	    btnNewButton.setBounds(470, 18, 110, 43);
	    btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					buyForm buy = new buyForm(server, main, symbol, name);
					buy.frame.setVisible(true);
			}
		});
	    frame.getContentPane().add(btnNewButton);
	    
	    JButton btnNewButton_1 = new JButton("Sell");
	    btnNewButton_1.setBounds(590, 18, 110, 43);
	    btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					sellForm sell = new sellForm(server, main, symbol, name);
					sell.frame.setVisible(true);
			}
		});
	    frame.getContentPane().add(btnNewButton_1);
	    
	    JPanel panel_2 = new JPanel();
	    panel_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(204, 204, 204)));
	    panel_2.setBounds(28, 491, 198, 47);
	    frame.getContentPane().add(panel_2);
	    panel_2.setLayout(null);
	    
	    JLabel lblOpen = new JLabel("Open:");
	    lblOpen.setBounds(6, 16, 62, 25);
	    panel_2.add(lblOpen);
	    lblOpen.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    
	    JLabel lblOpen1 = new JLabel("$ 150.07");
	    lblOpen1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblOpen1.setBounds(78, 16, 110, 25);
	    panel_2.add(lblOpen1);
	    
	    JPanel panel_3 = new JPanel();
	    panel_3.setLayout(null);
	    panel_3.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(204, 204, 204)));
	    panel_3.setBounds(28, 548, 198, 47);
	    frame.getContentPane().add(panel_3);
	    
	    JLabel lblClosed = new JLabel("Closed:");
	    lblClosed.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblClosed.setBounds(6, 16, 62, 25);
	    panel_3.add(lblClosed);
	    
	    JLabel lblClosed1 = new JLabel("$ 149.59");
	    lblClosed1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblClosed1.setBounds(78, 16, 110, 25);
	    panel_3.add(lblClosed1);
	    
	    JPanel panel_4 = new JPanel();
	    panel_4.setLayout(null);
	    panel_4.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(204, 204, 204)));
	    panel_4.setBounds(309, 494, 277, 47);
	    frame.getContentPane().add(panel_4);
	    
	    JLabel lblLastPrice = new JLabel("Last Price:");
	    lblLastPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblLastPrice.setBounds(6, 16, 88, 25);
	    panel_4.add(lblLastPrice);
	    
	    JLabel lblLastPrice1 = new JLabel("$ 3041.14");
	    lblLastPrice1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblLastPrice1.setBounds(104, 16, 173, 25);
	    panel_4.add(lblLastPrice1);
	    
	    JPanel panel_5 = new JPanel();
	    panel_5.setLayout(null);
	    panel_5.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(204, 204, 204)));
	    panel_5.setBounds(309, 548, 277, 47);
	    frame.getContentPane().add(panel_5);
	    
	    JLabel lblDayRange = new JLabel("Day Range:");
	    lblDayRange.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblDayRange.setBounds(6, 16, 87, 25);
	    panel_5.add(lblDayRange);
	    
	    JLabel lblDayRange1 = new JLabel("$ 148.82 - $ 150.30");
	    lblDayRange1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblDayRange1.setBounds(103, 16, 174, 25);
	    panel_5.add(lblDayRange1);
	    
	    JPanel panel_6 = new JPanel();
	    panel_6.setLayout(null);
	    panel_6.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(204, 204, 204)));
	    panel_6.setBounds(648, 494, 258, 47);
	    frame.getContentPane().add(panel_6);
	    
	    JLabel lblVolume = new JLabel("Volume:");
	    lblVolume.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblVolume.setBounds(6, 16, 79, 25);
	    panel_6.add(lblVolume);
	    
	    JLabel lblVolume1 = new JLabel("15841680");
	    lblVolume1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblVolume1.setBounds(124, 16, 124, 25);
	    panel_6.add(lblVolume1);
	    
	    JPanel panel_7 = new JPanel();
	    panel_7.setLayout(null);
	    panel_7.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(204, 204, 204)));
	    panel_7.setBounds(648, 548, 258, 47);
	    frame.getContentPane().add(panel_7);
	    
	    JLabel lblAvgVolume = new JLabel("Last Updated:");
	    lblAvgVolume.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblAvgVolume.setBounds(6, 16, 122, 25);
	    panel_7.add(lblAvgVolume);
	    
	    JLabel lblAvgVolume1 = new JLabel("22,580,420");
	    lblAvgVolume1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblAvgVolume1.setBounds(126, 16, 122, 25);
	    panel_7.add(lblAvgVolume1);
	    
	    JButton backBtn = new JButton("Back to Main Page");
	    backBtn.setBounds(710, 18, 196, 43);
	    backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	    frame.getContentPane().add(backBtn);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnSetting = new JMenu("Setting");
		menuBar.add(mnSetting);
		
		Object[] data = new Object[9];
		data = server.getCompanyData(this.symbol);
		lblVolume1.setText(data[0].toString());
		lblPrice.setText(data[1].toString());
		lblAvgVolume1.setText(data[2].toString());
		lblClosed1.setText(data[3].toString());
		if(Double.parseDouble(data[4].toString()) < 0) {
			lblNewLabel_1.setIcon(new ImageIcon(scaledImg2));
			lblNewLabel.setForeground(new Color(255,0,0));
		}else {
			lblNewLabel_1.setIcon(new ImageIcon(scaledImg));
			lblNewLabel.setForeground(new Color(51, 153, 0));
		}
		lblNewLabel.setText(data[4].toString() + "     " + data[5].toString());
		lblOpen1.setText(data[6].toString());
		lblDayRange1.setText(data[8].toString() + " - " + data[7].toString());
		
		List<OHLCDataItem> items = server.getChartData(symbol);
		CandleChart chart = new CandleChart("Stock makert", symbol, items);
		JFreeChart stock = chart.drawChart();
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(187, 139, 580, 300);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		ChartPanel chartPanel = new ChartPanel( stock, false, false, false,false, true);
		chartPanel.setBounds(10, 11, 560, 278);
		panel.add(chartPanel);
	    chartPanel.setRangeZoomable(false);
	    chartPanel.setDomainZoomable(false);
	    chartPanel.setPopupMenu(null);
	    chartPanel.setDisplayToolTips(true);
	    chartPanel.setPreferredSize( new Dimension( 560 , 367 ) );
	    chartPanel.setInitialDelay(0);
	    
	    JButton btnAddIntoPortfolio = new JButton("Add into Portfolio");
	    btnAddIntoPortfolio.setBounds(710, 68, 196, 43);
	    btnAddIntoPortfolio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					if(server.addPortfolio(symbol)){
						PortfolioView pv = new PortfolioView(server, main);
						pv.frame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null,"This company already in your Portfolio");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
	    	
	    });
	    frame.getContentPane().add(btnAddIntoPortfolio);
	}
}
