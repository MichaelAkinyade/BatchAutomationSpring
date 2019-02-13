package com.globalcapital.pack.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/*@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor*/
@Entity
@Table(name = "user")
public class User {

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String name) {
		this.firstname = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getUsername() {
		
		return this.username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
	
	@Column(name = "username")
    private String username;
	
    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    
    
    @Column(name = "firstname")
    @NotEmpty(message = "*Please provide your name")
    private String firstname;
   
    @Column(name = "lastname")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    
    @Column(name="role_id")
    private int rolebyId;

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public int getRolebyId() {
		return rolebyId;
	}
	public void setRolebyId(int rolebyId) {
		this.rolebyId = rolebyId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
  
}
