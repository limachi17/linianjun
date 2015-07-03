/**
 * @Title: SensetiveWordsInitializer.java
 * @Package com.moxian.quartz.validation
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月20日 上午11:29:06
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.validation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.moxian.quartz.dao.SensitiveWordsDao;
import com.tfc.analysis.entity.Keyword;

/**
 * @ClassName: SensetiveWordsInitializer
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月20日 上午11:29:06
 *
 */
public class SensitiveWordsInitializer {
	
	@Inject
	private SensitiveWordsDao sensetiveWordsDao;

	public List<Keyword> getAllSensetiveWords() {
		
		List<String> strList = sensetiveWordsDao.getAllSensetiveWords();
		List<Keyword> kws = new ArrayList<Keyword>();
		for(String str: strList){
			Keyword key = new Keyword(str);
			kws.add(key);
		}
		return kws;
	}
}
