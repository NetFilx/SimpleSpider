package cn.limbo.utils;

import cn.limbo.entity.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by limbo on 2016/11/13.
 */
public class Spider {

    public static String SendGet(String url){
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try{
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();

            // 注意：如果是服务器端禁止抓取,那么可以通过设置User-Agent来欺骗服务器。
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line ;
            while((line = in.readLine()) !=null){
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送请求出现异常" + e);
            e.printStackTrace();
        } finally {
            try{
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }

    public static ArrayList<Question> getRecommendations(String content){
        // 预定义一个ArrayList来存储结果
        ArrayList<Question> questions = new ArrayList<>();
        // 用来匹配url，也就是问题的链接
        Pattern pattern = Pattern.compile("<h2>.+?question_link.+?href=\"(.+?)\".+?</h2>");
        Matcher matcher = pattern.matcher(content);
        // 是否存在匹配成功的对象

        while (matcher.find()){
            //定义一个问题对象来存储抓取到的信息
            Question temp = new Question(matcher.group(1));
            // 添加成功匹配的结果
            questions.add(temp);
            // 继续查找下一个匹配对象
        }
        return questions;
    }
}
