package bs.html2text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToTextFile {
	private static int i=0;
	Matcher slashMatcher =null ;

	void getFiles(String filePath,String outputpath) throws IOException
	{
		File root = new File(filePath);
		File[] files = root.listFiles();
		for(File file:files)
		{     
			if(file.isDirectory())
			{
				getFiles(file.getAbsolutePath(),outputpath);
			}
			else
			{
				String fileName = file.getName();
				if((fileName.lastIndexOf(".")!=-1)&&fileName.substring(fileName.lastIndexOf(".")).equals(".html"))
				{
					String fileDir=file.getAbsolutePath();
					int position = getCharacterPosition(fileDir, 6, "/");
					
					BufferedWriter writer = null;
					StringBuffer buffer = new StringBuffer();
					buffer = new ParseHtmlToText().ParserH(file.getAbsolutePath());
					if(buffer.length()>0){
						System.out.println(file.getAbsolutePath().substring(position+1));
						
						writer = new BufferedWriter(new FileWriter(outputpath+fileName.substring(0, fileName.lastIndexOf("."))+i+".txt"));
						buffer.insert(0, "地址：http://"+file.getAbsolutePath().substring(position+1)+"\r\n") ;
						writer.write(buffer.toString());
						i++;
						writer.close();
					}

				}

			}   

		}
	}
	
	public  int getCharacterPosition(String string ,int i,String character)
	{  
		slashMatcher = Pattern.compile("/").matcher(string);  
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


