package kr.swkang.snstemplate.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
  private static final String TAG = Utils.class.getSimpleName();

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
   * Drawable1과 Drawable2간의 트랜지션 에니메이션을 적용한 TransitionDrawable을 만든다.
   * 만들어진 TransitionDrawable의 객채의 startTransition(durationMillis)메소드를 이용하여
   * 애니메이션을 시작 한다.
   *
   * @param layer1 before Drawable.
   * @param layer2 after Drawable.
   * @return {@link TransitionDrawable}
   */
  public static TransitionDrawable createTransitionDrawable(Drawable layer1, Drawable layer2) {
    TransitionDrawable td = new TransitionDrawable(new Drawable[]{layer1, layer2});
    td.setCrossFadeEnabled(true);
    return td;
  }

  /**
   * Drawable1(resId1) 과 Drawable2(resId2)간의 트랜지션 애니메이션을 적용한 TransitionDrwable을 만든다.  만들어진
   * TransitionDrwable 객체의 startTransition(durationMillis)를 이용하여 애니메이션을 시작한다.
   *
   * @param res         Resources (from Context)
   * @param layerResId1 before Drawable Resource ID.
   * @param layerResId2 after Drawable Resource ID.
   * @return {@link TransitionDrawable}
   */
  public static TransitionDrawable createTransitionDrawable(Resources res,
                                                            int layerResId1,
                                                            int layerResId2) {
    if (res != null) {
      TransitionDrawable td = new TransitionDrawable(new Drawable[]{res.getDrawable(layerResId1),
          res.getDrawable(layerResId2)});
      td.setCrossFadeEnabled(true);
      return td;
    }
    return null;
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


  /**
   * Bitmap이미지를 리사이즈 한다.
   *
   * @param originalImg   리사이즈 대상 원본 Bitmap 이미지.
   * @param maxResolution width, height 대상 중 최대 감안 크기.
   * @return 리사이징 된 Bitmap 이미지.
   */
  public static Bitmap getResizeImg(Bitmap originalImg, int maxResolution) {
    if (originalImg == null) return null;
    final int width = originalImg.getWidth();
    final int height = originalImg.getHeight();
    int newWidth = width;
    int newHeight = height;
    float rate = 0.0f;

    if (width > height) {
      if (maxResolution < width) {
        rate = maxResolution / (float) width;
        newHeight = (int) (height * rate);
        newWidth = maxResolution;
      }
    }
    else {
      if (maxResolution < height) {
        rate = maxResolution / (float) height;
        newWidth = (int) (width * rate);
        newHeight = maxResolution;
      }
    }
    return Bitmap.createScaledBitmap(originalImg, newWidth, newHeight, true);
  }

  /**
   * 문자열 str이 이미지 경로 인지 여부를 확인 한다.
   *
   * @param str 이미지 경로인지 체크할 문자열
   * @return true일 경우 이미지 경로
   */
  public static boolean isImagePath(String str) {
    if (!TextUtils.isEmpty(str)) {
      return (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".webp") || str.endsWith(".gif"));
    }
    return false;
  }

  /**
   * 파일 하나를 삭제 한다.
   *
   * @param file 삭제할 파일의 경로 혹은 파일 객체
   * @param <T>  String or File instance
   * @return true일 경우 파일 삭제가 성공적으로 수행 됨.
   */
  public static <T> boolean fileDelete(T file) {
    if (file != null) {
      File targetFile = null;
      if (file instanceof String) {
        targetFile = new File((String) file);
      }
      else if (file instanceof File) {
        targetFile = (File) file;
      }
      else {
        Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
        return false;
      }
      return targetFile.delete();
    }
    return false;
  }


  /**
   * 파일 하나를 이동 한다. AsyncTask등에 태워서 사용 할 것
   *
   * @param fromPath 이동할 파일의 절대 경로
   * @param target   이동할 경로 혹은 파일 객체
   * @param <T>      String or File instance
   * @return true일 경우 이동이 성공적으로 수행 됨.
   */
  public static <T> boolean fileMove(String fromPath, T target) {
    if (!TextUtils.isEmpty(fromPath)) {
      if (target != null) {
        File targetFile = null;
        if (target instanceof String) {
          targetFile = new File((String) target);
        }
        else if (target instanceof File) {
          targetFile = (File) target;
        }
        else {
          // Uri는 getPathFromUri()메소드를 활용 할 것.
          Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
          return false;
        }

        File fromFile = new File(fromPath);
        if (fromFile.exists()) {
          if (!fromFile.renameTo(targetFile)) {
            // File의 renameTo는 파일을 정상적으로 이동시키지 못하는 경우가 있다.
            // 또한 이에 대한 예외조차 없다.
            try {
              // 기존 파일을 복사 하고 난 뒤 원본을 삭제 한다.
              FileInputStream fis = new FileInputStream(fromFile);
              FileOutputStream fos = new FileOutputStream(targetFile);

              byte[] buf = new byte[1024];
              int read = 0;
              while ((read = fis.read(buf, 0, buf.length)) != -1) {
                fos.write(buf, 0, read);
              }
              fis.close();
              fos.close();

              return fromFile.delete();

            } catch (FileNotFoundException fnfe) {
              Log.e(TAG, fnfe.getMessage());
              return false;
            } catch (IOException ioe) {
              Log.e(TAG, ioe.getMessage());
              return false;
            }
          }
          else {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * 파일 하나를 복사 한다. AsyncTask등에 태워서 처리 할 것
   *
   * @param from   복사할 파일의 절대 경로 혹은 객체
   * @param target 저장할 파일의 경로 혹은 객체
   * @param <T>    String or File instance
   * @return true일 경우 파일 복사가 성공적으로 수행 됨.
   */
  public static <T> boolean fileCopy(T from, T target) {
    if (from != null) {
      File fromFile = null;
      if (from instanceof String) {
        fromFile = new File((String) from);
      }
      else if (from instanceof File) {
        fromFile = (File) from;
      }
      else {
        Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
        return false;
      }

      if (fromFile.exists()) {
        File targetFile = null;
        if (target != null) {
          if (target instanceof String) {
            targetFile = new File((String) target);
          }
          else if (target instanceof File) {
            targetFile = (File) target;
          }
          else {
            Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
            return false;
          }

          try {
            FileInputStream fis = new FileInputStream(fromFile);
            FileOutputStream fos = new FileOutputStream(targetFile);
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = fis.read(buffer, 0, 1024)) != -1) {
              fos.write(buffer, 0, readCount);
            }
            fos.close();
            fis.close();

          } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
            return false;
          }

          if (targetFile.length() > 0) {
            return true;
          }
        }
      }
    }
    return false;
  }

}
