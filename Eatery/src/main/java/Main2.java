
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main2 {
	public static void main(String[] args) throws IOException {
		 
         
		// ggoreb site ���� �о�ͼ� dev ������ html�� ����
		URL url = new URL("http://ggoreb.com/hgr");
		URLConnection con = url.openConnection();
		
		InputStream in = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(isr);
		while(true) {
			String data = reader.readLine();
			if(data==null) break;
			System.out.println(data);
			/*int data = in.read();
			if(data ==-1) { // Main���� ���� txt���� ���� �� ������ -1 ����
				break;*/
			}  // System.out.println((char)data); // ���� �� ���� ������ ����
		}
		
	}

