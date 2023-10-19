/**********************************/
/* Table Name: 카테고리 */
/**********************************/
commit;

drop table category;

CREATE TABLE CATEGORY(
		cate_id                        		NUMBER(10)		 NOT NULL,
		cate_name                          		VARCHAR2(50)		 NOT NULL,
		cate_cnt                           		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		rdate                         		DATE		 NOT NULL,
        SEQNO                               NUMBER(5)    DEFAULT 1      NOT NULL,
        VISIBLE                             CHAR(1)     DEFAULT 'N'     NOT NULL,
        PRIMARY KEY (cate_id)
);


COMMENT ON TABLE CATEGORY is '카테고리';
COMMENT ON COLUMN CATEGORY.cate_id is '카테고리 번호';
COMMENT ON COLUMN CATEGORY.cate_name is '카테고리 이름';
COMMENT ON COLUMN CATEGORY.cate_cnt is '관련 자료수';
COMMENT ON COLUMN CATEGORY.rdate is '등록일';
COMMENT ON COLUMN CATEGORY.SEQNO is '출력순서';
COMMENT ON COLUMN CATEGORY.VISIBLE is '출력모드';

DROP SEQUENCE CATEGORY_SEQ;

CREATE SEQUENCE CATEGORY_SEQ
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999999
    CACHE 2
    NOCYCLE;
    
-- CREATE
INSERT INTO CATEGORY(cate_id, cate_name, cate_cnt, rdate) VALUES(category_seq.nextval, '경기도',0,sysdate);
INSERT INTO CATEGORY(cate_id, cate_name, cate_cnt, rdate) VALUES(category_seq.nextval, '강원도',0,sysdate);
INSERT INTO CATEGORY(cate_id, cate_name, cate_cnt, rdate) VALUES(category_seq.nextval, '충청남도',0,sysdate);

commit;

-- READ: LIST
SELECT * FROM CATEGORY;
-- READ
SELECT cateno, name, cnt, rdate FROM cate WHERE cateno=1 ORDER BY cateno ASC;
-- UPDATE
UPDATE cate SET name='전라도',cnt=1 WHERE cateno=1;
-- DELETE
DELETE FROM cate WHERE cateno=1;
DELETE FROM cate WHERE cateno>=7;


-- 우선순위 높임, 10등 -> 1등
UPDATE cate SET seqno= seqno - 1 WHERE cateno=1;
SELECT cateno, name, cnt, rdate, seqno FROM cate ORDER BY cateno ASC;

UPDATE cate SET seqno= seqno + 1 WHERE cateno=1;
SELECT cateno, name, cnt, rdate, seqno FROM cate ORDER BY cateno ASC;

-- READ: LIST
SELECT cateno, name, cnt, rdate, seqno FROM cate ORDER BY seqno ASC;



-- 카테고리 공개 설정
UPDATE cate SET visible='Y' where cateno=1;
SELECT cateno, name, cnt, rdate, seqno, visible FROM cate ORDER BY cateno ASC;

-- 카테고리 비공개 설정
UPDATE cate SET visible='N' where cateno=1;
SELECT cateno, name, cnt, rdate, seqno, visible FROM cate ORDER BY cateno ASC;

COMMIT;


-- 비회원/회원 SELECT LIST, id: list_all_y
SELECT cateno, name, cnt, rdate, seqno, visible 
FROM cate 
WHERE visible='Y'
ORDER BY seqno ASC;


-- COUNT
SELECT COUNT(*) as cnt FROM cate;
/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE ADMIN(
		ADMINNO                       		NUMBER(10)		 NOT NULL
);

COMMENT ON TABLE ADMIN is '관리자';
COMMENT ON COLUMN ADMIN.ADMINNO is '관리자 번호';


/**********************************/
/* Table Name: 컨텐츠 */
/**********************************/
CREATE TABLE CONTENTS(
		CONTENTSNO                    		NUMBER(10)		 NOT NULL,
		CATENO                        		NUMBER(10)		 NULL ,
		ADMINNO                       		NUMBER(10)		 NULL 
);

COMMENT ON TABLE CONTENTS is '컨텐츠';
COMMENT ON COLUMN CONTENTS.CONTENTSNO is '컨텐츠 번호';
COMMENT ON COLUMN CONTENTS.CATENO is '카테고리 번호';
COMMENT ON COLUMN CONTENTS.ADMINNO is '관리자 번호';


/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE MEMBER(
		MEMBERNO                      		NUMBER(10)		 NOT NULL
);

COMMENT ON TABLE MEMBER is '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO is '회원 번호';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE REPLY(
		REPLYNO                       		NUMBER(10)		 NOT NULL,
		CONTENTSNO                    		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL 
);

COMMENT ON TABLE REPLY is '댓글';
COMMENT ON COLUMN REPLY.REPLYNO is '댓글번호';
COMMENT ON COLUMN REPLY.CONTENTSNO is '컨텐츠 번호';
COMMENT ON COLUMN REPLY.MEMBERNO is '회원 번호';



ALTER TABLE CATE ADD CONSTRAINT IDX_CATE_PK PRIMARY KEY (CATENO);

ALTER TABLE ADMIN ADD CONSTRAINT IDX_ADMIN_PK PRIMARY KEY (ADMINNO);

ALTER TABLE CONTENTS ADD CONSTRAINT IDX_CONTENTS_PK PRIMARY KEY (CONTENTSNO);
ALTER TABLE CONTENTS ADD CONSTRAINT IDX_CONTENTS_FK0 FOREIGN KEY (CATENO) REFERENCES CATE (CATENO);
ALTER TABLE CONTENTS ADD CONSTRAINT IDX_CONTENTS_FK1 FOREIGN KEY (ADMINNO) REFERENCES ADMIN (ADMINNO);

ALTER TABLE MEMBER ADD CONSTRAINT IDX_MEMBER_PK PRIMARY KEY (MEMBERNO);

ALTER TABLE REPLY ADD CONSTRAINT IDX_REPLY_PK PRIMARY KEY (REPLYNO);
ALTER TABLE REPLY ADD CONSTRAINT IDX_REPLY_FK0 FOREIGN KEY (CONTENTSNO) REFERENCES CONTENTS (CONTENTSNO);
ALTER TABLE REPLY ADD CONSTRAINT IDX_REPLY_FK1 FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO);

