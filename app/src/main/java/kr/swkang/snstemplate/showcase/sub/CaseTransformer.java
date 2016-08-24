package kr.swkang.snstemplate.showcase.sub;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import kr.swkang.snstemplate.R;

/**
 * @author KangSung-Woo
 * @since 2016/08/24
 */
public class CaseTransformer
    implements ViewPager.PageTransformer {

  @Override
  public void transformPage(View page, float position) {
    final int pageWidth = page.getWidth();

    if (position < -1) {          // [-Infinity, -1]
      page.setAlpha(1);
    }

    else if (position <= 1) {     // [-1, 1]
      ImageView bg = (ImageView) page.findViewById(R.id.showcase_sub_f_ivBg);
      if (bg != null) {
        // parallax effect on Background imageview.
        bg.setTranslationX(-position * (pageWidth / 2));
      }

      // some more transform effect here..

    }

    else {                        // [1, +Infinity]
      page.setAlpha(1);
    }
  }

}
