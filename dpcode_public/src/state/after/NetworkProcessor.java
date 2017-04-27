package state.after;

/**
 * Created by Administrator on 2017/4/26.
 */
public class NetworkProcessor {
    NetworkState state;

    public NetworkProcessor(NetworkState state) {
        this.state = state;
    }

    public void handle1() {
        state.handle1();
        state = state.next;
    }

    public void handle2() {
        state.handle2();
        state = state.next;
    }

    public void handle3() {
        state.handle3();
        state = state.next;
    }
}
