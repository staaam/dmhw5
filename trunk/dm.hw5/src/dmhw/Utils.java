package dmhw;

public class Utils {

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static Integer toInteger(String str) {
		try {
			return Integer.valueOf(str);
		}
		catch (NumberFormatException e) {
			return null;
		}	
	}

}
