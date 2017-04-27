package prototype.v2;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Bean implements Cloneable{
    private String name;
    private String address;

    public Bean(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // 类似Android中intent的实现  没用到jvm的clone
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Bean bean = new Bean("allen", "guangzhou");
//        bean.name = "nothing";
        return bean;
    }
}
