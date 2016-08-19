package kr.swkang.snstemplate.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.utils.common.BaseActivity;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class MainActivity
    extends BaseActivity
    implements MainActivityPresenter.View {

  private MainActivityPresenter presenter;

  @Override
  public BasePresenter attachPresenter() {
    this.presenter = new MainActivityPresenter(this);
    return presenter;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
  }

}
