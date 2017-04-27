package builder.pro;

/**表示风格B
 * Created by Administrator on 2017/4/26.
 */
public class BuilderB implements IBuilder {
    private Product product = new Product();
    @Override
    public void buildA() {
        product.buildA();
    }

    @Override
    public void buildB() {
        product.buildC();
    }

    @Override
    public void buildC() {
        product.buildB();
    }

    @Override
    public Product create() {
        return product;
    }
}
