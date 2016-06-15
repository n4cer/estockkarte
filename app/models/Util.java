package models;

import play.mvc.Http.Context;

public class Util {
	public static User getUser() {
		String user_name = Context.current().session().get("username");
		return User.find.where().eq("name", user_name).findUnique();
	}
	
	public static Boolean isAuthenticated() {
		if (Util.getUser() == null) {
			return false;
		}
		
		return true;
	}
}