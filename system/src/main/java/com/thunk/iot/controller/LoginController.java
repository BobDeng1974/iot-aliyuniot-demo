package com.thunk.iot.controller;

import com.alibaba.fastjson.JSONObject;
import com.thunk.iot.service.LoginService;
import com.thunk.iot.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: hxy
 * @description: 登录相关Controller
 * @date: 2017/10/24 10:33
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 登录
	 */
	@PostMapping("/auth")
	public JSONObject authLogin(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "username,password");
		return loginService.authLogin(requestJson);
	}

	/**
	 * 查询当前登录用户的信息
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/getInfo")
	public JSONObject getInfo(HttpServletRequest request, HttpServletResponse response) {
		return loginService.getInfo();
	}

	/**
	 * 登出
	 */
	@PostMapping("/logout")
	public JSONObject logout() {
		return loginService.logout();
	}
}
