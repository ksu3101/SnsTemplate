package kr.swkang.snstemplate.utils.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author KangSung-Woo
 * @since 2016/07/07
 */
public class BasePresenter {
  private CompositeSubscription compositeSubscription;

  public BasePresenter() {
    this.compositeSubscription = new CompositeSubscription();
  }

  @CallSuper
  public Subscription addSubscriber(@NonNull final Subscription subscriber) {
    if (compositeSubscription != null) {
      compositeSubscription.add(subscriber);
    }
    return subscriber;
  }

  @CallSuper
  public void destroy() {
    if (compositeSubscription != null) {
      compositeSubscription.unsubscribe();
    }
  }

  protected final String getTag(@NonNull Class<? extends BasePresenter> clazz) {
    return clazz.getClass().getSimpleName();
  }

}
