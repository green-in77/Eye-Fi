package kr.or.bit.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board_List;

public class NoticeboardList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//게시판 리스트 가져오기
		BoardDao boarddao = new BoardDao();
		List<Board_List> boardList = boarddao.listSel();
		
		request.setAttribute("boardList", boardList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/noticeboardList.jsp");

		return forward;
	}

}
