package com.oozeander.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	List<Object> params = new ArrayList<Object>();
	// More Pointcuts
	/*
	 * within(com.oozeander..*) => all methods within all classes from sub packages
	 * of com.oozeander
	 *
	 * within(com.oozeander.service.EmployeeService) => ClassNames (instead of
	 * method for execution)
	 *
	 * args(java.math.BigInteger, ..) => applied for method with args that
	 * correspond
	 */

	@Pointcut("execution(* com.oozeander.service.EmployeeService.*(..))")
	public void beforePointcut() {
	}

	@Pointcut("execution(com.oozeander.model.Employee com.oozeander.service.EmployeeService.get*(java.math.BigInteger, ..))")
	public void afterPointcut() {
	}

	@Pointcut("execution(* com.oozeander.service.EmployeeService.delete(java.math.BigInteger, ..))")
	public void aroundPointcut() {
	}

	@Pointcut("execution(java.util.List com.oozeander.service.EmployeeService.*(..))")
	public void afterReturningPointcut() {
	}

	@Pointcut("execution(com.oozeander.model.Employee com.oozeander.service.EmployeeService.*(..))")
	public void afterThrowingPointcut() {
	}

	@Before("beforePointcut()")
	public void beforeAdvice(JoinPoint joinPoint) {
		params.clear();
		Arrays.stream(joinPoint.getArgs()).forEach(params::add);
		if (joinPoint.getArgs().length == 0) {
			LOGGER.info(joinPoint.getSignature().getName() + " has been called, target : "
					+ joinPoint.getTarget().getClass().getCanonicalName());
		} else {
			LOGGER.info(joinPoint.getSignature().getName() + " has been called with arguments array : " + params
					+ ", target : " + joinPoint.getTarget().getClass().getCanonicalName());
		}
	}

	@After("afterPointcut()")
	public void afterAdvice(JoinPoint joinPoint) {
		params.clear();
		Arrays.stream(joinPoint.getArgs()).forEach(params::add);
		if (joinPoint.getArgs().length == 0) {
			LOGGER.info(joinPoint.getSignature().getName() + " has successfully finished, target : "
					+ joinPoint.getTarget().getClass().getCanonicalName());
		} else {
			LOGGER.info(joinPoint.getSignature().getName() + " has successfully finished with arguments array : "
					+ params + ", target : " + joinPoint.getTarget().getClass().getCanonicalName());
		}
	}

	@Around("aroundPointcut()")
	public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		// Do something before
		LOGGER.info(joinPoint.getSignature().getName() + " has been called");
		try {
			joinPoint.proceed();
		} finally {
			// Do something after
		}
		LOGGER.info(joinPoint.getSignature().getName() + " has successfully ended");
	}

	@AfterReturning(pointcut = "afterReturningPointcut()", returning = "list")
	public void afterReturningAdvice(JoinPoint joinPoint, Object list) {
		LOGGER.info(joinPoint.getSignature().getName() + " has been successfully finished and returned : " + list);
	}

	@AfterThrowing(pointcut = "afterThrowingPointcut()", throwing = "ex")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
		LOGGER.error(joinPoint.getSignature().getName() + " has thrown an exception of type : "
				+ ex.getClass().getName() + ", cause : " + ex.getLocalizedMessage());
	}

}