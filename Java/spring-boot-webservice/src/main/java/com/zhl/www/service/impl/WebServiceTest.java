/**
 * 
 */
package com.zhl.www.service.impl;

import javax.jws.WebService;

import com.zhl.www.service.IWebServiceTest;

/**
 * WebService类
 *
 * @author zhanghuanliang
 * @email zhanghuanliang@boco.com.cn
 * @version v1.0
 * @time 2019年5月29日 下午3:23:59
 * @modify <BR/>
 *         修改内容：<BR/>
 *         修改人员：<BR/>
 *         修改时间：<BR/>
 */
@WebService(endpointInterface = "com.zhl.www.service.IWebServiceTest")
public class WebServiceTest implements IWebServiceTest {

	@Override
	public String doSSO(String loginName, String ticket, String clientIp, Integer clientPort, String userIp) {
		System.out.println(loginName + ticket + clientIp + clientPort + userIp);
		return loginName + ticket + clientIp + clientPort + userIp;
	}

}
