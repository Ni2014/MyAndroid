package templatemethod.example;

/**使用者 App开发者
 * TODO
 * 1 整体稳定的执行流程不该写在本类中
 * 2 App写的代码较多
 * 3 调用关系 App -> Lib
 * Created by Administrator on 2017/4/26.
 */
public class App {
    public boolean step2() {
        return true;
    }

    public void step4() {

    }

    public static void main(String[] args) {
        Lib lib = new Lib();
        App app = new App();
        lib.step1();
        if (app.step2()) {
            lib.step3();
        }
        for (int i = 0; i < 3; i++) {
            app.step4();
        }
        lib.step5();
    }
}
