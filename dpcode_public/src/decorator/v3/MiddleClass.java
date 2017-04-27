package decorator.v3;

/**
 * Created by Administrator on 2017/4/26.
 */
public abstract class MiddleClass extends Stream{
    protected Stream stream;

    public MiddleClass(Stream stream) {
        this.stream = stream;
    }
}
