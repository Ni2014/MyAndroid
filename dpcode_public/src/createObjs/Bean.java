package createObjs;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Bean implements Cloneable,Serializable {
    private String name;
    private String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    protected Object clone() {
        Bean bean = null;
        try {
            bean = (Bean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
