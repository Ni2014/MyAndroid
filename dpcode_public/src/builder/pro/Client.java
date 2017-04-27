package builder.pro;

/**TODO
 * 链式调用
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        IBuilder builder = new BuilderB();
        builder.buildA();
        builder.buildB();
        builder.buildC();

        Product product = builder.create();
        product.demo();
    }
}
