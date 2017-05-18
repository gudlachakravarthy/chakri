package com.DAO;

import java.util.List;
import com.model.Job;

public interface JobDAO {
	void saveJobDetails(Job job);
	List<Job> getAllJobDetails();
	Job getJobById(int id);
}