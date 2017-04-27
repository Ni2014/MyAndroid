package facade.after.sdk;

/**as Facade
 * Created by Administrator on 2017/4/26.
 */
public class Bmob {
    public static void init(String appkey) {
        String cryptoedAppkey = CryptoUtil.encryptoAppkey(appkey);
        SPUtil.saveAppkeyToSp(cryptoedAppkey);
    }
}
