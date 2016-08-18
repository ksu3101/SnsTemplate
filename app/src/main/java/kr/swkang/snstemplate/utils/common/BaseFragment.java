package kr.swkang.snstemplate.utils.common;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;

/**
 * @author KangSung-Woo
 * @since 2016/08/11
 */
public abstract class BaseFragment
    extends Fragment
    implements BaseView {
  private BasePresenter basePresenter;

  public abstract BasePresenter attachPresenter();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.basePresenter = attachPresenter();
  }

  @Override
  public void onDestroy() {
    if (basePresenter != null) {
      basePresenter.destroy();
    }
    super.onDestroy();
  }

  @CallSuper
  @Override
  public void onError(String tag, String message) {
    Log.e(tag != null ? tag : "BaseActivity", message != null ? message : "Message is null.");
  }

}
