package kr.swkang.snstemplate.login;

import android.support.annotation.NonNull;
import android.util.Log;

import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.utils.SwObservable;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class LoginActivityPresenter
    extends BasePresenter {
  private View view;

  public LoginActivityPresenter(@NonNull View activity) {
    this.view = activity;
  }

  public void startLoginJobs(@NonNull String email, @NonNull String pw) {
    Log.d(getTag(LoginActivityPresenter.class), "/// email = " + email + ", password = " + pw);
    final SwObservable observable = new SwObservable(
        this,
        Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {

                int resultCode = LoginResultCode.SUCCESS.getValue();

                try {
                  Thread.sleep(100);
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
              view.onError(getTag(LoginActivityPresenter.class), e.getMessage());
            }
          }

          @Override
          public void onNext(Integer result) {
            if (view != null) {
              final LoginResultCode resultCode = LoginResultCode.parseFromValue(result);
              // 로그인 결과 콜백
              view.resultOfLogin(resultCode);
            }
            onCompleted();
          }
        }
    );
  }

  public interface View
      extends BaseView {
    void resultOfLogin(@NonNull LoginResultCode resultCode);
  }
}
