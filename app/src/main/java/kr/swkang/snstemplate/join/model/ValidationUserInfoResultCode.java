package kr.swkang.snstemplate.join.model;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public enum ValidationUserInfoResultCode {
  // 이메일 입력란 비어있음
  EMPTY_EMAIL(10),
  // 중복된 이메일
  DUPLICATE_EMAIL(11),

  // 비밀번호란 비어있음
  EMPTY_PW(12),
  // 비밀번호가 너무 짧음 (최소 6자 이상)
  PW_SHORT(13),
  // 비밀번호가 취약함
  PW_WEAK(14),

  // 비밀번호 재 입력란이 비어 있음
  EMPTY_PWRE(15),
  // 비밀번호와 재입력한 비밀번호가 맞지 않음
  PWRE_NOT_MATCHED(16),

  // 알수 없음
  ERROR_UNKNOWN(0),

  COMPLETE(1);

  int value;

  ValidationUserInfoResultCode(int v) {
    this.value = v;
  }

  public int getValue() {
    return value;
  }

  public static ValidationUserInfoResultCode parseFromValue(int v) {
    switch (v) {
      case 1:
        return COMPLETE;
      case 10:
        return EMPTY_EMAIL;
      case 11:
        return DUPLICATE_EMAIL;
      case 12:
        return EMPTY_PW;
      case 13:
        return PW_SHORT;
      case 14:
        return PW_WEAK;
      case 15:
        return EMPTY_PWRE;
      case 16:
        return PWRE_NOT_MATCHED;
      default:
        return ERROR_UNKNOWN;
    }
  }

}
