package kr.swkang.snstemplate.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import kr.swkang.snstemplate.utils.mvp.models.UserInfo;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public class Utils {

  public static UserInfo createDummyUserInfo(@Nullable String profileImgCachedURI,
                                             @NonNull String userIdEmail,
                                             @NonNull String password) {
    final UserInfo userInfo = new UserInfo();
    userInfo.setCoverImgUrl(profileImgCachedURI);
    userInfo.setEmail(userIdEmail);
    return userInfo;
  }

  public static UserInfo loadDummyUserInfo() {
    return null;
  }

  /**
   * 연결되어진 데이터 네트워크의 타입을 얻는다.
   *
   * @param context Context
   * @return TYPE_MOBILE, TYPE_WIFI or -1
   * @see ConnectivityManager
   */
  public static int getNetworkConnectionType(@NonNull Context context) {
    ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
      return ConnectivityManager.TYPE_MOBILE;
    }
    else if (mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
      return ConnectivityManager.TYPE_WIFI;
    }
    else {
      return -1;
    }
  }

  /**
   * 디바이스의 전화번호(MDN)를 얻는다.
   *
   * @param context Context
   * @return 전화번호 문자열.
   */
  public static String getPhoneNumber(@NonNull Context context) {
    TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return tMgr.getLine1Number();
  }

  /**
   * Calendar를 ISO8601 규격에 맞춘 시간 문자열로 얻는다.
   *
   * @param cal Calendar instance.
   * @return ISO8601 규격에 맞춘 시간 문자열.
   */
  public static String getCalendar(Calendar cal) {
    Date date = cal.getTime();
    String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(date);
    return format.substring(0, 22) + ":" + format.substring(22);
  }

  /**
   * 날짜 및 시간정보를 dateFormat에 맞추어서 얻는다.
   *
   * @param cal        날짜 및 시간의 Calendar 객체
   * @param dateFormat SimpleDateFormat을 참고 할 것
   * @return 시간 문자열
   */
  public static String getNow(Calendar cal, String dateFormat) {
    Date date = cal.getTime();
    return new SimpleDateFormat(dateFormat, Locale.getDefault()).format(date);
  }

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

  /**
   * Pixel 단위 숫자를 DPI단위 Float형태의 숫자로 변환한다.
   *
   * @param res   Resources.
   * @param pixel 변환대상 Pixel 단위 숫자.
   * @return Float형태의 DPI.
   */
  public static float convertPixelToDpi(@NonNull Resources res, int pixel) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixel, res.getDisplayMetrics());
  }

  /**
   * DPI 단위 숫자를 Pixel 단위 Float형태의 숫자로 변환한다.
   *
   * @param res Resources.
   * @param dpi 변환대상 DPI단위의 숫자.
   * @return Float형태의 pixel 숫자.
   */
  public static float convertDpiToPixel(@NonNull Resources res, int dpi) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, res.getDisplayMetrics());
  }

  /**
   * Pixel 단위 숫자를 DPI단위 Float형태의 숫자로 변환한다.
   *
   * @param res   Resources.
   * @param pixel 변환대상 Pixel 단위 숫자.
   * @return Float형태의 DPI.
   */
  public static float convertPixelToDpi(@NonNull Resources res, float pixel) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixel, res.getDisplayMetrics());
  }

  /**
   * DPI 단위 숫자를 Pixel 단위 Float형태의 숫자로 변환한다.
   *
   * @param res Resources.
   * @param dpi 변환대상 DPI단위의 숫자.
   * @return Float형태의 pixel 숫자.
   */
  public static float convertDpiToPixel(@NonNull Resources res, float dpi) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, res.getDisplayMetrics());
  }

}
