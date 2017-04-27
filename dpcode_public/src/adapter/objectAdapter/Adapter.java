package adapter.objectAdapter;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Adapter implements ITarget {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void req() {
        adaptee.specReq();
    }
}
