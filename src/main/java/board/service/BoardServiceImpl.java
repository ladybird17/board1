package board.service;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		boardMapper.updateHitCount(boardIdx); //조회수
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		return board;
	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest files) throws Exception{
		//boardMapper.insertBoard(board);
		
		//업로드한 파일이 존재하는지 여부 확인
		if(ObjectUtils.isEmpty(files)==false) {
			//업로드한 파일의 이름 목록을 받아옴
			Iterator<String> iterator = files.getFileNames();
			String fileName;
			
			//받아온 이름 목록에서 다음 것이 존재하는지 확인함
			while(iterator.hasNext()) {
				fileName = iterator.next();
				
				//실제 파일을 가져와서 List 타입에 저장함
				List<MultipartFile> fileList = files.getFiles(fileName);
				
				//list에 저장된 파일을 하나씩 꺼내어 정보 출력
				for(MultipartFile file : fileList) {
					log.debug("===start file information===");
					log.debug("file name : "+file.getOriginalFilename());
					log.debug("file size : "+file.getSize());
					log.debug("file content type : "+file.getContentType());
					log.debug("===end file information.===\n");
				}
			}
		}
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception{
		boardMapper.updateBoard(board);
	}
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception{
		boardMapper.deleteBoard(boardIdx);
	}
}
