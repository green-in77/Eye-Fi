package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Reboard;

public class RewriteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String bcode = request.getParameter("bcode");
		String seq = request.getParameter("seq");
		String classify = request.getParameter("classify");
		String btype = request.getParameter("btype");
		String arcode = request.getParameter("arcode");
		
		
		//2. 데이터 확인
		//System.out.println(userid +"/"+ subject +"/"+ content +"/ bcode: "+ bcode + "/"+seq + "/" + classify);
		
		//3. 처리
		Reboard reboard = new Reboard();
		reboard.setUserid(userid);
		reboard.setSubject(subject);
		reboard.setContent(content);
		reboard.setClassify(classify);
		reboard.setBcode(Integer.parseInt(bcode));
		reboard.setPseq(Integer.parseInt(seq));
		reboard.setNotice("false");
		//System.out.println(reboard);
			
		BoardDao boarddao = new BoardDao();
		int result = boarddao.reWrite(reboard);
		
		String msg = "";
		String url = "";
		
		if(result > 0 ) {
			msg = "답글이 등록되었습니다.";
			url = "boardList.bdo?bcode="+bcode+"&btype="+btype+"&arcode="+arcode;
		}else {		
			msg = "글쓰기가 실패하였습니다. 다시 작성 부탁드립니다.";
			url = "boardWrite.bdo";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		
		return forward;
	}

}
