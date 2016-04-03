package com.sdk.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * FixBugManage 2015-12-22 下午9:59:28
 *
 * @author 乔晓松 965266509@qq.com
 */
public class FixBugManage {

    /**
     * Class tag
     */
    private final static String TAG = "FixBugManage";

    /**
     * 上下文
     */
    private Context context;

    /**
     * patch文件存放目录
     */
    private File patchs;

    /**
     * Patch存放目录
     */
    private final static String PatchsDir = "patchs";

    /**
     * patch文件优化过后dex存放目录
     */
    private File patchsOptFile;

    /**
     * Patch文件优化后的存放目录
     */
    private final static String PatchsOptDir = "patchsopt";

    /**
     * 补丁后缀
     */
    private final static String PatchSuffix = ".jar";

    /**
     * 关于FixBug信息存储的Key
     */
    private final static String FixBug = "fixbug";

    /**
     * FixBug的版本号
     */
    private final static String VersionCode = "versionCode";

    /**
     * 初始化上下文，同时也初始化存储补丁的目录
     *
     * @param context
     */
    public FixBugManage(Context context) {
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
    private void loadPatchs() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        if (patchs.exists() && patchs.isDirectory()) {// 判断文件是否存在并判断是否是文件夹
            File[] patchFiles = patchs.listFiles();// 获取文件夹下的所有的文件
            for (int i = 0; i < patchFiles.length; i++) {
                if (patchFiles[i].getName().lastIndexOf(PatchSuffix) == patchFiles[i]
                        .getName().length() - 4) {// 仅处理.jar文件
                    this.loadPatch(patchFiles[i].getAbsolutePath(), patchFiles[i].getName());// 加载jar文件
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
     * 加载单个补丁文件
     *
     * @param patchPath
     */
    private void loadPatch(String patchPath, String fileName) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (hasLexClassLoader()) {//判断是否是AliyunOS系统
            log("hasLexClassLoader");
            //TODO AliyunOS系统的处理
            injectInAliyunOs(context, patchPath, patchsOptFile.getAbsolutePath(), fileName);
        } else if (hasDexClassLoader()) {//判断是否是Api是否>=14
            log("hasDexClassLoader");
            injectDexAtFirst(patchPath, patchsOptFile.getAbsolutePath());// 读取jar文件中dex内容
        } else {
            log("injectBelowApiLevel14");
            injectDexBelowApiLevel14(patchPath, patchsOptFile.getAbsolutePath());
        }
    }

    /**
     * 对Api大于14的支持
     *
     * @param dexPath
     * @param defaultDexOptPath
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static void injectDexAtFirst(String dexPath, String defaultDexOptPath)
            throws NoSuchFieldException, IllegalAccessException,
            ClassNotFoundException {
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,
                defaultDexOptPath, dexPath, getPathClassLoader());// 把dexPath文件补丁处理后放入到defaultDexOptPath目录中
        Object baseDexElements = getDexElements(getPathList(getPathClassLoader()));// 获取当面应用Dex的内容
        Object newDexElements = getDexElements(getPathList(dexClassLoader));// 获取补丁文件Dex的内容
        Object allDexElements = combineArray(newDexElements, baseDexElements);// 把当前apk的dex和补丁文件的dex进行合并
        Object pathList = getPathList(getPathClassLoader());// 获取当前的patchList对象
        setField(pathList, pathList.getClass(), "dexElements", allDexElements);// 利用反射设置对象的值
    }

    /**
     * 对Api小于14的处理
     *
     * @param patchPath
     * @param dexOptPatch
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void injectDexBelowApiLevel14(String patchPath, String dexOptPatch) throws NoSuchFieldException, IllegalAccessException {
        PathClassLoader obj = (PathClassLoader) context.getClassLoader();
        DexClassLoader dexClassLoader =
                new DexClassLoader(patchPath, dexOptPatch, patchPath, context.getClassLoader());
        setField(obj, PathClassLoader.class, "mPaths",
                appendArray(getField(obj, PathClassLoader.class, "mPaths"),
                        getField(dexClassLoader, DexClassLoader.class, "mRawDexPath")
                ));
        setField(obj, PathClassLoader.class, "mFiles",
                combineArray(getField(dexClassLoader, DexClassLoader.class, "mFiles"),
                        getField(obj, PathClassLoader.class, "mFiles")));

        setField(obj, PathClassLoader.class, "mZips",
                combineArray(getField(dexClassLoader, DexClassLoader.class, "mZips"),
                        getField(obj, PathClassLoader.class, "mZips")));

        setField(obj, PathClassLoader.class, "mDexs",
                combineArray(getField(dexClassLoader, DexClassLoader.class, "mDexs"),
                        getField(obj, PathClassLoader.class, "mDexs")));
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
     * 对AliyunOs系统的热修复处理
     *
     * @param context
     * @param patchDexFile
     * @param dexOptPatch
     * @param patchName
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    private static void injectInAliyunOs(Context context, String patchDexFile, String dexOptPatch, String patchName)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchFieldException {

        PathClassLoader obj = (PathClassLoader) context.getClassLoader();
        String replaceAll = new File(patchDexFile).getName().replaceAll("\\.[a-zA-Z0-9]+", ".lex");
        Class cls = Class.forName("dalvik.system.LexClassLoader");

        Object newInstance =
                cls.getConstructor(new Class[]{String.class, String.class, String.class, ClassLoader.class}).newInstance(
                        new Object[]{dexOptPatch + File.separator + patchName,
                                dexOptPatch, patchDexFile, obj});

        Object newInstance2 =
                cls.getConstructor(new Class[]{String.class, String.class, String.class, ClassLoader.class}).newInstance(
                        new Object[]{context.getDir("dex", 0).getAbsolutePath() + File.separator + replaceAll,
                                context.getDir("dex", 0).getAbsolutePath(), patchDexFile, obj});

//        cls.getMethod("loadClass", new Class[]{String.class}).invoke(newInstance, new Object[]{patchClassName});

        setField(obj, PathClassLoader.class, "mPaths",
                appendArray(getField(obj, PathClassLoader.class, "mPaths"), getField(newInstance, cls, "mRawDexPath")));
        setField(obj, PathClassLoader.class, "mFiles",
                combineArray(getField(obj, PathClassLoader.class, "mFiles"), getField(newInstance, cls, "mFiles")));
        setField(obj, PathClassLoader.class, "mZips",
                combineArray(getField(obj, PathClassLoader.class, "mZips"), getField(newInstance, cls, "mZips")));
        setField(obj, PathClassLoader.class, "mLexs",
                combineArray(getField(obj, PathClassLoader.class, "mLexs"), getField(newInstance, cls, "mDexs")));
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
            File md5File = FileUtils.copyFile(outFile, inFile, PatchSuffix);//复制文件到patch文件中
            this.loadPatch(md5File.getAbsolutePath(), md5File.getName());//加载补丁文件
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
     * 判断dalvik.system.LexClassLoader类是否存在
     *
     * @return
     */
    private boolean hasLexClassLoader() {
        try {
            Class.forName("dalvik.system.LexClassLoader");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断dalvik.system.BaseDexClassLoader类是否存在
     *
     * @return
     */
    private boolean hasDexClassLoader() {
        try {
            Class.forName("dalvik.system.BaseDexClassLoader");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 把新Elements放到数据最前面，后面加上当面apk中的Elements
     *
     * @param obj
     * @param obj2
     * @return
     */
    private static Object appendArray(Object obj, Object obj2) {
        Class componentType = obj.getClass().getComponentType();
        int length = Array.getLength(obj);
        Object newInstance = Array.newInstance(componentType, length + 1);
        Array.set(newInstance, 0, obj2);
        for (int i = 1; i < length + 1; i++) {
            Array.set(newInstance, i, Array.get(obj, i - 1));
        }
        return newInstance;
    }

    /**
     * 此方法是合并2个数组，把补丁dex中的内容放到数组最前，达到修复bug的目的
     *
     * @param firstArray
     * @param secondArray
     * @return
     */
    private static Object combineArray(Object firstArray, Object secondArray) {
        Class<?> localClass = firstArray.getClass().getComponentType();
        int firstArrayLength = Array.getLength(firstArray);
        int allLength = firstArrayLength + Array.getLength(secondArray);
        Object result = Array.newInstance(localClass, allLength);
        for (int k = 0; k < allLength; ++k) {
            if (k < firstArrayLength) {
                Array.set(result, k, Array.get(firstArray, k));
            } else {
                Array.set(result, k,
                        Array.get(secondArray, k - firstArrayLength));
            }
        }
        return result;
    }

    /**
     * 获取当前的ClassLoader
     *
     * @return
     */
    private static PathClassLoader getPathClassLoader() {
        PathClassLoader pathClassLoader = (PathClassLoader) FixBugManage.class
                .getClassLoader();// 获取类加载器
        return pathClassLoader;
    }

    /**
     * 获取dexElements属性值
     *
     * @param paramObject
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getDexElements(Object paramObject)
            throws IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException {
        return getField(paramObject, paramObject.getClass(), "dexElements");// 利用反射获取到dexElements属性
    }

    private static Object getPathList(Object baseDexClassLoader)
            throws IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException, ClassNotFoundException {
        return getField(baseDexClassLoader,
                Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");// 利用反射获取到pathList属性
    }

    /**
     * 获取某个属性的值
     *
     * @param obj
     * @param cl
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static Object getField(Object obj, Class<?> cl, String field)
            throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);// 强制反射
        return localField.get(obj);// 获取值
    }

    /**
     * 设置某个属性的值
     *
     * @param obj
     * @param cl
     * @param field
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static void setField(Object obj, Class<?> cl, String field,
                                 Object value) throws NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);// 强制反射
        localField.set(obj, value);// 设置值
    }
}