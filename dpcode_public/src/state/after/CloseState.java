package state.after;

/**
 * Created by Administrator on 2017/4/26.
 */
public class CloseState extends NetworkState {
    @Override
    public void handle1() {
        next = new ConnectState();
    }

    @Override
    public void handle2() {

    }

    @Override
    public void handle3() {

    }
}
