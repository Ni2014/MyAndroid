package mediator.another;

/**
 * Created by Administrator on 2017/4/23.
 */
public abstract class Colleague {
    protected IMediator mediator;

    public void setMediator(IMediator mediator) {
        this.mediator = mediator;
    }
}
