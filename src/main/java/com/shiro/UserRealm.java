package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.domain.User;
import com.service.UserService;


/**
 * 自定义realm
 * @author Administrator
 *
 */
public class UserRealm extends AuthorizingRealm {
	
	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		//添加资源授权字符串
//		info.addStringPermission("User:add");
		//获取当前登录用户的id
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		User dbUser = useService.findById(user.getId());

		info.addStringPermission(dbUser.getPerms());

		return info;
	}
	//注入业务
		@Autowired
		private UserService useService;
	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		
		
		
		//编写shiro判断逻辑，判断用户名和密码
		//1.判断用户名
		UsernamePasswordToken userToken = (UsernamePasswordToken)token;
		
		User user = useService.findByName(userToken.getUsername());
		if(null == user) {
			return null;//shiro底层会抛出UnknownAccountException
		}
		//2.判断密码
		return new SimpleAuthenticationInfo(user,user.getPassword(),"");
	}


}
