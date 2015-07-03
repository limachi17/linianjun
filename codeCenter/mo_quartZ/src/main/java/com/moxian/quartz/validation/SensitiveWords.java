/**
 * @Title: SensetiveWords.java
 * @Package com.moxian.quartz.validation
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月16日 下午9:45:28
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @ClassName: SensetiveWords
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月16日 下午9:45:28
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SensitiveWordsValidator.class)
public @interface SensitiveWords {

	 String message() default "{SensetiveWords}";
	 
	 Class<?>[] groups() default {};
	 
	 Class<? extends Payload>[] payload() default {};
}
