package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.Colony;
import models.Hive;
import models.QueenColor;
import models.Util;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.colonies.*;

@Security.Authenticated(Secured.class)
public class Colonies extends Controller {
  @Inject FormFactory formFactory;
  
  public static final int COLOR_NONE = -1;
  public static final int COLOR_BLUE = 0;
  public static final int COLOR_WHITE = 1;
  public static final int COLOR_YELLOW = 2;
  public static final int COLOR_RED = 3;
  public static final int COLOR_GREEN = 4;
  
  public Result index() {
    List<Colony> colonies = Colony.find.where().eq("user", Util.getUser()).order().asc("id").findList();
    
    return ok(index.render(colonies));
  }
  
  public Result add() {
    List<Hive> hives = Hive.find.all();
    
    return ok(add.render(formFactory.form(Colony.class), hives, QueenColor.getColors()));
  }
  
  public Result create() {
    Form<Colony> form = formFactory.form(Colony.class).bindFromRequest();
    List<Hive> hives = Hive.find.all();

    if (form.hasErrors()) {
      return badRequest(add.render(form, hives, QueenColor.getColors()));
    } 
    
    Colony colony = form.get();
    colony.user = Util.getUser();
    colony.save();
      
    return redirect(routes.Colonies.index());
  }
}