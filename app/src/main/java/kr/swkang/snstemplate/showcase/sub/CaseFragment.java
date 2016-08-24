package kr.swkang.snstemplate.showcase.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.utils.common.BaseFragment;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;

/**
 * @author KangSung-Woo
 * @since 2016/08/24
 */
public class CaseFragment
    extends BaseFragment {
  private static final String TAG                 = CaseFragment.class.getSimpleName();
  public static final  String BUNDLE_KEY_POSITION = TAG + "_BUNDLE_KEY_POSITION";

  @BindView(R.id.showcase_sub_f_container)
  RelativeLayout containerLayout;
  @BindView(R.id.showcase_sub_f_ivBg)
  ImageView      ivBg;
  @BindView(R.id.showcase_sub_f_ivIcon)
  ImageView      ivIcons;
  @BindView(R.id.showcase_sub_f_tv_desc)
  TextView       tvDesc;

  public static Fragment newInstance(Bundle args) {
    CaseFragment fragment = new CaseFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public BasePresenter attachPresenter() {
    return null;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.showcase_sub_fragment, container, false);

    if (rootView != null) {
      ButterKnife.bind(this, rootView);

      Bundle args = getArguments();
      if (args != null) {
        final int position = args.getInt(BUNDLE_KEY_POSITION, 0);

        tvDesc.setText(String.valueOf(position));

        switch (position) {
          case 0: {
            ivBg.setImageResource(R.drawable.test01);
            break;
          }
          case 1: {
            ivBg.setImageResource(R.drawable.test02);
            break;
          }
          case 2: {
            ivBg.setImageResource(R.drawable.test03);
            break;
          }
        }
      }
    }
    return rootView;
  }

}
