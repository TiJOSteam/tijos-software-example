import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 此例程演示了TiJOS中的文件操作，包括文件创建，读写和列举
 * 注: TiJOS中不支持文件路径
 *
 *@author TiJOS
 */
public class FileDemo {
	public static void main(String[] args) {

		System.out.println("File operation demo.");
		
		String filename = "file1.txt";
		String content = "12345678901234567890";

		creatFile(filename);

		writeFile(filename, content);
		readFile(filename, 10, 10);

		listFile();

	}

	private static void creatFile(String fileName) {
		File file;
		try {
			file = new File(fileName);

			if (!file.exists()) 
			{
				if (file.createNewFile()) 
				{
					System.out.println("Create file successfully.");
				}
			} 
			else 
			{
				System.out.println("The file exists.");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void writeFile(String fileName, String content){
		try {
			File file = new File(fileName);
			// Write data with stream
			FileOutputStream out = new FileOutputStream(file);
			out.write(content.getBytes(), 0, content.length());
			System.out.println("Write successfully.");
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readFile(String fileName, int offset, int len) {
		try {
			File file = new File(fileName);
			FileInputStream in = new FileInputStream(file);
			in.skip(offset);
			byte[] outBuffer = new byte[len];
			in.read(outBuffer, 0, len);
			String s = new String(outBuffer, "UTF-8");
			System.out.println(s);

			in.close();

		} catch (Exception e) {

		}
	}

	private static void listFile() {
		try {
			System.out.println("List file");
			File [] fa = File.listFiles();

			System.out.println("File number = " + fa.length);
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];

				System.out.println("File Name : " + fs.getName() + "File Size = " + fs.length());

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
