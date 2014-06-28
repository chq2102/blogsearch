package bs.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import bs.search.IndexSearch;

public class IndexCreate {
	/**
	 * 抽象的父类文件夹
	 * */
	public static Directory directory;

	/**
	 * 返回IndexWriter
	 * */
	public static IndexWriter getWriter(){
		// Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_47);//设置标准分词器
		// ,默认是一元分词
		Analyzer analyzer = new IKAnalyzer(true); // true智能分词
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47,
				analyzer);// 设置IndexWriterConfig
		// iwc.setRAMBufferSizeMB(3);//设置缓冲区大小
		IndexWriter iw = null ;
		try {
			iw = new IndexWriter(directory, iwc) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iw;
	}

	/**
	 * @indexPath 索引存放路径
	 * **/
	public static void addIndex(String sourceText, String indexWriterPath) {
		IndexWriter writer = null;
		try {
			directory = FSDirectory.open(new File(indexWriterPath));// 打开存放索引的路径
			writer = getWriter();
			File file = new File(sourceText);
			// File file2 = new File("/home/huanglei/2.txt");
			// File file3 = new File("/home/huanglei/3.txt");

			writer.addDocument(readFile(file));// 添加进写入流里
			// writer.addDocument(readFile(file2));// 添加进写入流里
			// writer.addDocument(readFile(file3));// 添加进写入流里

			// writer.forceMerge(1);// 优化压缩段,大规模添加数据的时候建议，少使用本方法，会影响性能
			writer.commit();// 提交数据
			System.out.println("添加成功");
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (writer != null) {
				try {
					writer.close();// 关闭流
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 读取文本文件并分析，生成Document
	public static Document readFile(File file){

		Document doc = new Document();

		// 获取txt文本
		StringBuilder sb = new StringBuilder();
		String s = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
			br.close();
			String text = sb.toString();
			// 获取web地址
			int i = text.indexOf("地址");
			int j = text.indexOf("标题");
			String url = text.substring(i + 3, j);
			// System.out.println("地址：" + url);

			// 获取标题
			i = text.indexOf("内容");
			String title = text.substring(j + 3, i);
			// System.out.println("标题:" + title);
			// 获取内容
			String content = text.substring(i + 3);

			doc.add(new StringField("url", url, Store.YES));// 存储
			doc.add(new TextField("title", title, Store.YES));// 分词存储
			doc.add(new TextField("content", content, Store.YES));// 存储
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

		return doc;
	}

	/**
	 * 
	 * @param analyzer
	 * @param indexFile
	 * @param keyword
	 * @return url,lightTitle,lightContent
	 * @throws IOException
	 */

	public static void main(String args[]){
		
		//String sourcePath[] = { "/home/huanglei/1.txt", "/home/huanglei/2.txt","/home/huanglei/3.txt" };

		File root = new File("/home/huanglei/outputpath");
		File[] files = root.listFiles();
		
		String indexPath = "/home/huanglei/index";
		for(File file:files){
			addIndex(file.toString(), indexPath);// 调用添加方法
		}
		
		/*
		for (String str : sourcePath) {
			addIndex(str, indexPath);// 调用添加方法
		}
		*/
	}
}
