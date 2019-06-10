# Eclipse使用学习

标签（空格分隔）： java 开发工具

---
一、界面配置
-
- 修改Eclipse里项目的包层次结构排版
> Eclipse新建工程和若干包默认的排序是扁平的Flat，显示出的包结构不是很清晰，通过设置可以调整为包继承的结构Hierarchical模式，点击Window-->Navigation-->Show View Menu-->Package Presentation--Hierarchical

- 为Eclipse添加更多提示功能
> 我们在使用Eclipse编辑代码时，常用的提示信息往往出现在输入符号"."的时候，如输入类名.会出现该类下的所有方法列表，这样的提示可能不太方便，如果能在输入任意一个字母时就有相应的提示，会更便捷。因此我们进行如下操作：Window-->Perferences-->Java-->Editor--Content Assist，将Auto activation triggers for java:内容更改为：“.abcdefghigklmnopqrstuvwxyz” ，Auto activation delay(ms):中的时延也可以设置如60ms，使得提示更加实时，设置完点击Apply即可。这样在输入任意一个字符时便会有提示出现。

- Eclipse中修改系统字体大小
> Window-->Preferences -->General-->Apperance-->Colors and Fonts -->Basic-->Text Font-->Edit。

二、快捷键
--

- 重命名快捷键：Alt+Shift+R
> 可用于类名，方法名，属性名等的重命名，鼠标点击要重命名的地方，按住Alt+Shift+R ，会提示重新输入名字，此时重新键入新的名字，按Enter键即可改名，不管需要重命名的类，还是调用了该类的其他类名字都会改变。如果重命名的是类中的属性则按住快捷键两次，可实现get/set方法的自动重命名。

- 格式化代码：Ctrl+Shift+F
> 用于格式化代码，代码的编写尽量规范整洁，使得代码的阅读更加顺畅，此格式化操作可以使用默认的配置，也可以自己设定。如果想整个类格式化，可以先Ctrl+A全选，再执行格式化操作。格式化也可以通过在代码区右键Source-->Format来实现。

- 自动导入包及清除多余的包：Ctrl+Shift+O
> 在编写java代码时需要引入已有的jar包，但是手动一个个导包很麻烦，使用快捷键Ctrl+Shift+O，选择需要的jar包后确认，就可以实现自动导入整个类依赖的所有jar包和清除不用的jar包。
 
- 把多行代码抽象为一个方法（提取方法）:Alt+Shift+M
> 在代码编写的过程中，一个方法写的过长会影响阅读和调试，因此可以把某部分功能块的代码抽象出来单独作为一个方法。同样Alt+Shift+L用于提取本地变量。

- 复制代码：
> Ctrl+Alt+向下键箭头，用于向下复制一行代码，Ctrl+Alt+向上键箭头，用于向上复制一行代码。

- 代码注释：
> 代码中加入注释可以让代码的可读性更高。Ctrl+/ 用于单行注释，去掉单行注释的快捷键是同样的。 选中要注释的代码键入Ctrl+Shift+/可用于多行注释，光标在区域内键入Ctrl+Shift+\用于取消注释。

- 自动生成构造方法：
> Alt+Shift+S打开Source，接着键入其他的字母可以自动生成构造方法，自动生成get/set方法等。+C可以生成空参构造方法，+O根据本地字段（成员变量）生成有参构造，+R生成get/set方法。例如，先键入Alt+Shift+S后再键入R可以调出生成get/set方法的界面.

- 代码提示信息：
> Alt+/用于提示输入代码，补充输出语句，选中需要输出的部分语句，alt+/选择需要的一项即可。其中main+Alt+/用于补全main方法，syso+Alt+/用于补全System.out.println()打印输出代码。定义自己的alt+/ 的设置在windows --> perfernces --> Java --> Editor -- >Templates -- >New,

- 其他常用的快捷键：
> 删除代码：Ctrl+D用于删除代码
新建：Ctrl+N，可以现在需要新建的工程等
