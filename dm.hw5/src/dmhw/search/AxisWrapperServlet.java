package dmhw.search;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.axis.transport.http.AxisServlet;
import org.eclipse.jdt.internal.compiler.ast.NameReference;

import dmhw.registration.RegistrationService;
import dmhw.registration.RegistrationServiceService;
import dmhw.registration.RegistrationServiceServiceLocator;

public class AxisWrapperServlet extends AxisServlet {
	private static final long serialVersionUID = 4518794261799731857L;

	private String url;
	@Override
	public void init() throws ServletException {
		super.init();
//		try {
//			url = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/dm.hw5/services/MBSearchImpl";
//			RegistrationServiceService rss = new RegistrationServiceServiceLocator();
//			RegistrationService rs = rss.getEndpointsRegistration();
//			rs.addEndpoint(url); 
//			for (String s :rs.getRegisteredEndpoints()) {
//				System.out.println(s);
//			}
//		} catch (Exception e) {
//		}
	}

	@Override
	public void destroy() {
//		try {
//			RegistrationServiceService rss = new RegistrationServiceServiceLocator();
//			RegistrationService rs = rss.getEndpointsRegistration();
//			rs.deleteEndpoint(url); 
//			for (String s :rs.getRegisteredEndpoints()) {
//				System.out.println(s);
//			}
//		} catch (Exception e) {
//		}
		super.destroy();
	}
	
	
}
