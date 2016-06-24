package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class Hive extends Model {

  public static Finder<Long, Hive> find = new Finder<Long, Hive>(Hive.class);
  @Id
  public Long id;
  @Constraints.Required
  public String name;

  @Override
  public String toString() {
    return name;
  }
}