package com.imooc.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.bean.AdminUser;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public IMoocJSONResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return IMoocJSONResult.errorMsg("用户名密码不能为空....");
		} else if ("admin".equals(username) && "admin".equals(password)) {
			String token = UUID.randomUUID().toString();
			AdminUser user = new AdminUser(username, password, token);
			request.getSession().setAttribute("sessionUser", token);
			return IMoocJSONResult.ok();
		}

		return IMoocJSONResult.errorMsg("用户名密码错误....");
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("sessionUser");
		return "login";
	}

	@GetMapping("/showList")
	public String userList() {

		return "users/usersList";
	}

	@ResponseBody
	@PostMapping("/list")
	public PagedResult list(Users user, Integer page) {

		return userService.getUserList(user, page == null ? 1 : page, 10);

	}
}
