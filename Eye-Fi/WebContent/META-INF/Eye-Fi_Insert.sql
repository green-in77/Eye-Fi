--활성화코드 (Active table)
insert into ACTIVE values (1,'활동');
insert into ACTIVE values (2,'정지');
insert into ACTIVE values (3,'탈퇴');
commit;

--관리자 생성(Member table)
insert into member (userid, userpw, email, admin, code) values('admin', 1004, 'eyefi1920@gmail.com',1,1);
commit;

--일반회원 생성(Member table) 이메일 인증없이 가입불가하기 때문에 회원관리 테스트 차원에서 회원생성
insert into member (userid, userpw, email, admin, code) values('nunu', 1004, 'eyefi1920@gmail.com',0,1);
insert into member (userid, userpw, email, admin, code) values('nene', 1004, 'eyefi1920@gmail.com',0,1);
insert into member (userid, userpw, email, admin, code) values('papa', 1004, 'eyefi1920@gmail.com',0,1);
insert into member (userid, userpw, email, admin, code) values('baby', 1004, 'eyefi1920@gmail.com',0,1);
insert into member (userid, userpw, email, admin, code) values('vuvu', 1004, 'eyefi1920@gmail.com',0,1);
insert into member (userid, userpw, email, admin, code) values('kiki', 1004, 'eyefi1920@gmail.com',0,1);
insert into member (userid, userpw, email, admin, code) values('gugu', 1004, 'eyefi1920@gmail.com',0,1);
commit;

--게시판 타입 (Board_type)
insert into board_type(btype, btype_name) values(1, '공지사항');
insert into board_type(btype, btype_name) values(2, '게시판');
insert into board_type(btype, btype_name) values(3, '앨범게시판');
commit;

--카테고리 (Category table) 전체 카테고리 기본생성(사용자 추가불가)
insert into category(ccode, cname) values(0, '전체');
commit;

--게시판리스트 (Board_list) 전체 공지사항 게시판 기본생성(사용자 추가불가)
insert into board_list(bcode, bname, btype, ccode) values(blist_seq.nextval, '전체 공지사항', 1, 0);
commit;

