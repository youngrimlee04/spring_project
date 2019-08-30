import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
	public static void main(String[] args) throws IOException {
		
		InputStream in = new FileInputStream("c:/dev/error.txt");
		int data = in.read();
		System.out.println((char)data); //java application으로 시행, 한 번만 뽑으면 한 글자 a밖에 안뽑힘
		
		OutputStream out = new FileOutputStream("c:/dev/error_copy.txt");
		out.write(data);
	}
}
