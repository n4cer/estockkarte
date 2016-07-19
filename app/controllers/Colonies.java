package controllers;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import models.Colony;
import models.Hive;
import models.QueenColor;
import models.Race;
import models.User;
import models.Util;
import net.glxn.qrgen.javase.QRCode;
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
    List<Race> races = Race.find.all();
    
    return ok(add.render(formFactory.form(Colony.class), hives, QueenColor.getColors(), races));
  }
  
  public Result create() {
    Form<Colony> form = formFactory.form(Colony.class).bindFromRequest();
    List<Hive> hives = Hive.find.all();
    List<Race> races = Race.find.all();

    if (form.hasErrors()) {
      return badRequest(add.render(form, hives, QueenColor.getColors(), races));
    } 
    
    Colony colony = form.get();
    colony.user = Util.getUser();
    colony.shortUrl = Util.rndUrl(10);
    colony.save();
      
    return redirect(routes.Colonies.index());
  }
  
  public Result edit(Long id) {
    Colony colony = Colony.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(colony.user)) {
      return badRequest("Zugriff nicht erlaubt!");
    }
    
    List<Hive> hives = Hive.find.all();
    List<Race> races = Race.find.all();
    
    QRCode.from("https://blah").stream();
    
    return ok(edit.render(colony, formFactory.form(Colony.class).fill(colony), hives, QueenColor.getColors(), races));
  }
  
  public Result update(Long id) {
    Colony old_colony = Colony.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(old_colony.user)) {
        return badRequest("Zugriff nicht erlaubt!");
    }
    
    Form<Colony> form = formFactory.form(Colony.class).bindFromRequest();
    List<Hive> hives = Hive.find.all();
    List<Race> races = Race.find.all();

    if (form.hasErrors()) {
      return badRequest(edit.render(old_colony, form, hives, QueenColor.getColors(), races));
    } 
    
    Colony colony = form.get();
    colony.id = old_colony.id;
    colony.user = Util.getUser();
    colony.update();
      
    return redirect(routes.Colonies.index());
  }
  
  public Result delete(Long id) {
    Colony colony = Colony.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(colony.user)) {
      return badRequest("Zugriff nicht erlaubt!");
    }
    
    colony.delete();
    
    return redirect(routes.Colonies.index());
  }
  
  public Result showQRCode(String shortUrl) {
    File qrcode = QRCode.from("https://www.estockkarte.de/direct/" + shortUrl).file();
    
    return ok(qrcode).as("image/png");
  }
}