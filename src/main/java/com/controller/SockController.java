package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.DAO.UserDAO;
import com.model.Chat;
import com.model.User;

@Controller
public class SockController {
	private final SimpMessagingTemplate messagingTemplate;
	private List<String> users = new ArrayList<String>();
	
	@Autowired
	public SockController(SimpMessagingTemplate messagingTemplate){
		this.messagingTemplate = messagingTemplate;
	}
	
	@Autowired
	private UserDAO userDao;
	
	@SubscribeMapping("/join")
	public List<String> join(HttpSession session){
		
		 User user = (User)session.getAttribute("user");
		 System.out.println("inside join method");
		 String username = user.getUsername();
		 List<String> onlineUsers = userDao.getOnlineUsers();
		 
		 for(String s : onlineUsers){
			 if(!users.contains(s)){
				 username = s;
				 users.add(s);
			 }
		 }
		 for(String u : users){
			 if(!onlineUsers.contains(u))
				 users.remove(u);
		 }
		 
		 System.out.println("JOIN====="+username);
		 messagingTemplate.convertAndSend("/topic/join",username);
		 return users;
	}
	
	public void remove(String username){
		users.remove(username);
	}
	
	@MessageMapping(value="/chat")
	public void chatRevived(Chat chat){
		
		System.out.println(chat.getFrom());
		if("all".equals(chat.getTo())){
			
			System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());
			messagingTemplate.convertAndSend("/queue/chats",chat);
		}
		else{
			messagingTemplate.convertAndSend( "/queue/chats/"+chat.getTo(),chat);
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(),chat);
		}
	}
}
