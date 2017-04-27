package mediator.another;

/**
 * Created by Administrator on 2017/4/23.
 */
public class ColleagueA extends Colleague {
    public void actionA() {
        mediator.change(this);
    }
}
