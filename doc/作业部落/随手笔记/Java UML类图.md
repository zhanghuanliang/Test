# Java UML类图

标签（空格分隔）： 随手笔记

---

### 简介
> 
类图 是用于描述系统中所包含的类以及它们之间的相互关系，帮助人们简化对系统的理解，它是系统分析和设计阶段的重要产物，也是系统编码和测试的重要模型依据。学习好类图的绘制，是一位合格的软件工程师应有的技能。

### 常见UML类图关系.

> 
- 泛化（Generalization）
- 实现（Realization）
- 关联（Association）
- 聚合（Aggregation）
- 组合(Composition)
- 依赖(Dependency)

### 一、泛化

【泛化关系】：是一种`继承`关系,它指定了子类如何特化父类的所有特征和行为
例如：老虎是动物的一种.

【箭头指向】：带三角箭头的实线，箭头指向父类
 
![image.png-5.7kB][1]
### 二、实现
【实现关系】：是一种`类与接口`的关系，表示类是接口所有特征和行为的实现

【箭头指向】：带三角箭头的虚线，箭头指向接口
![image.png-5.7kB][2]
### 三、关联
【关联关系】：是一种`拥有`的关系,它使一个类知道另一个类的属性和方法；如：老师与学生，丈夫与妻子

关联可以是双向的，也可以是单向的。双向的关联可以有两个箭头或者没有箭头，单向的关联有一个箭头。

【代码体现】：成员变量

【箭头及指向】：带普通箭头的实心线，指向被拥有者
![0_1303436801W1kf.gif-18.1kB][3]
上图中，老师与学生是双向关联，老师有多名学生，学生也可能有多名老师。但学生与某课程间的关系为单向关联，一名学生可能要上多门课程，课程是个抽象的东西他不拥有学生。
![image.png-3.4kB][4]
上图为自身关联：

### 四、聚合
【聚合关系】：是`整体与部分`的关系.如车和轮胎是整体和部分的关系.

聚合关系是关联关系的一种，是强的关联关系；关联和聚合在语法上无法区分，必须考察具体的逻辑关系。

【代码体现】：成员变量

【箭头及指向】：带空心菱形的实心线，菱形指向整体
![image.png-9kB][5]

### 五、组合
【组合关系】：是`整体与部分`的关系.,没有公司就不存在部门      组合关系是关联关系的一种，是比聚合关系还要强的关系，它要求`普通的聚合关系中代表整体的对象负责代表部分的对象的生命周期`

【代码体现】：成员变量

【箭头及指向】：带实心菱形的实线，菱形指向整体

![image.png-6.1kB][6]



### 六、依赖
 
【依赖关系】：是一种`使用`的关系,所以要尽量不使用双向的互相依赖。

【代码表现】：局部变量、方法的参数或者对静态方法的调用

【箭头及指向】：带箭头的虚线，指向被使用者
![image.png-5.5kB][7]
 
- 各种关系的强弱顺序：

`泛化`=`实现`>`组`合>`聚合`>`关联`>`依赖`

 下面这张UML图，比较形象地展示了各种类图关系：
![image.png-36.8kB][8]


  [1]: http://static.zybuluo.com/zhanghuanliang/8hpdajkh4gx24lb4x2yvu6cr/image.png
  [2]: http://static.zybuluo.com/zhanghuanliang/jok4f51tbk4r3tbwmkf84z1u/image.png
  [3]: http://static.zybuluo.com/zhanghuanliang/3144u5rdzv17aidhh2aqfkzw/0_1303436801W1kf.gif
  [4]: http://static.zybuluo.com/zhanghuanliang/oo6uc0g6jrai1cr2a19sllys/image.png
  [5]: http://static.zybuluo.com/zhanghuanliang/oz810y1qep52s7afysprlbpq/image.png
  [6]: http://static.zybuluo.com/zhanghuanliang/jrtd8766nplrxbrmz2itt291/image.png
  [7]: http://static.zybuluo.com/zhanghuanliang/orgtg6qfl5f5q0kwmo66g39t/image.png
  [8]: http://static.zybuluo.com/zhanghuanliang/nclq5trj49fn0u9zbutcivnl/image.png