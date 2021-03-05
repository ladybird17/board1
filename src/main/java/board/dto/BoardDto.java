package board.dto;

//lombok.jar파일 구글 검색후 다운받아 설치했음
import lombok.Data;

//lombok 라이브러리를 사용하여 기본적으로 필요한 getter, setter를 자동으로 생성
@Data
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String createdDatetime;
	private String createdId;
	private	String updatedDatetime;
	private String updaterId;
	private String deletedYn;

}
