package controllers;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;

import org.apache.commons.lang3.StringEscapeUtils;

import models.Colony;
import models.Hive;
import models.HiveRecord;
import models.QueenColor;
import models.Race;
import models.User;
import models.Util;
import net.glxn.qrgen.javase.QRCode;
import play.Logger;
import play.api.Configuration;
import play.api.Play;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.data.validation.Constraints.MinLength;
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
    @MinLength(6)
    public String password;
    @MinLength(6)
    public String confirmPassword;

    public String validate() {
      if(!password.equals(confirmPassword)) {
        return "Passwörter stimmen nicht überein";
      }
      
      User user = User.find.where().eq("name", username).findUnique();
      if(user != null) {
        return "Der Benutzer existiert bereits.";
      }
      
      return null;
    }
  }
  
  @Inject FormFactory formFactory;
  @Inject Configuration configuration;

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
      User oldUser = Util.getUser();
      //upper case usernames are not allowed
      user.name = user.name.toLowerCase();
      user.id = oldUser.id;
      user.update();
      
      //logout if user has changed
      if(!oldUser.name.equals(user.name)) {
        flash("success", "Benutzername geändert. Bitte melden Sie sich erneut an.");
        return redirect(routes.Application.logout());
      }
      
      flash("success", "gespeichert");
      
      return ok(user_profile.render(form));
    }
  }
  
  public Result imprint() {
    String name = configuration.underlying().getString("owner.name");
    String street = configuration.underlying().getString("owner.street");
    String city = configuration.underlying().getString("owner.city");
    String email = configuration.underlying().getString("owner.email");
    String email_encoded = "";
    for (int i = 0; i < email.length(); i++) {
      email_encoded += ("&#" + email.codePointAt(i) + ";");
    }
    System.out.println(email_encoded);
    
    return ok(imprint.render(name, street, city, email_encoded));
  }
  
  /**
   * redirect to colony overview
   * @return Result
   */
  public Result direct(String shortUrl) {
    Colony colony = Colony.find.where().eq("shortUrl", shortUrl).findUnique();
    
    if(colony == null) {
      return badRequest("Seite nicht gefunden");
    }
    
    return redirect(controllers.routes.Application.colonyDetail(colony.id));
  }
  
  public Result colonyDetail(Long id) {
    Colony colony = Colony.find.byId(id);
    List<HiveRecord> records = HiveRecord.find.where().eq("colony", colony).order().desc("date").setMaxRows(4).findList();
    User user = Util.getUser();
    
    if(colony.visible) {
      return ok(views.html.colonies.detail.render(colony, records));
    }
    
    if (user != null && user.equals(colony.user)) {
      return ok(views.html.colonies.detail.render(colony, records));
    }
    
    return badRequest("Zugriff nicht erlaubt!");
  }
  
  public Result recordDetail(Long id) {
    HiveRecord record = HiveRecord.find.byId(id);
    Colony colony = record.colony;
    User user = Util.getUser();
    
    if(colony.visible) {
      return ok(views.html.records.detail.render(record));
    }
    
    if (user != null && user.equals(colony.user)) {
      return ok(views.html.records.detail.render(record));
    }
    
    return badRequest("Zugriff nicht erlaubt!");
  }
}