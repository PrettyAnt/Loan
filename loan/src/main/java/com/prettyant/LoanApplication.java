package com.prettyant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanApplication {
	//默认情况下Tomcat等服务器是拒绝url中带%2F或者%5C的URL,因为它们经浏览器解析之后就变成了/和\,
	// 服务器默认是拒绝访问的,所以需要通过服务的配置来解决这个问题。
	static {
		//解决URL中包含%2F的问题
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		//解决URL中包含%5C的问题
		System.setProperty("org.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH", "true");
	}
	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
