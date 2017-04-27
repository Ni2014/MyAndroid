package bridge.patterncode;

/**
 * Created by Administrator on 2017/4/23.
 */
public abstract class Abstraction {
    public IImplementor implementor;

    public Abstraction(IImplementor implementor) {
        this.implementor = implementor;
    }

    public void opera() {
        implementor.operaImpl();
    }
}
