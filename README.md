# 요구사항(~ 2주차)
> 참고 : [블로그 만들기](https://teamsparta.notion.site/Spring-Lv-3-61227372735242bb903ea8c575e66a3f) </br> <br>

# ERD
<img src = "https://velog.velcdn.com/images/strangehoon/post/9334c02c-d005-45d0-a1de-9ac233f40063/image.png" height = "350px" width = "550px" allign = "left">


* Null or Not Null ?? </br>

  -> 모든 컬럼들이 다 필요해 보이지만 확장성 있게 설계하기 위해 대체로 널 값을 허용했다.

# 질문
**1. 처음 설계한 API 명세서에 변경 사항이 있었나요?
  변경되었다면 어떤 점 때문일까요? 첫 설계의 중요성에 대해 작성해 주세요!**
* 처음 설계한 API 명세서와 비교할 때 변경 사항은 없었다. 처음 설계할 때 신경을 많이 썼기 때문이다. </br> </br>



**2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?**

* 솔직히 테이블이 3개 밖에 없어서 도움이 많이 되지 않았다. 하지만 테이블이 많아지면 그에 따른 연관관계를 고려하기 복잡해지기 마련인데 ERD를 먼저 설계하면 한눈에 연관관계를 볼 수 있어서 개발 편의성을 크게 향상시켜줄 것이다. </br> </br>

**3. JWT를 사용하여 인증/인가를 구현했을 때의 장점은 무엇일까요?**

* 서버에 별도의 저장소가 불필요하므로 서버 자원을 절약할 수 있다
(쿠키/세션 방식은 서버에 session storage가 필요)
* 쿠키를 전달하지 않아도 되므로 쿠키를 사용함으로써 발생하는 취약점이 사라진다.
* 트래픽에 대한 부담이 적다. </br> </br>


**4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?**

* 토큰의 페이로드(PayLoad)에 3종류의 클레임을 저장하기 때문에, 정보가 많아질수록 토큰의 길이가 늘어나 네트워크에 부담을 줄 수 있다.
* secret key 유출시 JWT 조작이 가능하다. </br> </br>


**5. 댓글이 달려있는 게시글을 삭제하려고 할 때 무슨 문제가 발생할까요? JPA가 아닌 Database 테이블 관점에서 해결 방법이 무엇일까요?**
* 게시글과 댓글 테이블은 1대n 관계를 맺고 있다. 여기서 만약 게시글을 삭제하면 참조 무결성 제약조건을 위반하게 된다. 쉽게 말하면 댓글 테이블의 게시글을 참조하는 외래키가 가리키는 값이 없어진다. RDBMS는 기본적으로 제약조건을 위반하지 않도록 설계되어 있으므로 삭제를 시도하면 에러가 터진다.
* RDBMS 관점에서 해결책은 제약조건을 수정해주면 된다. 제약 조건에는 ON DELETE NO ACTION(default), ON DELETE CASCADE, ON DELETE SET NULL, ON DELETE SET DEFAULT가 있으며 이 중에서 ON DELETE CASCADE를 지정해주면 삭제할 수 있다. </br> </br>

**6. 5번과 같은 문제가 발생했을 때 JPA에서는 어떻게 해결할 수 있을까요?**
* JPA의 영속성 전이를 이용한다. cascade = CascadeType.REMOVE를 추가하면 게시물을 삭제할 시 관련있는 댓글도 같이 삭제할 수 있다. </br> </br>



**7. IoC / DI에 대해 간략하게 설명해 주세요!**  
* IOC(제어의 역전) : 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IOC)이라 한다. DI는 외부에서 주입해주므로 IOC이다.
* DI : 스프링이 다른 프레임워크와 차별화되어 제공하는 의존 관계 주입 기능으로, 객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 것을 뜻한다.</br> </br>


# 기타

### API 명세서
* Swagger를 사용해 API 문서를 자동화 했습니다.   </br> <br>

### DTO 관리
* 정영호 매니저님께서 이전 세션에서 DTO 관리하는 방법을 공유해주셨는데 이를 적용했습니다. 이번 과제를 진행하면서 DTO 관리법에 대해 공부할 수 있었고 이를 개인 블로그에 정리했습니다.
> [DTO 관리](https://velog.io/@strangehoon/%EA%B9%94%EA%B8%88%ED%95%98%EA%B2%8C-DTO-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0)

