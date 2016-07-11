package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;

@Entity
public class HiveRecord extends Model {

  public static Finder<Long, HiveRecord> find = new Finder<Long, HiveRecord>(HiveRecord.class);
  @Id
  public Long id;
  @ManyToOne
  public Colony colony;
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
  @ManyToOne
  public User user;

  @Override
  public String toString() {
    return id + " " + colony;
  }
}