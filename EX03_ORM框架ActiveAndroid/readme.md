##03.ActciveAndroid使用

###1. 复杂的表结构——一对多关联表
###2. 创建类的关系
1) 定义”一“类型
   ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/527673CE2E7E45FC9406699C16E2CEEB/36A8246D45CD4B3EA1D8484A964F68AC)
    
2) 定义”多“类型
   ![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/527673CE2E7E45FC9406699C16E2CEEB/8C8B3D063A854D94A716BEC88A8ED6E3)
###3. 保存数据的顺序
    先保存"一"，再保存"多"
![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/527673CE2E7E45FC9406699C16E2CEEB/54EEEF06B10C4432A3E71A6F5630CC82)

###4. 查询方法
    先查"一"，再调用getXXX()方法查"多"
![](http://note.youdao.com/yws/public/resource/e94c7b2464a26385e7a81f02667208cc/527673CE2E7E45FC9406699C16E2CEEB/E00F549050764B90AC9BA41957BB0DB0)