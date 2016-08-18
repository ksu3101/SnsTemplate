package kr.swkang.snstemplate;

import android.os.Bundle;

import kr.swkang.snstemplate.utils.common.BaseActivity;
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
    setContentView(R.layout.activity_splash);

    // 앱 최초 실행시 해야 할 작업들을 여기에서 한다.

    // 예를 들면,
    // 1. 현재 서버 서비스 동작중인지 여부 체크
    // 1.1 서비스 중지 알림 팝업 등장 `isDisabledService()` -> 앱 종료
    presenter.checkServiceStatus();

    // 2. 앱의 최신 버전인지 여부 체크 (서버와의 통신)
    // 2.1 최신버전이 아닐 경우 `isNotLastesAppVersion()` -> 팝업 등장 -> 구글 플레이 스토어 강제 이동

    // 3. 저장된 사용자 데이터가 있는지 체크
    // 3.1 있을 경우 자동 로그인 실행
    // 3.1.1 서버 콜백에 따른 처리 (등록되지 않은 유저, 차단된 유저 등)
    // 3.2 없을 경우 로그인 화면으로 이동 `completeValidationUser()`

  }

  @Override
  public void isDisabledService() {
    // 서버의 서비스가 현재 작동중이 아닐 경우 -> 팝업 등장
    showDialog_DisableServices();
  }

  @Override
  public void isNotLastesAppVersion() {

  }

  @Override
  public void completeValidationUser() {

  }
}
