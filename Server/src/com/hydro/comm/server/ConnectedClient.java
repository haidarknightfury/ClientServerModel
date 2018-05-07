package com.hydro.comm.server;

import java.net.Socket;

public class ConnectedClient {
	private Socket clientSocket;
	private String userId;

	public ConnectedClient(Socket clientSocket, String userId) {
		super();
		this.clientSocket = clientSocket;
		this.userId = userId;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientSocket == null) ? 0 : clientSocket.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectedClient other = (ConnectedClient) obj;
		if (clientSocket == null) {
			if (other.clientSocket != null)
				return false;
		} else if (!clientSocket.equals(other.clientSocket))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
