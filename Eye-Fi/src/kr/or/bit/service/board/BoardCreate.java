package kr.or.bit.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board_List;
import kr.or.bit.dto.board.Board_Type;
import kr.or.bit.dto.board.Category;

//게시판 생성 화면
public class BoardCreate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//게시판타입,카테고리 조회
		BoardDao boarddao = new BoardDao();
		List<Board_Type> typeList = boarddao.btypeSel();
		List<Category> cateList = boarddao.cateSel();
		List<Board_List> boardList = boarddao.listSel();
		
		//값저장
		request.setAttribute("boardList", boardList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("cateList", cateList);
		
		//화면이동
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/boardCreate.jsp");
		return forward;
	}

}
