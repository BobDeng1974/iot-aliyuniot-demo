package com.thunk.iot.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.thunk.iot.domain.SysPermission;
import com.thunk.iot.domain.SysRolePermission;
import com.thunk.iot.dto.BaseResult;
import com.thunk.iot.dto.ResultEnum;
import com.thunk.iot.service.SysPermissionService;
import com.thunk.iot.service.SysRolePermissionService;
import com.thunk.iot.service.UserService;
import com.thunk.iot.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: hxy
 * @description: 用户/角色/权限相关controller
 * @date: 2017/11/2 10:19
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SysPermissionService permissionService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("user:list")
	@GetMapping("/list")
	public JSONObject listUser(HttpServletRequest request) {
		return userService.listUser(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("user:add")
	@PostMapping("/addUser")
	public JSONObject addUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "username, password, nickname,   roleId");
		return userService.addUser(requestJson);
	}

	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
	public JSONObject updateUser(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, " nickname,   roleId, deleteStatus, userId");
		return userService.updateUser(requestJson);
	}

	@RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
	@GetMapping("/getAllRoles")
	public JSONObject getAllRoles() {
		return userService.getAllRoles();
	}

	/**
	 * 角色列表
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listRole")
	public JSONObject listRole() {
		return userService.listRole();
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	public JSONObject listAllPermission() {
		return userService.listAllPermission();
	}

	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	public JSONObject addRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
		return userService.addRole(requestJson);
	}

	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	public JSONObject updateRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
		return userService.updateRole(requestJson);
	}

	/**
	 * 删除角色
	 */
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId");
		return userService.deleteRole(requestJson);
	}


	/**
	 * 查询权限列表
	 * @param pagenum
	 * @param pageSize
	 * @param sysPermission
	 * @return
	 */
	@RequiresPermissions("permission:list")
	@GetMapping("/permission/{pageNum}/{pageSize}")
	public BaseResult addPermission(@PathVariable("pageNum") Integer pagenum,
									@PathVariable("pageSize") Integer pageSize,
									@RequestBody(required = false) SysPermission sysPermission){
		PageInfo page = permissionService.page(pagenum, pageSize, sysPermission);
		List<SysPermission> list = page.getList();
		BaseResult.Cursor cursor = new BaseResult.Cursor();
		cursor.setTotal((int) page.getTotal());
		cursor.setOffset(page.getPageNum());
		cursor.setLimit(page.getPageSize());
		cursor.setPages(page.getPages());
		cursor.setFirstPage(page.isIsFirstPage());
		cursor.setLastPage(page.isIsLastPage());
		cursor.setPreviousPage(page.isHasPreviousPage());
		cursor.setNextPage(page.isHasNextPage());
		return BaseResult.ok(list, cursor);
	}

	/**
	 * 更新权限信息
	 * @param id
	 * @param permission
	 * @return
	 */
	@RequiresPermissions("permission:update")
	@PutMapping("/permission")
	public BaseResult updatePermission(@RequestBody SysPermission permission){
		int update = permissionService.updateByPermissionId(permission);
		System.out.println(update);
		if (update == 1){
			return BaseResult.ok();
		}
		return BaseResult.notOk(ResultEnum.SaveError.getCode(), ResultEnum.SaveError.getMessage());
	}


	/**
	 * 删除权限信息
	 * @param id
	 * @return
	 */
	@RequiresPermissions("permission:delete")
	@DeleteMapping("/permission/{id}")
	public BaseResult deletePermission(@PathVariable("id") Integer id){
		SysRolePermission sysRolePermission = new SysRolePermission();
		sysRolePermission.setPermissionId(id);
		if (!StringUtils.isEmpty(sysRolePermissionService.selectOne(sysRolePermission))){
			return BaseResult.notOk(ResultEnum.DeleteError.getCode(), "权限绑定有角色，请先解除绑定");
		}

		SysPermission sysPermission = new SysPermission();
		sysPermission.setId(id);

		if (permissionService.delete(sysPermission) == 1) {
			return BaseResult.ok();
		}
		return BaseResult.notOk(ResultEnum.DeleteError.getCode(), "删除失败，请重试");
	}

	/**
	 * 添加权限
	 * @param sysPermission
	 * @return
	 */
	@RequiresPermissions("permission:add")
	@PostMapping("/permission")
	public BaseResult addPermission(@RequestBody SysPermission sysPermission){
		log.info(sysPermission.toString());
		if (permissionService.insert(sysPermission) == 1) {
			return BaseResult.ok();
		}
		return BaseResult.notOk(ResultEnum.SaveError.getCode(), ResultEnum.SaveError.getMessage());
	}
}
