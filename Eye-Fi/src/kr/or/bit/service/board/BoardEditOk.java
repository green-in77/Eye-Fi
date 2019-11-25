package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Reboard;

public class BoardEditOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String seq = request.getParameter("seq");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String classify = request.getParameter("classify");
		String notice = request.getParameter("notice");
		String bcode = request.getParameter("bcode");
		
		//2. 데이터 확인
		//System.out.println(seq + " / " + subject +"/"+ content +"/"+ classify +"/"+ notice +"/ bcode: "+ bcode);
		
		if(notice == null) {
			notice = "false";
		}
		
		//3. 처리
		BoardDao boarddao = new BoardDao();
		int btype = boarddao.btypeSel(Integer.parseInt(bcode));
		int result = 0;
		
		String msg = "";
		String url = "";
		
		//수정
		Board board = new Board();
		board.setSeq(Integer.parseInt(seq));
		board.setSubject(subject);
		board.setContent(content);
		board.setClassify(classify);
		board.setNotice(notice);
		board.setBcode(Integer.parseInt(bcode));
		//System.out.println(board);

		result = boarddao.noticeEditOk(board);

		if(result > 0 ) {
			msg = "글이 수정되었습니다.";
			url = "boardList.bdo?bcode="+bcode;
		}else {		
			msg = "글수정에 실패하였습니다. 다시 작성 부탁드립니다.";
			url = "boardEdit.bdo?seq="+seq+"&bcode="+bcode;
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		
		return forward;
	}

}
