/**
 * 
 */
package com.zhl.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * WebServiceApplication类
 *
 * @author zhanghuanliang
 * @email zhanghuanliang@boco.com.cn
 * @version v1.0
 * @time 2019年5月29日 上午10:59:35
 * @modify <BR/>
 *         修改内容：<BR/>
 *         修改人员：<BR/>
 *         修改时间：<BR/>
 */
@SpringBootApplication
@ServletComponentScan
public class WebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class);
	}

}
