package state.after;

/**
 * Created by Administrator on 2017/4/26.
 */
public abstract class NetworkState {
    public NetworkState next;

    public abstract void handle1();
    public abstract void handle2();
    public abstract void handle3();
}
