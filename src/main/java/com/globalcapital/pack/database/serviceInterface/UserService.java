/**
 * @Author Michael Akinyade  Administrator
 */
package com.globalcapital.pack.database.serviceInterface;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.globalcapital.pack.database.entity.Users;

/**
 * @author Administrator
 *
 */
public interface UserService {

	public UserDetails loadUserByUsername(String userId);
	void save(Users user);
	public List getUsers();
	
	
}
