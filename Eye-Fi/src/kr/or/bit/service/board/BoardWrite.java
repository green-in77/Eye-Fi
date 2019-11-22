package kr.or.bit.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board_List;

public class BoardWrite implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String bcode = request.getParameter("bcode");

		//2. 데이터 확인
		//System.out.println(bcode);
		
		
		//3.처리
		BoardDao boarddao = new BoardDao();
		List<Board_List> boardList = boarddao.listSel();
		
		request.setAttribute("bcode", bcode);
		request.setAttribute("boardList", boardList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/boardWrite.jsp");
		
		return forward;
	}

}
