package mediator;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteColleagueB extends Colleague{

    public ConcreteColleagueB(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void action() {
        System.out.println("b");
    }
}
