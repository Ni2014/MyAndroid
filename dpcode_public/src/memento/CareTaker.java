package memento;

/**可以做一些持久化对象的操作
 * Created by Administrator on 2017/4/26.
 */
public class CareTaker {
    private Memento memento;

    public void saveMemento(Memento memento) {
        // 只是模拟实现
        this.memento = memento;
    }

    /**
     * 备忘录对象的恢复
     * @return
     */
    public Memento restoreMemento() {
        return memento;
    }
}
