package simplefactory.other;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Factory {
    public static IProduct create(Class<?> clazz) {
        IProduct product = null;
        try {
            product = (IProduct) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return product;
    }
}
