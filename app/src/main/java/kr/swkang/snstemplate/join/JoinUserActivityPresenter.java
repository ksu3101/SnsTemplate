package kr.swkang.snstemplate.join;

import android.support.annotation.NonNull;

import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;

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

  public interface View
      extends BaseView {
  }

}
