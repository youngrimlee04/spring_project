
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
		// https://developers.naver.com/docs/search/blog/ 네이버 API 적용
		String text = URLEncoder.encode("그린팩토리", "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
        
     // ggoreb site 내용 읽어와서 dev 폴더에 html로 저장
     		URL url = new URL(apiURL);
     		URLConnection con = url.openConnection();
     		// https://developers.naver.com/main/ 여기서 나의 Application 들어가서 client id와 key 확인
     		
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
     			} JSONObject obj = new JSONObject(result.toString()); //JSON으로 된 문자열이 .으로 접근하는 자바스크립트처럼 됨
     			  JSONArray items = obj.getJSONArray("items");
     			  for(int i=0; i<items.length(); i++) {
     				  JSONObject item = items.getJSONObject(i);
     				  String title = item.getString("title");
     				  String bl = item.getString("bloggerlink");
     				  System.out.println(title+"  "+bl);
     			  }
     		}
     		
     	}

