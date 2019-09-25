package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.utils.PagedResult;

public interface UserService {

	public PagedResult getUserList(Users user, Integer page,Integer pageSize);
	
	
}
