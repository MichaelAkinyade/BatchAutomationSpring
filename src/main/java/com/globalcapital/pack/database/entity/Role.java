/**
 * @Author Michael Akinyade  Administrator
 */
package com.globalcapital.pack.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */

@Entity
@Table
public class Role {
	


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@SequenceGenerator(name = "team_Sequence", sequenceName = "TEAM_SEQ")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_name")
	private String roleName;
	


}
