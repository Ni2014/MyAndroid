package builder.callaschain;

/**��ʾ���A
 * Created by Administrator on 2017/4/26.
 */
public class BuilderA implements IBuilder {
    private Product product = new Product();

    @Override
    public IBuilder buildA() {
        product.buildA();
        return this;
    }

    @Override
    public IBuilder buildB() {
        product.buildB();
        return this;
    }

    @Override
    public IBuilder buildC() {
        product.buildC();
        return this;
    }

    @Override
    public Product create() {
        return product;
    }
}
