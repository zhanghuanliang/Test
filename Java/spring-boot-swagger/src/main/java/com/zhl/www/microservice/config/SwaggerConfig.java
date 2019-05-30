/**
 * 
 */
package com.zhl.www.microservice.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import io.swagger.annotations.Api;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger.enable:false}")
	private Boolean enableSwagger;

	@Value("${swagger.basePackage:com.zhl.www.*.*.controller}")
	private List<String> basePackages;

	@Value("${swagger.api.title:请配置swagger.app.title}")
	private String apiTitle;

	@Value("${swagger.api.desc:Swagger2}")
	private String apiDesc;

	@Value("${swagger.serviceUrl:SwaggerServiceUrl}")
	private String serviceUrl;

	@Value("${swagger.version:1.0.0}")
	private String version;

	@Bean
	public Docket createRestApi() {

		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("token").description("user token").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).build(); // 参数非必填，传空也可以
		pars.add(tokenPar.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				// 是否开启
				.enable(enableSwagger).select()
				// 扫描的路径包
				.apis(SwaggerConfig.getBasePackage(basePackages))
				// 指定路径处理PathSelectors.any()代表所有的路径
				.paths(PathSelectors.any()).build().pathMapping("/").globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(apiTitle).description(apiDesc).version(version).build();
	}

	private static Predicate<RequestHandler> basePackage(final List<String> basePackages) {
		return new Predicate<RequestHandler>() {

			@Override
			public boolean apply(RequestHandler input) {
				return declaringClass(input).transform(handlerPackage(basePackages)).or(true);
			}
		};
	}

	private static Predicate<RequestHandler> getBasePackage(final List<String> basePackages) {
		return new Predicate<RequestHandler>() {

			@Override
			public boolean apply(RequestHandler input) {
				return declaringClass(input).transform(handlerPackage(basePackages)).or(true);
			}
		};
	}

	/**
	 * 处理包路径配置规则,支持多路径扫描匹配以逗号隔开
	 * 
	 * @param basePackage
	 *            扫描包路径
	 * @return Function
	 */
	private static Function<Class<?>, Boolean> handlerPackage(final List<String> basePackages) {
		return new Function<Class<?>, Boolean>() {

			@Override
			public Boolean apply(Class<?> input) {
				for (String strPackage : basePackages) {
					Api api = AnnotationUtils.findAnnotation(input, Api.class);// 获取类注解
					if (api == null) {// 假如获取不到则抛出异常
						return false;
					}

					boolean isMatch = input.getPackage().getName().startsWith(strPackage);
					if (isMatch) {
						return true;
					}
				}

				return false;
			}
		};
	}

	/**
	 * @param input
	 *            RequestHandler
	 * @return Optional
	 */
	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}

}
