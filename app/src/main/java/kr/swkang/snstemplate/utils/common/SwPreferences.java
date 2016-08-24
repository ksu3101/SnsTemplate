package kr.swkang.snstemplate.utils.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class SwPreferences {

  private static final String K_VISIT_SHOWCASE_SCREEN = "has_visit_showcase_screen";

  /**
   *
   * @param context
   * @return
   */
  public static boolean hasVisitShowcaseScreen(@NonNull Context context) {
    return preferencesLoad_Boolean(context, K_VISIT_SHOWCASE_SCREEN);
  }

  public static boolean saveHasVisitShowcaseScreen(@NonNull Context context) {
    return preferenceSave(context, K_VISIT_SHOWCASE_SCREEN, true);
  }

  public static void resetHasVisitShowcaseScreen(@NonNull Context context) {
    preferencesRemove(context, K_VISIT_SHOWCASE_SCREEN);
  }

  /**
   * (G) 앱 설정값을 저장한다.
   *
   * @param context Context
   * @param key     저장할 값의 키.
   * @param value   저장할 값 (Integer, Long, Boolean, Float, String)
   * @return 설정값의 저장 여부.
   */
  public static <T> boolean preferenceSave(Context context, String key, T value) {
    boolean result = false;

    if (value != null && context != null) {
      SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = pref.edit();

      if (value instanceof Integer) {
        editor.putInt(key, (Integer) value);
      }
      else if (value instanceof Long) {
        editor.putLong(key, (Long) value);
      }
      else if (value instanceof Boolean) {
        editor.putBoolean(key, (Boolean) value);
      }
      else if (value instanceof Float) {
        editor.putFloat(key, (Float) value);
      }
      else if (value instanceof String) {
        editor.putString(key, (String) value);
      }
      else {
        Log.e("Utils", ">> Save preferences ERROR : 데이터 타입이 유효하지 않습니다. 데이터 타입은"
            + " Integer, Long, Float, Boolean, String 만 유효합니다.");
      }
      editor.apply();
      result = true;
    }

    return result;
  }

  /**
   * 저장된 앱 설정값중 문자열을 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 문자열이나 기본값(-1, "", false)
   */
  public static String preferencesLoad_String(Context context, String key) {
    String result = null;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getString(key, "");
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Integer를 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 int 정수. 오류혹은 저장한 값이 없을시 -1.
   */
  public static int preferencesLoad_Int(Context context, String key) {
    int result = -1;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getInt(key, -1);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Long를 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 long 정수. 오류혹은 저장한 값이 없을시 -1.
   */
  public static long preferencesLoad_Long(Context context, String key) {
    long result = -1;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getLong(key, -1);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Float을 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 float 실수. 오류혹은 저장한 값이 없을시 -1.
   */
  public static float preferencesLoad_Float(Context context, String key) {
    float result = -1;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getFloat(key, -1);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Boolean 불러온다.
   *
   * @param context Context
   * @param key     저장한 값의 키.
   * @return 저장한 boolean. 오류혹은 저장한 값이 없을시 false.
   */
  public static boolean preferencesLoad_Boolean(Context context, String key) {
    boolean result = false;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getBoolean(key, false);
    }
    return result;
  }

  /**
   * 저장된 앱 설정값중 Boolean 불러온다.
   *
   * @param context  Context
   * @param key      저장한 값의 키
   * @param defValue 저장한 값이 없거나 할 때의 기본 값.
   * @return 저장한 boolean, 오류 혹은 저장한 값이 없을시 defValue를 반환
   */
  private static boolean preferencesLoad_Boolean(Context context, String key, boolean defValue) {
    boolean result = false;
    SharedPreferences pref = preferencesLoad(context);
    if (pref != null) {
      result = pref.getBoolean(key, defValue);
    }
    return result;
  }

  /**
   * SharedPreferences 를 생성한다.
   *
   * @param context Context
   * @return SharedPreferences or null.
   */
  private static SharedPreferences preferencesLoad(Context context) {
    return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
  }

  /**
   * key에 해당하는 저장된 Preference의 value를 삭제 한다.
   *
   * @param context Context
   * @param key     삭제할 value의 key값.
   */
  private static void preferencesRemove(Context context, String key) {
    SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    editor.remove(key);
    editor.apply();
  }
}
