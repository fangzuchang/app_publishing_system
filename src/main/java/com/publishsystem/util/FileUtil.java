package com.publishsystem.util;

import java.io.*;

/**
 * @author 夕橘子-O
 * @version 2016年7月8日 上午10:38:49
 */
public class FileUtil {
    //文件路径+名称
    private static String filenameTemp;

    /**
     * @param path
     * @param fileName
     * @param filecontent
     * @return
     */
    public static boolean createPlistFile(String path, String fileName, String filecontent) {
        Boolean bool = false;
        filenameTemp = path + fileName;//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        try {
            if (file.exists()) {
                deleteFile(filenameTemp);
            }
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is " + filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean createPlist(String host, String custName, String iphoneUrl, String appLogoUrl, String iphonePackageName, String plistPath, String plistName) {
        StringBuffer fileContent1 = new StringBuffer(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\"><plist version=\"1.0\"><dict><key>items</key><array><dict><key>assets</key><array><dict><key>kind</key><string>software-package</string><key>url</key><string>" + host);
        // 之前为plist文件地址
        String fileContent2 = "</string></dict><dict><key>kind</key><string>full-size-image</string><key>needs-shine</key><false/><key>url</key><string>" + host;
        // 之前为软件logo地址
        String fileContent3 = "</string></dict><dict><key>kind</key><string>display-image</string><key>needs-shine</key><false/><key>url</key><string>" + host;
        // 之前为软件logo地址
        String fileContent4 = "</string></dict></array><key>metadata</key><dict><key>bundle-identifier</key><string>";
        // 之前为软件包名
        String fileContent5 = "</string><key>bundle-version</key><string>1.0</string><key>kind</key><string>software</string><key>title</key><string>" + custName + "</string></dict></dict></array></dict></plist>";
        String filecontent = fileContent1.append(iphoneUrl)
                .append(fileContent2).append(appLogoUrl)
                .append(fileContent3).append(appLogoUrl)
                .append(fileContent4)
                .append(iphonePackageName)
                .append(fileContent5).toString();
        FileUtil.createPlistFile(plistPath, plistName,
                filecontent);
        return true;
    }

    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

}