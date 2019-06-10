# MocMvc单元测试

标签（空格分隔）： 学习笔记 随手笔记

---

[toc]
### API简介
```
1、mockMvc.perform执行一个请求；

2、MockMvcRequestBuilders.get("/user/1")构造一个请求

3、ResultActions.andExpect添加执行完成后的断言

4、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。

5、ResultActions.andReturn表示执行完成后返回相应的结果。
```
### AppTest
```
@SpringBootTest(classes = ServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AppTest {
	protected MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext

	@Before // 在测试开始前初始化工作
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test() {

	}
}
```
### 示例代码

#### GET请求
```
@Test
	public void testQueryStationByRouteId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/Subway/queryStationByRouteId").param("routeId", "20171300002"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

	}
```
#### POST请求
```
	@Test
	public void testQuerySubwayLineData() throws Exception {
		SubwayLineDataAnalysisQO subwayLineDataAnalysisQO = new SubwayLineDataAnalysisQO();
		subwayLineDataAnalysisQO.setSumLevel(1);
		subwayLineDataAnalysisQO.setScanStartTime("");
		subwayLineDataAnalysisQO.setDay(20180510);
		subwayLineDataAnalysisQO.setMon(0);
		subwayLineDataAnalysisQO.setRouteID(20171300002L);
		subwayLineDataAnalysisQO.setPageNum(1);
		subwayLineDataAnalysisQO.setPageSize(1);

		mockMvc.perform(MockMvcRequestBuilders.post("/Subway/querySubwayLineData")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.toJson(subwayLineDataAnalysisQO)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

	}
```

