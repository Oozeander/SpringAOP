package com.oozeander;

import java.math.BigInteger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.oozeander.model.Employee;
import com.oozeander.service.EmployeeService;
import com.oozeander.spring.AopConfig;

public class App {
	public static void main(String[] args) {
		/*
		 * AbstractApplicationContext ctx = new
		 * ClassPathXmlApplicationContext("applicationContext.xml");
		 * ctx.registerShutdownHook();
		 * 
		 * EmployeeService service = ctx.getBean(EmployeeService.class);
		 * 
		 * service.save(new Employee(BigInteger.ONE, "Billel", "KETROUCI",
		 * "billel.ketrouci@gmail.com")); System.out.println(service.getEmployees());
		 * System.out.println(service.getEmployee(BigInteger.ONE));
		 * service.delete(BigInteger.ONE);
		 * System.out.println(service.getEmployee(BigInteger.ONE));
		 * 
		 * ctx.close();
		 */
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(new Class<?>[] { AopConfig.class });
		ctx.registerShutdownHook();

		EmployeeService service = ctx.getBean(EmployeeService.class);

		service.save(new Employee(BigInteger.ONE, "Billel", "KETROUCI", "billel.ketrouci@gmail.com"));
		System.out.println(service.getEmployees());
		System.out.println(service.getEmployee(BigInteger.ONE));
		service.delete(BigInteger.ONE);
		System.out.println(service.getEmployee(BigInteger.ONE));

		ctx.close();
	}
}