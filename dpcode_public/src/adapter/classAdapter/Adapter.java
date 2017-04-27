package adapter.classAdapter;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Adapter extends Adaptee implements ITarget {


    @Override
    public void req() {
        specReq();
    }
}
