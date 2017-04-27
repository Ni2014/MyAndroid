package flyweight;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Flyweight implements IFlyweight {
    private String innerState1;
    private String innerState2;
    private String outterState;

    public Flyweight(String innerState1, String innerState2) {
        this.innerState1 = innerState1;
        this.innerState2 = innerState2;
    }

    @Override
    public void operate(String outterState) {
        this.outterState = outterState;
    }
}
