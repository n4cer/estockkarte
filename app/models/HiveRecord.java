package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;

@Entity
public class HiveRecord extends Model {

  public static Finder<Long, HiveRecord> find = new Finder<Long, HiveRecord>(HiveRecord.class);
  @Id
  public Long id;
  @OneToOne
  public Colony colony;
  public Date date;
  public Integer gentleness;
  public Integer swarming;
  public Integer strength;
  public Boolean queen;
  public Boolean maggots;
  public Boolean cappedBrood;
  public Boolean queenCells;
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