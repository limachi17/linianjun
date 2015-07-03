/**
 * @Title: SensetiveWordsMapper.java
 * @Package com.moxian.quartz.dao.impl
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月21日 上午11:52:50
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.tfc.analysis.entity.Keyword;

/**
 * @ClassName: SensetiveWordsMapper
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月21日 上午11:52:50
 *
 */
public interface SensitiveWordsMapper {
	
	@Select("SELECT word FROM sensitive_word where 1=1")
	@Results(
			value = {
				@Result(property="word", column="word", javaType=String.class,jdbcType=JdbcType.VARCHAR)
			}
			)
	List<String> getAllSensetiveWords();

}
