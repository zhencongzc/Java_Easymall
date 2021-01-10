package cn.tedu.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class AnalyzerTest {
    //准备一个分词计算逻辑
    public void printTerm(Analyzer analyzer, String msg) throws IOException {
        //将文本转化成流对象
        StringReader reader = new StringReader(msg);
        TokenStream tokenStream =
                analyzer.tokenStream("test", msg);//分词的计算一定是绑定某个document进行
//fieldName才有效
        tokenStream.reset();//重置指针
        //获取文本属性打印
        CharTermAttribute chart = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.println(chart.toString());
        }
    }

    @Test
    public void run() throws Exception {
        //准备多个分词器
        Analyzer a1 = new StandardAnalyzer();//标准,处理字
        Analyzer a2 = new SimpleAnalyzer();//简单分词器,回车符,空格,标点等
        Analyzer a3 = new WhitespaceAnalyzer();//空格分词器
        Analyzer a4 = new SmartChineseAnalyzer();//智能中文分词器
        Analyzer a5=new IKAnalyzer6x();
        String msg = "王者荣耀,英雄联盟,微信账号";
        System.out.println("**************标准**************");
        printTerm(a1, msg);
        System.out.println("**************简单**************");
        printTerm(a2, msg);
        System.out.println("**************空格**************");
        printTerm(a3, msg);
        System.out.println("**************智能中文**************");
        printTerm(a4, msg);
        System.out.println("**************IK中文**************");
        printTerm(a5,msg);
    }

}
