package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.UserDAO;
import com.model.User;
import com.model.Error;

@Controller
public class UserController {
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
		
		try{
			user.setEnabled(true);
			user.setOnline(false);
			User savedUser = userDao.registerUser(user);
			
			return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		}catch(Exception e){
			
			e.printStackTrace();
			Error error = new Error(2,"could not be inserted, cannot have null/duplicate values");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
		user = userDao.login(user);
		
		if(user == null){
			Error error = new Error(3,"Invalid credentials,please enter valid username and password");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		else{
			session.setAttribute("user", user);
			user.setOnline(true);
			userDao.updateUser(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session){
		
		  
		  User user = (User)session.getAttribute("user");
		  if(user == null){
			  Error error = new Error(3,"Unauthorized user, please log in");
			  return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		  }
		  else{
			 
			  user.setOnline(false);
			  user = userDao.getUser(user.getId());
			  userDao.updateUser(user);
			  session.removeAttribute("user");
			  session.invalidate();
			  return new ResponseEntity<Void>(HttpStatus.OK);
		  }
	}
	
	@RequestMapping(value="/getuser",method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session){
		 User user = (User)session.getAttribute("user");
		 if(user == null){
			 Error error = new Error(3, "Unauthorized user");
			 return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		 }
		 else{
			 user = userDao.getUser(user.getId());
			 return new ResponseEntity<User>(user,HttpStatus.OK);
		 }
	}
	
	@RequestMapping(value="/updateuser",method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User updatedUser,HttpSession session){
		User user = (User)session.getAttribute("user");
	    if(user == null){
	    	Error error = new Error(3, "Unauthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    }
	    else{
	    	userDao.updateUser(updatedUser);
	    	return new ResponseEntity<Void>(HttpStatus.OK);
	    }
	}
}