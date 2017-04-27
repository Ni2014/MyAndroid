package observer.pull;

/**
 * Created by Administrator on 2017/4/26.
 */
public class ConcreteSubject extends Subject {
    private String name;
    private String address;

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public void change(String name, String address) {
        this.name = name;
        this.address = address;
        this.notifyObservers();
    }
}
