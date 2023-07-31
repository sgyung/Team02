package log_analysis;


import java.util.HashMap;
import java.util.Map;

public class UserInfo {
	
	private Map<String, String> user = new HashMap<String, String>();
	
	public UserInfo() {
		user.put("admin", "1234");
		user.put("root", "1111");
		user.put("administrator", "12345");
	}
	
	public Map<String, String> getUser() {
		return user;
	}
	

}

