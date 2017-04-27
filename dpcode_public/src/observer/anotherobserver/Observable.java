package observer.anotherobserver;

/**观察者模式的扩展 -- 把订阅关系抽出来
 * Created by Administrator on 2017/4/26.
 */
public class Observable {
    protected OnAttach onAttach;

    // 接口注入
    public Observable(OnAttach onAttach) {
        this.onAttach = onAttach;
    }

    public void attach(Observer observer) {
        onAttach.call(observer);
    }

    public interface OnAttach{
        void call(Observer observer);
    }

}
