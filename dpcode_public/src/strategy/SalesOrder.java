package strategy;

/**
 * Created by Administrator on 2017/4/24.
 */
public class SalesOrder {
    TaxBase tax;

    public int calc() {
        switch (tax) {
            case CN_Tax:
                // ...
                return 1;
            case US_Tax:
                // ...
                return 1;
            case DE_Tax:
                // ...
                return 1;
            case FR_Tax:
                // ...
                return 1;
        }
        return 0;
    }
}
