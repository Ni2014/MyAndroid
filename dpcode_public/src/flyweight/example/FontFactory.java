package flyweight.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/24.
 */
public class FontFactory {
    private Map<String, Font> map = new HashMap<>();

    public Font getFont(String key) {
        if (!map.containsKey(key)) {
            map.put(key, new Font(key));
        }
        return map.get(key);
    }

}
