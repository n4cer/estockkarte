package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Colonies extends Controller {
  public Result index() {
    return ok(index.render("Liste der Völker."));
  }
}
