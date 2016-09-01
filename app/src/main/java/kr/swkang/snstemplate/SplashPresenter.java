package kr.swkang.snstemplate;

import android.support.annotation.NonNull;

import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.utils.widgets.SwObservable;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public class SplashPresenter
    extends BasePresenter {
  private View view;

  public SplashPresenter(View activity) {
    this.view = activity;
  }

  /**
   * 1. 현재 서버 서비스 동작중인지 여부 체크
   * 1.1 서비스 중지 알림 팝업 등장 -> 앱 종료
   */
  public void checkServiceStatus() {
    final SwObservable observable = new SwObservable(
        this,
        Observable.create(
            new Observable.OnSubscribe<Boolean>() {
              @Override
              public void call(Subscriber<? super Boolean> subscriber) {
                try {
                  Thread.sleep(500);
                } catch (InterruptedException ie) {
                  subscriber.onError(ie);
                } finally {
                  subscriber.onNext(true);
                }
              }
            }
        ).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    );
    observable.subscribe(
        new Subscriber<Boolean>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
            if (view != null) {
              view.onError(getTag(SplashPresenter.class), e.getMessage());
            }
          }

          @Override
          public void onNext(Boolean result) {
            if (result) {
              // 앱 버전 체크
              checkLastestAppVersion();
            }
            else {
              if (view != null) {
                // 현재 서비스 이용 불가능 -> 종료
                view.isDisabledService();
              }
            }
            onCompleted();
          }
        }
    );
  }

  /**
   * 2. 앱의 최신 버전인지 여부 체크 (서버와의 통신)
   * 2.1 최신버전이 아닐 경우 -> 팝업 등장 -> 구글 플레이 스토어 강제 이동
   */
  public void checkLastestAppVersion() {
    final SwObservable observable = new SwObservable(
        this,
        Observable.create(
            new Observable.OnSubscribe<Boolean>() {
              @Override
              public void call(Subscriber<? super Boolean> subscriber) {

                try {
                  Thread.sleep(500);
                } catch (InterruptedException ie) {
                  subscriber.onError(ie);
                } finally {
                  subscriber.onNext(true);
                }
              }
            }
        ).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    );
    observable.subscribe(
        new Subscriber<Boolean>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
            if (view != null) {
              view.onError(getTag(SplashPresenter.class), e.getMessage());
            }
          }

          @Override
          public void onNext(Boolean result) {
            if (result) {
              // start auto login
              startUserAutoLogin();
            }
            else {
              if (view != null) {
                view.isNotLastesAppVersion();
              }
            }
            onCompleted();
          }
        }
    );
  }

  /**
   *
   */
  public void startUserAutoLogin() {
    final SwObservable observable = new SwObservable(
        this,
        Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {

                // 저장된 로그인 사용자의 기록이 있나 체크
                // -> 있을 경우 -> 서버에 자동 로그인 시도
                // -> 없을 경우 -> 로그인 화면으로 이동

                final int resultCode = LoginResultCode.FAILED_WRONG_INFOS.getValue();

                try {
                  Thread.sleep(500);
                } catch (InterruptedException ie) {
                  subscriber.onError(ie);
                } finally {
                  subscriber.onNext(resultCode);
                }
              }
            }
        ).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    );
    observable.subscribe(
        new Subscriber<Integer>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
            if (view != null) {
              view.onError(getTag(SplashPresenter.class), e.getMessage());
            }
          }

          @Override
          public void onNext(Integer resultCodeInt) {
            final LoginResultCode resultCode = LoginResultCode.parseFromValue(resultCodeInt);
            if (view != null) {
              view.resultOfAutoLoginUser(resultCode);
            }
            onCompleted();
          }
        }
    );
  }

  public interface View
      extends BaseView {
    void isDisabledService();

    void isNotLastesAppVersion();

    void resultOfAutoLoginUser(@NonNull LoginResultCode resultCode);
  }

}
