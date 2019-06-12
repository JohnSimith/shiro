package com.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro的配置类
 * @author Administrator
 *
 */
@Configuration
public class 		ShiroConfig {
	
	/**
	 * 创建shiroFilterFactoryBean 
	 */
	@Bean
	public ShiroFilterFactoryBean getshiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		/**
		 * shiro内置过滤器，可以实现权限相关的拦截器
		 * 	常用的过滤器：
		 * 		anon:无需认证（登录）可以访问
		 * 		authc:必须认证才可以访问
		 * 		user:如果使用rememberMe的功能可以直接访问
		 * 		perms：该资源必须有资源权限才可以访问
		 * 		role：该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterMap = new LinkedHashMap<String, String>();
		
		filterMap.put("/testThymeleaf", "anon");
		//放行login请求
		filterMap.put("/login", "anon");

		//授权过滤器
		//注意：当授权拦截器后，shiro会自动跳转到未授权页面
		filterMap.put("/add","perms[user:add]");
		filterMap.put("/update","perms[user:update]");

//		filterMap.put("/add", "authc");
//		filterMap.put("/update", "authc");
		//修改为统配的方式
		filterMap.put("/*", "authc");
		//修改调整登录页面
		shiroFilterFactoryBean.setLoginUrl("/toLogin");

		//设置未授权跳转页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}
	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(userRealm);
		return securityManager;
	}

	/**
	 * 创建Realm的对象
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}

	/**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}
}
