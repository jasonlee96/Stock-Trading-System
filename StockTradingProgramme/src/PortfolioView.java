import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JButton;

public class PortfolioView {

	public JFrame frame;
	public StockTradingInterface main;
	public JTable table;
	public DefaultTableModel model;
	protected ServerConnection server;
	public int selectedRow = -1;
	/**
	 * Launch the application.


	/**
	 * Create the application.
	 */
	public PortfolioView(ServerConnection server, StockTradingInterface main) {
		this.server = server;
		this.main = main;
		initialize();
	}
	public void getPortfolioData() throws IOException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		dataItems = server.getPortfolioData();
		if(dataItems == null) {
			JOptionPane.showMessageDialog(null, "Error is occured when retrieving data");
		}else {	
			for(Object[] temp: dataItems) {
				Object[] row = new Object[6];
				row[0] = temp[0];
				row[1] = temp[1];
				row[2] = temp[2];
				row[3] = temp[3];
				row[4] = temp[4];
				row[5] = temp[5];
				((DefaultTableModel)table.getModel()).addRow(row);
			}
			
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 562);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		panel.setBounds(5, -5, 769, 58);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblMyPortfolio = new JLabel("My Portfolio");
		lblMyPortfolio.setBounds(6, 16, 159, 36);
		panel.add(lblMyPortfolio);
		lblMyPortfolio.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JButton btnBuy = new JButton("Buy");
		btnBuy.setBounds(175, 16, 66, 36);
		btnBuy.addActionListener(new ActionListener() {

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
		panel.add(btnBuy);
		
		JButton btnSell = new JButton("Sell");
		btnSell.setBounds(251, 16, 66, 36);
		btnSell.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedRow >= 0) {
					sellForm sell = new sellForm(server,main, table.getValueAt(selectedRow, 0).toString(), table.getValueAt(selectedRow, 1).toString());
					sell.frame.setVisible(true);

				}else {
					JOptionPane.showMessageDialog(null,"Please Select A Company From Table");
				}
			}
	    	
	    	
	    });
		panel.add(btnSell);
		
		JButton btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.setBounds(625, 16, 144, 36);
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(btnBackToMain);
		
		JButton btnRemoveFromPortfolio = new JButton("Remove from Portfolio");
		btnRemoveFromPortfolio.setBounds(439, 16, 175, 36);
		btnRemoveFromPortfolio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedRow >= 0) {
					try {
						if(server.removePortfolio(table.getValueAt(selectedRow, 0).toString())){
							frame.dispose();
							PortfolioView pv = new PortfolioView(server, main);
							pv.frame.setVisible(true);
						}else {
							JOptionPane.showMessageDialog(null,"Try Again Later");
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
		panel.add(btnRemoveFromPortfolio);
		
		JButton btnViewDetail = new JButton("View Detail");
		btnViewDetail.setBounds(327, 16, 106, 36);
		btnViewDetail.addActionListener(new ActionListener() {
	    	
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
		panel.add(btnViewDetail);
		
		 String[] column_header = {"ID", "Name", "Volume", "Price", "High", "Low"};
		 JScrollPane scrollPane_1 = new JScrollPane();
		    scrollPane_1.setBounds(15, 64, 759, 450);
		    frame.getContentPane().add(scrollPane_1);
		    model = new DefaultTableModel(column_header, 0);
		    table = new JTable(model){
		    	public boolean isCellEditable(int row, int column) {
		    		
	                return false;           
		    	}
		    };
		    table.getColumnModel().getColumn(0).setPreferredWidth(55);
		    table.getColumnModel().getColumn(1).setPreferredWidth(175);
		    try {
				getPortfolioData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
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
	}
}
