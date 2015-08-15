package me.yuchen.com.ex05_otto;

/**
 * Created by Administrator on 15.4.20.
 */
public class MyEvent {
    private String info;
    private int i;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public MyEvent(String info, int i) {
        this.info = info;
        this.i = i;
    }
}
