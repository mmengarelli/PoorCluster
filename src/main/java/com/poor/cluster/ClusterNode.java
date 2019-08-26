package com.poor.cluster;

import org.jgroups.JChannel;
import org.jgroups.Message;

public class ClusterNode {
	//Inject this
	private Receiver receiver = new Receiver();
	private JChannel channel = null;

	public static final String STARTED = "STARTED";

	private void start() throws Exception {
		channel = new JChannel();
		channel.setReceiver(receiver);
		channel.connect("ChatCluster");
		channel.getState(null, 10000);
		
		//Send a message to peers indicated you have started
		channel.send(new Message(null, STARTED));

		execute();

		channel.close();
	}

	/**
	 * Dummy metho - replace with logic
	 */
	private void execute() {
		while (true) {
			System.out.println("...");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new ClusterNode().start();
	}
}
