package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import io.ebean.*;
import play.data.validation.Constraints.Required;

@Entity
public class HiveRecord extends Model {

  public static Finder<Long, HiveRecord> find = new Finder<>(HiveRecord.class);
  @Id
  public Long id;
  @ManyToOne
  public Colony colony;
  @Required
  public Date date;
  public Integer gentleness;
  public Boolean swarming = false;
  public Integer strength;
  public Boolean queen = false;
  public Boolean eggs = false;
  public Boolean maggots = false;
  public Boolean cappedBrood = false;
  public Boolean queenCells = false;
  public String feeding;
  public String varroaCheck;
  public String varroa;
  public String comment;
  public Float weight;
  @ManyToOne
  public User user;
  
  public String swarmingHtml() {
    return Util.BooleanToGlyphIcon(this.swarming);
  }
  
  public String queenHtml() {
    return Util.BooleanToGlyphIcon(this.queen);
  }
  
  public String eggsHtml() {
    return Util.BooleanToGlyphIcon(this.eggs);
  }
  
  public String maggotsHtml() {
    return Util.BooleanToGlyphIcon(this.maggots);
  }
  
  public String cappedBroodHtml() {
    return Util.BooleanToGlyphIcon(this.cappedBrood);
  }
  
  public String queenCellsHtml() {
    return Util.BooleanToGlyphIcon(this.queenCells);
  }
  
  @Override
  public String toString() {
    return id + " " + colony;
  }
}