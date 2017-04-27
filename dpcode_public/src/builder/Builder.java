package builder;

/**TODO
 * 看似作用不大
 * 1 分离对象的构建(product做)和表示(builder做)
 * 2 如果构建复杂就单独提取出来为一个builder做
 * 3 不同表现意味着？ -> 把builder做抽象 用不同builder即可
 * Created by Administrator on 2017/4/26.
 */
public class Builder {
    private Product product = new Product();

    public void buildA() {
        product.buildA();
    }

    public void buildB() {
        product.buildB();
    }

    public void buildC() {
        product.buildC();
    }

    public Product create() {
        return product;
    }
}
