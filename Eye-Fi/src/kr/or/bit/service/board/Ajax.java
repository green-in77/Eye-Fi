package kr.or.bit.service.board;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import com.sun.org.apache.xerces.internal.util.XML11Char;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import net.sf.json.JSONObject;


public class Ajax implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
	        
		String arcode = request.getParameter("arcode");
		 
		String addr = "http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request?";
		String serviceKey = "key=3cf25a2b59744f50a35d241f64ec6248";
		String parameter = "";
		 
		//String datalist="";
	 
		StringBuilder datalist = new StringBuilder();
		
		parameter = parameter + "&" + "arcode=11260";
		addr += serviceKey + parameter;
	    System.out.println("addr :" + addr);  
		//datalist.append("[");
		try {
			
			
			PrintWriter out = response.getWriter();
			URL url = new URL(addr);
		           
			InputStream in = url.openStream(); 
			CachedOutputStream bos = new CachedOutputStream();
			IOUtils.copy(in, bos);
			in.close();
			bos.close();
		           
			String data = bos.getOut().toString();
		           
			 //datalist.append(data+",");
		     //datalist.append("]");
		        
		      /*
		       * JSONArray json = new JSONArray(); json.put("data", datalist);
		      */
			//JSONArray json = new JSONArray(data.toString());
			
			//JSONObject json = JSONObject.fromObject(data);
			//out.print(json);

			System.out.println("data : " + data);
			request.setAttribute("json", data);
			
	
		} catch (Exception e) {
			System.out.println("ajax : " + e.getMessage());
		}
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/ajax.jsp");
		
		return forward;
	}

}
