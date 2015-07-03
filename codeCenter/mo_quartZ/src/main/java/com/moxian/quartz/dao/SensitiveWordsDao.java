/**
 * @Title: SensetiveWordsDao.java
 * @Package com.moxian.quartz.dao
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月21日 上午11:49:00
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.dao;

import java.util.List;

import com.tfc.analysis.entity.Keyword;

/**
 * @ClassName: SensetiveWordsDao
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月21日 上午11:49:00
 *
 */
public interface SensitiveWordsDao {
	
	/**
	 * 
	* @Title: getAllSensetiveWords 
	* @param: 
	* @Description: 加载所有的敏感词
	* @return List<Keyword>
	 */
	public List<String> getAllSensetiveWords();

}
