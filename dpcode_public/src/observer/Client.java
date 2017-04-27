package observer;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        Observer observer = new ConcreteObserver();
        subject.attach(observer);
        subject.change("waiting...");
    }
}
