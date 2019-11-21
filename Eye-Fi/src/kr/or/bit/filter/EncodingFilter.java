package kr.or.bit.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter(//모든 페이지 한글처리
		urlPatterns = { "/*" }, 
		initParams = {  // 실행 시 가장 먼저 실행됨.
				@WebInitParam(name = "encoding", value = "UTF-8")
		})
public class EncodingFilter implements Filter {
	private String encoding; //변수 생성
	
    public EncodingFilter() {} //기본생성자

	public void init(FilterConfig fConfig) throws ServletException {
		//key : value 형식으로 UTF-8을 변수에 저장
		this.encoding = fConfig.getInitParameter("encoding"); //key => value : UTF-8
	}
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(this.encoding);
			//한글처리가 비어있다면 UTF-8로 설정
		}
		chain.doFilter(request, response);
	}
	
	public void destroy() {}

}
