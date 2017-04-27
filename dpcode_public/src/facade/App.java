package facade;

import facade.sdk.CryptoUtil;
import facade.sdk.SPUtil;

/**
 * Created by Administrator on 2017/4/26.
 */
public class App {
    public void init(String appkey) {
        String cryptoedAppkey = CryptoUtil.encryptoAppkey(appkey);
        SPUtil.saveAppkeyToSp(cryptoedAppkey);
    }
}
