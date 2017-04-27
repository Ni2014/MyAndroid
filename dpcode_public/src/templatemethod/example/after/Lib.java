package templatemethod.example.after;

/**
 * Created by Administrator on 2017/4/26.
 */
public abstract class Lib {
    protected  void step1(){}
    protected  void step3(){}
    protected  void step5(){}

    protected abstract boolean step2();
    protected abstract void step4();

    public void run() {
        step1();
        if (step2()) {
            step3();
        }
        for (int i = 0; i < 3; i++) {
            step4();
        }
        step5();
    }
}
