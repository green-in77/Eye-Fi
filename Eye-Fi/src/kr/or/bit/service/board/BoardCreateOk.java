package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board_List;
import kr.or.bit.dto.board.Category;

//게시판 생성 화면
public class BoardCreateOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//데이터 받기
		String bname = request.getParameter("bname");
		String btype = request.getParameter("btype");
		String ccode = request.getParameter("ccode");
		String cname = request.getParameter("cname");
		
		//System.out.println("bname="+bname+" btype="+btype+" ccode="+ccode + " cname="+cname);
		
		Board_List boardlist = new Board_List();
		boardlist.setBname(bname);
		boardlist.setBtype(Integer.parseInt(btype));
		boardlist.setCcode(Integer.parseInt(ccode));
		
		Category cate = new Category();
		cate.setCcode(Integer.parseInt(ccode));
		cate.setCname(cname);
		
		BoardDao boarddao = new BoardDao();
		int result = boarddao.boardCreate(boardlist,cate);
		
		String msg = "";
		String url = "boardCreate.bdo";
		
		if(result > 0 ) {
			msg = "게시판이 생성되었습니다.";
		}else {
			msg = "게시판이 생성에 실패했습니다. 다시확인바랍니다.";
		}
		//값저장
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		//화면이동
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		return forward;
	}

}
