DROP TABLE IF EXISTS T_LN_MSTR
;
CREATE TABLE IF NOT EXISTS T_LN_MSTR
(
LN_NUM
CHAR (10)
NOT NULL 
COMMENT 'ライン番号'

,BNG_NUM
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,BNG_CNT
CHAR (2)
NOT NULL 
COMMENT 'ビンゴカウント'

,END_FLG
CHAR (1)
NOT NULL 
COMMENT '終了フラグ'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (LN_NUM)
,INDEX (BNG_NUM,BNG_CNT,END_FLG,KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_SHBT_CD
;
CREATE TABLE IF NOT EXISTS T_SHBT_CD
(
PRFRMNC_TRGT_CD
CHAR (2)
NOT NULL 
COMMENT '演出対象コード'

,PRFRMNC_TRGT_NAME
VARCHAR (40)
NOT NULL 
COMMENT '演出対象名称'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (PRFRMNC_TRGT_CD)
,UNIQUE (PRFRMNC_TRGT_NAME)
)
;
DROP TABLE IF EXISTS T_MSRE
;
CREATE TABLE IF NOT EXISTS T_MSRE
(
MSRE_NUM
CHAR (2)
NOT NULL 
COMMENT 'マス番号'

,BNG_NO
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,END_FLG
CHAR (1)
NOT NULL 
COMMENT '終了フラグ'

,FREE_FLG
CHAR (1)
NOT NULL 
COMMENT 'FREEフラグ'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (MSRE_NUM,BNG_NO)
,INDEX (KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_BNG_CNTNT_MSTR
;
CREATE TABLE IF NOT EXISTS T_BNG_CNTNT_MSTR
(
CNTNT_ID
CHAR (4)
NOT NULL 
COMMENT '項目ID'

,BNG_NO
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,SHZK_CD
CHAR (7)
NOT NULL 
COMMENT 'ビンゴ項目名'

,PRBBLLTY
CHAR (2)
NULL 
COMMENT '固有確率'

,END_FLG
CHAR (1)
NOT NULL 
COMMENT '終了フラグ'

,KUSN_NYU_CD
CHAR (1)
NOT NULL 
COMMENT '更新内容コード'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (CNTNT_ID)
,UNIQUE (SHZK_CD)
,INDEX (BNG_NO,PRBBLLTY,END_FLG,KUSN_NYU_CD,KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_PRFRMNC_MSTR
;
CREATE TABLE IF NOT EXISTS T_PRFRMNC_MSTR
(
PRFRMNC_ID
CHAR (10)
NOT NULL 
COMMENT 'パフォーマンスID'

,PRFRMNC_IMG
VARCHAR (80)
NULL 
COMMENT '演出画像'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (PRFRMNC_ID)
,INDEX (KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_BNG_MSTR
;
CREATE TABLE IF NOT EXISTS T_BNG_MSTR
(
BNG_NO
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,HSH
VARCHAR (80)
NOT NULL 
COMMENT 'ハッシュ'

,PRFMNC_FLG
CHAR (1)
NULL 
COMMENT '演出フラグ'

,PRFRMNC_CNT_LCH
VARCHAR (40)
NOT NULL 
COMMENT '演出回数_リーチ'

,PRFRMNC_CNT_BNG
VARCHAR (40)
NOT NULL 
COMMENT '演出回数_ビンゴ'

,BNG_FNSH_NUM
CHAR (2)
NOT NULL 
COMMENT 'ビンゴ終了回数'

,LCH_NUM
CHAR (2)
NULL 
COMMENT 'リーチ回数'

,BNG_NUM
CHAR (2)
NOT NULL 
COMMENT 'ビンゴ回数'

,END_MSRE
CHAR (2)
NULL 
COMMENT '終了マス'

,FREE_MSRE
CHAR (2)
NULL 
COMMENT 'Freeマス'

,PRBBLTY_FLG
CHAR (1)
NOT NULL 
COMMENT '確率設定Flg'

,PRBBLTY_CD
CHAR (2)
NULL 
COMMENT '確率設定CD'

,BNG_MD_CD
CHAR (1)
NOT NULL 
COMMENT 'ビンゴモードCD'

,GM_STTS_CD
CHAR (1)
NOT NULL 
COMMENT 'ゲーム状態CD'

,AT_STP_LRS_CD
CHAR (2)
NULL 
COMMENT '自動一時停止CD'

,AT_STP_LRS_TM
INT (11)
NULL 
COMMENT '自動一時停止解除時間'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (BNG_NO)
,INDEX (HSH,PRFMNC_FLG,PRFRMNC_CNT_LCH,PRFRMNC_CNT_BNG,BNG_FNSH_NUM,LCH_NUM,BNG_NUM,BNG_MD_CD,GM_STTS_CD,AT_STP_LRS_CD,AT_STP_LRS_TM,KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_BNG_USR
;
CREATE TABLE IF NOT EXISTS T_BNG_USR
(
USR_NAME
VARCHAR (20)
NOT NULL 
COMMENT 'ユーザ名'

,BNG_NO
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,PSSWD
INT (11)
NOT NULL 
COMMENT 'リーチ数'

,USR_ADMIN_CD
INT (11)
NOT NULL 
COMMENT 'ビンゴ数'

,END_FLG
CHAR (1)
NULL 
COMMENT '終了フラグ'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (USR_NAME)
,UNIQUE (BNG_NO)
,INDEX (PSSWD,USR_ADMIN_CD,END_FLG,KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_USR_MSRE
;
CREATE TABLE IF NOT EXISTS T_USR_MSRE
(
USR_NAME
VARCHAR (20)
NOT NULL 
COMMENT 'ユーザー名'

,BNG_NO
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,PSSN_CD
CHAR (2)
NULL 
COMMENT 'マス番号'

,CNTNT_ID
CHAR (3)
NOT NULL 
COMMENT '項目ID'

,CLL_FLG
CHAR (1)
NOT NULL 
COMMENT 'コールフラグ'

,TURK_NTJ
DATE NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
DATE NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (USR_NAME,BNG_NO,PSSN_CD)
,INDEX (CNTNT_ID,CLL_FLG,KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_LN_TO＿MSRE_MSTR
;
CREATE TABLE IF NOT EXISTS T_LN_TO＿MSRE_MSTR
(
BNG_NO
CHAR (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,LN_NUM
CHAR (2)
NOT NULL 
COMMENT 'ライン番号'

,MSRE_NUM
CHAR (2)
NOT NULL 
COMMENT 'マス番号'

,TURK_NTJ
CHAR (14)
NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
CHAR (14)
NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (BNG_NO,LN_NUM,MSRE_NUM)
,INDEX (KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_PRFRMNC_TO_BNG
;
CREATE TABLE IF NOT EXISTS T_PRFRMNC_TO_BNG
(
BNG_NO
char(n) (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,PRFRMNC_ID
char(n) (10)
NOT NULL 
COMMENT 'パフォーマンスID'

,PRFRMNC_TRGT_CD
char(n) (2)
NOT NULL 
COMMENT '演出対象CD'

,TURK_NTJ
char(n) (14)
NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
char(n) (14)
NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (BNG_NO,PRFRMNC_ID,PRFRMNC_TRGT_CD)
,INDEX (KUSN_NTJ)
)
;
DROP TABLE IF EXISTS T_USR_LN
;
CREATE TABLE IF NOT EXISTS T_USR_LN
(
LN_NUM
char(n) (10)
NOT NULL 
COMMENT 'ライン番号'

,BNG_NUM
char(n) (10)
NOT NULL 
COMMENT 'ビンゴ番号'

,USR_NAME
varchar (20)
NOT NULL 
COMMENT 'ユーザー名'

,BNG_CNT
char(n) (2)
NOT NULL 
COMMENT 'ビンゴカウント'

,END_FLG
char(n) (1)
NOT NULL 
COMMENT '終了フラグ'

,BNG_FLG
char(n) (1)
NULL 
COMMENT 'ビンゴフラグ'

,LCH_FLG
char(n) (1)
NULL 
COMMENT 'リーチフラグ'

,TURK_NTJ
char(n) (14)
NOT NULL 
COMMENT '登録日時'

,KUSN_NTJ
char(n) (14)
NOT NULL 
COMMENT '更新日時'

,PRIMARY KEY (LN_NUM,BNG_NUM,USR_NAME)
,INDEX (BNG_CNT,END_FLG,KUSN_NTJ)
)
;
