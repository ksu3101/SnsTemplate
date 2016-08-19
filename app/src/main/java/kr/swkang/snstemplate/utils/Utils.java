package kr.swkang.snstemplate.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public class Utils {

  /**
   * 디바이스의 네트워크 연결 여부를 확인 한다.
   *
   * @param context Context
   * @return true일 경우 온라인 상태. false 일 경우 오프라인 상태.
   */
  public static boolean isOnline(Context context) {
    if (context != null) {
      ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
      return (networkInfo != null && networkInfo.isConnected());
    }
    return false;
  }

}
