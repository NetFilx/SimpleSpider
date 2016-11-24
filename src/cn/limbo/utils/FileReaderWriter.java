package cn.limbo.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by limbo on 2016/11/13.
 */
public class FileReaderWriter {


    /**
     * @param filePath 文件路径的字符串表示形式
     * @param keyword  查找到包含某个关键字的信息：而非null为带关键字查询，null为全文显示
     * @return
     */
    public static String ReadFromFile(String filePath, String keyword) {

        StringBuffer stringBuffer = null;
        File file = new File(filePath);
        if (file.exists()) {
            stringBuffer = new StringBuffer();
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            String temp = "";
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                while ((temp = bufferedReader.readLine()) != null) {
                    if (keyword == null) {
                        stringBuffer.append(temp + "\n");
                    } else {
                        if (temp.contains(keyword)) {
                            stringBuffer.append(temp + "\n");
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileReader != null) {
                        fileReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (stringBuffer == null) {
            return null;
        } else {
            return stringBuffer.toString();
        }

    }

    /**
     * 将指定字符串写入文件。如果给定的文件路径不存在，将新建文件后写入。
     *
     * @param content  要写入文件的内容
     * @param filePath 文件路径的字符串表示形式，目录的层次分隔是“/”
     * @param isAppend true：追加到文件的末尾，false：以覆盖原文件的方式写入
     */
    public static boolean writeIntoFile(String content, String filePath, boolean isAppend) {
        boolean isSuccess = true;
        // 先过滤掉文件名
        int index = filePath.lastIndexOf("/");
        String dir = filePath.substring(0, index);
        // 创建除文件的路径
        File fileDir = new File(dir);
        fileDir.mkdirs();
        // 再创建路径下的文件
        File file = null;

        try {
            file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        }

        //写入文件
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file, isAppend);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    /**
     * 获取当前时间，用于文件命名
     *
     * @param format yyyy 表示4位年， MM 表示2位月， dd 表示2位日，hh小时，mm分钟。
     * @return true:创建成功，false创建不成功
     */
    public static String getNowTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }


    public static boolean createNewFile(String filePath) {
        boolean isSuccess = true;
        // 如有则将"\\"转为"/",没有则不产生任何变化
        String filePathTurn = filePath.replaceAll("\\\\", "/");
        //先过滤文件名
        int index = filePathTurn.lastIndexOf("/");
        String dir = filePathTurn.substring(0, index);
        // 再创建文件夹
        File fileDir = new File(dir);
        isSuccess = fileDir.mkdirs();
        // 创建文件
        File file = new File(filePathTurn);
        try {
            isSuccess = file.createNewFile();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }
}
