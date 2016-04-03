package com.sdk.lib;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

/**
 * Coolspan on 2016/1/29 18:36
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class FixBugAssetsManage {//计划替换资源文件

    private final static String TAG = "FixBugAssetsManage";

    private static FixBugAssetsManage instance;

    private static Context context;

    public static FixBugAssetsManage getInstance() {
        if (instance == null) {
            instance = new FixBugAssetsManage();
        }
        return instance;
    }

    public static void init(Context context) {
        FixBugAssetsManage.context = context;
    }

    private File assetsFileDir;

    public InputStream open(String fileName) throws IOException {
        InputStream inputStream = null;
        File file = new File(assetsFileDir, fileName);
        if (file.exists()) {
            inputStream = new FileInputStream(file);
        } else {
            inputStream = context.getResources().getAssets().open(fileName);
        }
        return inputStream;
    }

    public void replaceAssetsFiles() throws IOException {
        String sourceDir = context.getApplicationInfo().sourceDir;
        FileOutputStream fileOutputStream = new FileOutputStream(sourceDir.substring(0, sourceDir.lastIndexOf("/") + 1) + "coolspan.apk");
        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream);

        JarInputStream jarInputStream = new JarInputStream(new FileInputStream(sourceDir));

        //TODO 把新的资源文件替换到apk包文件中

    }
}
