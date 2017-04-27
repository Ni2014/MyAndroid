package builder.pro;

/**
 * Created by Administrator on 2017/4/26.
 */
public interface IBuilder {
    void buildA();
    void buildB();
    void buildC();

    Product create();
}
