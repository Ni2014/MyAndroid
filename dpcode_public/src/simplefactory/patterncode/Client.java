package simplefactory.patterncode;

/**TODO
 * 1 直接修改if else 不符合开闭原则 可以进一步转为工厂方法模式
 * 2 传Class对象 用反射去创建具体的产品类
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        IProduct product = Factory.create(Factory.TYPE_A);
        product.demo();
    }
}
