package templatemethod;

/**
 * Created by Administrator on 2017/4/26.
 */
public abstract class AbsClass {

    /**
     * 控制整体执行顺序 是为模板方法
     */
    public void run() {
        option1();
        option2();
        option3();
    }
    protected void option1() {}
    protected void option2() {}
    protected void option3() {}

}
