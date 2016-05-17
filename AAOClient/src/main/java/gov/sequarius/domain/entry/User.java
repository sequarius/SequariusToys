package gov.sequarius.domain.entry;

import org.hibernate.validator.constraints.Length;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by Sequarius on 2016/4/14.
 */
@Entity
@Table(name = "user_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    @Column(name ="user_login_token")
    private String token;
    @Column(name = "user_name")
    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
