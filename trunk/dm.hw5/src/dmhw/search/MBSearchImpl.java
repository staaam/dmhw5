package dmhw.search;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dmhw.model.Message;
import dmhw.model.MessageManager;

public class MBSearchImpl implements MBSearch {

	public String[] search(String[] keywords, int rank, long time)
			throws RemoteException {
		ArrayList<Message> l = MessageManager.searchMessages(keywords, rank, time);
		String[] r = new String[l.size()];
		int i=0;
		for (Message m : l) {
			r[i] = m.toXML();
			i++;
		}
		return r;
	}

}
