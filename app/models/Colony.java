package models;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;

import controllers.Colonies;
import play.data.validation.Constraints;

@Entity
public class Colony extends Model {

  public static Finder<Long, Colony> find = new Finder<Long, Colony>(Colony.class);
  @Id
  public Long id;
  @Constraints.Required
  public String name;
  public String number;
  @ManyToOne
  public Race race;
  public String queen;
  public Integer queenColor;
  public String queenIdent;
  @ManyToOne
  public Hive hive;
  public String comment;
  @ManyToOne
  public User user;
  @Column(columnDefinition = "boolean default false")
  public Boolean visible = false;
  @OneToMany(mappedBy="colony", cascade=CascadeType.ALL)
  public List<HiveRecord> hiveRecords;
  @Column(length=10,unique=true)
  public String shortUrl;
  public String hiveNumber;
  
  public String getLastEntryDate() {
    if(this.hiveRecords.size() > 0) {
      HiveRecord record = this.hiveRecords.get(0);
      SimpleDateFormat dt = new SimpleDateFormat("dd.MM.YYYY"); 
      
      if(record.date != null) {
        return dt.format(record.date);
      }
    }
    
    return "";
  }
  
  public String getQueenColorString() {
    if(this.queenColor == null) {
      return "";
    }
    
    switch (this.queenColor) {
      case Colonies.COLOR_NONE:
        return "nicht gezeichnet";
      case Colonies.COLOR_BLUE:
        return "Blau";
      case Colonies.COLOR_WHITE:
        return "Weiß";
      case Colonies.COLOR_YELLOW:
        return "Gelb";
      case Colonies.COLOR_RED:
        return "Rot";
      case Colonies.COLOR_GREEN:
        return "Grün";
      default:
        return "n/a";
    }
  }
  
  public String visibleHtml() {
    return Util.BooleanToGlyphIcon(this.visible);
  }
  
  @Override
  public String toString() {
    return name;
  }
}