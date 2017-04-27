package composite;

/**
 * Created by Administrator on 2017/4/24.
 */
public class Leaf extends Component {
    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void process() {
        // ...
    }
}
