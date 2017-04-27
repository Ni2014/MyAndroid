package observer.withgeneric;

/**
 * Created by Administrator on 2017/4/27.
 */
public interface Observer<T> {
    void update(T state);
}
