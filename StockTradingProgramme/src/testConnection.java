import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class testConnection {
	public Socket socket = null;
	public testConnection() {
		int port = 8080;
		try {
			socket = new Socket("localhost", port);
		    DataInputStream in = new DataInputStream( socket.getInputStream());
			DataOutputStream out = new DataOutputStream( socket.getOutputStream());
			out.writeUTF("hello");
			System.out.println("Server sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testConnection test =new testConnection();
	}

}
