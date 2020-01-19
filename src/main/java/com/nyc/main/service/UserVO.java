package com.nyc.main.service;

import java.io.Serializable;

public class UserVO implements Serializable {
	
	/** 회원ID */
	private String USR_ID = "";

	/** 회원 이름 */
	private String USR_NM = "";

	/** 권한ID */
	private String ROLE_ID = "";

	/** 이메일 */
	private String EMAIL = "";

	/** 휴대폰번호 */
	private String MBLPHN_NO = "";

	/** 전화번호 */
	private String TELNO = "";

	/** 사용여부 */
	private String USE_AT = "";

	/** 비밀번호 */
	private String PASSWORD = "";

	/** 코멘트 */
	private String COMMENT = "";

	/** 최초등록일시 */
	private String FRST_REGIST_DT = "";

	/** 최초등록자ID */
	private String FRST_REGISTER_ID = "";

	/** 최종수정일시 */
	private String LAST_MODF_DT = "";

	/** 최종수정자ID */
	private String LAST_UPDUSR_ID = "";

	public String getUSR_ID() {
		return USR_ID;
	}

	public void setUSR_ID(String uSR_ID) {
		USR_ID = uSR_ID;
	}

	public String getUSR_NM() {
		return USR_NM;
	}

	public void setUSR_NM(String uSR_NM) {
		USR_NM = uSR_NM;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getMBLPHN_NO() {
		return MBLPHN_NO;
	}

	public void setMBLPHN_NO(String mBLPHN_NO) {
		MBLPHN_NO = mBLPHN_NO;
	}

	public String getTELNO() {
		return TELNO;
	}

	public void setTELNO(String tELNO) {
		TELNO = tELNO;
	}

	public String getUSE_AT() {
		return USE_AT;
	}

	public void setUSE_AT(String uSE_AT) {
		USE_AT = uSE_AT;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getCOMMENT() {
		return COMMENT;
	}

	public void setCOMMENT(String cOMMENT) {
		COMMENT = cOMMENT;
	}

	public String getFRST_REGIST_DT() {
		return FRST_REGIST_DT;
	}

	public void setFRST_REGIST_DT(String fRST_REGIST_DT) {
		FRST_REGIST_DT = fRST_REGIST_DT;
	}

	public String getFRST_REGISTER_ID() {
		return FRST_REGISTER_ID;
	}

	public void setFRST_REGISTER_ID(String fRST_REGISTER_ID) {
		FRST_REGISTER_ID = fRST_REGISTER_ID;
	}

	public String getLAST_MODF_DT() {
		return LAST_MODF_DT;
	}

	public void setLAST_MODF_DT(String lAST_MODF_DT) {
		LAST_MODF_DT = lAST_MODF_DT;
	}

	public String getLAST_UPDUSR_ID() {
		return LAST_UPDUSR_ID;
	}

	public void setLAST_UPDUSR_ID(String lAST_UPDUSR_ID) {
		LAST_UPDUSR_ID = lAST_UPDUSR_ID;
	}
	
}
