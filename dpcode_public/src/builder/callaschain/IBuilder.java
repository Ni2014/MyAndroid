package builder.callaschain;

/**
 * Created by Administrator on 2017/4/26.
 */
public interface IBuilder {
    IBuilder buildA();
    IBuilder buildB();
    IBuilder buildC();

    Product create();
}
