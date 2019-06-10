# Vue学习

标签（空格分隔）： 学习笔记 随手笔记

---

[TOC]
### Vue项目创建
#### 使用vue-cli创建项目
> - 选择项目所在的位置，通过命令行进入该目录（或者直接在该目录，右键，打开命令行）。
- 使用脚手架安装项目： `vue init webpack demo` 项目是基于webpack的
Project name（工程名）:回车
Project description（工程介绍）：回车
Author：作者名
Vue build（是否安装编译器）:回车
Install vue-router（是否安装Vue路由）：回车
Use ESLint to lint your code（是否使用ESLint检查js代码）：n
Set up unit tests（安装单元测试工具）：n
Setup e2e tests with Nightwatch（是否安装端到端测试工具）：n
Should we run npm install for you after the project has been created? (recommended)：回车。

#### 启动项目
>- 进入项目目录：cd demo
- 安装项目所需要的依赖：npm install
- 启动项目：npm run dev

启动成功，浏览器打开：localhost:8080，即可看到vue项目。

#### vue目录结构

![282552288-5ac86f130489f_articlex.png-164.9kB][1]


#### package.json
![1514621110-5ac86f7d38e7d_articlex.png-97.8kB][2]


### Vue Router(路由)

#### 示例
```
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const routers = [
  {
    path: '/Test/SimpleTest',
    name: 'SimpleTest',
    meta:{
        title:'简单测试界面'
    },
    component: (resolve) => require(['@/components/SimpleTest'],resolve)
  },
  {
    path: '/Test/HelloWorld',
    name: 'HelloWorld',
    meta:{
      title:'HelloWorld界面'
    },
    component: (resolve) => require(['@/components/HelloWorld'],resolve)
  }

]

const router = new Router({
  base: '/',
  routes: routers
})
export default router;


```
### 组件配置
#### element-ui示例

##### 安装
###### 1、 npm 安装
```
npm i element-ui -S
```

###### 2、 CDN
目前可以通过 [unpkg.com/element-ui](https://unpkg.com/element-ui/) 获取到最新版本的资源，在页面上引入 js 和 css 文件即可开始使用。
```
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
```















 



 
  [1]: http://static.zybuluo.com/zhanghuanliang/6u19gxlkgr190qnretboyt02/282552288-5ac86f130489f_articlex.png
  [2]: http://static.zybuluo.com/zhanghuanliang/g4jq1x7mw52zzeahmwg5bmht/1514621110-5ac86f7d38e7d_articlex.png