/**
 * 
 */
package com.zhl.www.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * IWebService类
 *
 * @author zhanghuanliang
 * @email zhanghuanliang@boco.com.cn
 * @version v1.0
 * @time 2019年5月29日 下午3:23:31
 * @modify <BR/>
 *         修改内容：<BR/>
 *         修改人员：<BR/>
 *         修改时间：<BR/>
 */
@WebService
public interface IWebServiceTest {

	@WebMethod
	String doSSO(String loginName, String ticket, String clientIp, Integer clientPort, String userIp);

}
