# 익명 의견 교환 웹 페이지 프로젝트 미션
- 익명으로 소통할 수 있는 웹 페이지를 제작했다
- 게시글과 댓글을 작성할 때 `password`를 받아 추후 수정 및 삭제가 가능하게 구현하였다
- (+)게시글에는 말머리를 선택할 수 있게 하고 각 말머리는 게시판별로 설정하였다

## 1. 게시판
- 자유 게시판, 개발 게시판, 일상 게시판, 사건 사고 게시판이 있음
- 각 게시판별 Article을 확인할 수 있어야 함

### `Board`
- `category`로 게시판 이름 변수 설정
- `description`으로 게시판 소개 변수 설정
- @Entity @OneToMany -> ArticleList(게시글)
- @OneToMany -> postTypeList(말머리)
- `@Getter`와 `@Setter` 어노테이션으로 코드 간결화 진행 
> ✏ 이후 data.sql 에서 INSERT로 게시판 이름과 해당 게시판의 소개글 데이터를 입력하였다  
> ✏ 소개글 삽입에 대한 고민 이후 생각한 방법으로 만족스러우나 보안 관련한 보완은 필요할 것으로 판단

---
### `BoardRepository`
- JPA를 활용하여 기본적인 CRUD가 가능하게 작성


### `BoardServie`
- Service Bean 등록 클래스
- `BoardRepository` 생성자 주입
- `readBoard`와 `readAllBoard`로 Board와 BoardList 반환 메서드 작성

### `BoardController`
- `BoardService`와 `ArticleService` 생성자 주입
- Home 화면에서의 `Board List`의 확인을 위해 `readAll` 작성
- `BoardService`의 readAllBoard를 Model을 사용하여 "board"에 넣고 home으로 return
- 각 Board 별 Article 확인을 위해 `/board/read/{id}` 엔드포인트에 boardView 작성
- 뷰 템플릿에 Board에 속한 Article도 확인해야 하기 때문에 readArticlesByBoardId을 model로 전달

## 2. 게시글
- 글 작성시 게시하고 싶은 게시판 선택 가능
- password를 가지고 작성하여 수정, 삭제지 입력하였던 password가 일치해야 수행 가능 

### `Article`
- id, title, contents, username, password의 필드를 가짐
- id는 PK로 지정, `GenerationType.IDENTITY`를 사용하여 id 겸 글 번호 개념 주입
- @Entity @ManyToOne -> Board, @OneToMany -> commentList(댓글)
- 추가 - @ManyToOne -> postType (말머리 지정)


### `ArticleRepository` 
- 기능 사용을 위한 레포지토리 인터페이스 작성 (상동)

### `ArticleService`
- article, board, postType Repository 생성자 주입
- 글 작성 메서드 `write` 작성(postId, boardId를 포함)
- `IllegalArgumentException`으로 예외 처리 작성
- 전체 게시글의 수를 view에서 보여주고 싶어 추가 로직 작성
```Java
    public Long getTotalArticleCount() {
        return articleRepository.count();
        }
```
- 이후 `th:text`로 뷰에서 확인 구현

### `ArticleController`
> ✏ 엔드 포인트와 매핑의 경로 설정 설계에 다소 어려움을 겪었다  
> html에서 제대로 된 값을 작성하지 못한 경우가 대부분이었다
- RESTful한 URL을 사용하여 GET과 POST 요청을 각각의 메서드가 처리하게끔 작성하였다
- home 화면에서 전체 글을 볼 수 있도록 readAll을 추가하였다


## 3. 댓글
- 게시글 확인하는 화면에서 해당 게시글에 속한 댓글들의 확인과 작성이 가능
- password를 가지고 작성하여 수정, 삭제지 입력하였던 password가 일치해야 수행 가능 

### `Comment`
- content와 password 필드를 가짐
- @Entity @ManyToOne -> article

### `CommentRepository`
- 기능 사용을 위한 레포지토리 인터페이스 작성 (상동)
- 각 게시글의 속해 있는 comment를 조회해야 하기 때문에 findByArticleId List 추가

