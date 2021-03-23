package board.service;

//import java.util.Iterator;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtil;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	//private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		//게시글 상세보기 클릭 시 View Count(조회수) 올림
		boardMapper.updateHitCount(boardIdx);
		
		/*지정한 게시물의 상세 정보 가져오기
		게시물의 상세 정보를 가져오는 시점에는 첨부된 파일에 대한 정보가 없음
		fileList 멤버 변수의 값은 null 임*/
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		//지정한 게시물에 첨부된 파일 리스트 가져오기
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		//가져온 첨부 파일 리스트를 기존의 게시물 정보에 추가하기
		board.setFileList(fileList);
		
		return board;
	}
	
//	@Override
//	public void insertBoard(BoardDto board) throws Exception{
//		boardMapper.insertBoard(board);
//	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception{
		
		log.debug("----- BoardServer의 insertBoard 실행 이전 -----");
		log.debug("출력값 : " + Integer.toString(board.getBoardIdx()));
		
//		기존의 게시물 등록
		boardMapper.insertBoard(board);
		
		log.debug("----- service insertBoard 이후 -----");
		log.debug("출력값 : " + Integer.toString(board.getBoardIdx()));
		
//		FileUtil 클래스를 통해서 생성한 파일 정보 받아오기(서버에 파일 저장 기능 포함)
		List<BoardFileDto> fileList = fileUtil.parseFileInfo(board.getBoardIdx(), uploadFiles);
		
//		데이터 베이스에 업로드된 파일 정보 저장
//		CollectionUtils 클래스는 스프링 프레임워크에서 지원하는 유틸 중 하나임, 여기서는 fileList.isEmpty() 를 사용해도 상관없음
		if (CollectionUtils.isEmpty(fileList) == false) {
			boardMapper.insertBoardFileList(fileList);
		}
		
////		업로드한 파일이 존재하는지 여부 확인
////		ObjectUtils.isEmpty() 는 자바 Object가 없을 경우 true, 존재할 경우 false
//		if (ObjectUtils.isEmpty(files) == false) {
////			업로드 된 파일의 이름 목록을 받아옴
//			Iterator<String> iterator = files.getFileNames();
//			String fileName;
//			
////			받아온 이름 목록에서 다음 것이 존재하는지 확인
//			while (iterator.hasNext()) {
//				fileName = iterator.next(); // 실제 가져옴
//				
////				실제 파일을 가져와서 List 타입에 저장
//				List<MultipartFile> fileList = files.getFiles(fileName);
//				
////				list에 저장된 파일을 하나씩 꺼내어 정보 출력
//				for (MultipartFile file : fileList) {
////					System.out.println("start file infomation");
////					System.out.println("file name : " + file.getOriginalFilename());
////					System.out.println("file size : " + file.getSize());
////					System.out.println("file content type : " + file.getContentType());
////					System.out.println("end file information.\n");
//					log.debug("===== start file infomation =====");
//					log.debug("file name : " + file.getOriginalFilename());
//					log.debug("file size : " + file.getSize());
//					log.debug("file content type : " + file.getContentType());
//					log.debug("===== end file information.=====\n\n");
//				}
//			}
//		}
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception{
		boardMapper.updateBoard(board);
	}
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception{
		boardMapper.deleteBoard(boardIdx);
	}
	
	/*
	마이바티스는 map을 파라미터(매개변수로) 사용하는 기능을 지원함.
	쿼리의 파라미터가 2~3개정도인 경우 이를 위해서 DTO를 새로 생성하기
	애매한 경우가 존재함.
	이럴 떄 @Param어노테이션을 사용하면 해당 파라미터들을
	Map에 저장하여 DTO를 생성하지 않고 여러개의 파라미터 지정 가능
	sql에서 parameterType="map"으로 지정하여 사용 가능
	 */
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception{
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
}
