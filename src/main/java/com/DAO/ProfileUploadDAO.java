package com.DAO;

import com.model.ProfilePicture;

public interface ProfileUploadDAO {
	void save(ProfilePicture profilePicture);
    ProfilePicture getProfilePic(String username);
}
