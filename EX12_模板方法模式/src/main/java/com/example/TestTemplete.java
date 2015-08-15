package com.example;

import java.util.Map;
import java.util.Stack;

public class TestTemplete {
    public static void main(String[] args) {
        Stack<Activity> stack = new Stack<Activity>();
        Activity activity = new ChildActivity();
        activity.onCreate();
        activity.onPostCreate();
        // 其他流程
        stack.add(activity);
        activity.onStart();
        activity.onDestroy();
    }
}

class Activity {
    protected  void onCreate(){}

    protected  void onStart(){}

    /**
     * @param id
     * @see #onCreateDialog(int id, Map map)
     * @Deprecated
     */
    protected void onCreateDialog(int id){}

    protected void onCreateDialog(int id, Map map){}

    protected  void onDestroy(){}

    protected void onPostCreate(){}
}


class ChildActivity extends Activity {

    @Override
    public void onCreate() {

    }

    @Override
    protected void onCreateDialog(int id) {
        super.onCreateDialog(id);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    protected void onPostCreate() {
        super.onPostCreate();
    }
}