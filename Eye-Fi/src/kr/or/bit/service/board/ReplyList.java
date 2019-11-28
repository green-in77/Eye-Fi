package kr.or.bit.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Reply;
import net.sf.json.JSONArray;

public class ReplyList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String seq = request.getParameter("seq");
		
		//2. 데이터 확인
		//System.out.println(seq);
		
		//3. 처리
		BoardDao boarddao = new BoardDao();
		List<Reply> replyList = boarddao.replyList(seq);
		
		//System.out.println(replyList);
		
		JSONArray replyListJson = JSONArray.fromObject(replyList);
		
		request.setAttribute("replyList", replyListJson);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/replyList.jsp");
		return forward;
	}

}
