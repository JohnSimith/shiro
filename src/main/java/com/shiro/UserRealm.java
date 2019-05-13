package com.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
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
		return null;
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
		return new SimpleAuthenticationInfo("",user.getPassword(),"");
	}


}
