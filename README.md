#version = 1.70v

[Springboard 1.70v]
1. 기존의 로그인 기능에서 Spring Security를 사용한 로그인, 권한 기능 구현
2. 기존의 tb_user 테이블에서 users, authorities 테이블을 사용하도록 수정
3. CommonController 클래스, commonError.jsp 삭제
4. 최대 업로드 가능 개수 변수 maxUploadCount 빈 추가

[Springboard 1.63v]
1. FileUtiles 클래스 명을 CustomFileUtils로 변경
2. 다운로드 기능 common 패키지에서 board 패키지로 이동
3. 파일 경로 변수 filePath를 운영체제에 따라 동적으로 주입 받도록 수정
4. CustomfileUtiles에 readFileToByteArray 메소드 추가
5. 다운로드 기능 처리를 controller에서 service로 처리하도록 수정

[Springboard 1.62v]
1. resultMap, association, collection 사용하여 SQL 수정
2. DAO 객체를 인터페이스 클래스와 구현 클래스로 분리
3. DAO 메소드 명, Mapper SQL id명을 좀 더 직관적으로 변경

[Springboard 1.61v]
1. Mapper 클래스를 사용하여 DAO 클래스에 사용
2. AbstractDAO 클래스 삭제
3. AbstractDAO에서 페이징, 전자정부 관련 코드 BoardService로 이동

[Springboard 1.60v]
1. 서버에서도 @Valid를 사용하여 검증하도록 수정 
2. commonVO를 상속하여 VO 객체에서 중복되는 코드 제거

[Springboard 1.59v]
1. BoardVO, FileVO 객체를 사용하도록 수정
2. jsp 경로 sample을 board로 수정
3. jsp 변수명 수정

[Springboard 1.58v]
1. sample mapper를 board, comment로 분리 및 수정
2. CommentVO 추가
3. Comment 기능에 VO 객체를 사용하도록 수정

[Springboard 1.57v]
1. sample을 board로 수정
2. login 기능을 스프링 security를 사용하여 구축하도록 수정 중
3. throws Exception 제거
4. 생성자를 통한 주입으로 변경

[Springboard 1.56v]
1. web.xml 설정 수정
2. comment 코드 분리

[Springboard 1.55v]
1. FileUtils.java의 stored_file_name 확장자명 제거

[Springboard 1.54v]
1. common.js 수정 및 idxInit() 함수 삭제
2. view 코드 리펙토링

[Springboard 1.53v]
1. pom.xml 수정

[Springboard 1.52v]
1. common.js 페이징 오류 수정

[Springboard 1.51v]
1. 게시글 상세보기 방식을 GET으로 변경

[Springboard 1.5v]
1. 댓글 좋아요, 싫어요 기능 추가
2. 댓글창 UI 변경

[Springboard 1.4v]
1. 게시글, 댓글 삭제시 확인창을 띄우도록 변경
2. 제목, 게시글, 댓글의 길이가 길면 경고창을 띄우도록 함
3. 게시글 내용 작성시 현재 글자수를 우하단에 보이도록 함
4. 이벤트 메서드 bind를 on으로 변경

[Springboard 1.3v]
1. 기존 로그인 페이지를 없에고 게시판에서 로그인할 수 있도록 변경
2. 로그인을 하지 않아도 게시글, 댓글을 읽을 수 있도록 변경
3. 게시글 수정 시에도 조회수 오르는 문제 해결 

[Springboard 1.2v]
1. 첨부파일 다운로드 후 수정, 삭제, 이전글, 다음글 클릭시 IDX 값이 중복 전송되는 문제 해결
2. 일부 변수명 변경
3. common.js에 함수 idxInit() 추가

[Springboard 1.1v]
1. 전자전부 페이징 페이지 추가

[Springboard 1.0v]
1. 최초 배포버전
