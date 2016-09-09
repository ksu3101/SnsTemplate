package kr.swkang.snstemplate.utils.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.join.JoinUserActivity;
import kr.swkang.snstemplate.login.LoginActivity;
import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.main.MainActivity;
import kr.swkang.snstemplate.showcase.ShowCaseActivity;
import kr.swkang.snstemplate.utils.common.dialogs.SwDialog;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public abstract class BaseActivity
    extends AppCompatActivity
    implements BaseView {
  protected SwDialog      dialog;
  private   BasePresenter basePresenter;
  private TransitionStyle transitionStyle = TransitionStyle.NORMAL;

  // - - Abstract methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public abstract BasePresenter attachPresenter();

  // - - Life cycle methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @CallSuper
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.basePresenter = attachPresenter();
  }

  @Override
  public void finish() {
    super.finish();
    if (transitionStyle != null) {
      switch (transitionStyle) {
        case VERTICAL: {
          // enter activity : 알파효과와 함께 등장 - 새로 등장
          // exit activity : 위에서 아래로 사라짐
          overridePendingTransition(R.anim.default_alpha_in, R.anim.slide_out_from_up_to_bottom);
        }
        break;

        case VER_TO_VER: {
          // enter activity : bottom -> up
          // exit activity : up -> bottom
          overridePendingTransition(R.anim.slide_in_from_bottom_to_up, R.anim.slide_out_from_up_to_bottom);
        }
        break;

        case HORIZONTAL: {
          // enter activity : 알파효과와 함께 등장 - 새로 등장
          // exit activity : 왼쪽에서 오른쪽으로 사라짐
          overridePendingTransition(R.anim.default_alpha_in, R.anim.slide_in_from_left_to_right);
        }
        break;

        case HOR_TO_HOR: {
          overridePendingTransition(R.anim.slide_out_from_right_to_left, R.anim.slide_in_from_left_to_right);
        }
        break;

        case FADE: {
          // enter activity : 알파효과와 함께 등장 - 새로 등장
          // exit activity : 알파효과로 사라짐
          overridePendingTransition(R.anim.slide_in_from_bottom_to_up, R.anim.slide_out_from_up_to_bottom);
        }
        break;

        case VER_CAR_PROFILE: {
          // FINISH
          // (위에서 기존의 액티비티가 밀고 내려옴)
          // enter Activity : [NEW]    translate from up(-1) to bottom(0);
          // exit Activity :  [FINISH] translate from up(0) to bottom(1);
          overridePendingTransition(R.anim.slide_cp_minus_to_zero, R.anim.slide_cp_zero_to_full);
        }
        break;

        default: {
          // ignore case
        }
        break;
      }
    }

  }

  @CallSuper
  @Override
  protected void onDestroy() {
    Picasso.with(this).cancelTag(this);
    if (dialog != null) {
      dialog.dismiss();
    }
    if (basePresenter != null) {
      // unscribe registered Subscriptions
      basePresenter.destroy();
    }
    super.onDestroy();
  }

  // - - Implements methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @CallSuper
  @Override
  public void onError(String tag, String message) {
    Log.e(tag != null ? tag : "BaseActivity", message != null ? message : "Message is null.");
  }

  // - - Common methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public SwApplication getApplications() {
    return SwApplication.getInstance();
  }

  /**
   * 액티비티 트렌지션을 추가 한다.
   * Intent를 통해서 startActivity() 하고 난 뒤에 이 메소드를 호출 하면 된다.
   *
   * @param style 트랜지션 스타일 (VERTICAL, HORIZONTAL, FADE)
   */
  public void setEnterOverridePendingTransition(TransitionStyle style) {
    switch (style) {
      case VERTICAL: {
        // enter activity : 아래에서 위로 등장 - 새로 등장
        // exit activity : 알파효과로 사라짐
        overridePendingTransition(R.anim.slide_in_from_bottom_to_up, R.anim.default_alpha_out);
      }
      break;

      case VER_TO_VER: {
        // enter activity : up -> bottom
        // exit activity : bottom -> up
        overridePendingTransition(R.anim.slide_in_from_bottom_to_up, R.anim.slide_out_from_up_to_bottom);
      }
      break;

      case HORIZONTAL: {
        // enter activity : 오른쪽에서 왼쪽으로 등장 - 새로 등장
        // exit activity : 알파효과로 사라짐
        overridePendingTransition(R.anim.slide_out_from_right_to_left, R.anim.default_alpha_out);
      }
      break;

      case HOR_TO_HOR: {
        overridePendingTransition(R.anim.slide_out_from_right_to_left, R.anim.slide_in_from_left_to_right);
      }
      break;

      case FADE: {
        // enter activity : 알파효과와 등장 - 새로 등장
        // exit activity : 알파효과로 사라짐
        overridePendingTransition(R.anim.default_alpha_in, R.anim.default_alpha_out);
      }
      break;

      case VER_CAR_PROFILE: {
        // ENTER
        // (밑에서 새로운 액티비티가 밀고 올라옴)
        // enter Activity : [NEW]    translate from up(1) to bottom(0);
        // exit Activity :  [FINISH] translate from up(0) to bottom(-1);
        overridePendingTransition(R.anim.slide_cp_full_to_zero, R.anim.slide_cp_zero_to_minus);
      }
      break;

      default: {
        // ignore case
      }
      break;
    }
  }

  protected void setExitOverridePendingTransition(TransitionStyle style) {
    this.transitionStyle = style;
  }

  /**
   * 로그인 작업 후 결과에 따라서 분기 처리 한다.
   *
   * @param resultCode 로그인 결과값
   */
  @CallSuper
  public void processResultOfLogin(@NonNull LoginResultCode resultCode) {
    if (resultCode == LoginResultCode.SUCCESS) {
      if (SwPreferences.hasVisitShowcaseScreen(this)) {
        startActivity_Main();
      }
      else {
        startActivity_ShowCase();
      }
      finish();
    }
    else {
      String dlgTitle = getString(R.string.error_network_common_title);
      String dlgMsg = getString(R.string.error_network_common_message);
      SwDialog.OnDialogButtonClickListener clickListener = null;

      if (resultCode == LoginResultCode.FAILED_WRONG_INFOS) {
        // 잘못된 로그인 정보 -> 저장된 세션 삭제 하고 로그인 화면으로
        dlgTitle = getString(R.string.error_wrong_login_infos_title);
        dlgMsg = getString(R.string.error_wrong_login_infos_message);
        clickListener = new SwDialog.OnDialogButtonClickListener() {
          @Override
          public void onClicked(SwDialog dialog, View v) {
            // move to Login activity.
            dialog.dismiss();
            startActivity_Login();
          }
        };
      }

      else if (resultCode == LoginResultCode.FAILED_NOT_JOINED_USER) {
        // 존재하지 않는 사용자 -> 가입 화면으로 이동
        showDialog_JoinUser();
        return;
      }

      else if (resultCode == LoginResultCode.NOT_CONNECTED_NETWORK) {
        // 네트워크에 연결되지 않았을 경우
        dlgMsg = getString(R.string.error_network_disabled);
      }

      if (!isShowingDialog()) {
        dialog = new SwDialog.Builder(this)
            .title(dlgTitle)
            .message(dlgMsg)
            .positiveButton(getString(R.string.c_ok))
            .buttonClickListener(
                clickListener == null ?
                    new SwDialog.OnDialogButtonClickListener() {
                      @Override
                      public void onClicked(SwDialog dialog, View v) {
                        dialog.dismiss();
                      }
                    } : clickListener)
            .build();
        dialog.show();
      }
    }
  }

  /**
   * 존재 하지 않는 사용자
   */
  private void showDialog_JoinUser() {
    if (!isShowingDialog()) {
      dialog = new SwDialog.Builder(this)
          .title(getString(R.string.error_not_joined_user_title))
          .message(getString(R.string.error_not_joined_user_message))
          .positiveButton(getString(R.string.c_join))
          .negativeButton(getString(R.string.c_cancel))
          .buttonClickListener(
              new SwDialog.OnDialogButtonClickListener() {
                @Override
                public void onClicked(SwDialog dialog, View v) {
                  if (v.getId() == R.id.swdialog_btn_left) {
                    // move to Join user activity
                    startActivity_JoinUser();
                  }
                  dialog.dismiss();
                }
              }
          )
          .build();
      dialog.show();
    }
  }

  // - - Common / move to Activity methods - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public final void startActivity_Login() {
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
  }

  public final void startActivity_ShowCase() {
    Intent intent = new Intent(this, ShowCaseActivity.class);
    startActivity(intent);
  }

  public final void startActivity_JoinUser() {
    Intent intent = new Intent(this, JoinUserActivity.class);
    startActivity(intent);
  }

  public final void startActivity_ResetPassword() {

  }

  public final void startActivity_Main() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  // - - functional methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  protected boolean isShowingDialog() {
    return (dialog != null && dialog.isShowing());
  }

  // - - Enum values - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  /**
   * Activity transition effect style enum value
   */
  public enum TransitionStyle {
    /**
     * 세로 간 액티비티 전환. (Top - Bottom)
     */
    VERTICAL,
    /**
     *
     */
    VER_TO_VER,
    /**
     * 가로 간 액티비티 전환. (Left - RIght)
     */
    HORIZONTAL,
    /**
     *
     */
    HOR_TO_HOR,
    /**
     * Fade(Alpha) 액티비티 전환 (Fade in / Fade out)
     */
    FADE,
    /**
     * 마이페이지 -> 차량 수정 화면에서 사용되는 놈 (vertical)
     */
    VER_CAR_PROFILE,
    /**
     * 기본 액티비티 전환
     */
    NORMAL
  }
}
