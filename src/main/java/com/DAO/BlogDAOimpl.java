package com.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.BlogComment;
import com.model.BlogPost;

@Repository
public class BlogDAOimpl implements BlogDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveBlogPost(BlogPost blogPost) {
		
		Session session = sessionFactory.openSession();
		session.save(blogPost);
		session.flush();
		session.close();
	}
	
	public List<BlogPost> getBlogPosts() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from BlogPost");
		List<BlogPost> blogPosts = query.list();
		session.close();
		return blogPosts;
	}

	public BlogPost getBlogPostById(int id) {
		Session session = sessionFactory.openSession();
		BlogPost blogPost = (BlogPost)session.get(BlogPost.class, id);
		session.close();
		return blogPost;
	}
	
	public void addBlogComment(BlogComment blogComment) {
		Session session = sessionFactory.openSession();
		session.save(blogComment);
		session.flush();
		session.close();
	}

	public List<BlogComment> getBlogComments(int blogPostId) {
		Session session = sessionFactory.openSession();
		BlogPost blogPost = (BlogPost)session.get(BlogPost.class, blogPostId);
		List<BlogComment> blogComments = blogPost.getBlogComments();
		System.out.println(blogPost+"-----------in impl");
		session.close();
		return blogComments;
	}

	public List<BlogPost> getBlogPosts(int approved) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from BlogPost where approved="+approved);
		List<BlogPost> blogPosts = query.list();
		session.close();
		return blogPosts;
	}
    
	public void update(BlogPost blogPost){
		Session session = sessionFactory.openSession();
		session.update(blogPost);
		session.flush();
		session.close();
	}
}