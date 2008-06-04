package dmhw.search;

import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.util.ArrayList;

import dmhw.model.Message;
import dmhw.model.MessageManager;
import dmhw.registration.RegistrationService;
import dmhw.registration.RegistrationServiceService;
import dmhw.registration.RegistrationServiceServiceLocator;
public class MBSearchImpl implements MBSearch {
	public MBSearchImpl() {
		System.out.println("asd");
	}

	public String[] search(String[] keywords, int rank, long time)
			throws RemoteException {
		try {
		ArrayList<Message> l = MessageManager.searchMessages(keywords, rank, time);
		String[] r = new String[l.size()];
		int i=0;
		for (Message m : l) {
			r[i] = m.toXML();
			i++;
		}
		return r;
		} catch (Exception e) {
			return new String[0];
		}
	}
	
	
	private static String url;
	public static void register() {
		try {
			url = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/dm.hw5/services/MBSearchImpl";
			RegistrationServiceService rss = new RegistrationServiceServiceLocator();
			RegistrationService rs = rss.getEndpointsRegistration();
			rs.addEndpoint(url); 
//			for (String s :rs.getRegisteredEndpoints()) {
//				System.out.println(s);
//			}
		} catch (Exception e) {
		}
	}
	
	public static void unregister() {
		try {
			RegistrationServiceService rss = new RegistrationServiceServiceLocator();
			RegistrationService rs = rss.getEndpointsRegistration();
			rs.deleteEndpoint(url); 
//			for (String s :rs.getRegisteredEndpoints()) {
//				System.out.println(s);
//			}
		} catch (Exception e) {
		}
	}

}
