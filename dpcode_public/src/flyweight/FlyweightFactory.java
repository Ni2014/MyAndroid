package flyweight;

import java.util.HashMap;
import java.util.Map;

/**管理这些复用的对象
 * Created by Administrator on 2017/4/26.
 */
public class FlyweightFactory {
    private Map<String, IFlyweight> map = new HashMap<>();

    public IFlyweight get(String innerState1, String innerState2) {
        // 模拟
        String key = innerState1 + innerState2;
        if (!map.containsKey(key)) {
            map.put(key, new Flyweight(innerState1, innerState2));
        }
        return map.get(key);
    }
}
