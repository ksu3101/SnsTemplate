package kr.swkang.snstemplate.login.model;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public enum LoginResultCode {
  SUCCESS(0),
  FAILED_WRONG_INFOS(1),
  FAILED_EXPIRED_SESSION(3),
  FAILED_WITHDRAW(4),
  UNKNOWN(999);

  private int value;

  LoginResultCode(int v) {
    this.value = v;
  }

  public int getValue() {
    return value;
  }

  public static LoginResultCode parseFromValue(int v) {
    switch (v) {
      case 0:
        return SUCCESS;
      case 1:
        return FAILED_WRONG_INFOS;
      case 3:
        return FAILED_EXPIRED_SESSION;
      case 9:
        return FAILED_WITHDRAW;
    }
    return UNKNOWN;
  }

}
