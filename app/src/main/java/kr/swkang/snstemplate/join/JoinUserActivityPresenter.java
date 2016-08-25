package kr.swkang.snstemplate.join;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import kr.swkang.snstemplate.join.model.ValidationUserInfoResultCode;
import kr.swkang.snstemplate.utils.SwObservable;
import kr.swkang.snstemplate.utils.Utils;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;
import kr.swkang.snstemplate.utils.mvp.models.UserInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author KangSung-Woo
 * @since 2016/08/23
 */
public class JoinUserActivityPresenter
    extends BasePresenter {

  private View view;

  public JoinUserActivityPresenter(@NonNull View viewImpl) {
    this.view = viewImpl;
  }

  public void startSignUpUser(@Nullable final String profileImgCachedURI,
                              @NonNull final String userIdEmail,
                              @NonNull final String password) {
    SwObservable observable = new SwObservable(
        this,
        Observable.create(
            new Observable.OnSubscribe<Integer>() {
              @Override
              public void call(Subscriber<? super Integer> subscriber) {

                // DUMMY RESULT CODE
                int resultCode = ValidationUserInfoResultCode.COMPLETE.getValue();

                try {
                  Thread.sleep(1500);
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
              view.onError(getTag(JoinUserActivityPresenter.class), e.getMessage());
            }
          }

          @Override
          public void onNext(Integer resultCodeValue) {
            if (view != null) {
              final ValidationUserInfoResultCode resultCode = ValidationUserInfoResultCode.parseFromValue(resultCodeValue);

              // user info (Dummy)
              final UserInfo userInfo = Utils.createDummyUserInfo(profileImgCachedURI, userIdEmail, password);

              // callback
              view.resultOfSignUp(userInfo, resultCode);

            }
            onCompleted();
          }
        }
    );
  }

  public interface View
      extends BaseView {
    void resultOfSignUp(@NonNull UserInfo signedUserInfo, @NonNull ValidationUserInfoResultCode resultCode);
  }

}
