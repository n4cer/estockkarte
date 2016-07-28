package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Model;

import play.data.validation.Constraints;

@Entity
public class Stand extends Model {

  public static Finder<Long, Stand> find = new Finder<Long, Stand>(Stand.class);
  @Id
  public Long id;
  @Constraints.Required
  public String name;
  public String street;
  public String zipCode;
  public String city;
  @ManyToOne
  public User user;
  
  @Override
  public String toString() {
    return name;
  }
}