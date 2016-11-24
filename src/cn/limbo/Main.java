package cn.limbo;

import cn.limbo.entity.Question;
import cn.limbo.utils.FileReaderWriter;
import cn.limbo.utils.Spider;

import java.util.List;

/**
 * Created by limbo on 2016/11/13.
 */
public class Main {
    public static void main(String[] args) {
        String url = "https://www.zhihu.com/explore/recommendations";
        String content = Spider.SendGet(url);
        List<Question> questions = Spider.getRecommendations(content);

        for (Question question : questions){
            FileReaderWriter.writeIntoFile(question.writeString(),"/Users/limbo/Documents/eBook/hhh.txt",true);
        }
    }
}
