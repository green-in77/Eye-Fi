package kr.or.bit.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Reboard;
import net.sf.json.JSONArray;

public class BoardListOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String bcode = request.getParameter("bcode");
		String cp = request.getParameter("cp");
		
		if(cp == null || cp.trim().equals("") || cp.equals("null")){
			//default 값 설정
			cp = "1";
		}
		
		//2. 데이터 확인
		//System.out.println("bcode : " + bcode + "cp : " + cp);
		
		BoardDao boarddao = new BoardDao();
		int btype = boarddao.btypeSel(Integer.parseInt(bcode));
		JSONArray boardlistJson = null;
		
		if( btype == 1 ) {
			List<Board> boardlist = boarddao.noticeboardList(Integer.parseInt(cp), Integer.parseInt(bcode));
			boardlistJson = JSONArray.fromObject(boardlist);
		} else if( btype == 2) {
			List<Reboard> boardlist = boarddao.boardList(Integer.parseInt(cp), Integer.parseInt(bcode));
			//System.out.println(boardlist);
			boardlistJson = JSONArray.fromObject(boardlist);	
		}
		
		request.setAttribute("boardlist", boardlistJson);
		request.setAttribute("cp", cp);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/noticeboardList.jsp");
		
		return forward;
	}

}
