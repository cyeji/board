# 게시판

- database MySQL 8.0
: database 실행 명령어

```
docker run -d --name mysql-local -p 3306:3306 -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=testdb -e MYSQL_USER=test -e MYSQL_PASSWORD=test1234 mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --default-time-zone=+09:00
```

### 기능명세

- 게시글 등록
- 게시글 수정
- 게시글 삭제
- 게시글 목록 조회
- 게시글 상세 조회
- 댓글 등록
- 댓글 수정
- 댓글 삭제
- 댓글 목록 조회
- 댓글 상세 조회

