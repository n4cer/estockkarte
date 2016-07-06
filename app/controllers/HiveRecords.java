package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.Colony;
import models.Hive;
import models.HiveRecord;
import models.QueenColor;
import models.Race;
import models.Util;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.records.*;

@Security.Authenticated(Secured.class)
public class HiveRecords extends Controller {
  @Inject FormFactory formFactory;
  
  public Result index() {
    List<Colony> colonies = Colony.find.where().eq("user", Util.getUser()).order().asc("id").findList();
    
    return ok(index.render(colonies));
  }
  
  public Result show(Long id) {
    Colony colony = Colony.find.byId(id);
    
    List<HiveRecord> records = null;
    if(colony == null) {
      records = new ArrayList<>();
    } else {
      records = HiveRecord.find.where().eq("user", Util.getUser()).eq("colony", colony).orderBy().desc("date").findList();
    }
    
    return ok(show.render(colony, records));
  }
  
  public Result add(Long id) {
    Colony colony = Colony.find.byId(id);
    
    if(!colony.user.equals(Util.getUser())) {
      return badRequest("Dieses Volk gehört Dir nicht.");
    }
    
    return ok(add.render(formFactory.form(HiveRecord.class), colony));
  }
  
  public Result create(Long id) {
    Colony colony = Colony.find.byId(id);
    
    if(!colony.user.equals(Util.getUser())) {
      return badRequest("Dieses Volk gehört Dir nicht.");
    }
    
    Form<HiveRecord> form = formFactory.form(HiveRecord.class).bindFromRequest();

    if (form.hasErrors()) {
      return badRequest(add.render(form, colony));
    } 
    
    HiveRecord record = form.get();
    record.user = Util.getUser();
    record.colony = colony;
    record.save();
      
    return redirect(routes.HiveRecords.show(colony.id));
  }
}