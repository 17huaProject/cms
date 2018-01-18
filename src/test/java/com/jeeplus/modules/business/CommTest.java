package com.jeeplus.modules.business;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.utils.CommonConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={	"classpath*:/spring-context.xml",
									"classpath*:/spring-context-shiro.xml",
									"classpath*:/spring-context-jedis.xml",
									"classpath*:/spring-mvc.xml",
									"classpath*:/mybatis.xml"})
public class CommTest {

	@Test
	public void test(){
		System.out.println(JSON.toJSONString(CommonConstants.STATUS_EFFECTIVE));
	}
}
