package com.site.hospital_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @EnableAspectJAutoProxy — explicitly activates Spring's AOP proxy mechanism.
 * (Spring Boot auto-configures this via spring-boot-starter-aop, but declaring
 * it explicitly makes the intent clear and is considered best practice.)
 *
 * proxyTargetClass = true  → uses CGLIB subclass proxies (works even without interfaces).
 * proxyTargetClass = false → uses JDK dynamic proxies (requires interface).
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class HospitalManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementApplication.class, args);
	}

}
