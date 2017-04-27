package builder.callaschain;

/**TODO
 * 链式调用
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        IBuilder builder = new BuilderB();
        builder.buildA();
        builder.buildB();
        builder.buildC();

        Product product = builder.create();
        product.demo();

        // 转为链式调用

        product = new BuilderA().buildA()
                .buildB()
                .buildC()
                .create();
        product.demo();

        /*BmobQuery<Book> query = new BmobQuery<>();
        query.setLimit(8)
                .setSkip(1)
                .order("-createdAt")
                .findObjects(new FindListener<Book>() {
                    @Override
                    public void done(List<Book> object, BmobException e) {
                        if (e == null) {
                            // ...
                        } else {
                            // ...
                        }
                    }
                });*/
    }
}
