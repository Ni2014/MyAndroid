package mediator.another;

/**
 * Created by Administrator on 2017/4/23.
 */
public class ColleagueB extends Colleague {
    public void actionB() {
        mediator.change(this);
    }
}
