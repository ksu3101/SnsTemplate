package kr.swkang.snstemplate.utils.widgets;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;

/**
 * @author KangSung-Woo
 * @since 2016/08/10
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TransitionListenerAdapter
    implements Transition.TransitionListener {
  @Override
  public void onTransitionStart(Transition transition) {
  }

  @Override
  public void onTransitionEnd(Transition transition) {
  }

  @Override
  public void onTransitionCancel(Transition transition) {
  }

  @Override
  public void onTransitionPause(Transition transition) {
  }

  @Override
  public void onTransitionResume(Transition transition) {
  }
}
