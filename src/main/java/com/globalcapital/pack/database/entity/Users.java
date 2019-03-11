package com.globalcapital.pack.database.entity;

import java.util.List;

import com.globalcapital.database.datasource.H2DatabaseLuncher;

public class Users {
	public String confirmPassword;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

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

	public String firstName;

	public String lastName;

	public String userName;

	public String password;

	public int role_id;

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
		List<Role> hs = H2DatabaseLuncher.listRole();

		int counter = hs.size();

		if (counter >= 0 && !(hs.isEmpty()) && getRole_id() >= 0) {
			counter--;

			for (Role r : hs) {
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

	public boolean match(String usernamename, String password) {
		return this.userName.equals(userName) && this.password.equals(password);
	}

}
