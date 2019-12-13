import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginForm {

	public JFrame frame;
	public JTextField textField;
	public JPasswordField passwordField;
	private ServerConnection server;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public LoginForm(ServerConnection server) {
		this.server = server;
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Login");
		frame.setBounds(100, 100, 448, 543);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTdSystem = new JLabel("Stock Trading Client");
		lblTdSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblTdSystem.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTdSystem.setBounds(35, 22, 345, 70);
		frame.getContentPane().add(lblTdSystem);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(204, 204, 204)));
		panel.setBounds(35, 111, 363, 265);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(49, 63, 96, 31);
		panel.add(lblUsername);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(155, 63, 178, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(49, 165, 96, 31);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(155, 167, 178, 30);
		panel.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new loginHandler(this));
		btnLogin.setBounds(63, 400, 317, 35);
		frame.getContentPane().add(btnLogin);
	}
	class loginHandler implements ActionListener{
		LoginForm login;
		public loginHandler(LoginForm login) {
			this.login = login;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO check Login
			String res = server.Login(textField.getText(), passwordField.getPassword());
			
			if(res.startsWith("Success")) {
				String[] params = res.split("\t");
				JDialog proc = new JDialog(new JFrame(), "Gathering Data....");
				proc.setBounds(125, 150, 250, 50);
				proc.setVisible(true);
				server.uid = params[1];
				server.username = params[2];
				StockTradingInterface main = new StockTradingInterface(login, server, params[1], params[2], proc);
				frame.setVisible(false);
				main.tradingPage.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "Login Failed");
				passwordField.setText("");
			}
			
			//initServer.frame.setVisible(true);
		}
	}
}



