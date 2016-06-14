package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;

@Entity
public class HiveRecords extends Model {

  public static Finder<Long, HiveRecords> find = new Finder<Long, HiveRecords>(HiveRecords.class);
  @Id
  public Long id;
  public Colony colony;
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