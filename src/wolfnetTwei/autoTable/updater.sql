DROP TABLE IF EXISTS C_INPT_MTHD
;
CREATE TABLE IF NOT EXISTS C_INPT_MTHD
(
CD
CHAR (2)
NOT NULL 
COMMENT 'コード値'

,NAME
VARCHAR (40)
NOT NULL 
COMMENT '名称'

,PRIMARY KEY (CD)
,INDEX (NAME)
)
;
