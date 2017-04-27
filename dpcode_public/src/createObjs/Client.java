package createObjs;


import createObjs.serialize.SerializationUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**java创建对象的方式 除了new之外的
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        // 1 反射 Class.newInstance()
        Bean bean = null;
        try {
            // 默认调无参的构造方法
            bean = (Bean) Class.forName("createObjs.Bean").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        bean.setName("wahaha");
        bean.setAddress("beijing");
        System.out.println(bean);


        // 2  反射 Constructor的newInstance()

        try {
            Constructor<Bean> constructor = Bean.class.getConstructor();
            bean = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        bean.setName("from ctor");
        bean.setAddress("gz");
        System.out.println(bean);

        // 3 用clone

        Bean bean1 = new Bean();
        bean1.setName("origin");
        bean1.setAddress("huizhou");

        Bean beanByClone = (Bean) bean1.clone();
        System.out.println(beanByClone);

        // 4 用反序列化

        // 序列化
        SerializationUtil.writeObj(bean1);
        // 反序列化
        Bean beanByDeserialize = (Bean) SerializationUtil.readObj();
        System.out.println(beanByDeserialize);

    }
}
