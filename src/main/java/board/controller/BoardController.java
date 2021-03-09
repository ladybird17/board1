package board.controller;

import java.util.List;

//Logback 사용하더라도 slf4j 패키지의 의존성을 활용하여 logger를 사용함
// -> 따라서 다른 로깅시스템을 사용하더라도 쉽게 변경 가능함
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.service.BoardService;

@Controller
public class BoardController {
	/*
	logback 사용하기
	: 기존 자바의 로깅 시스템인 log4j라는 프로젝트가 종료된 후
		출범된 로그를 위한 프로젝트
		
	로그란? 각종 시스템이 작동하는 내역을 남기는 것.
	
	getLogger() 메서드의 매개변수는 로거의 이름.
	getLogger("name"); 으로 생성하면 name이라는 이름을 가진 로거 객체가 생성됨
	일반적으로는 클래스객체를 사용함. 패키지이름+클래스이름으로 구성하여 출력
	*/
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//@Autowired : 객체 자동 생성 어노테이션
	@Autowired
	private BoardService boardService;
	
	//게시판 목록 조회
	//@RequestMapping : 사용자가 접속하는 주소와 Controller의 메서드와 연동시키는 어노테이션
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		/*
		/board/insert, /board/update, /board/delete 등으로 작성해두고
		따로 작업하면됨. 따라서 동시에 협업 개발이 가능하다.
		
		ModelAndView : 사용자 화면과 데이터베이스정보를 가지고있는 클래스
		*/
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		log.debug("openBoardList");
		
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
	
	//지정한 게시글 조회
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
	//게시글 작성 페이지 호출하기
	//스프링부트에서는 사용자 주소 입력을 무조건 controller가 담당하기 때문에 해당 주소와 연동되는 메소드가 반드시 필요함
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		/*해당 메서드는 단순 웹페이지 출력부분이므로
		데이터를 클라이언트에 전송할 필요가 없기 때문에
		웹페이지 이름만 입력하면 스프링부트가 자동으로 
		해당 html 파일을 찾아서 화면에 출력함*/
		return "/board/boardWrite";
	}
	
	//새 게시글 작성 및 등록
	/*
	 insertBoard메서드의 매개변수로 
	 BoardDto 클래스타입의 객체 board를 사용하므로
	 사용자 입력 데이터를 담당하는 boardWrite.html에서
	 input 태그의 name속성을 전부 BoardDto 클래스의 멤버변수명으로 지정해야함
	 */
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board) throws Exception{
		/*
		 해당 메서드 실행시 boardService의 insertBoard 메서드를 실행하여
		 데이터베이스에 접근.
		 
		 insert, update, delete문은 반환값이 없어도 상관없기 때문에
		 sql 쿼리 결과를 받아오는 부분이 없어도 됨
		 */
		boardService.insertBoard(board);
		
		/*
		 redirect를 통해서 지정한 주소로 화면 전환 
		 */
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		
		boardService.updateBoard(board);
		
		//서비스를 이용하여 데이터베이스에 접근해서 수정
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		
		boardService.deleteBoard(boardIdx);
		
		//서비스를 이용하여 데이터베이스에 접근해서 삭제
		return "redirect:/board/openBoardList.do";
	}
}

