package com.service;

import com.domain.User;

public interface UserService {
	public User findByName(String name);

	public User findById(Integer id);
}
