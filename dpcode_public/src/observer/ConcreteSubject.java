package observer;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteSubject extends Subject {
    public void change(String state) {
        this.notifyObservers(state);
    }
}
