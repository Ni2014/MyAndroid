package iterator.injava8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */
public class Client {
    public static void main(String[] args) {
        List<Shape> list = new ArrayList<>();

        // 外部迭代
        for (Shape shape : list) {
            shape.setColor("red");
        }
        // 内部迭代
        list.forEach(shape -> shape.setColor("red"));



        list.stream()
                .filter(shape -> shape.getColor() == "blue")
                .forEach(shape -> shape.setColor("red"));
    }
}
