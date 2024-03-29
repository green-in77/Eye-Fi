DB ID : web / PW : 1004

/* 회원 */
DROP TABLE MEMBER 
	CASCADE CONSTRAINTS;

/* 게시판 */
DROP TABLE BOARD 
	CASCADE CONSTRAINTS;

/* 게시판종류 */
DROP TABLE BOARD_LIST 
	CASCADE CONSTRAINTS;

/* 게시판형식 */
DROP TABLE BOARD_TYPE 
	CASCADE CONSTRAINTS;

/* 게시판카테고리 */
DROP TABLE CATEGORY 
	CASCADE CONSTRAINTS;

/* 댓글 */
DROP TABLE REPLY 
	CASCADE CONSTRAINTS;

/* 답글 */
DROP TABLE REBOARD 
	CASCADE CONSTRAINTS;

/* 앨범 */
DROP TABLE ALBUM 
	CASCADE CONSTRAINTS;

/* 활성화코드 */
DROP TABLE ACTIVE 
	CASCADE CONSTRAINTS;

/* 회원 */
CREATE TABLE MEMBER (
	userid VARCHAR2(20) NOT NULL, /* 아이디 */
	userpw VARCHAR2(30) NOT NULL, /* 비밀번호 */
	email VARCHAR2(40) NOT NULL, /* 이메일 */
	admin NUMBER DEFAULT 0 NOT NULL, /* 관리자 */
	code NUMBER NOT NULL /* 코드 */
);

COMMENT ON TABLE MEMBER IS '회원';

COMMENT ON COLUMN MEMBER.userid IS '아이디';

COMMENT ON COLUMN MEMBER.userpw IS '비밀번호';

COMMENT ON COLUMN MEMBER.email IS '이메일';

COMMENT ON COLUMN MEMBER.admin IS '관리자';

COMMENT ON COLUMN MEMBER.code IS '코드';

CREATE UNIQUE INDEX PK_MEMBER
	ON MEMBER (
		userid ASC
	);

ALTER TABLE MEMBER
	ADD
		CONSTRAINT PK_MEMBER
		PRIMARY KEY (
			userid
		);

/* 게시판 */
CREATE TABLE BOARD (
	seq NUMBER NOT NULL, /* 글번호 */
	userid VARCHAR2(20) NOT NULL, /* 아이디 */
	subject VARCHAR2(100) NOT NULL, /* 글제목 */
	content VARCHAR2(4000) NOT NULL, /* 글내용 */
	hit NUMBER DEFAULT 0 NOT NULL, /* 조회수 */
	logtime TIMESTAMP DEFAULT SYSDATE NOT NULL, /* 작성일 */
	classify VARCHAR2(50), /* 말머리 */
	del NUMBER DEFAULT 0 NOT NULL, /* 삭제 */
	notice VARCHAR2(10) NOT NULL, /* 공지 */
	bcode NUMBER NOT NULL /* 게시판코드 */
);

COMMENT ON TABLE BOARD IS '게시판';

COMMENT ON COLUMN BOARD.seq IS '글번호';

COMMENT ON COLUMN BOARD.userid IS '아이디';

COMMENT ON COLUMN BOARD.subject IS '글제목';

COMMENT ON COLUMN BOARD.content IS '글내용';

COMMENT ON COLUMN BOARD.hit IS '조회수';

COMMENT ON COLUMN BOARD.logtime IS '작성일';

COMMENT ON COLUMN BOARD.classify IS '말머리';

COMMENT ON COLUMN BOARD.del IS '삭제';

COMMENT ON COLUMN BOARD.notice IS '공지';

COMMENT ON COLUMN BOARD.bcode IS '게시판코드';

CREATE UNIQUE INDEX PK_BOARD
	ON BOARD (
		seq ASC
	);

ALTER TABLE BOARD
	ADD
		CONSTRAINT PK_BOARD
		PRIMARY KEY (
			seq
		);

/* 게시판종류 */
CREATE TABLE BOARD_LIST (
	bcode NUMBER NOT NULL, /* 게시판코드 */
	bname VARCHAR2(100) NOT NULL, /* 게시판이름 */
	btype NUMBER NOT NULL, /* 게시판형식번호 */
	ccode NUMBER NOT NULL /* 카테고리코드 */
);

COMMENT ON TABLE BOARD_LIST IS '게시판종류';

