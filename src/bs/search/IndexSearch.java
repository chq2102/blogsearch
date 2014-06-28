package bs.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import bs.index.Item;

public class IndexSearch {
	DirectoryReader ireader = null;
	Directory directory = null ;
	List<String> result = null ;
	public List<String> searchIndexer(Analyzer analyzer, File indexFile,
			String keyword) {
		try {
			directory = FSDirectory.open(indexFile);
			result = new ArrayList<String>();
			String lightTitle = null ;
			String lightContent = null ;
			// 设定搜索目录
			ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

			// 对多field进行搜索
			java.lang.reflect.Field[] fields = Item.class.getDeclaredFields();
			int length = fields.length;
			
			
			String[] multiFields = new String[length];
			for (int i = 0; i < length; i++) {
				multiFields[i] = fields[i].getName();
			}
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					Version.LUCENE_47, multiFields, analyzer);
			

			// 设定具体的搜索词
			Query query = parser.parse(keyword);   //query: (url:反感 url:二叉树) (title:反感 title:二叉树) (content:反感 content:二叉树)
			ScoreDoc[] hits = isearcher.search(query, null, Integer.MAX_VALUE).scoreDocs;   //hits即查到的文档数组  Integer.MAX_VALUE表示查找query前无穷个
			//得到高亮后的文本
			

			//由于文章数过多的时候会下面的循环会耗费太多时间，而且搜索时，一般前几页才有用，所以这里只取前20页即200篇文章
			int docNum = hits.length ;
			if(docNum>200)
				docNum = 198 ;
			Document hitDoc =null ;
			for(int i=0 ;i<docNum;++i){
				hitDoc = isearcher.doc(hits[i].doc);    //hits[0]表示文档1的内容 hitDoc即文本内容
				lightTitle = lightText(query,hitDoc.get(multiFields[1]),keyword) ;   //multiFields表示字段字符串数组
				lightContent = lightText(query,hitDoc.get(multiFields[2]),keyword) ;
	            
				result.add(hitDoc.get(multiFields[0])) ;  //add url
				
				if(lightTitle!=null){
					result.add(lightTitle) ;                //add lightTitle
				}else{
					result.add(hitDoc.get(multiFields[1])) ;
				}
				
				//此处的ifelse就是使得当内容里面没有关键词的时候，只显示内容的前100个字符串，而不是全部输出
				if(lightContent!=null){
					result.add(lightContent) ;				  //add ligthContent
				}else{
					if(hitDoc.get(multiFields[2]).length()>100){
						result.add(hitDoc.get(multiFields[2]).substring(0, 100)) ;
					}else{
						result.add(hitDoc.get(multiFields[2])) ;
					}
					
				}
			}
			result.add(""+hits.length) ;
			/*
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				Item item = new Item();
				for (String field : multiFields) {
					String setMethodName = "set"
							+ toFirstLetterUpperCase(field);
					item.getClass().getMethod(setMethodName, String.class)
							.invoke(item, hitDoc.get(field));
				}
				result.add(item);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ireader.close();
				directory.close();
			} catch (IOException e) {
			}
		}
		return result;
	}
	
	public String lightText(Query query,String content,String keyword){
		
		/*Begin:开始关键字高亮*/
        SimpleHTMLFormatter formatter=new SimpleHTMLFormatter("<b><font color='#dd4b39'>","</font></b>");
        Highlighter highlighter=new Highlighter(formatter, new QueryScorer(query));
        highlighter.setTextFragmenter(new SimpleFragmenter(100));  //设置输出包含高亮关键词的文本总字符数
        Analyzer luceneAnalyzer= new IKAnalyzer(true) ;
        if(content!=null){
            TokenStream tokenstream;
			try {
				tokenstream = luceneAnalyzer.tokenStream(keyword, new StringReader(content));
				content=highlighter.getBestFragment(tokenstream, content);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidTokenOffsetsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                
        }
        /*End:结束关键字高亮*/
        
		return content ;
	}
	//首字母转大写
	public static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
	}
	
	
	public List<String> search(String searchContent) {
		//String indexPath = "/home/huanglei/index";
		
		String indexPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		indexPath = indexPath.substring(0, indexPath.indexOf("WEB-INF"))+"index";
		
		/*
		 * list:{url1,title1,content1,url2,title2,content2}
		 */
		List<String> list = null ;  //此处必须初始化list为null，不然每次的值都不变
		long begin = System.currentTimeMillis() ;
		System.out.println("*****begin***** : "+ begin) ;
		
		list = searchIndexer(new IKAnalyzer(true), new File(
				indexPath), searchContent);
		
		long end = System.currentTimeMillis() ;
		double time = end-begin ;
		System.out.println("*****end***** : "+ end) ;
		System.out.println("Time: "+ time) ;
		list.add(""+(time/1000)) ;
		// for(Item item : result){
		// System.out.println(item.toString()) ;
		// }
		
		return list ;
	}
}
