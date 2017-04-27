package flyweight.example;

/**
 * Created by Administrator on 2017/4/24.
 */
public class Font {
    private String key;
    private String name;
    private boolean isBold;
    private int size;

    public Font(String key) {
        this.key = key;
    }
}
