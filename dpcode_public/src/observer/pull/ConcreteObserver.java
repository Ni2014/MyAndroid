package observer.pull;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteObserver implements Observer {
    @Override
    public void update(Subject subject) {
        System.out.println(((ConcreteSubject)subject).getName());
        System.out.println(((ConcreteSubject)subject).getAddress());
    }
}
