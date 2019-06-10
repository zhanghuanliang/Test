# socket说明文档

标签（空格分隔）： socket

---

序列图


```seq
APP->SocketServer: 创建连接
SocketServer->APP: 返回连接命名
SocketServer->Window: 通知XXX连接的建立
APP->SocketServer: 发送运动参数
Note right of SocketServer: XXX连接还未获取控制权不予处理
Window->SocketServer: 通知XXX连接允许控制
APP->SocketServer: 发送运动参数
Note right of SocketServer:对运动参数进行解析
SocketServer->MoveController: 调用运动接口
APP->SocketServer: 断开连接
SocketServer->Window: 通知XXX连接的已断开
```




