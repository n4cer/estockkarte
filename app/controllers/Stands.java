package controllers;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import models.Colony;
import models.Hive;
import models.HiveRecord;
import models.QueenColor;
import models.Race;
import models.Stand;
import models.User;
import models.Util;
import net.glxn.qrgen.javase.QRCode;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.stands.*;

@Security.Authenticated(Secured.class)
public class Stands extends Controller {
  @Inject FormFactory formFactory;
  
  public Result index() {
    List<Stand> stands = Stand.find.query().where().eq("user", Util.getUser()).order().asc("id").findList();
    
    return ok(index.render(stands));
  }
  
  public Result add() {
    return ok(add.render(formFactory.form(Stand.class)));
  }
  
  public Result create() {
    Form<Stand> form = formFactory.form(Stand.class).bindFromRequest();
    
    if (form.hasErrors()) {
      return badRequest(add.render(form));
    } 
    
    Stand stand = form.get();
    stand.user = Util.getUser();
    stand.save();
      
    return redirect(routes.Stands.index());
  }
  
  public Result edit(Long id) {
    Stand stand = Stand.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(stand.user)) {
      return badRequest("Zugriff nicht erlaubt!");
    }
    
    return ok(edit.render(stand, formFactory.form(Stand.class).fill(stand)));
  }
  
  public Result update(Long id) {
    Stand old_stand = Stand.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(old_stand.user)) {
        return badRequest("Zugriff nicht erlaubt!");
    }
    
    Form<Stand> form = formFactory.form(Stand.class).bindFromRequest();

    if (form.hasErrors()) {
      return badRequest(edit.render(old_stand, form));
    } 
    
    Stand stand = form.get();
    stand.id = old_stand.id;
    stand.user = Util.getUser();
    stand.update();
      
    return redirect(routes.Stands.index());
  }
  
  public Result delete(Long id) {
    Stand stand = Stand.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(stand.user)) {
      return badRequest("Zugriff nicht erlaubt!");
    }
    
    stand.delete();
    
    return redirect(routes.Stands.index());
  }
  
  public Result detail(Long id) {
    Stand stand = Stand.find.byId(id);
    List<Colony> colonies = Colony.find.query().where().eq("stand", stand).order().asc("id").findList();
    User user = Util.getUser();
    
    if (user != null && user.equals(stand.user)) {
      return ok(views.html.stands.detail.render(stand, colonies));
    }
    
    return badRequest("Zugriff nicht erlaubt!");
  }
}