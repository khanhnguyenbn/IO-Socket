package vn.topica.itlab4.excercise2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.ValueBase;

public class Client {
	private Socket clientSocket;
	private DataOutputStream os;
	private DataInputStream is;

	// start to connect server 
	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// send message to server
	private String sendMessage(InformationPackage sendPack) {
		try {
			os.write(sendPack.getByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InformationPackage arrivePack;
		try {
			arrivePack = Utils.readInformationPackage(is);
			return arrivePack.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	// stopping connect server
	public void stopConnection() {
		try {
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// create a client
		Client client = new Client();
		client.startConnection(Constant.SERVER_HOST, Constant.PORT_NUMBER);

		// create authen message to send to server and show respone
		Map<Short, TLV> tLVAuthenMessage = new HashMap<Short, TLV>();
		tLVAuthenMessage.put(Constant.PHONE_NUMBER, new TLV(Constant.PHONE_NUMBER, "0987654321"));
		tLVAuthenMessage.put(Constant.KEY, new TLV(Constant.KEY, "topica"));
		InformationPackage authenMessage = new InformationPackage(Constant.AUTHEN, tLVAuthenMessage);
		System.out.println(authenMessage.toString());
		String response = client.sendMessage(authenMessage);
		System.out.println(response);

		// create insertion message to send to server and show response
		Map<Short, TLV> tLVMapInsert = new HashMap<Short, TLV>();
		tLVMapInsert.put(Constant.PHONE_NUMBER, new TLV(Constant.PHONE_NUMBER, "0987654321"));
		tLVMapInsert.put(Constant.NAME, new TLV(Constant.NAME, "username"));
		InformationPackage insertMessage = new InformationPackage(Constant.INSERT, tLVMapInsert);
		System.out.println(insertMessage.toString());
		System.out.println(client.sendMessage(insertMessage));
		
		// create commit message, send to server and show response
		Map<Short, TLV> tLVCommit = new HashMap<Short, TLV>();
		tLVCommit.put(Constant.PHONE_NUMBER, new TLV(Constant.PHONE_NUMBER, "0987654321"));
		InformationPackage commitMessage = new InformationPackage(Constant.COMMIT, tLVCommit);
		System.out.println(commitMessage.toString());
		String responeCommit = client.sendMessage(commitMessage);
		System.out.println(responeCommit);
		
		// create select message, send to server and show response
		Map<Short, TLV> tLVSelect = new HashMap<Short, TLV>();
		tLVSelect.put(Constant.PHONE_NUMBER, new TLV(Constant.PHONE_NUMBER, "0987654321"));
		InformationPackage sellectMessage = new InformationPackage(Constant.SELECT, tLVSelect);
		System.out.println(sellectMessage.toString());
		String responseMessage = client.sendMessage(sellectMessage);
		System.out.println(responseMessage);
		
		// stop connect
		client.stopConnection();

	}
}
