package createObjs.serialize;

import java.io.*;

/**序列化和反序列化的工具类
 * Created by Administrator on 2017/4/26.
 */
public final class SerializationUtil {
    private static String FILE_NAME = "D:/object.bin";

    /**
     * 序列化
     * @param serializable
     */
    public static void writeObj(Serializable serializable) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(serializable);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     * @return
     */
    public static Object readObj() {
        Object object = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            object = ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
