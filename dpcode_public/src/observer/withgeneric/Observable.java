package observer.withgeneric;

/**
 * Created by Administrator on 2017/4/27.
 */
public class Observable<T> {
    protected OnAttach onAttach;

    public Observable(OnAttach onAttach) {
        this.onAttach = onAttach;
    }

    public static <T> Observable<T> create(OnAttach<T> onAttach) {
        return new Observable<>(onAttach);
    }

    public void attach(Observer<T> observer) {
        onAttach.call(observer);
    }

    public interface OnAttach<T> {
        void call(Observer<? super T> observer);
    }
}
