package controllers;

import java.util.List;

import models.Colony;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.colonies.*;

@Security.Authenticated(Secured.class)
public class Colonies extends Controller {
  public Result index() {
    List<Colony> colonies = Colony.find.all();
    
    return ok(index.render(colonies));
  }
}
