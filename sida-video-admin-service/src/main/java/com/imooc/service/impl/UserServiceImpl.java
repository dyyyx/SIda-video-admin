package com.imooc.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.UsersExample;
import com.imooc.pojo.UsersExample.Criteria;
import com.imooc.service.UserService;
import com.imooc.utils.PagedResult;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UsersMapper userMapper;
	
	
	@Override
	public PagedResult getUserList(Users user, Integer page, Integer pageSize) {
		String userName = "";
		String nickName = "";
		if (user != null) {
			userName = user.getUsername();
			nickName = user.getNickname();
		}

		UsersExample example = new UsersExample();
		Criteria criteria = example.createCriteria();

		if (StringUtils.isNoneBlank(userName)) {
			criteria.andUsernameLike("%" + userName + "%");
		}
		if (StringUtils.isNoneBlank(nickName)) {
			criteria.andNicknameLike("%" + nickName + "%");
		}
		
		List<Users> list = userMapper.selectByExample(example);
		
		PageHelper.startPage(page, pageSize);
		
		PageInfo<Users> info = new PageInfo<>(list);
		
		PagedResult result = new PagedResult();
		result.setRows(list);
		result.setPage(page);
		result.setRecords(info.getTotal());
		result.setTotal(info.getPages());

		return result;
	}

}
