package kr.swkang.snstemplate.login;

import android.support.annotation.NonNull;

import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;

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

  public interface View
      extends BaseView {

  }
}
