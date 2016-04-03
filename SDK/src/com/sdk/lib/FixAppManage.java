package com.sdk.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

import dalvik.system.DexFile;

/**
 * Coolspan on 2016/1/28 15:02
 * <p/>
 * 基础分包MultiDex实现热更新
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class FixAppManage {


    private final static String TAG = "FixBugManage";

    /**
     * 上下文
     */
    private Context context;

    /**
     * 读取缓存大小
     */
    private final static int BUF_SIZE = 512;

    /**
     * patch文件存放目录
     */
    private File patchs;

    private final static String PatchsDir = "patchs";

    /**
     * patch文件优化过后dex存放目录
     */
    private File patchsOptFile;

    private final static String PatchsOptDir = "patchsopt";

    private final static String PatchSuffix = ".dex";

    private final static String FixBug = "fixbug";

    private final static String VersionCode = "versionCode";

    public FixAppManage(Context context) {
        this.context = context;//初始化上下文对象，进行获取别的操作
        this.patchs = new File(this.context.getFilesDir(), PatchsDir);// 存放补丁文件
        this.patchsOptFile = new File(this.context.getFilesDir(), PatchsOptDir);// 存放预处理补丁文件压缩处理后的dex文件
    }

    /**
     * 初始化版本号
     *
     * @param versionCode
     */
    public void init(String versionCode) throws FixBugException {
        try {
            SharedPreferences sharedPreferences = this.context
                    .getSharedPreferences(FixBug, Context.MODE_PRIVATE);
            String oldVersionCode = sharedPreferences
                    .getString(VersionCode, null);
            if (oldVersionCode == null
                    || !oldVersionCode.equalsIgnoreCase(versionCode)) {
                this.initPatchsDir();// 初始化补丁文件目录
                this.clearPaths();// 清楚所有的补丁文件
                sharedPreferences.edit().clear().putString(VersionCode, versionCode)
                        .commit();// 存储版本号
            } else {
                this.loadPatchs();// 加载已经添加的补丁文件(.jar)
            }
        } catch (IllegalAccessException e) {
            throw new FixBugException("IllegalAccessException", e);
        } catch (NoSuchFieldException e) {
            throw new FixBugException("NoSuchFieldException", e);
        } catch (ClassNotFoundException e) {
            throw new FixBugException("ClassNotFoundException", e);
        } catch (NoSuchMethodException e) {
            throw new FixBugException("NoSuchMethodException", e);
        } catch (InvocationTargetException e) {
            throw new FixBugException("InvocationTargetException", e);
        } catch (InstantiationException e) {
            throw new FixBugException("InstantiationException", e);
        } catch (Exception e) {
            throw new FixBugException(e);
        }
    }

    /**
     * 读取补丁文件夹并加载
     */
    private void loadPatchs() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IOException {
        if (patchs.exists() && patchs.isDirectory()) {// 判断文件是否存在并判断是否是文件夹
            File patchFiles[] = patchs.listFiles();// 获取文件夹下的所有的文件//TODO 处理指定后缀
            for (int i = 0; i < patchFiles.length; i++) {
                if (patchFiles[i].getName().lastIndexOf(PatchSuffix) == patchFiles[i]
                        .getName().length() - 4) {// 仅处理.jar文件
                    this.loadPatch(patchFiles[i]);// 加载jar文件
                }
            }
        } else {
            this.initPatchsDir();//初始化Patch目录
        }
    }

    /**
     * 初始化存放补丁的文件目录
     */
    private void initPatchsDir() {
        if (!this.patchs.exists()) {//判断目录是否存在
            this.patchs.mkdirs();//创建多层目录
        }
        if (!this.patchsOptFile.exists()) {//判断目录是否存在
            this.patchsOptFile.mkdirs();//创建多层目录
        }
    }

    /**
     * 加载多个补丁文件
     *
     * @param patchFiles 补丁文件列表
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws IOException
     * @throws IllegalAccessException
     */
    private void loadPatch(List<File> patchFiles) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IOException, IllegalAccessException {
        installSecondaryDexes(context.getClassLoader(), patchsOptFile, patchFiles);
    }

    /**
     * 加载单个补丁文件
     *
     * @param patchFile 补丁文件
     */
    private void loadPatch(final File patchFile) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException, IOException {
        boolean isEx = patchFile.exists();
        installSecondaryDexes(context.getClassLoader(), patchsOptFile, new ArrayList<File>() {{//匿名ArrayList并初始化一个元素
            this.add(patchFile);
        }});
    }

    private void installSecondaryDexes(ClassLoader loader, File dexDir, List<File> files)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
            InvocationTargetException, NoSuchMethodException, IOException {
        if (!files.isEmpty()) {
            if (Build.VERSION.SDK_INT >= 19) {
                V19.install(loader, files, dexDir);
            } else if (Build.VERSION.SDK_INT >= 14) {
                V14.install(loader, files, dexDir);
            } else {
                V4.install(loader, files);
            }
        }
    }

    /**
     * Installer for platform versions 19.
     */
    private static final class V19 {

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries,
                                    File optimizedDirectory)
                throws IllegalArgumentException, IllegalAccessException,
                NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            /* The patched class loader is expected to be a descendant of
             * dalvik.system.BaseDexClassLoader. We modify its
             * dalvik.system.DexPathList pathList field to append additional DEX
             * file entries.
             */
            Field pathListField = findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
            expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList,
                    new ArrayList<File>(additionalClassPathEntries), optimizedDirectory,
                    suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                for (IOException e : suppressedExceptions) {
                    Log.w(TAG, "Exception in makeDexElement", e);
                }
                Field suppressedExceptionsField =
                        findField(loader, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions =
                        (IOException[]) suppressedExceptionsField.get(loader);

                if (dexElementsSuppressedExceptions == null) {
                    dexElementsSuppressedExceptions =
                            suppressedExceptions.toArray(
                                    new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined =
                            new IOException[suppressedExceptions.size() +
                                    dexElementsSuppressedExceptions.length];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions, 0, combined,
                            suppressedExceptions.size(), dexElementsSuppressedExceptions.length);
                    dexElementsSuppressedExceptions = combined;
                }

                suppressedExceptionsField.set(loader, dexElementsSuppressedExceptions);
            }
        }

        /**
         * A wrapper around
         * {@code private static final dalvik.system.DexPathList#makeDexElements}.
         */
        private static Object[] makeDexElements(
                Object dexPathList, ArrayList<File> files, File optimizedDirectory,
                ArrayList<IOException> suppressedExceptions)
                throws IllegalAccessException, InvocationTargetException,
                NoSuchMethodException {
            Method makeDexElements =
                    findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class,
                            ArrayList.class);

            return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory,
                    suppressedExceptions);
        }
    }

    /**
     * Installer for platform versions 14, 15, 16, 17 and 18.
     */
    private static final class V14 {

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries,
                                    File optimizedDirectory)
                throws IllegalArgumentException, IllegalAccessException,
                NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            /* The patched class loader is expected to be a descendant of
             * dalvik.system.BaseDexClassLoader. We modify its
             * dalvik.system.DexPathList pathList field to append additional DEX
             * file entries.
             */
            Field pathListField = findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList,
                    new ArrayList<File>(additionalClassPathEntries), optimizedDirectory));
        }

        /**
         * A wrapper around
         * {@code private static final dalvik.system.DexPathList#makeDexElements}.
         */
        private static Object[] makeDexElements(
                Object dexPathList, ArrayList<File> files, File optimizedDirectory)
                throws IllegalAccessException, InvocationTargetException,
                NoSuchMethodException {
            Method makeDexElements =
                    findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class);

            return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory);
        }
    }

    /**
     * Installer for platform versions 4 to 13.
     */
    private static final class V4 {
        private static void install(ClassLoader loader, List<File> additionalClassPathEntries)
                throws IllegalArgumentException, IllegalAccessException,
                NoSuchFieldException, IOException {
            /* The patched class loader is expected to be a descendant of
             * dalvik.system.DexClassLoader. We modify its
             * fields mPaths, mFiles, mZips and mDexs to append additional DEX
             * file entries.
             */
            int extraSize = additionalClassPathEntries.size();

            Field pathField = findField(loader, "path");

            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            for (ListIterator<File> iterator = additionalClassPathEntries.listIterator();
                 iterator.hasNext(); ) {
                File additionalEntry = iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, entryPath + ".dex", 0);
            }

            pathField.set(loader, path.toString());
            expandFieldArray(loader, "mPaths", extraPaths);
            expandFieldArray(loader, "mFiles", extraFiles);
            expandFieldArray(loader, "mZips", extraZips);
            expandFieldArray(loader, "mDexs", extraDexs);
        }
    }

    private static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(name);


                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
            }
        }

        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    private static void expandFieldArray(Object instance, String fieldName,
                                         Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(
                original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static Method findMethod(Object instance, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);


                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                return method;
            } catch (NoSuchMethodException e) {
                // ignore and search next
            }
        }

        throw new NoSuchMethodException("Method " + name + " with parameters " +
                Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /**
     * log输出
     *
     * @param msg
     */
    private static void log(String msg) {
        Log.e(TAG, msg);
    }

    /**
     * patch所在文件目录
     *
     * @param patchPath
     */
    public void addPatch(String patchPath) throws FixBugException {
        try {
            File inFile = new File(patchPath);//输入文件
            if (inFile != null && !inFile.exists()) {
                throw new FixBugException("FileNotFoundException", new FileNotFoundException("file path:" + patchPath));
            }
            File outFile = new File(patchs, inFile.getName() + "_" + System.currentTimeMillis());//输出文件
            File md5File = this.copyFile(outFile, inFile);//复制文件到patch文件中
            this.loadPatch(md5File);//加载补丁文件
        } catch (IllegalAccessException e) {
            throw new FixBugException("IllegalAccessException", e);
        } catch (NoSuchFieldException e) {
            throw new FixBugException("NoSuchFieldException", e);
        } catch (ClassNotFoundException e) {
            throw new FixBugException("ClassNotFoundException", e);
        } catch (NoSuchMethodException e) {
            throw new FixBugException("NoSuchMethodException", e);
        } catch (InstantiationException e) {
            throw new FixBugException("InstantiationException", e);
        } catch (InvocationTargetException e) {
            throw new FixBugException("InvocationTargetException", e);
        } catch (IOException e) {
            throw new FixBugException("IOException", e);
        } catch (Exception e) {
            throw new FixBugException(e);
        }
    }

    /**
     * 移除所有的patch文件
     */
    public void removeAllPatch() {
        this.clearPaths();
    }

    /**
     * 清除所有的补丁文件
     */
    private void clearPaths() {
        if (patchs != null && patchs.exists() && patchs.isDirectory()) {
            File patchFiles[] = patchs.listFiles();
            for (int i = 0; i < patchFiles.length; i++) {
                if (patchFiles[i].getName().lastIndexOf(PatchSuffix) == patchFiles[i]
                        .getName().length() - 4) {
                    patchFiles[i].delete();//删除补丁文件
                }
            }
        }
    }

    /**
     * 复制文件从inFile到outFile
     *
     * @param outFile
     * @param inFile
     * @return
     */
    private File copyFile(File outFile, File inFile) {
        BufferedInputStream bis = null;
        OutputStream dexWriter = null;
        try {
            MessageDigest digests = MessageDigest.getInstance("MD5");

            bis = new BufferedInputStream(new FileInputStream(inFile));
            dexWriter = new BufferedOutputStream(new FileOutputStream(outFile));
            byte[] buf = new byte[BUF_SIZE];
            int len;
            while ((len = bis.read(buf, 0, BUF_SIZE)) > 0) {
                digests.update(buf, 0, len);
                dexWriter.write(buf, 0, len);
            }
            dexWriter.close();
            bis.close();
            BigInteger bi = new BigInteger(1, digests.digest());
            String result = bi.toString(16);

            File toFile = new File(outFile.getParentFile(), inFile.getName());//使用文件的md5值做为patch文件名称
            outFile.renameTo(toFile);
            return toFile;
        } catch (Exception e) {
            if (dexWriter != null) {
                try {
                    dexWriter.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            return null;
        }
    }
}
