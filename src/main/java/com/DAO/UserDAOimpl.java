package com.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository
public class UserDAOimpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;
     
	public User registerUser(User user){
    	 Session session = sessionFactory.openSession();
    	 session.save(user);
    	 session.flush();
    	 session.close();
    	 return user;
     }
	
	public User login(User user){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where username=? and password=?");
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		User validUser = (User)query.uniqueResult();
		session.close();
		return validUser;
	}
	
	public void updateUser(User user) {
		Session session = sessionFactory.openSession();
		session.update(user);
		session.flush();
		session.close();
	}

	public User getUser(int id) {
		Session session = sessionFactory.openSession();
		User user = (User)session.get(User.class, id);
		session.close();
		return user;
	}
	
	public List<String> getOnlineUsers(){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where online="+true);
		List<User> users = query.list();
		List<String> username = new ArrayList<String>();
		for(User user : users)
		   username.add(user.getUsername());
		session.close();
		return username;
	}
	
}