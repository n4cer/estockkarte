package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class Colony extends Model {

  public static Finder<Long, Colony> find = new Finder<Long, Colony>(Colony.class);
  @Id
  public Long id;
  @Constraints.Required
  public String name;
  public String number;
  public String race;
  public String queen;
  public Integer queenColor;
  public String queenIdent;
  @ManyToOne
  public Hive hive;
  public String comment;
  @ManyToOne
  public User user;
  @Column(columnDefinition = "boolean default false")
  public Boolean visible;

  @Override
  public String toString() {
    return name;
  }
}