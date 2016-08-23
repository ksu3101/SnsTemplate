package kr.swkang.snstemplate.showcase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.utils.common.BaseActivity;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.widgets.viewpagers.pagerindicator.ViewPagerIndicator;

/**
 * @author KangSung-Woo
 * @since 2016/08/23
 */
public class ShowCaseActivity
    extends BaseActivity {

  @BindView(R.id.showcase_vp)
  ViewPager          viewPager;
  @BindView(R.id.showcase_vp_indicator)
  ViewPagerIndicator indicator;
  @BindView(R.id.showcase_tv_skip)
  TextView           tvSkip;
  @BindView(R.id.showcase_ibtn_left)
  ImageButton        ibtnLeft;
  @BindView(R.id.showcase_ibtn_right)
  ImageButton        ibtnRight;

  @Override
  public BasePresenter attachPresenter() {
    return null;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.showcase_activity);
    ButterKnife.bind(this);
  }

  @OnClick({R.id.showcase_tv_skip, R.id.showcase_ibtn_left, R.id.showcase_ibtn_right})
  public void onClick(View v) {
    if (v.getId() == R.id.showcase_tv_skip) {

    }
    else if (v.getId() == R.id.showcase_ibtn_left) {

    }
    else if (v.getId() == R.id.showcase_ibtn_right) {

    }
  }

}
