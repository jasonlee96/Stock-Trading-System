import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import org.jfree.data.xy.OHLCDataItem;

public class ServerConnection extends SwingWorker<Void, Void> {

	private String connection = "localhost";
	public JFrame frame;
	public LoginForm form;
	private Socket socket = null;
	private JLabel lblConnectingToServer;
	private DataOutputStream out;
	private DataInputStream in;
	public String uid;
	public String username;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerConnection conn = new ServerConnection();
					conn.execute();
					conn.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String Login(String username, char[] password) {
		String pw = new String(password);
		try {
			out.writeUTF("LOGIN\t"+username+"\t"+pw);
			String response = in.readUTF();
			
			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
		
	}
	public String Logout(String uid) throws IOException {
		out.writeUTF("LOGOUT\t" + uid +"\t"+username);
		String response = in.readUTF();
		
		return response;
	}
	
	public List<Object[]> getCompanyData() throws IOException, ParseException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		out.writeUTF("VIEW_STK\t" + username);
		out.flush();

		String result;
		while(!(result = in.readUTF()).isEmpty()) {
			String[] row = result.split("\t");
			Object[] item = new Object[9];
			//symbol+"\t"+rs.getString(2)+"\t"+volume+"\t"+price+"\t"+volume+"\t"+price+"\t"+volume+"\t"+high+"\t"+low;
			item[0] = row[0];
			item[1] = row[1];
			item[2] = Integer.parseInt(row[2]);
			item[3] = Double.parseDouble(row[3]);
			item[4] = Integer.parseInt(row[4]);
			item[5] = Double.parseDouble(row[5]);
			item[6] = Integer.parseInt(row[6]);
            item[7] = Double.parseDouble(row[7]);
            item[8] = Double.parseDouble(row[8]);

            dataItems.add(item);
		}
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return dataItems;
		}else {
			return null;
		}
	}
	public List<Object[]> getPortfolioData() throws IOException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		out.writeUTF("VIEW_PORT\t" +uid+"\t"+ username);
		out.flush();

		String result;
		while(!(result = in.readUTF()).isEmpty()) {
			String[] row = result.split("\t");
			Object[] item = new Object[6];
			//symbol+"\t"+rs.getString(2)+"\t"+volume+"\t"+price+"\t"+volume+"\t"+price+"\t"+volume+"\t"+high+"\t"+low;
			item[0] = row[0];
			item[1] = row[1];
			item[2] = Integer.parseInt(row[2]);
			item[3] = Double.parseDouble(row[3]);
            item[4] = Double.parseDouble(row[4]);
            item[5] = Double.parseDouble(row[5]);

            dataItems.add(item);
		}
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return dataItems;
		}else {
			return null;
		}
	}
	
	public Object[] getCompanyData(String symbol) throws IOException {
		out.writeUTF("VIEW_STK_DETAIL\t"+symbol+ "\t"+username);
		out.flush();
		
		String res = in.readUTF();
		String[] params = res.split("\t");
		if(params.length == 1) {
			return null;
		}
		Object[] row = new Object[9];
		
		row[0] = params[0];
		row[1] = params[1];
		row[2] = params[2];
		row[3] = params[3];
		row[4] = params[4];
		row[5] = params[5];
		row[6] = params[6];
		row[7] = params[7];
		row[8] = params[8];
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return row;
		}else {
			return null;
		}
	}
	
	public List<OHLCDataItem> getChartData(String symbol) throws IOException, ParseException {
		List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
		String result;
		DateFormat df = new SimpleDateFormat("y-M-d");
		while(!(result = in.readUTF()).isEmpty()) {
			if(result.startsWith("Failed")) {
				JOptionPane.showMessageDialog(null, "Data Retrieve Failed");
				break;
			}
			String[] row = result.split("\t");
			Date date = df.parse(row[0]);
            double open = Double.parseDouble(row[1]);
            double high = Double.parseDouble(row[2]);
            double low = Double.parseDouble(row[3]);
            double close = Double.parseDouble(row[4]);
            double adjClose = Double.parseDouble(row[5]);
            double volume = Double.parseDouble(row[6]);
            
            open = open * adjClose / close;
            high = high * adjClose / close;
            low = low * adjClose / close;
			//symbol+"\t"+rs.getString(2)+"\t"+volume+"\t"+price+"\t"+volume+"\t"+price+"\t"+volume+"\t"+high+"\t"+low;
			
            OHLCDataItem item = new OHLCDataItem(date, open, high, low, adjClose, volume);
            dataItems.add(item);
		}
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return dataItems;
		}else {
			return null;
		}
	}
	
	public List<Object[]> getOrderData(String uid) throws IOException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		out.writeUTF("VIEW_ORD\t"+uid+"\t"+username);
		out.flush();
		
		String result;
		while(!(result = in.readUTF()).isEmpty()) {
			String[] row = result.split("\t");
			
			Object[] item = new Object[9];
			//symbol+"\t"+rs.getString(2)+"\t"+volume+"\t"+price+"\t"+volume+"\t"+price+"\t"+volume+"\t"+high+"\t"+low;
			item[0] = row[0];
			item[1] = row[1];
			item[2] = row[2];
			item[3] = row[3];
			
            dataItems.add(item);
		}
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return dataItems;
		}else {
			return null;
		}
			
	}
	
	public List<Object[]> getWalletData(String uid) throws IOException {
		List<Object[]> dataItems = new ArrayList<Object[]>();
		out.writeUTF("VIEW_WLT\t"+uid+"\t"+username);
		out.flush();
		
		String result;
		while(!(result = in.readUTF()).isEmpty()) {
			String[] row = result.split("\t");
			
			Object[] item = new Object[9];
			//symbol+"\t"+rs.getString(2)+"\t"+volume+"\t"+price+"\t"+volume+"\t"+price+"\t"+volume+"\t"+high+"\t"+low;
			item[0] = row[0];
			item[1] = row[1];
			item[2] = row[2];
			
            dataItems.add(item);
		}
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return dataItems;
		}else {
			return null;
		}
			
	}
	
	public boolean buyStk(String symbol, float price, int quantity) throws IOException {
		out.writeUTF("BUY\t"+symbol+"\t"+price+"\t"+quantity+"\t"+this.uid+ "\t"+username);
		out.flush();
		String response = in.readUTF();
		System.out.println(response);
		if(response.startsWith("Success")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean SellStk(String symbol, float price, int quantity) throws IOException {
		out.writeUTF("SELL\t"+symbol+"\t"+price+"\t"+quantity+"\t"+this.uid+"\t"+username);
		out.flush();
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean addPortfolio(String symbol) throws IOException {
		out.writeUTF("ADD_PORT\t"+symbol+"\t"+uid+"\t"+username);
		out.flush();
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean removePortfolio(String symbol) throws IOException {
		out.writeUTF("RMV_PORT\t"+symbol+"\t"+uid+"\t"+username);
		out.flush();
		String response = in.readUTF();
		if(response.startsWith("Success")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Create the application.
	 */
	public ServerConnection() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 105);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblConnectingToServer = new JLabel("Connecting to Server ...");
		lblConnectingToServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectingToServer.setBounds(114, 26, 164, 14);
		frame.getContentPane().add(lblConnectingToServer);
	}

	@Override
	protected Void doInBackground() throws Exception {
		int port = 8080;
		try {
			
			socket = new Socket("169.254.134.47", port);
		    in = new DataInputStream( socket.getInputStream());
			out = new DataOutputStream( socket.getOutputStream());
			Thread.sleep(1500);
			lblConnectingToServer.setText("Connected To Server");
			Thread.sleep(1000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	protected void done(){
		form = new LoginForm(this);
		this.form.frame.setVisible(true);
		this.frame.dispose();
		
	}
}
