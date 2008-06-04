package dmhw.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static Integer toInteger(String str) {
		try { return Integer.valueOf(str); }
		catch (NumberFormatException e) { return null; }	
	}

	public static Date getDate(HttpServletRequest request, String p) {
		Integer d = Utils.toInteger(request.getParameter(p+"d"));
		Integer m = Utils.toInteger(request.getParameter(p+"m"));
		Integer y = Utils.toInteger(request.getParameter(p+"y"));
		Integer H = Utils.toInteger(request.getParameter(p+"H"));
		Integer M = Utils.toInteger(request.getParameter(p+"M"));
		return new Date(y-1900, m, d,H, M);
	}

}
