package bs.html2text;

import java.io.File;

public class Main {
	public static void main(String[] args) throws Exception {

		String filePath = "/home/huanglei/larbin/save";
		String outputpath = "/home/huanglei/outputpath/";
		if(!new File(outputpath).exists())
			new File(outputpath).mkdirs();
		new ToTextFile().getFiles(filePath, outputpath);
	} 
}
