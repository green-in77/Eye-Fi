package kr.or.bit.service.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.member.Member;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//관리자 회원수정 : 비동기
public class MemberSelectId implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		
		//2. 데이터 확인
		//System.out.println(userid);
		
		//3. 처리
		MemberDao memberdao = new MemberDao();
		Member member = memberdao.select(userid);
		
		JSONObject memberJson = JSONObject.fromObject(member);
		
		//4. 저장
		request.setAttribute("member", memberJson);
		
		//5. 이동경로설정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/memberAjaxJsp/memberSelectId.jsp");
		return forward;
	}

}
