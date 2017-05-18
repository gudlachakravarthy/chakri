package com.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.ProfilePicture;

@Repository
public class ProfileUploadDAOImpl implements ProfileUploadDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(ProfilePicture profilePicture) {
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(profilePicture);
		session.flush();
		session.close();
	}

	public ProfilePicture getProfilePic(String username) {
		Session session = sessionFactory.openSession();
		ProfilePicture profilePic = (ProfilePicture)session.get(ProfilePicture.class, username);
		session.close();
		return profilePic;
	}
}
