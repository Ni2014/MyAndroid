package mediator;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteColleagueA extends Colleague{

    public ConcreteColleagueA(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void action() {
        System.out.println("a");
    }
}
