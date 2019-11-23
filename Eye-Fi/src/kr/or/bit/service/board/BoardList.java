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

public class BoardList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1.데이터 받기
		String cp = request.getParameter("cp");
		String bcode = request.getParameter("bcode");
				
		//System.out.println("cp : " + cp + " / bcode : " + bcode);
		
		//게시판 리스트 가져오기
		BoardDao boarddao = new BoardDao();
		List<Board_List> boardList = boarddao.listSel();
		List<Board_Type> typeList = boarddao.btypeSel();
		List<Category> cateList = boarddao.cateSel();
		
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("cateList", cateList);
		
		request.setAttribute("cp", cp);
		request.setAttribute("bcode", bcode);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/boardList.jsp");

		return forward;
	}

}
