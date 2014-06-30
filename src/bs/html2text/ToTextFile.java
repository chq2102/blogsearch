package bs.html2text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToTextFile {
	private static int i=0;//文件的个数
	//Matcher slashMatcher =null ;
	//遍历获取指定路径下的所有文件
	void getFiles(String filePath,String outputpath) throws IOException
	{
		File root = new File(filePath);
		File[] files = root.listFiles();//获取当前文件夹下所有子目录或文件， 并加入文件数组
		for(File file:files)//(int n=0;n<files.length;n++)-->file = files[n];  
		{     
			if(file.isDirectory())//判断files[n]是不是目录（不是文件）
			{
				getFiles(file.getAbsolutePath(),outputpath);//递归遍历该目录
			}
			else//files[n]不是目录（是文件）
			{
				String fileName = file.getName();//获得文件名
				//判断文件是否是".html"文件
				if((fileName.lastIndexOf(".")!=-1)&&fileName.substring(fileName.lastIndexOf(".")).equals(".html"))
				{	//如果是".html"文件，获取该文件的绝对路径
					String fileDir=file.getAbsolutePath();
					int position = getCharacterPosition(fileDir, 6, "/");//在绝对路径中获取网址前一个分隔符位置
					
					BufferedWriter writer = null;
					//字符串缓存
					StringBuffer buffer = new StringBuffer();
					//把该文件解析后的内容写入 buffer缓存中
					buffer = new ParseHtmlToText().ParserH(file.getAbsolutePath());
					//判断buffer长度，过滤空的buffer，避免大量空文件产生
					if(buffer.length()>0){
						//该文件的来源输出网址
						System.out.println(file.getAbsolutePath().substring(position+1));
						//输出文件的路径和文件名(源文件的名字+i-->i避免同名文件被覆盖)、格式(.txt)
						writer = new BufferedWriter(new FileWriter(outputpath+fileName.substring(0, fileName.lastIndexOf("."))+i+".txt"));
						//在buffer  缓存最前面插入文件网址
						buffer.insert(0, "地址：http://"+file.getAbsolutePath().substring(position+1)+"\r\n") ;	
						writer.write(buffer.toString());//buffer 输出到txt文件
						i++;//每生成一个文件 i+1，避免同名文件被覆盖
						
						writer.flush();//保证全部输出后关闭输出
						writer.close();
					}

				}

			}   

		}
	}
	
	//定义一个获取 读取字符串第n次出现特定符号的位置的函数
	public  int getCharacterPosition(String string ,int i,String character)
	{  
		Matcher slashMatcher = Pattern.compile("/").matcher(string);  
		int mIdx = 0;  
		while(slashMatcher.find()) {  
			mIdx++;  
			if(mIdx == i){  
				break;  
			}  
		}
		return slashMatcher.start();  
	}
	
	

}


