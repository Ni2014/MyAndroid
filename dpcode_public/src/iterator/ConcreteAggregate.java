package iterator;

/**
 * Created by Administrator on 2017/4/24.
 */
public class ConcreteAggregate extends Aggregate {
    @Override
    Iterator iterator() {
        return new ConcreteIterator(this);
    }
}
