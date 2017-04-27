package observer.withgeneric;

import java.util.ArrayList;
import java.util.Arrays;

/**TODO
 * 可以加操作符
 * Created by Administrator on 2017/4/27.
 */
public class Client {
    public static void main(String[] args) {
        // 注册关系
        Observable.OnAttach onAttach = new Observable.OnAttach() {
            @Override
            public void call(Observer observer) {
                ArrayList<String> list = new ArrayList<>(Arrays.asList("a","b","c","d"));
                observer.update(list);
            }
        };
        Observable observable = Observable.create(onAttach);
        Observer observer = new Observer<ArrayList<String>>(){

            @Override
            public void update(ArrayList<String> state) {
                /*for (String str : state) {
                    System.out.println(str);
                }*/
                // use java8
                state.stream().forEach(str -> System.out.println(str));
//                state.stream().forEach(System.out::println);
            }
        };

        observable.attach(observer);
    }
}
