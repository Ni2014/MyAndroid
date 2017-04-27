package builder.pro;

/**表示风格A
 * Created by Administrator on 2017/4/26.
 */
public class BuilderA implements IBuilder {
    private Product product = new Product();
    @Override
    public void buildA() {
        product.buildA();
    }

    @Override
    public void buildB() {
        product.buildB();
    }

    @Override
    public void buildC() {
        product.buildC();
    }

    @Override
    public Product create() {
        return product;
    }
}
