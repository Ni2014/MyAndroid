package mediator.another;

/**
 * Created by Administrator on 2017/4/23.
 */
public class Mediator implements IMediator{
    // inject colla b c
    private ColleagueA colleagueA;
    private ColleagueB colleagueB;
    private ColleagueC colleagueC;

    public void setColleagueA(ColleagueA colleagueA) {
        this.colleagueA = colleagueA;
    }

    public void setColleagueB(ColleagueB colleagueB) {
        this.colleagueB = colleagueB;
    }

    public void setColleagueC(ColleagueC colleagueC) {
        this.colleagueC = colleagueC;
    }

    @Override
    public void change(Colleague colleague) {
        if (colleague instanceof ColleagueA) {
            colleagueB.actionB();
        }else if (colleague instanceof ColleagueB) {
            colleagueC.actionC();
        }else if (colleague instanceof ColleagueC) {
            colleagueA.actionA();
        }
    }
}
