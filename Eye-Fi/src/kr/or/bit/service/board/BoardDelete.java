package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;

public class BoardDelete implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String seq = request.getParameter("seq");
		String bcode = request.getParameter("bcode");
		
		//2. 데이터 확인
		//System.out.println(seq + " / " + bcode);
		
		BoardDao boarddao = new BoardDao();
		int result = boarddao.boardDeleteOk(seq);
		
		String msg = "";
		String url = "boardList.bdo?bcode="+bcode;
		
		if(result > 0 ) {
			msg = "글이 삭제되었습니다.";
		}else {		
			msg = "삭제를 실패하였습니다. 다시 확인 부탁드립니다.";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		return forward;
	}

}
