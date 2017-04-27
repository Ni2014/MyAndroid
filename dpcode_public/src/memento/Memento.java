package memento;

/**备忘录对象
 * Created by Administrator on 2017/4/26.
 */
public class Memento {
    // 被保存对象需要被保存的状态
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
