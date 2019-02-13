package com.globalcapital.pack.database.entity;

import java.util.List;

import com.globalcapital.database.datasource.H2DatabaseLuncher;

public class Users {



	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {


		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String firstName;

	private String lastName;

	private String userName;

	private String password;

	private int role_id;

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public Role roleByd;

	public Role getRoleByd() {
		setRoleByd(roleByd);
		return roleByd;
	}

	private String passwordConfirm;

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	private void setRoleByd(Role roleByd) {

		H2DatabaseLuncher hs = new H2DatabaseLuncher();
		int counter = hs.listRole().size();

		if (counter >= 0 && !(hs.listRole().isEmpty()) && getRole_id() >= 0) {
			counter--;

			for (Role r : hs.listRole()) {
				if (r.getId() == getRole_id()) {

					this.roleByd = r;
				}

			}
		}

	}

	private List<Role> Role;

	public List<Role> getRole() {
		setRole(this.Role);
		return Role;
	}

	public void setRole(List<Role> role) {
		H2DatabaseLuncher hs = new H2DatabaseLuncher();
		Role = hs.listRole();
	}


}
