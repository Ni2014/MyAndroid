package memento;

/**需要被保存状态的对象
 * Created by Administrator on 2017/4/26.
 */
public class Bean {
    private String state;

    // 创建备忘录对象
    public Memento createMemento() {
        Memento memento = new Memento();
        memento.setState(state);
        return memento;
    }

    public void setMemento(Memento memento) {
        state = memento.getState();
    }
}
