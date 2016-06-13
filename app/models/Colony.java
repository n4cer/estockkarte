package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class Colony extends Model {

  public static Finder<Long, Colony> find = new Finder<Long, Colony>(Colony.class);
  @Id
  public Long id;
  @Constraints.Required
  @Column(unique = true)
  public String name;
  public String race;
  public String queen;
  public String queenColor;
  public String queenIdent;
  public String hive;
  public String comment;

  @Override
  public String toString() {
    return name;
  }
}