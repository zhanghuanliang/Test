/**
 * 
 */
package com.zhl.www.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhl.www.service.impl.WebServiceTest;

/**
 * TestConfig类
 *
 * @author zhanghuanliang
 * @email zhanghuanliang@boco.com.cn
 * @version v1.0
 * @time 2019年5月29日 下午3:30:54
 * @modify <BR/>
 *         修改内容：<BR/>
 *         修改人员：<BR/>
 *         修改时间：<BR/>
 */
@Configuration
public class TestConfig {

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	public WebServiceTest testService() {
		return new WebServiceTest();
	}

	@Bean
	public EndpointImpl endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), testService());
		endpoint.publish("/ssoserver/services/WorkService");
		return endpoint;
	}
}
