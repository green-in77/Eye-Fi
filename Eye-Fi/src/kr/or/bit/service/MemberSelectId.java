package kr.or.bit.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.Member;
import net.sf.json.JSONArray;

public class MemberSelectId implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		
		//2. 데이터 확인
		//System.out.println(userid);
		
		//3. 처리
		MemberDao memberdao = new MemberDao();
		List<Member> memberList = memberdao.memberSelectId(userid);
		
		JSONArray memberListJson = JSONArray.fromObject(memberList);
		
		//4. 저장
		request.setAttribute("memberlist", memberListJson);
		
		//5. 이동경로설정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/memberAjaxJsp/memberSelectId.jsp");
		return forward;
	}

}
