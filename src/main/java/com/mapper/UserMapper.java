package com.mapper;

import com.domain.User;

public interface UserMapper {
	public User findByName(String name);
}
