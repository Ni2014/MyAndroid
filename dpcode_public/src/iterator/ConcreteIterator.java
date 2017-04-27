package iterator;

/**
 * Created by Administrator on 2017/4/24.
 */
public class ConcreteIterator implements Iterator {
    private ConcreteAggregate aggregate;

    public ConcreteIterator(ConcreteAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