### `CommentService`
- article과 comment 생성자 주입
> ✏ article과 마찬가지로 예외 처리에 대한 부분이 다소 미흡   
> 보안 관련한 부분에도 추가 및 보완이 필요해 보임

### `CommentController`
- templates/article/comment 디렉토리를 생성하여 댓글의 수정, 삭제 구현
> ✏ 게시글 확인 페이지에서 댓글의 수정과 삭제를 모두 구현하고 싶었으나 뷰를 추가하여 구현하였다  
> 추후 수정하고 싶은 부분
---


### Feature test
- 🚧 프로젝트 완성 후 작성 예정 🚧


---

### ***Additional comment..***

- `DTO`: Entity를 직접 반환하기 보다는 DTO와 함께 분리하여 사용하고 싶어 DTO 메서드를 작성하였으나,
  html thymeleaf 작성이 매우 복잡하게 다가와 결국 Entity만 사용한 셈이 되었다    
  이 부분이 트래픽에도 어느정도 영향을 미치는 것으로 알고 있는데 공부하여 추후에는 잘 분리하여 사용할 수 있도록 익숙해져야겠다


- `thymeleaf`: 프로젝트 제작 초반 기능 구현 확인 단계에서 html thymeleaf 경로 작성이 매우 헷갈려
  수많은 에러창을 보게 되었고,
  뭐가 잘못되었는지 파악하기도 어려운 상황 속에서 많은 삽질(..)을 하게 되었다  
  기능들에 대한 제대로된 정보 없이 작성하려니 정말 오랜 시간이 걸렸다  
  teat code의 중요성을 느끼게 되었고 복잡해 보여서 들여다 볼 엄두를 안 냈지만 추후에는 잘 확인할 수 있도록 성장해야 함을 느꼈다


- `예외 처리와 보안`: password 검증 로직을 제작하면서 예외 처리 작성이 다소 낯설게 느껴졌다   
  얼마나, 어느정도의 범위를 확인해야 하는지, 이렇게 작성하는 것이 맞는지 의문점이 계속 생겨나던 부분  
  생각 없이 제작하다 보안 관련한 로직 추가에 대한 고민을 아주 잠시나마 하게 해 주었다  
  추후 공부하여 보안상의 이슈가 없는 프로젝트로 구현하고 싶다


- `하드 코딩`: 기본적인 기능 구현 이후, 추가 기능을 제작 시도(..) 해 보기 전 board별 선택할 수 있는 간단한 말머리를 추가하고 싶었다  
  처음에는 article 내부에서 변수를 정의했다가, board에서 정의했다가 롤백하는 등 많은 시행착오를 겪고 고민하는 시간을 가지게 되었다  
  결국 깔끔하게 entity를 추가하여 board의 ID값을 기준으로 말머리를 나누어 데이터베이스에 입력하였다  
  ManyToMany를 사용해 보고 싶었으나, 중복 말머리를 사용하고 싶지 않아 OneToMany로 추가  
  이후 구현했던 로직을 수정하며 thymeleaf도 복습한 셈이 되었다  
  테이블을 구성하고 클래스를 작성할 때에 어떤 식으로 접근해야 하는지 조금은 체감했던 부분, *하드 코딩은 나쁘다..*

![PostType](posttype.jpg)


- `getTotalArticleCount`: JpaRepository에서 count 기능이 보이길래 시험 삼아 작성해 보았는데 한 번에 구현되어 재미있고 신기했던 부분  
  ORM 프레임워크의 편리함을 체감하였고 많이 사용해 보며 익숙해져야겠다고 생각하였다


- `HTML 작성`: 위에 언급했던 것처럼 testcode와 디버깅 확인에 익숙하지 않아 지속 뷰를 확인하며 진행하다 보니 미감적인 부분이 매우 거슬려 css를 추가하게 되었다  
  시간이 매우 오래 걸리는 부분임을 느꼈다  
  이 부분 또한 오래 걸렸지만 기본적인 html/css 복습을 하게 되었다