COMMENT ON COLUMN BOARD_LIST.bcode IS '게시판코드';

COMMENT ON COLUMN BOARD_LIST.bname IS '게시판이름';

COMMENT ON COLUMN BOARD_LIST.btype IS '게시판형식번호';

COMMENT ON COLUMN BOARD_LIST.ccode IS '카테고리코드';

CREATE UNIQUE INDEX PK_BOARD_LIST
	ON BOARD_LIST (
		bcode ASC
	);

ALTER TABLE BOARD_LIST
	ADD
		CONSTRAINT PK_BOARD_LIST
		PRIMARY KEY (
			bcode
		);

/* 게시판형식 */
CREATE TABLE BOARD_TYPE (
	btype NUMBER NOT NULL, /* 게시판형식번호 */
	btype_name VARCHAR2(50) NOT NULL /* 게시판형식이름 */
);

COMMENT ON TABLE BOARD_TYPE IS '게시판형식';

COMMENT ON COLUMN BOARD_TYPE.btype IS '게시판형식번호';

COMMENT ON COLUMN BOARD_TYPE.btype_name IS '게시판형식이름';

CREATE UNIQUE INDEX PK_BOARD_TYPE
	ON BOARD_TYPE (
		btype ASC
	);

ALTER TABLE BOARD_TYPE
	ADD
		CONSTRAINT PK_BOARD_TYPE
		PRIMARY KEY (
			btype
		);

/* 게시판카테고리 */
CREATE TABLE CATEGORY (
	ccode NUMBER NOT NULL, /* 카테고리코드 */
	cname VARCHAR2(50) NOT NULL /* 카테고리이름 */
);

COMMENT ON TABLE CATEGORY IS '게시판카테고리';

COMMENT ON COLUMN CATEGORY.ccode IS '카테고리코드';

COMMENT ON COLUMN CATEGORY.cname IS '카테고리이름';

CREATE UNIQUE INDEX PK_CATEGORY
	ON CATEGORY (
		ccode ASC
	);

ALTER TABLE CATEGORY
	ADD
		CONSTRAINT PK_CATEGORY
		PRIMARY KEY (
			ccode
		);

/* 댓글 */
CREATE TABLE REPLY (
	rseq NUMBER NOT NULL, /* 댓글식별번호 */
	seq NUMBER NOT NULL, /* 글번호 */
	rcontent VARCHAR2(500) NOT NULL, /* 글내용 */
	reuserid VARCHAR2(20) NOT NULL, /* 작성자 */
	retime DATE DEFAULT SYSDATE NOT NULL, /* 작성일 */
	redel NUMBER DEFAULT 0 NOT NULL /* 삭제 */
);

COMMENT ON TABLE REPLY IS '댓글';

COMMENT ON COLUMN REPLY.rseq IS '댓글식별번호';

COMMENT ON COLUMN REPLY.seq IS '글번호';

COMMENT ON COLUMN REPLY.rcontent IS '글내용';

COMMENT ON COLUMN REPLY.reuserid IS '작성자';

COMMENT ON COLUMN REPLY.retime IS '작성일';

COMMENT ON COLUMN REPLY.redel IS '삭제';

CREATE UNIQUE INDEX PK_REPLY
	ON REPLY (
		rseq ASC
	);

ALTER TABLE REPLY
	ADD
		CONSTRAINT PK_REPLY
		PRIMARY KEY (
			rseq
		);

/* 답글 */
CREATE TABLE REBOARD (
	COL NUMBER NOT NULL, /* 답변식별번호 */
	seq NUMBER NOT NULL, /* 글번호 */
	refer NUMBER NOT NULL, /* 그룹번호 */
	depth NUMBER NOT NULL, /* 들여쓰기 */
	step NUMBER NOT NULL, /* 답변정렬 */
	pseq NUMBER NOT NULL /* 원글번호 */
);

COMMENT ON TABLE REBOARD IS '답글';

COMMENT ON COLUMN REBOARD.COL IS '답변식별번호';

COMMENT ON COLUMN REBOARD.seq IS '글번호';

COMMENT ON COLUMN REBOARD.refer IS '그룹번호';

COMMENT ON COLUMN REBOARD.depth IS '들여쓰기';

COMMENT ON COLUMN REBOARD.step IS '답변정렬';

