package controllers;

import java.util.List;

import javax.inject.Inject;

import controllers.Application.Signup;
import models.Colony;
import models.User;
import models.Util;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.signup;
import views.html.colonies.*;

@Security.Authenticated(Secured.class)
public class Colonies extends Controller {
  @Inject FormFactory formFactory;
  
  public Result index() {
    List<Colony> colonies = Colony.find.where().eq("user", Util.getUser()).order().asc("id").findList();
    
    return ok(index.render(colonies));
  }
  
  public Result add() {
    return ok(add.render(formFactory.form(Colony.class)));
  }
  
  public Result create() {
    Form<Colony> form = formFactory.form(Colony.class).bindFromRequest();

    if (form.hasErrors()) {
      return badRequest(add.render(form));
    } 
    
    Colony colony = form.get();
    colony.user = Util.getUser();
    colony.save();
      
    return redirect(routes.Colonies.index());
  }
}
