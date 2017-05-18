package com.DAO;

import java.util.List;

import com.model.User;

public interface UserDAO {
	User registerUser(User user);
	User login(User user);
	void updateUser(User user);
	User getUser(int id);
	public List<String> getOnlineUsers();
}