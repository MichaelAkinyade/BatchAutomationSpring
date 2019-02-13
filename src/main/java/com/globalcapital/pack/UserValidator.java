/**
 * @Author Michael Akinyade  Administrator
 */
package com.globalcapital.pack;

/**
 * @author Administrator
 *
 */

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.globalcapital.pack.database.entity.Users;
import com.globalcapital.pack.service.UserService;

	@Component
	public class UserValidator implements Validator {

	    @Autowired
	    private UserService userService;

	    
	    @Override
	    public boolean supports(Class<?> aClass) {
	        return Users.class.equals(aClass);
	    }

	    @Override
	    public void validate(Object o, Errors errors) {
	        Users user = (Users) o;

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
	        if (user.getUserName().length() < 3 || user.getUserName().length() > 32) {
	            errors.rejectValue("username", "Size.userForm.username");
	        }
		/*
		 * if (userService.findByUsername(user.getFirstName()) != null) {
		 * errors.rejectValue("username", "Duplicate.userForm.username"); }
		 */

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
	            errors.rejectValue("password", "Size.userForm.password");
	        }

	        if (!user.getPasswordConfirm().equals(user.getPassword())) {
	            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	        }
	    }
	}


