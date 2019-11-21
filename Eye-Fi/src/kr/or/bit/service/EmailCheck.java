package kr.or.bit.service;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;

public class EmailCheck implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String email = request.getParameter("email");
		//System.out.println(email);
		
		//2.이메일 보내기 위한 변수설정
		String host = "smtp.gmail.com"; //smtp 서버
		String subject = "Eye-fi 이메일 인증입니다."; //보내는 제목 설정
		String fromName = "Eye-fi"; //보내는 이름 설정
		String from = "eyefi1920@gmail.com"; //보내는 메일 계정
		String authNum = EmailCheck.authNum(); //인증번호
		String content = "인증번호 [" + authNum + "]"; //메일내용
		
		//3.SMTP 설정 및 메일보내기
		try {
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.setProperty ("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.port","465");
			props.put("mail.smtp.user",from);
			props.put("mail.smtp.auth","true");
			
			Session mailSession = Session.getInstance(props,new javax.mail.Authenticator(){
			    protected PasswordAuthentication getPasswordAuthentication(){
				    return new PasswordAuthentication(from,"rhksflwk11!"); // gmail계정
			    }
			});
			
			Message msg = new MimeMessage(mailSession);
			InternetAddress []address1 = {new InternetAddress(email)};
			msg.setFrom(new InternetAddress
	                      (from, MimeUtility.encodeText(fromName,"utf-8","B")));
			msg.setRecipients(Message.RecipientType.TO, address1); // 받는사람 설정
			msg.setSubject(subject); // 제목설정
			msg.setSentDate(new java.util.Date()); // 보내는 날짜 설정
			msg.setContent(content,"text/html; charset=utf-8"); // 내용설정
			
			Transport.send(msg); // 메일보내기
			
		}catch(Exception e){
			System.out.println("Email : " + e.getMessage());
		}
		
		//4.발생시킨 난수 저장
		request.setAttribute("authNum", authNum);
		
		//5.이동경로 설정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/emailCheck.jsp");
		
		return forward;

	}
	
	//난수발생 함수
	public static String authNum() {
		Random ran = new Random();
		StringBuffer buffer = new StringBuffer();
		int num = 0;
		
		do {
			num = ran.nextInt(75)+48;
			if((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				buffer.append((char)num);
			}else {
				buffer.append(num);
			}
		}while(buffer.length() < 10);
		
		return buffer.toString();
	}

}
