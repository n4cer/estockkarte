package models;

import javax.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

import com.avaje.ebean.Model;
import play.data.validation.*;

@Entity
@Table(name="beekeepers")
public class User extends Model{
    
	@Id
    public Long id;
    @Constraints.Required
    @Column(unique=true)
    public String name;
    @Constraints.Email
    public String email;
    public String passwordHash;
    
    public static Finder<Long,User> find = new Finder<Long,User>(User.class);
    
    /**
     * Create new user with password hash
     * @param userName
     * @param password
     * @return User
     */
    public static User create(String userName, String password) {
        User user = new User();
        user.name = userName;
        user.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        user.save();
        return user;
      }
    
    /**
     * Checks username and password
     * @param userName
     * @param password
     * @return User
     */
    public static User authenticate(String userName, String password) {
        User user = User.find.where().eq("name", userName).findUnique();
        if (user != null && BCrypt.checkpw(password, user.passwordHash)) {
          return user;
        } else {
          return null;
        }
      }
    
    public String toString() {
        return name;
    }
}