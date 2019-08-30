
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main3 {
	public static void main(String[] args) throws IOException { 
		// https://developers.naver.com/docs/search/blog/ ���̹� API ����
		String text = URLEncoder.encode("�׸����丮", "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json ���
        
     // ggoreb site ���� �о�ͼ� dev ������ html�� ����
     		URL url = new URL(apiURL);
     		URLConnection con = url.openConnection();
     		// https://developers.naver.com/main/ ���⼭ ���� Application ���� client id�� key Ȯ��
     		
     		con.addRequestProperty("X-Naver-Client-Id", "RCyf74b_HIvSHvumZVgg"); // client-Id
     		con.addRequestProperty("X-Naver-Client-Secret", "Oc0UjC04kI"); // Client secret
     		
     		InputStream in = con.getInputStream();
     		InputStreamReader isr = new InputStreamReader(in,"utf-8");
     		BufferedReader reader = new BufferedReader(isr);
     		StringBuffer result = new StringBuffer();
     		
     		while(true) {
     			String data = reader.readLine();
     			if(data==null) break;
     			result.append(data);
     			} JSONObject obj = new JSONObject(result.toString()); //JSON���� �� ���ڿ��� .���� �����ϴ� �ڹٽ�ũ��Ʈó�� ��
     			  JSONArray items = obj.getJSONArray("items");
     			  for(int i=0; i<items.length(); i++) {
     				  JSONObject item = items.getJSONObject(i);
     				  String title = item.getString("title");
     				  String bl = item.getString("bloggerlink");
     				  System.out.println(title+"  "+bl);
     			  }
     		}
     		
     	}

