/**
 * @Title: tt.java
 * @Package com.moxian.common.sso.aop
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月9日 下午5:06:46
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.aop;

/**
 * @ClassName: tt
 * @Description: 监控serviceImpl层方法执行时间
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月9日 下午5:06:46
 *
 */
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
 
@Slf4j
@Aspect
@Component
public class LoggingAspect {
   @Around("execution(* com.moxian.*.*.service.impl.*.*(..))")
   public Object beforeMethodService(ProceedingJoinPoint joinPoint) throws Throwable{
       String name = joinPoint.getSignature().getName();
       log.info("########## Call Method {} Begin ########## ", name);
       StopWatch sw = new StopWatch();
       try {
           sw.start();
           return joinPoint.proceed();
       } 
       finally{
           sw.stop();
           log.info("########## Call Method {} End ########## Method {} takes {}ms", name, name, sw.getTotalTimeMillis());
       }
   }
}