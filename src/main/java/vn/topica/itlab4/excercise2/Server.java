package vn.topica.itlab4.excercise2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

	// user list to save with insertion message
	public static List<User> userList;

	public static void main(String args[]) throws IOException {

		ServerSocket listener = null;
		userList = new ArrayList<User>();

		System.out.println("Server is waiting to accept user...");
		int clientNumber = 0;

		try {
			listener = new ServerSocket(Constant.PORT_NUMBER);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

		while (true) {

			Socket socketOfServer = listener.accept();
			new ServiceThread(socketOfServer, clientNumber++).start();
		}

	}

	private static void log(String message) {
		System.out.println(message);
	}

	private static class ServiceThread extends Thread {

		private int clientNumber;
		private Socket socketOfServer;

		public ServiceThread(Socket socketOfServer, int clientNumber) {
			this.clientNumber = clientNumber;
			this.socketOfServer = socketOfServer;

			// Log
			log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
		}

		public static boolean connected = true;

		@Override
		public void run() {

			DataInputStream is = null;
			DataOutputStream os = null;
			try {
				is = new DataInputStream(socketOfServer.getInputStream());
				os = new DataOutputStream(socketOfServer.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			int state = ClientState.INIT;

			while (connected) {
				try {
					InformationPackage informationPackage = Utils.readInformationPackage(is);
					// check have field phone number
					if (informationPackage.gettLVMap().containsKey(Constant.PHONE_NUMBER)) {
						System.out.println("SERVER: " + informationPackage.toString());
						String phoneNumber = informationPackage.gettLVMap().get(Constant.PHONE_NUMBER).getValue();
						// check phone match
						if (checkPhoneNumber(phoneNumber)) {
							
							// check authen message
							if (informationPackage.getCmdCode() == Constant.AUTHEN) {

								if (informationPackage.gettLVMap().get(Constant.KEY).getValue().equals("topica")) {

									InformationPackage reponse = createReponse(informationPackage, "OK");
									os.write(reponse.getByteArray());
									state = ClientState.READY;

								} else {

									InformationPackage response = createReponse(informationPackage, "NOK");
									os.write(response.getByteArray());
								}
								// check insert and commit message
							} else if (informationPackage.getCmdCode() == Constant.INSERT
									|| informationPackage.getCmdCode() == Constant.COMMIT) {

								if (state != ClientState.READY) {
									InformationPackage reponse = createReponse(informationPackage, "NOK");
									os.write(reponse.getByteArray());
								} else {
									if (informationPackage.getCmdCode() == Constant.INSERT) {

										String userName = null;
										if (informationPackage.gettLVMap().containsKey(Constant.NAME)) {
											userName = informationPackage.gettLVMap().get(Constant.NAME).getValue();
										}

										userList.add(new User(userName, phoneNumber));

										InformationPackage reponse = createReponse(informationPackage, "OK");
										os.write(reponse.getByteArray());
									} else if (informationPackage.getCmdCode() == Constant.COMMIT) {
										state = ClientState.SELECT;
										InformationPackage reponse = createReponse(informationPackage, "OK");
										os.write(reponse.getByteArray());

									}
								}
								// check select message
							} else if (informationPackage.getCmdCode() == Constant.SELECT) {
								if (state != ClientState.SELECT) {
									InformationPackage reponse = createReponse(informationPackage, "NOK");
									os.write(reponse.getByteArray());
								} else {

									InformationPackage respone = createReponseOfSelect(informationPackage, "OK");
									os.write(respone.getByteArray());
								}
							}

						} else {
							InformationPackage errorInformationPackage = createErorInforPack();
							os.write(errorInformationPackage.getByteArray());
						}
					} else {
						InformationPackage errorInformationPackage = createErorInforPack();
						os.write(errorInformationPackage.getByteArray());
					}

					System.out.println(informationPackage.toString());

					// check if client close connect to server then end loop
				} catch (EOFException e) {
					connected = false;
					System.out.println(e);
					System.out.println("Client service disconnected");
				} 
				
				catch (IOException e) {
					
				}
			}

		}

		
		private boolean checkPhoneNumber(String phoneNumber) {
			String phoneRegex = "098[^01][0-9]{6}";
			if (phoneNumber.matches(phoneRegex)) {
				return true;
			} else {
				return false;
			}
		}

		// create response error
		private InformationPackage createErorInforPack() {
			Map<Short, TLV> tLVMap = new HashMap<Short, TLV>();

			tLVMap.put(Constant.RESULT_CODE, new TLV(Constant.RESULT_CODE, "NA"));
			InformationPackage informationPackage = new InformationPackage(Constant.ERROR, tLVMap);

			return informationPackage;
		}

		// create response for authen, insert, commit message
		private InformationPackage createReponse(InformationPackage request, String resultCodeValue) {

			Map<Short, TLV> tLVMapResponse = new HashMap<Short, TLV>();

			if (request.gettLVMap().containsKey(Constant.PHONE_NUMBER)) {
				tLVMapResponse.put(Constant.PHONE_NUMBER, request.gettLVMap().get(Constant.PHONE_NUMBER));
			}

			tLVMapResponse.put(Constant.RESULT_CODE, new TLV(Constant.RESULT_CODE, resultCodeValue));

			InformationPackage reponse = new InformationPackage(request.getCmdCode(), tLVMapResponse);

			return reponse;

		}

		// create response for select message
		private InformationPackage createReponseOfSelect(InformationPackage request, String resultCodeValue) {
			InformationPackage respone = createReponse(request, resultCodeValue);
			String userName = null;
			for (User user : userList) {
				if (user.getPhoneNumber().equals(request.gettLVMap().get(Constant.PHONE_NUMBER).getValue())) {
					userName = user.getName();
				}
			}
			respone.gettLVMap().put(Constant.NAME, new TLV(Constant.NAME, userName));
			return respone;
		}

	}
}
