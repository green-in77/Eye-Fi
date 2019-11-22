package kr.or.bit.service.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.member.Member;
import net.sf.json.JSONArray;

public class MemberListOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String cp = request.getParameter("cp");
		
		if(cp == null || cp.trim().equals("") || cp.equals("null")){
			//default 값 설정
			cp = "1";
		}
		
		MemberDao memberdao = new MemberDao();
		List<Member> memberList = memberdao.selectAll(Integer.parseInt(cp));
		
		JSONArray memberListJson = JSONArray.fromObject(memberList);
		
		request.setAttribute("memberList", memberListJson);
				
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/memberAjaxJsp/memberList.jsp");
		
		return forward;
	}

}
