package controllers;

import play.mvc.*;

import views.html.colonies.*;

@Security.Authenticated(Secured.class)
public class Colonies extends Controller {
    public Result index() {
        return ok(index.render("Liste der Völker."));
    }
}