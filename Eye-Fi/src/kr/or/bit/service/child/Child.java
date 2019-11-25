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


public class Child implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String addr = "http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request?key=3cf25a2b59744f50a35d241f64ec6248&arcode=";
			String arcode = request.getParameter("arcode");
			
			String stcode = request.getParameter("stcode");
			
			if(stcode == null) {
				stcode = "";
			}
			
			String parameter = "&stcode="+stcode;
						
			addr += arcode+parameter;
			
			// String urlStr = "접속할 url";
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
			JSONArray json = null;
			JSONObject jsonobj = null;
			
			if(stcode == "") {
				json = (JSONArray) new XMLSerializer().read(buffer);
				request.setAttribute("child", json);
			}else {
				jsonobj = (JSONObject) new XMLSerializer().read(buffer);
				request.setAttribute("child", jsonobj);
			}
			
			//System.out.println(json);
		
		} catch (Exception e) {
			System.out.println("ajax : " + e.getMessage());
		}

		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/child.jsp");
		
		return forward;
	}

}
