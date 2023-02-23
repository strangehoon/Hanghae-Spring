package fourth.dto.basic;

public class ResponseMessage {
    //일반
    public static final String SUCCESS = "성공";

    // 회원 가입 and 로그인
    public static final String TOKEN_ERROR = "토큰 에러";
    public static final String CREATED_USER_DUPLICATE_ERROR = "회원 중복 에러";
    public static final String NOT_FOUND_USER = "회원 조회 에러";
    public static final String NO_ATUTHENTIFICATION_ERROR = "권한 에러";

    // 좋아요
    public static final String LIKE_SUCCESS = "좋아요";
    public static final String LIKE_CANCEL = "좋아요 취소";


    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";



}