package com.mapper;

import com.domain.User;

public interface UserMapper {
	public User findByName(String name);

	public User findById(Integer id);
}
