package bs.html2text;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlToText {
	
	//把解析后的内容输出到缓存中
	public  StringBuffer ParserH(String htmlDrictoey) throws IOException
	{
		File input = new File(htmlDrictoey);//获取文件
		Document doc = Jsoup.parse(input, "UTF-8");//用Jsoup解析文件生成docment
		StringBuffer buffer = new StringBuffer();

		Element blogContent = null;//要在文件中查找的元素		

		//按数组中的关键ID到文件中去查看是否有符合的元素 如果在文件中没有找到在数组中的关键ID 则去找特殊的class
		if(doc.getElementById("centercontent")!=null)
			blogContent = doc.getElementById("centercontent");
		
		else if(doc.getElementById("cnblogs_post_body")!=null)
			blogContent = doc.getElementById("cnblogs_post_body");
		
		else if(doc.getElementsByAttributeValue("class", "Blog_wz1").first()!=null)
			blogContent = doc.getElementsByAttributeValue("class", "Blog_wz1").first();
		
		else 
			blogContent = doc.getElementsByAttributeValue("class", "postText").first();
		//blogContent不为空，即有找到元素
		if(blogContent!=null){	
			Elements tagDivs = blogContent.getElementsByTag("div");//获取整个DIV中的元素

			String title = doc.title();//博客标题
			String content = null;//博客内容
			//逐个提取DIV元素中的内容 并添加到content中
			for (Element link : tagDivs) {		  
				String linkText = link.text();		 
				content+=linkText;
			} 
			//把文件的标题和内容输出到buffer缓存中
			buffer.append("标题："+title).append("\r\n");
			buffer.append("内容："+content.replaceFirst("null", ""));//由于DIV的原因 内容前面会有一个“null"字符串--去掉
		}
		//不管有没有找到元素，返回buffer 该buffer有可能是空
		return buffer;

	}

}
