package kr.swkang.snstemplate;

import android.os.Bundle;
import android.view.View;

import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.utils.Utils;
import kr.swkang.snstemplate.utils.common.BaseActivity;
import kr.swkang.snstemplate.utils.common.dialogs.SwDialog;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;

public class SplashActivity
    extends BaseActivity
    implements SplashPresenter.View {

  private SplashPresenter presenter;

  @Override
  public BasePresenter attachPresenter() {
    this.presenter = new SplashPresenter(this);
    return presenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_activity);

    // 인터넷에 연결되었는지 여부를 체크 한다.
    if (!Utils.isOnline(this)) {
      completeAutoLoginUser(LoginResultCode.NOT_CONNECTED_NETWORK);
    }
    else {
      // 앱 최초 실행시 해야 할 작업들을 여기에서 한다. (비동기 순차적으로)

      // 예를 들면,
      // 1. 현재 서버 서비스 동작중인지 여부 체크
      // 1.1 서비스 중지 알림 팝업 등장 `isDisabledService()` -> 앱 종료
      presenter.checkServiceStatus();

      // 2. 앱의 최신 버전인지 여부 체크 (서버와의 통신)
      // 2.1 최신버전이 아닐 경우 `isNotLastesAppVersion()` -> 팝업 등장 -> 구글 플레이 스토어 강제 이동

      // 3. 저장된 사용자 데이터가 있는지 체크
      // 3.1 있을 경우 자동 로그인 실행
      // 3.1.1 서버 콜백에 따른 처리 (등록되지 않은 유저, 차단된 유저 등) -> `completeAutoLoginUser(ResultCode)`
      // 3.2 없을 경우 로그인 화면으로 이동 `completeAutoLoginUser()`
    }


  }

  @Override
  public void isDisabledService() {
    // 서버의 서비스가 현재 작동중이 아닐 경우 -> 팝업 등장
    if (!isShowingDialog()) {
      dialog = new SwDialog.Builder(this)
          .title(getString(R.string.error_disabled_services_title))
          .message(getString(R.string.error_disabled_servcies_message))
          .positiveButton(getString(R.string.c_ok))
          .buttonClickListener(
              new SwDialog.SwDialogOnButtonClickListener() {
                @Override
                public void onClicked(SwDialog dialog, View v) {
                  dialog.dismiss();
                  finish();
                }
              }
          )
          .build();
      dialog.show();
    }
  }

  @Override
  public void isNotLastesAppVersion() {
    // 최신버전이 아닐 경우 -> 팝업 등장 -> 구글 플레이 스토어 강제 이동
    if (!isShowingDialog()) {
      dialog = new SwDialog.Builder(this)
          .title(getString(R.string.error_lastest_appversion_title))
          .message(getString(R.string.error_lastest_appversion_message))
          .positiveButton(getString(R.string.c_update))
          .buttonClickListener(
              new SwDialog.SwDialogOnButtonClickListener() {
                @Override
                public void onClicked(SwDialog dialog, View v) {
                  // move to google play store
                  dialog.dismiss();
                  finish();
                }
              }
          )
          .build();
      dialog.show();
    }
  }

  @Override
  public void completeAutoLoginUser(LoginResultCode resultCode) {
    if (resultCode == LoginResultCode.SUCCESS) {
      // start MainActivity
    }
    else {
      String dlgTitle = getString(R.string.error_network_common_title);
      String dlgMsg = getString(R.string.error_network_common_message);
      SwDialog.SwDialogOnButtonClickListener clickListener = null;

      if (resultCode == LoginResultCode.FAILED_WRONG_INFOS) {
        // 잘못된 로그인 정보 -> 저장된 세션 삭제 하고 로그인 화면으로
        dlgTitle = getString(R.string.error_wrong_login_infos_title);
        dlgMsg = getString(R.string.error_wrong_login_infos_message);
        clickListener = new SwDialog.SwDialogOnButtonClickListener() {
          @Override
          public void onClicked(SwDialog dialog, View v) {
            // move to Login activity.
            startActivity_Login();
          }
        };
      }

      else if (resultCode == LoginResultCode.FAILED_NOT_JOINED_USER) {
        // 가입하지 않은 사용자 -> 가입 화면으로 이동
        showDialog_JoinUser();
        return;
      }

      else if (resultCode == LoginResultCode.NOT_CONNECTED_NETWORK) {
        // 네트워크에 연결되지 않았을 경우
        dlgMsg = getString(R.string.error_network_disabled);
      }

      if(!isShowingDialog()) {
        dialog = new SwDialog.Builder(this)
            .title(dlgTitle)
            .message(dlgMsg)
            .positiveButton(getString(R.string.c_ok))
            .buttonClickListener(
                clickListener == null ?
                    new SwDialog.SwDialogOnButtonClickListener() {
                      @Override
                      public void onClicked(SwDialog dialog, View v) {
                        dialog.dismiss();
                        finish();
                      }
                    } : clickListener)
            .build(true);
      }

    }
  }

  private void showDialog_JoinUser() {
    if (!isShowingDialog()) {
      dialog = new SwDialog.Builder(this)
          .title(getString(R.string.error_not_joined_user_title))
          .message(getString(R.string.error_not_joined_user_message))
          .positiveButton(getString(R.string.c_join))
          .negativeButton(getString(R.string.c_cancel))
          .buttonClickListener(
              new SwDialog.SwDialogOnButtonClickListener() {
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
          .build(true);
    }

  }

}
