package com.poor.cluster;

import static com.poor.cluster.ClusterNode.STARTED;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;
/**
 * Receiver implementation and distributed state coordinator
 * @author mengam
 *
 */
public class Receiver extends ReceiverAdapter {
	public static int NODE_CT = 0;

	final List<String> state = new LinkedList<>();

	public void getState(OutputStream output) throws Exception {
		synchronized (state) {
			Util.objectToStream(state, new DataOutputStream(output));
		}
	}

	public void setState(InputStream input) throws Exception {
		List<String> list = Util.objectFromStream(new DataInputStream(input));

		synchronized (state) {
			state.clear();
			state.addAll(list);
		}

		list.forEach(System.out::println);
	}
	/**
	 * 
	 */
	public void receive(Message msg) {
		System.out.println(String.format("received message[%s]: %s", msg.getSrc(), msg.getObject()));

		// First guy prints this
		if (!state.contains(STARTED)) {
			System.out.println("We are started");
		}

		synchronized (state) {
			state.add(msg.getObject());
		}
	}

	public void viewAccepted(View view) {
		NODE_CT = view.getMembers().size();

//		System.out.println("Creator: " + view.getCreator());
//		System.out.println("Coordinator: " + view.getCoord());
		System.out.println("Node Count: " + NODE_CT);
	}
}