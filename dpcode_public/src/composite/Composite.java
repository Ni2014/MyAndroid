package composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */
public class Composite extends Component {
    String name;
    List<Component> elements = new ArrayList<>();

    public Composite(String name) {
        this.name = name;
    }

    public void add(Component component) {
        elements.add(component);
    }

    public void remove(Component component) {
        elements.remove(component);
    }

    @Override
    public void process() {
        for (Component element : elements) {
            element.process();
        }
    }
}
