package bs.html2text;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlToText {
	private static String idName[] = {"article_content","cnblogs_post_body",
		"blog_content","sina_keyword_ad_area2"};

	
	public  StringBuffer ParserH(String htmlDrictoey) throws IOException
	{
		File input = new File(htmlDrictoey);
		Document doc = Jsoup.parse(input, "UTF-8");
		StringBuffer buffer = new StringBuffer();

		Element blogContent = null;
		for(int i=0;i<idName.length;i++){

			if(blogContent==null){
				blogContent = doc.getElementById(idName[i]);
			}
		}
		if(blogContent==null){
			blogContent = doc.getElementsByAttributeValue("class","Blog_wz1").first() ;
		}
		if(blogContent!=null){	
			Elements tagDivs = blogContent.getElementsByTag("div");

			String title = doc.title();
			String content = null;
			for (Element link : tagDivs) {		  
				String linkText = link.text();		 
				content+=linkText;
			} 
			buffer.append("标题："+title).append("\r\n");
			buffer.append("内容："+content.replaceFirst("null", ""));
		}
		
		return buffer;

	}

}
