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
		String arcode = request.getParameter("arcode");
		String bcode = request.getParameter("bcode");
		String cp = request.getParameter("cp");
		String btype = request.getParameter("btype");		
		
		//2. 데이터 확인
		//System.out.println(bcode + " / " + cp + " / " + btype + " / " + arcode);
			
		//3.처리
		BoardDao boarddao = new BoardDao();
		List<Board_List> boardList = boarddao.listSel();
		
		request.setAttribute("arcode", arcode);
		request.setAttribute("btype", btype);
		request.setAttribute("bcode", bcode);
		request.setAttribute("cp", cp);
		request.setAttribute("boardList", boardList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/boardWrite.jsp");
		
		return forward;
	}

}
