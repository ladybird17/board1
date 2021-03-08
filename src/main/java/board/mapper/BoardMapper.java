package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.BoardDto;

//DAO 로 동작함 (mybatis가 지원함)
@Mapper
public interface BoardMapper {
	//게시물 목록 불러오기
	List<BoardDto> selectBoardList() throws Exception;
	
	//지정한 게시물의 전체내용 불러오기
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	
	//지정한 게시물의 hit Count 올리기
	void updateHitCount(int boardIdx) throws Exception;
}
