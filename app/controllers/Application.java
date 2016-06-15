package controllers;

import javax.inject.Inject;
import javax.persistence.Column;

import models.User;
import models.Util;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
  public static class Login {
    @Constraints.Required
    public String username;
    @Constraints.Required
    public String password;

    public String validate() {
      if (User.authenticate(username, password) == null) {
        return "Benutzer oder Passwort ungültig!";
      }
      return null;
    }
  }
  
  public static class Signup {
    @Constraints.Required
    public String username;
    @Constraints.Required
    public String password;

    public String validate() {
      //TODO: validate data
      return null;
    }
  }
  
  @Inject
  FormFactory formFactory;

  public Result authenticate() {
    Form<Login> login_form = formFactory.form(Login.class).bindFromRequest();

    if (login_form.hasErrors()) {
      return badRequest(login.render(login_form));
    } else {
      String user_name = login_form.get().username.toLowerCase();
      session("username", user_name);
      Logger.info("Login: " + user_name);
      return redirect(routes.Application.index());
    }
  }

  public Result index() {
    return ok(index.render());
  }
  
  public Result signup() {
    if(Util.isAuthenticated()) {
      return badRequest("Bereits angemeldet.");
    }
    Form<Signup> signup_form = formFactory.form(Signup.class);
    
    return ok(signup.render(signup_form));
  }
  
  public Result createUser() {
    Form<Signup> signup_form = formFactory.form(Signup.class).bindFromRequest();

    if (signup_form.hasErrors()) {
      return badRequest(signup.render(signup_form));
    } else {
      String userName = signup_form.get().username.toLowerCase();
      String password = signup_form.get().password.toLowerCase();
      
      User user = User.create(userName, password);
      session("username", userName);
      
      return redirect(routes.Application.index());
    }
  }

  public Result login() {
    Form<Login> login_form = formFactory.form(Login.class);

    return ok(login.render(login_form));
  }

  public Result logout() {
    session().clear();
    flash("success", "Abgemeldet");
    return redirect(routes.Application.index());
  }
  
  public Result user() {
    Form<User> user_form = formFactory.form(User.class);

    return ok(user_profile.render(user_form.fill(Util.getUser())));
  }
  
  public Result userEdit() {
    Form<User> form = formFactory.form(User.class).bindFromRequest();

    if (form.hasErrors()) {
      flash("success", "Fehler");
      return badRequest(user_profile.render(form));
    } else {
      User user = form.get();
      user.id = Util.getUser().id;
      user.update();
      
      flash("success", "gespeichert");
      return ok(user_profile.render(form));
    }
  }
}