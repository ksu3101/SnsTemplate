package kr.swkang.snstemplate.showcase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.showcase.sub.CaseFragment;
import kr.swkang.snstemplate.showcase.sub.CaseTransformer;
import kr.swkang.snstemplate.utils.Utils;
import kr.swkang.snstemplate.utils.common.BaseActivity;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.widgets.viewpagers.pagerindicator.ViewPagerIndicator;

/**
 * @author KangSung-Woo
 * @since 2016/08/23
 */
public class ShowCaseActivity
    extends BaseActivity {
  private static final int VP_SIZE = 3;

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

  private ShowCaseVpAdapter adapter;

  @Override
  public BasePresenter attachPresenter() {
    return null;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.showcase_activity);
    ButterKnife.bind(this);

    adapter = new ShowCaseVpAdapter(getSupportFragmentManager());
    viewPager.setAdapter(adapter);

    indicator.setNormalItemDrawable(R.drawable.shape_vp_indicator_normal);
    indicator.setSelectedItemDrawable(R.drawable.shape_vp_indicator_selected);
    indicator.setCircleMarginDP(8);
    indicator.setViewPager(viewPager);

    viewPager.setPageMargin((int) Utils.convertDpiToPixel(getResources(), 5));
    viewPager.setPageTransformer(false, new CaseTransformer());

    viewPager.addOnPageChangeListener(
        new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
          }

          @Override
          public void onPageSelected(int position) {
            if (position == 0) {
              ibtnLeft.setVisibility(View.GONE);
            }
            else if (position == VP_SIZE - 1) {
              ibtnRight.setVisibility(View.GONE);
            }
            else {
              ibtnLeft.setVisibility(View.VISIBLE);
              ibtnRight.setVisibility(View.VISIBLE);
            }
          }

          @Override
          public void onPageScrollStateChanged(int state) {
          }
        }
    );
    viewPager.setCurrentItem(0);

  }

  @OnClick({R.id.showcase_tv_skip, R.id.showcase_ibtn_left, R.id.showcase_ibtn_right})
  public void onClick(View v) {
    int selectedPos = viewPager.getCurrentItem();

    if (v.getId() == R.id.showcase_tv_skip) {
      // FIXME : save Preference
      // SwPreferences.saveHasVisitShowcaseScreen(this);

      // start main Activity
      startActivity_Main();

    }
    else if (v.getId() == R.id.showcase_ibtn_left) {
      if (selectedPos > 0) {
        viewPager.setCurrentItem(--selectedPos, true);
      }
    }
    else if (v.getId() == R.id.showcase_ibtn_right) {
      if (selectedPos < VP_SIZE) {
        viewPager.setCurrentItem(++selectedPos, true);
      }
    }
  }

  private class ShowCaseVpAdapter
      extends FragmentStatePagerAdapter {

    public ShowCaseVpAdapter(@NonNull FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      final Bundle args = new Bundle();
      args.putInt(CaseFragment.BUNDLE_KEY_POSITION, position);
      return CaseFragment.newInstance(args);
    }

    @Override
    public int getCount() {
      return VP_SIZE;
    }
  }

}
