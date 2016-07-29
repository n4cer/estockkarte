package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
  @OneToMany(mappedBy="stand", cascade=CascadeType.ALL)
  public List<Colony> colonies;
  
  public Integer numberOfColonies() {
    if(this.colonies == null) {
      return 0;
    }
    
    return this.colonies.size();
  }
  
  @Override
  public String toString() {
    return name;
  }
}