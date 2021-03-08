package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.service.BoardService;

@Controller
public class BoardController {
	//@Autowired : 객체 자동 생성 어노테이션
	@Autowired
	private BoardService boardService;
	
	//@RequestMapping : 사용자가 접속하는 주소와 Controller의 메서드와 연동시키는 어노테이션
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		/*
		/board/insert, /board/update, /board/delete 등으로 작성해두고
		따로 작업하면됨. 따라서 동시에 협업 개발이 가능하다.
		
		ModelAndView : 사용자 화면과 데이터베이스정보를 가지고있는 클래스
		*/
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		/*
		List : 배열과 달리 자동크기조절가능함! 타입은 BoardDto
		boardService는 selectBoardList 리스트가 있는 인터페이스.
		인터페이스 자체로는 사용이 불가능하기 때문에
		인터페이스를 상속받는 자식클래스인 BoardServiceImpl을 사용해야하는데,
		위의 @Autowired가 객체생성할 때
		BoardServiceImpl의 @Service 어노테이션을 통해 자동연결?생성?해줌
		 */
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("data",list);
		
		return mv;
	}
	
	/*
	@RequestParam : 사용자가 입력한 파라미터값을 받아오기. jsp의 getParameter()와 동일함
	
	 */
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board",board);
		
		return mv;
	}
}
