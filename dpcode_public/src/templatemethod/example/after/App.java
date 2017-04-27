package templatemethod.example.after;

/**
 * Created by Administrator on 2017/4/26.
 */
public class App extends Lib {
    @Override
    protected boolean step2() {
        return true;
    }

    @Override
    protected void step4() {
        // ...
    }

    public static void main(String[] args) {
        // ¶àÌ¬µ÷ÓÃ
        Lib lib = new App();
        lib.run();
    }
}
