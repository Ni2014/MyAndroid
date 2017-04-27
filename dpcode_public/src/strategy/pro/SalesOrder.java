package strategy.pro;

/**
 * Created by Administrator on 2017/4/24.
 */
public class SalesOrder {
    TaxStrategy strategy;

    public SalesOrder(TaxStrategy strategy) {
        this.strategy = strategy;
    }

    public int calc() {
        Context context = null;
        return strategy.calc(context);
    }
}
