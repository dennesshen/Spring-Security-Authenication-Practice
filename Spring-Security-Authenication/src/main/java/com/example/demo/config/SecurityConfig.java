package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.handle.MyAccessDeniedHandler;

//若要自訂登入邏輯，則要繼承WebSecurityConfiguration  
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		 return new BCryptPasswordEncoder(); 
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//表單提交
		http.formLogin() //
			.loginProcessingUrl("/login")
			.loginPage("/loginpage") // 自定義登入頁面
			.successForwardUrl("/") // 登入成功後
			.failureForwardUrl("/fail");
		
		//授權認證
		http.authorizeHttpRequests()
			// 不需要被認證的頁面：/loginpage
			.antMatchers("/loginpage").permitAll()
			//權限判斷
			//必須要有admin權限才可以訪問
			.antMatchers("/adminpage").hasAuthority("admin")
			//必須要有manager角色才可以訪問	
			.antMatchers("/managerpage").hasRole("manage")
			//其他都可以訪問
			.antMatchers("/employeepage").hasAnyRole("manager", "employee")
			// 其他的都要被認證
			.anyRequest().authenticated();
		
		http.logout()
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/loginpage")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		//異常處理
		http.exceptionHandling()
			.accessDeniedHandler(myAccessDeniedHandler);
			//.accessDeniedPage("") 二擇一
		
		// 勿忘我（remember-me）
		http.rememberMe()
			.userDetailsService(userDetailsService)
			.tokenValiditySeconds(60 * 60 * 24); // 通常都會大於 session timeout 的時間
		
	}

	

}
