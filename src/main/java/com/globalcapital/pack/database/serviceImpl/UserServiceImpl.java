/**
 * @Author Michael Akinyade Administrator
 */
/*
 * package com.globalcapital.pack.database.serviceImpl;
 * 
 * import java.util.List;
 * 
 *//**
	 * @author Administrator
	 *
	 *//*
		 * 
		 * import org.springframework.beans.factory.annotation.Autowired; import
		 * org.springframework.beans.factory.annotation.Qualifier; import
		 * org.springframework.security.core.userdetails.UserDetails; import
		 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
		 * org.springframework.stereotype.Service;
		 * 
		 * import com.globalcapital.database.datasource.H2DatabaseLuncher; import
		 * com.globalcapital.pack.database.entity.Users; import
		 * com.globalcapital.pack.database.serviceInterface.UserService;
		 * 
		 * @Service
		 * 
		 * @Qualifier("userServiceImpl") public class UserServiceImpl implements
		 * UserService {
		 * 
		 * @Autowired private static BCryptPasswordEncoder bCryptPasswordEncoder;
		 * 
		 * @Override public void save(Users user) {
		 * user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //
		 * user.setRoles(new HashSet<>(roleRepository.findAll()));
		 * H2DatabaseLuncher.saveUser(user); }
		 * 
		 * @Override public Users findByUsername(String username) { return
		 * H2DatabaseLuncher.getUserByUsername(username); }
		 * 
		 * 
		 * (non-Javadoc)
		 * 
		 * @see com.globalcapital.pack.database.serviceInterface.UserService#
		 * loadUserByUsername(java.lang.String)
		 * 
		 * @Override public UserDetails loadUserByUsername(String userId) { // TODO
		 * Auto-generated method stub return null; }
		 * 
		 * 
		 * (non-Javadoc)
		 * 
		 * @see com.globalcapital.pack.database.serviceInterface.UserService#getUsers()
		 * 
		 * @Override public List getUsers() { // TODO Auto-generated method stub return
		 * null; }
		 * 
		 * }
		 */