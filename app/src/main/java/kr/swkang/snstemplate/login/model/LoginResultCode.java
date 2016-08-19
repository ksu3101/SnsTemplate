package kr.swkang.snstemplate.login.model;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public enum LoginResultCode {
  SUCCESS(0),
  FAILED_WRONG_INFOS(1),
  FAILED_NOT_JOINED_USER(2),
  FAILED_EXPIRED_SESSION(3),
  FAILED_WITHDRAW(4),
  NOT_CONNECTED_NETWORK(998),
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
      case 2:
        return FAILED_NOT_JOINED_USER;
      case 3:
        return FAILED_EXPIRED_SESSION;
      case 9:
        return FAILED_WITHDRAW;
      case 998:
        return NOT_CONNECTED_NETWORK;
    }
    return UNKNOWN;
  }

}
