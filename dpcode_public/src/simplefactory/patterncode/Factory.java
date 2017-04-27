package simplefactory.patterncode;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Factory {
    public final static int TYPE_A = 1;
    public final static int TYPE_B = 2;

    public static IProduct create(int type) {
        if (type == TYPE_A) {
            return new ProductA();
        }else if (type == TYPE_B) {
            return new ProductB();
        }
        return null;
    }
}
