package kr.swkang.snstemplate.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

  /**
   * 포커싱 된 뷰로 인해 등장한 소프트 키보드를 감 춘다.
   *
   * @param activity 키보드가 보여지고 있는 포커싱 된 뷰가 존재하는 액티비티
   */
  public static void hideSoftKeyboard(Activity activity) {
    hideSoftKeyboard(activity, activity.getCurrentFocus());
  }

  /**
   * 포커싱 된 뷰로 인해서 등장한 소프트 키보드를 감춘다.
   *
   * @param context Context
   * @param views   포커싱 된 뷰들
   */
  public static void hideSoftKeyboard(Context context, View... views) {
    if (views == null || context == null) return;
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    for (View currentView : views) {
      if (currentView == null) continue;
      imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
      currentView.clearFocus();
    }
  }

  /**
   * view에 포커스를 요청하고 키보드를 등장 시키게 한다.
   *
   * @param context Context
   * @param view    포커스받고 입력 받을 Input View.
   */
  public static void showSoftKeyboard(@NonNull Context context, View view) {
    if (view == null) return;
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    view.requestFocus();
    imm.showSoftInput(view, 0);
  }

}
