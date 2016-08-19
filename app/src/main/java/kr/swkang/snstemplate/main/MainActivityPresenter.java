package kr.swkang.snstemplate.main;

import android.support.annotation.NonNull;

import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class MainActivityPresenter
    extends BasePresenter {

  private View view;

  public MainActivityPresenter(@NonNull View activity) {
    this.view = activity;
  }

  public interface View
      extends BaseView {

  }

}
