package state;

/**
 * Created by Administrator on 2017/4/26.
 */
public class NetworkProcessor {
    NetworkState state;

    public void handle1() {
        switch (state) {
            case Open:
                //
                state = NetworkState.Close;
                break;
            case Close:
                //
                state = NetworkState.Connect;
                break;
            case Connect:
                //
                state = NetworkState.Open;
                break;
        }
    }

    public void handle2() {
        switch (state) {
            case Open:
                //
                state = NetworkState.Connect;
                break;
            case Close:
                //
                state = NetworkState.Open;
                break;
            case Connect:
                //
                state = NetworkState.Close;
                break;
        }
    }

    public void handle3() {
    }
}
