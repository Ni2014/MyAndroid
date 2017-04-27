package templatemethod.mock;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Activity {

    public final void run() {
        // activities stack manager
        onCreate();
        onStart();
        onResume();
        onStop();
        onDestory();
    }
    protected void onCreate(){}
    protected void onStart(){}
    protected void onResume(){}
    protected void onStop(){}
    protected void onDestory(){}
    protected void onRestart(){}
}
