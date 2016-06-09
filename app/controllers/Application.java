package controllers;

import models.*;
import play.*;
import play.data.*;

import javax.inject.Inject;

import controllers.Application.Login;
import play.data.validation.Constraints;
import play.mvc.*;


import views.html.*;

public class Application extends Controller {
	@Inject FormFactory formFactory;
	
	public static class Login {
    	@Constraints.Required
    	public String username;
    	@Constraints.Required
        public String password;
        
        public String validate() {
			if(User.authenticate(username, password) == null) {
                return "Benutzer oder Passwort ungültig!";
            }
            return null;
        }
    }
	
    public Result index() {
        return ok(index.render("Los gehts."));
    }
    
    public Result authenticate() {
        Form<Login> login_form = formFactory.form(Login.class).bindFromRequest();
        
        if(login_form.hasErrors()) {
            return badRequest(login.render(login_form));
        } else {
        	String user_name = login_form.get().username.toLowerCase();
            session("username", user_name);
            Logger.info("Login: " + user_name);
            return redirect(
                routes.Application.index()
            );
        }
    }
    
    public Result login() {
    	Form<Login> login_form = formFactory.form(Login.class);
    	
        return ok(
            login.render(login_form)
        );
    }
    
    public Result logout() {
        session().clear();
        flash("success", "Abgemeldet");
        return redirect(
            routes.Application.index()
        );
    }
}