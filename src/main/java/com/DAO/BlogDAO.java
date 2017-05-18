package com.DAO;

import java.util.List;

import com.model.BlogComment;
import com.model.BlogPost;

public interface BlogDAO {
	void saveBlogPost(BlogPost blogPost);
	List<BlogPost> getBlogPosts();
	List<BlogPost> getBlogPosts(int approved);
	BlogPost getBlogPostById(int id);
	void addBlogComment(BlogComment blogComment);
	List<BlogComment> getBlogComments(int blogPostId);
	 void update(BlogPost blogPost);
}