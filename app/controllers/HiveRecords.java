package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.Colony;
import models.Hive;
import models.HiveRecord;
import models.QueenColor;
import models.Race;
import models.User;
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
    List<Colony> colonies = Colony.find.query().where().eq("user", Util.getUser()).order().asc("id").findList();
    
    return ok(index.render(colonies));
  }
  
  public Result show(Long id) {
    Colony colony = Colony.find.byId(id);
    
    if(colony != null && !colony.user.equals(Util.getUser())) {
      return badRequest("Dieses Volk gehört Dir nicht.");
    }
    
    List<HiveRecord> records = null;
    if(colony == null) {
      records = new ArrayList<>();
    } else {
      records = HiveRecord.find.query().where().eq("user", Util.getUser()).eq("colony", colony).orderBy().desc("date").findList();
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
  
  public Result edit(Long id) {
    HiveRecord record = HiveRecord.find.byId(id);
    User user = Util.getUser();
    Colony colony = record.colony;
    
    if (!user.equals(record.user)) {
      return badRequest("Zugriff nicht erlaubt!");
    }
    
    return ok(edit.render(record, formFactory.form(HiveRecord.class).fill(record), colony));
  }
  
  public Result update(Long id) {
    HiveRecord old_record = HiveRecord.find.byId(id);
    User user = Util.getUser();
    Colony colony = old_record.colony;
    
    if (!user.equals(old_record.user)) {
        return badRequest("Zugriff nicht erlaubt!");
    }
    
    Form<HiveRecord> form = formFactory.form(HiveRecord.class).bindFromRequest();

    if (form.hasErrors()) {
      return badRequest(edit.render(old_record, form, colony));
    } 
    
    HiveRecord record = form.get();
    record.id = old_record.id;
    record.colony = colony;
    record.user = Util.getUser();
    record.update();
      
    return redirect(routes.HiveRecords.show(colony.id));
  }
  
  public Result delete(Long id) {
    HiveRecord record = HiveRecord.find.byId(id);
    User user = Util.getUser();
    
    if (!user.equals(record.user)) {
      return badRequest("Zugriff nicht erlaubt!");
    }
    
    record.delete();
    
    return redirect(routes.HiveRecords.show(record.colony.id));
  }
}