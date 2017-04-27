package mediator;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteMediator extends Mediator {
    @Override
    public void method() {
        colleagueA.action();
        colleagueB.action();
    }
}
