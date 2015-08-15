package com.yuchen.fast.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 15.4.5.
 */
public final class GsonUtils {
    public static <T> T parseJson(String jsonObj, Class<T> clazz)
    {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonObj, clazz);
        return t;
    }

    /**
     *
     * @param jsonArr
     * @param type
     * 例如：// Type type = new TypeToken&lt;ArrayList&lt;NewCourse>>() {}.getType();
     * @param <T>
     * @return
     */
    public static <T> T parseJson(String jsonArr, Type type)
    {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonArr, type);
        return t;
    }

    private GsonUtils(){}
}
