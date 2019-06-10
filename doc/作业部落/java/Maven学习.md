# Maven学习

标签（空格分隔）： Java Maven 

---

Maven
==
一、简介：
-
Apache Maven是一个软件项目管理和综合工具。基于项目对象模型（POM）的概念，Maven可以从一个中心资料片管理项目构建，报告和文件。

Maven是一个项目管理和综合工具。Maven提供了开发人员构建一个完整的生命周期框架。开发团队可以自动完成项目的基础工具建设，Maven使用标准的目录结构和默认构建生命周期。

在多个开发团队环境时，Maven可以设置按标准在非常短的时间里完成配置工作。由于大部分项目的设置都很简单，并且可重复使用，Maven让开发人员的工作更轻松，同时创建报表，检查，构建和测试自动化设置。


### POM文件里面可以引用一些内置属性
```
POM文件里面可以引用一些内置属性(Maven预定义可以直接使用)  
  
${basedir} 项目根目录   
${version}表示项目版本;  
${project.basedir}同${basedir};  
${project.version}表示项目版本,与${version}相同;  
${project.build.directory} 构建目录，缺省为target  
${project.build.sourceEncoding}表示主源码的编码格式;  
${project.build.sourceDirectory}表示主源码路径;  
${project.build.finalName}表示输出文件名称;  
${project.build.outputDirectory} 构建过程输出目录，缺省为target/classes  
```












