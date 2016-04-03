package com.sdk.lib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Coolspan on 2016/1/29 12:19
 * 操作文件的工具类
 *
 * @author 乔晓松 coolspan@sina.cn
 */
public class FileUtils {

    /**
     * 读取缓存大小
     */
    private final static int BUF_SIZE = 512;

    /**
     * 复制文件从inFile到outFile
     *
     * @param outFile
     * @param inFile
     * @return
     */
    public static File copyFile(File outFile, File inFile, String PatchSuffix) throws FixBugException {
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

            File toFile = new File(outFile.getParentFile(), result + PatchSuffix);//使用文件的md5值做为patch文件名称
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
            throw new FixBugException("Exception", e);
        }
    }
}
