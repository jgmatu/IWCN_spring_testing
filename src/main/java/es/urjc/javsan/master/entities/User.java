package es.urjc.javsan.master.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String user;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> roles;

    public User() {
    	this.user = "";
    	this.password = "";
    	this.roles = new ArrayList<GrantedAuthority>();
    }
    
    public User(String user, String password, List<GrantedAuthority> roles) {
        this.user = user;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.roles = roles;
    }
    
    public String getUser() {
    	return this.user;
    }
    
    public void setUser(String user) {
    	this.user = user;
    }
    
    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

	public List<GrantedAuthority> getRoles() {
		return this.roles;
	}
	
	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
}