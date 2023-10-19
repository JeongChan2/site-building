/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE admin(
		admin_id                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY
);

COMMENT ON TABLE admin is '관리자';
COMMENT ON COLUMN admin.admin_id is '관리자번호';


/**********************************/
/* Table Name: 카테고리 */
/**********************************/

DROP TABLE category;

CREATE TABLE category(
		cate_id                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL,
		cnt                           		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		rdate                         		DATE		 NOT NULL
);

COMMENT ON TABLE category is '카테고리';
COMMENT ON COLUMN category.cate_id is '카테고리번호';
COMMENT ON COLUMN category.name is '카테고리 이름';
COMMENT ON COLUMN category.cnt is '관련 자료수';
COMMENT ON COLUMN category.rdate is '마지막 등록일';

DROP SEQUENCE CATE_SEQ;

CREATE SEQUENCE CATE_SEQ
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 999999999
    CACHE 2
    NOCYCLE;

select * from category;

commit;
/**********************************/
/* Table Name: 컨텐츠 */
/**********************************/
CREATE TABLE contents(
		contents_id                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		cate_id                       		NUMBER(10)		 NULL ,
		admin_id                      		NUMBER(10)		 NULL ,
		rdate                         		DATE		 NOT NULL,
  FOREIGN KEY (cate_id) REFERENCES category (cate_id),
  FOREIGN KEY (admin_id) REFERENCES admin (admin_id)
);

COMMENT ON TABLE contents is '컨텐츠';
COMMENT ON COLUMN contents.contents_id is '컨텐츠 번호';
COMMENT ON COLUMN contents.cate_id is '카테고리번호';
COMMENT ON COLUMN contents.admin_id is '관리자번호';
COMMENT ON COLUMN contents.rdate is '업로드일자';


/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE member(
		member_id                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY
);

COMMENT ON TABLE member is '회원';
COMMENT ON COLUMN member.member_id is '회원번호';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE reply(
		reply_id                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		contents_id                   		NUMBER(10)		 NULL ,
		member_id                     		NUMBER(10)		 NULL ,
  FOREIGN KEY (contents_id) REFERENCES contents (contents_id),
  FOREIGN KEY (member_id) REFERENCES member (member_id)
);

COMMENT ON TABLE reply is '댓글';
COMMENT ON COLUMN reply.reply_id is '댓글번호';
COMMENT ON COLUMN reply.contents_id is '컨텐츠 번호';
COMMENT ON COLUMN reply.member_id is '회원번호';


/**********************************/
/* Table Name: 주식 정보 */
/**********************************/
CREATE TABLE Stocks(
		stock_id                      		VARCHAR2(20)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL
);

COMMENT ON TABLE Stocks is '주식 정보';
COMMENT ON COLUMN Stocks.stock_id is '국제 증권 식별 번호';
COMMENT ON COLUMN Stocks.name is '종목명';


/**********************************/
/* Table Name: 주식 가격 정보 */
/**********************************/
CREATE TABLE Stock_Prices(
		price_id                      		VARCHAR2(20)		 NOT NULL		 PRIMARY KEY,
		stock_id                      		VARCHAR2(20)		 NOT NULL,
		current_price                 		NUMBER(10)		 NOT NULL,
		closing_price                 		NUMBER(10)		 NOT NULL,
		last_updated                  		DATE		 NOT NULL,
		admin_id                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (stock_id) REFERENCES Stocks (stock_id)
);

COMMENT ON TABLE Stock_Prices is '주식 가격 정보';
COMMENT ON COLUMN Stock_Prices.price_id is '가격 식별자';
COMMENT ON COLUMN Stock_Prices.stock_id is '국제 증권 식별 번호';
COMMENT ON COLUMN Stock_Prices.current_price is '시세';
COMMENT ON COLUMN Stock_Prices.closing_price is '전일 종가';
COMMENT ON COLUMN Stock_Prices.last_updated is '주가 업데이트 시간';
COMMENT ON COLUMN Stock_Prices.admin_id is '관리자번호';


