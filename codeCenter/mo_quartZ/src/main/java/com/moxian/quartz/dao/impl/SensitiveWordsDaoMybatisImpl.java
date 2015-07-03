/**
 * @Title: SensetiveWordsDaoMybatisImpl.java
 * @Package com.moxian.quartz.dao
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月21日 上午11:50:52
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.dao.impl;

import java.util.List;

import com.moxian.quartz.dao.SensitiveWordsDao;
import com.tfc.analysis.entity.Keyword;

/**
 * @ClassName: SensetiveWordsDaoMybatisImpl
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月21日 上午11:50:52
 *
 */
public class SensitiveWordsDaoMybatisImpl implements SensitiveWordsDao{
	
	final private SensitiveWordsMapper sensetiveWordsMapper;

	public SensitiveWordsDaoMybatisImpl(SensitiveWordsMapper sensetiveWordsMapper) {
		this.sensetiveWordsMapper = sensetiveWordsMapper;
	}

	@Override
	public List<String> getAllSensetiveWords() {
		return sensetiveWordsMapper.getAllSensetiveWords();
	}

}
