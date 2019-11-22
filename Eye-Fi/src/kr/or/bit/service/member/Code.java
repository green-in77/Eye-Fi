package kr.or.bit.service.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.member.Active;
import kr.or.bit.dto.member.Member;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Code implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDao memberdao = new MemberDao();
		List<Active> codeList = memberdao.code();
		
		JSONArray codeListJson = JSONArray.fromObject(codeList);
		
		request.setAttribute("codeList", codeListJson);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/memberAjaxJsp/code.jsp");
		
		return forward;
	}

}
