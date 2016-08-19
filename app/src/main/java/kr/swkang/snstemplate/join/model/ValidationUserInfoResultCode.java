package kr.swkang.snstemplate.join.model;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public enum ValidationUserInfoResultCode {
  // 이메일 입력란 비어있음
  EMPTY_EMAIL,
  // 중복된 이메일
  DUPLICATE_EMAIL,

  // 비밀번호란 비어있음
  EMPTY_PW,
  // 비밀번호가 너무 짧음 (최소 6자 이상)
  PW_SHORT,
  // 비밀번호가 취약함
  PW_WEAK,

  // 비밀번호 재 입력란이 비어 있음
  EMPTY_PWRE,
  // 비밀번호와 재입력한 비밀번호가 맞지 않음
  PWRE_NOT_MATCHED,

  //
  COMPLETE
}
