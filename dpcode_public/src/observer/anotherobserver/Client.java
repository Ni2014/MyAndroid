package observer.anotherobserver;

/**TODO
 * 引入泛型
 * 用静态工厂代替构造函数
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        Observable observable = new Observable(observer -> observer.update("waiting"));
        observable.attach(state -> System.out.println("new stata is " + state));
        // 分步骤
        // 1 注册关系
        Observable.OnAttach onAttach = observer -> observer.update("waiting1");
        // 被观察者
        Observable observable1 = new Observable(onAttach);

        // 观察者
        Observer observer = state -> System.out.println("new stata is " + state);
        // 订阅
        observable1.attach(observer);

        /*Observable observable = new Observable(new Observable.OnAttach() {
            @Override
            public void call(Observer observer) {
                observer.update("waiting");
            }
        });
        observable.attach(new Observer() {
            @Override
            public void update(String state) {
                System.out.println("new stata is " + state);
            }
        });
        // 分步骤
        // 1 注册关系
        Observable.OnAttach onAttach = new Observable.OnAttach() {
            @Override
            public void call(Observer observer) {
                observer.update("waiting1");
            }
        };
        // 被观察者
        Observable observable1 = new Observable(onAttach);

        // 观察者
        Observer observer = new Observer() {
            @Override
            public void update(String state) {
                System.out.println("new stata is " + state);
            }
        };
        // 订阅
        observable1.attach(observer);*/

    }
}
