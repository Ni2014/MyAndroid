package prototype.v2;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Bean1 implements Cloneable{
    private String name;
    private String address;

    public Bean1(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // 没用jvm的clone

    @Override
    protected Object clone() {
        Bean bean = null;
        try {
            bean = (Bean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
