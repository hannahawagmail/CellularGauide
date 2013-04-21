package network;

import ocsf.client.AbstractClient;

public class TcpClient extends AbstractClient{
	private ChatIF clientUI;
	public TcpClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		getClientUI().display(msg);
	}

	public ChatIF getClientUI() {
		return clientUI;
	}

	public void setClientUI(ChatIF clientUI) {
		this.clientUI = clientUI;
	}

}
