package com.hydro.comm.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {

	private static Boolean SINGLE_INSTANCE = false;
	private static int serverPort = 8080;

	private ServerSocket serverSocket;
	private List<ConnectedClient> connectedClients;

	private static Server server;

	private Server() throws IOException {
		this.connectedClients = new ArrayList<>();
		this.serverSocket = new ServerSocket(serverPort);
	}

	public void start() {
		new Thread(() -> {
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					socket.setKeepAlive(true);
					String address = socket.getInetAddress().getHostAddress();
					ConnectedClient connectedClient = new ConnectedClient(socket, address);
					connectedClients.add(connectedClient);

					new Thread(() -> {
						try {
							InputStream inputStream = socket.getInputStream();
							DataInputStream fromClient = new DataInputStream(inputStream);
							while (socket.isConnected()) {
								String response = fromClient.readUTF();
								System.out.println("From client : " + response);
							}
						} catch (Exception e) {
							System.err.println(e.toString());
						}
					}).start();

				} catch (Exception e) {
					System.err.println("SERVER ERROR: " + e.toString());
				}
			}
		}).start();
	}

	public static Server getInstance() throws IOException {
		if (SINGLE_INSTANCE) {
			return server;
		}
		return new Server();
	}

	public void BroadcastMessage(String message) throws IOException {
		// new thread to broadcast message
		new Thread(() -> {
			Iterator<ConnectedClient> clientsIt = connectedClients.iterator();
			while (clientsIt.hasNext()) {
				ConnectedClient pair = clientsIt.next();
				Socket clientSocket = pair.getClientSocket();
				try {
					OutputStream OS = clientSocket.getOutputStream();
					DataOutputStream DOS = new DataOutputStream(OS);
					DOS.writeUTF(message);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

}