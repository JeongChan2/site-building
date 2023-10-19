/**********************************/
/* Table Name: 관리자 */
-- 개인 프로젝트에서는 개발자가 유일한 관리자로 처리됨.
/**********************************/
DROP TABLE ADMIN_STOCK;

CREATE TABLE ADMIN_STOCK(
    ADMIN_NUM       NUMBER(5)    NOT NULL,
    ADMIN_ID        VARCHAR(20)   NOT NULL UNIQUE, -- 아이디, 중복 안됨, 레코드를 구분 
    PASSWD          VARCHAR(15)   NOT NULL, -- 패스워드, 영숫자 조합
    MNAME           VARCHAR(20)   NOT NULL, -- 성명, 한글 10자 저장 가능
    MDATE           DATE          NOT NULL, -- 가입일    
    GRADE           NUMBER(2)     NOT NULL, -- 등급(1~10: 관리자, 정지 관리자: 20, 탈퇴 관리자: 99)    
    PRIMARY KEY (admin_num)              -- 한번 등록된 값은 중복 안됨
);

COMMENT ON TABLE ADMIN_STOCK is '관리자';
COMMENT ON COLUMN ADMIN_STOCK.ADMIN_NUM is '관리자 번호';
COMMENT ON COLUMN ADMIN_STOCK.ADMIN_ID is '아이디';
COMMENT ON COLUMN ADMIN_STOCK.PASSWD is '패스워드';
COMMENT ON COLUMN ADMIN_STOCK.MNAME is '성명';
COMMENT ON COLUMN ADMIN_STOCK.MDATE is '가입일';
COMMENT ON COLUMN ADMIN_STOCK.GRADE is '등급';

DROP SEQUENCE ADMIN_STOCK_SEQ;

CREATE SEQUENCE ADMIN_STOCK_SEQ
  START WITH 1              -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 99999            -- 최대값: 99999 --> NUMBER(5) 대응
  CACHE 2                   -- 2번은 메모리에서만 계산
  NOCYCLE;                  -- 다시 1부터 생성되는 것을 방지

-- CREATE
INSERT INTO admin_stock(admin_num, admin_id, passwd, mname, mdate, grade)
VALUES(admin_stock_seq.nextval, 'admin1', '1234', '관리자1', sysdate, 1);

INSERT INTO admin_stock(admin_num, admin_id, passwd, mname, mdate, grade)
VALUES(admin_stock_seq.nextval, 'admin2', '1234', '관리자2', sysdate, 1);

INSERT INTO admin_stock(admin_num, admin_id, passwd, mname, mdate, grade)
VALUES(admin_stock_seq.nextval, 'admin3', '1234', '관리자3', sysdate, 1);

commit;

-- READ: List
SELECT admin_num, admin_id, passwd, mname, mdate, grade 
FROM admin_stock 
ORDER BY admin_num ASC;
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1
         2 admin2               1234            관리자2              2022-10-06 11:47:56          1
         3 admin3               1234            관리자3              2022-10-06 11:47:56          1

-- READ         
SELECT adminno, id, passwd, mname, mdate, grade 
FROM admin
WHERE adminno=1;
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1

-- READ by id
SELECT adminno, id, passwd, mname, mdate, grade 
FROM admin
WHERE id='admin1';
   ADMINNO ID                   PASSWD          MNAME                MDATE                    GRADE
---------- -------------------- --------------- -------------------- ------------------- ----------
         1 admin1               1234            관리자1              2022-10-06 11:47:56          1

-- UPDATE
UPDATE admin
SET passwd='1234', mname='관리자1', mdate=sysdate, grade=1
WHERE ADMINNO=1;

COMMIT;

-- DELETE
DELETE FROM admin WHERE adminno=3;
         
-- 로그인, 1: 로그인 성공, 0: 로그인 실패
SELECT COUNT(*) as cnt
FROM admin
WHERE id='admin1' AND passwd='1234'; 