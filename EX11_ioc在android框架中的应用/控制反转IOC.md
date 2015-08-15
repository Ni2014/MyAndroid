##控制反转IOC
###1. 面向对象的设计原则之控制反转IOC

    术语： 依赖     控制
    List list = new ArrayList();
    框架 + 应用    Framework + App
    问题：
       1) 时间
          Framework在先，App在后
       2) App按照规则写子类， Framework写基类，并创建子类对象
          疑问之一，为什么要由框架来创建子类对象？
       3) 未知
          Framework不知道App中的子类名、方法名

###2. IOC核心
在XML中配置子类名称，框架解析XML获取类名，并通过反射技术创建子类

###3. IOC在Android框架中的应用
四大组件