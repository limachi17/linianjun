/**
 * @Title: SensetiveWordsValidator.java
 * @Package com.moxian.quartz.validation
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月16日 下午9:51:06
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.validation;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.moxian.quartz.SensitiveWordsConfig;
import com.tfc.analysis.KWSeeker;

/**
 * @ClassName: SensetiveWordsValidator
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月16日 下午9:51:06
 *
 */
public class SensitiveWordsValidator implements ConstraintValidator<SensitiveWords, String>{
	

/*	@Inject
	private SensetiveWordsService wordService;*/
	
	
	//private KWSeeker kw = null;
	
    public void initialize(SensitiveWords words) {
    }
    
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
    	KWSeeker kw =KWSeeker.getInstance(SensitiveWordsConfig.sensetiveWords);
    	Set<String> words = kw.findWords(value);
    	if(words.size()== 0 ){
    		return true;
    	}
        return false;
    }
}
