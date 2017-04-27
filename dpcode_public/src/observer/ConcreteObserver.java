package observer;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteObserver implements Observer {
    @Override
    public void update(String state) {
        System.out.println("has change is " + state);
    }
}
