package facade.sdk;

/**
 * Created by Administrator on 2017/4/26.
 */
public class CryptoUtil {
    public static String encryptoAppkey(String appkey) {
        String cryptoedAppkey = appkey.concat("demo");
        return cryptoedAppkey;
    }
}
