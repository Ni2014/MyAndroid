package simplefactory.other;

/**TODO
 * 1 如果写代码的时候并不存在需要传入的类怎么办
 * -> 读取配置文件传入的类名
 * -> 修改配置文件 不用重新编译
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        IProduct product = Factory.create(ProductC.class);
        product.demo();
    }
}
