package cn.limbo.entity;

import cn.limbo.utils.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by limbo on 2016/11/13.
 * 问题模型
 */
public class Question {

    private String question;
    private String questionDescribe;
    private String url;
    private List<String> answers;

    public Question() {
    }

    public Question(String question, String questionDescribe, String url, List<String> answers) {
        this.question = question;
        this.questionDescribe = questionDescribe;
        this.url = url;
        this.answers = answers;
    }

    public Question(String url) {
        this.question = "";
        this.questionDescribe = "";
        this.url = "";
        this.answers = new ArrayList<>();
        if (getUrl(url)) {
            System.out.println("正在抓取" + this.url);
            String content = Spider.SendGet(this.url);
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile("zh-question-title.+?<h2.+?>(.+?)</h2>");
            matcher = pattern.matcher(content);
            if(matcher.find()){
                question = matcher.group(1);
            }
            // 匹配描述
            pattern = Pattern.compile("zh-question-detail.+?<div.+?>(.*?)</div>");
            matcher = pattern.matcher(content);
            if(matcher.find()){
                this.questionDescribe = matcher.group(1);
            }
            //匹配答案
            pattern = Pattern.compile("/answer/content.+?<div.+?>(.*?)</div>");
            matcher = pattern.matcher(content);
            while (matcher.find()){
                answers.add(matcher.group(1));
            }
        }


    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionDescribe() {
        return questionDescribe;
    }

    public void setQuestionDescribe(String questionDescribe) {
        this.questionDescribe = questionDescribe;
    }

    //将答案链接转变为问题的链接
    public boolean getUrl(String url) {
        // 将http://www.zhihu.com/question/22355264/answer/21102139
        // 转化成http://www.zhihu.com/question/22355264
        Pattern pattern = Pattern.compile("question/(.*?)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            this.url = "https://www.zhihu.com/question/" + matcher.group(1);
        } else {
            return false;
        }
        return true;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String writeString() {
        String result = "";
        result += "问题: " + this.question + "\r\n";
        result += "描述: " + this.questionDescribe + "\r\n";
        result += "链接: " + this.url + "\r\n\r\n";
        int i = 0;
        for (String tmp : this.answers) {
            result += "回答" + (i++) + ": " + tmp + "\r\n\r\n\r\n";
        }
        result += "\r\n\r\n\r\n\r\n\r\n\r\n";
        result = result.replaceAll("<br>","\r\n").replaceAll("<.*?>","");
        return  result;
    }

    @Override
    public String toString() {
        String result = "";
        result += "问题: " + this.question + "\n";
        result += "描述: " + this.questionDescribe + "\n";
        result += "链接: " + this.url + "\n";
        result += "回答: " + this.answers.size() + "\n";

        return result;
    }
}
