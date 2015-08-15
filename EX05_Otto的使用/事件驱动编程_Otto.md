##事件驱动编程 Otto
###1. 场景
1) 观察者模式，自定义接口
2) 广播
![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/B4D9E2351323461C94817A16F174387F)

###2. 解决
分析类似问题
解决思路：
1) 统一
    例如：ContentProvider
2) 简单
3) 稳定、可扩展、高性能

###3. 事件驱动编程 EDD
    Event Drive Design

###4. 与观察者模式的差别
    观察者：单一功能的解决方案
    事件总线EventBus：系统级解决事件分发

###5. 与广播的差别
    广播：代码冗余、性能较差
    事件总线：相对简单    

###6. Otto的使用
1) 创建Bus对象
2) 两个角色：
    事件订阅者
    事件发布者
    首先都需要使用Bus对象注册和注销
    ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/42E3733F3A864158B478FF76DC1134BF)
    ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/23CC04326785447CAEC5CD01BE1B7370)
    
3) 定义事件类型
    ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/D82FE1397FB94353BDAD50B7B382EC3E)

4) 发布事件
    ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/A12C2EF0C63A40C2939843621F1DC09E)
    注意：post()参数必须用方法来封装，方法必须使用注解@Produce
    ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/4073B0458035412CAF33C75C9A0C2C6D)
    

5) 接收者订阅事件
    新增一个方法，参数类型为post()发出的事件类型，注解为@Subscribe
    ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/6B4F642624B44BEF9EF64781FE43E53A/ED595C04F8FD444A98EF75394EACF1DD)
    

