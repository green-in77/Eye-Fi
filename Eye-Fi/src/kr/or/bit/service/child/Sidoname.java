package kr.or.bit.service.child;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;

import nu.xom.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;


public class Sidoname implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String addr = "http://api.childcare.go.kr/mediate/rest/cpmsapi020/cpmsapi020/request?key=0e8f826ae4df4bc3bcdea31e05c417e2&arname=%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C";
			
			//System.out.println(addr);
				   
			URL url = new URL(addr);
				    
			URLConnection connection = url.openConnection();
			//connection.setDoOutput(true);
				    
			// 타입 설정
			//connection.setRequestProperty("CONTENT-TYPE","text/xml"); 
				    
			//openStream() : URL페이지 정보를 읽어온다.
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
				    
			String inputLine;
			String buffer = "";
			//StringBuffer sb = new StringBuffer();
				    
			// 페이지의 정보를 저장한다.
			while ((inputLine = in.readLine()) != null){
				buffer += inputLine.trim();
				//sb.append(inputLine.trim());
			}
			in.close();
			
			//System.out.println(buffer);
			JSONArray json = (JSONArray) new XMLSerializer().read(buffer);
			request.setAttribute("sigun", json);
			
			//System.out.println(json);
		
		} catch (Exception e) {
			System.out.println("ajax : " + e.getMessage());
		}

		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/childAjaxJsp/sigun.jsp");
		
		return forward;
	}

}