COMMENT ON COLUMN REBOARD.pseq IS '원글번호';

CREATE UNIQUE INDEX PK_REBOARD
	ON REBOARD (
		COL ASC
	);

ALTER TABLE REBOARD
	ADD
		CONSTRAINT PK_REBOARD
		PRIMARY KEY (
			COL
		);

/* 앨범 */
CREATE TABLE ALBUM (
	aseq NUMBER NOT NULL, /* 앨범식별번호 */
	seq NUMBER NOT NULL, /* 글번호 */
	orign_file VARCHAR2(30) NOT NULL, /* 원본사진이름 */
	save_file VARCHAR2(40) NOT NULL /* 저장사진이름 */
);

COMMENT ON TABLE ALBUM IS '앨범';

COMMENT ON COLUMN ALBUM.aseq IS '앨범식별번호';

COMMENT ON COLUMN ALBUM.seq IS '글번호';

COMMENT ON COLUMN ALBUM.orign_file IS '원본사진이름';

COMMENT ON COLUMN ALBUM.save_file IS '저장사진이름';

CREATE UNIQUE INDEX PK_ALBUM
	ON ALBUM (
		aseq ASC
	);

ALTER TABLE ALBUM
	ADD
		CONSTRAINT PK_ALBUM
		PRIMARY KEY (
			aseq
		);

/* 활성화코드 */
CREATE TABLE ACTIVE (
	code NUMBER NOT NULL, /* 코드 */
	status VARCHAR2(20) NOT NULL /* 회원상태 */
);

COMMENT ON TABLE ACTIVE IS '활성화코드';

COMMENT ON COLUMN ACTIVE.code IS '코드';

COMMENT ON COLUMN ACTIVE.status IS '회원상태';

CREATE UNIQUE INDEX PK_ACTIVE
	ON ACTIVE (
		code ASC
	);

ALTER TABLE ACTIVE
	ADD
		CONSTRAINT PK_ACTIVE
		PRIMARY KEY (
			code
		);

ALTER TABLE MEMBER
	ADD
		CONSTRAINT FK_ACTIVE_TO_MEMBER
		FOREIGN KEY (
			code
		)
		REFERENCES ACTIVE (
			code
		);

ALTER TABLE BOARD
	ADD
		CONSTRAINT FK_MEMBER_TO_BOARD
		FOREIGN KEY (
			userid
		)
		REFERENCES MEMBER (
			userid
		);

ALTER TABLE BOARD
	ADD
		CONSTRAINT FK_BOARD_LIST_TO_BOARD
		FOREIGN KEY (
			bcode
		)
		REFERENCES BOARD_LIST (
			bcode
		);

ALTER TABLE BOARD_LIST
	ADD
		CONSTRAINT FK_BOARD_TYPE_TO_BOARD_LIST
		FOREIGN KEY (
			btype
		)
		REFERENCES BOARD_TYPE (
			btype
		);

ALTER TABLE BOARD_LIST
	ADD
		CONSTRAINT FK_CATEGORY_TO_BOARD_LIST
		FOREIGN KEY (
			ccode
		)
		REFERENCES CATEGORY (
			ccode
		);

ALTER TABLE REPLY
	ADD
		CONSTRAINT FK_BOARD_TO_REPLY
		FOREIGN KEY (
			seq
		)
		REFERENCES BOARD (
			seq
		);

ALTER TABLE REBOARD
	ADD
		CONSTRAINT FK_BOARD_TO_REBOARD
		FOREIGN KEY (
			seq
		)
		REFERENCES BOARD (
			seq
		);

ALTER TABLE ALBUM
	ADD
		CONSTRAINT FK_BOARD_TO_ALBUM
		FOREIGN KEY (
			seq
		)
		REFERENCES BOARD (
			seq
		);


/*게시판 리스트 시퀀스 생성*/
create sequence blist_seq
start with 1 increment by 1 nocache;

/*게시판 시퀀스 생성*/
create sequence board_seq
start with 1 increment by 1 nocache;

/*답글 시퀀스 생성*/
create sequence reboard_seq
start with 1 increment by 1 nocache;

/*앨범 시퀀스 생성*/
create sequence album_seq
start with 1 increment by 1 nocache;

/*댓글 시퀀스 생성*/
create sequence reply_seq
start with 1 increment by 1 nocache;

