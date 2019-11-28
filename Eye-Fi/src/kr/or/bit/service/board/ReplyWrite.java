package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Reply;

public class ReplyWrite implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String reuserid = request.getParameter("reuserid");
		String seq = request.getParameter("seq");
		String rcontent = request.getParameter("rcontent");
		
		//2.데이터 확인
		//System.out.println(reuserid + " / " + seq + " / " + rcontent);
		
		//3. 처리
		Reply reply = new Reply();
		reply.setSeq(Integer.parseInt(seq));
		reply.setRcontent(rcontent);
		reply.setReuserid(reuserid);
		
		BoardDao boarddao = new BoardDao();
		int result = boarddao.replyWrite(reply);
		
		request.setAttribute("result", result);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/reply.jsp");
		return forward;
	}
	

}
