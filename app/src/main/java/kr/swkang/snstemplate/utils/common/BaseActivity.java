package kr.swkang.snstemplate.utils.common;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.utils.common.dialogs.SwDialog;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;
import kr.swkang.swimageview.utils.RoundedDrawableParams;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public abstract class BaseActivity
    extends AppCompatActivity
    implements BaseView {
  private BasePresenter basePresenter;
  private SwDialog      dialog;

  // - - Abstract methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public abstract BasePresenter attachPresenter();

  // - - Life cycle methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @CallSuper
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.basePresenter = attachPresenter();
  }

  @CallSuper
  @Override
  protected void onDestroy() {
    if (basePresenter != null) {
      // unscribe registered Subscriptions
      basePresenter.destroy();
    }
    if (dialog != null) {
      dialog.dismiss();
    }
    super.onDestroy();
  }

  // - - Implements methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @CallSuper
  @Override
  public void onError(String tag, String message) {
    Log.e(tag != null ? tag : "BaseActivity", message != null ? message : "Message is null.");
  }

  // - - Common methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public final void showDialog_DisableServices() {
    showDialog(
        null, null,
        getString(R.string.error_disabled_services_title),
        getString(R.string.error_disabled_servcies_message),
        getString(R.string.c_ok),
        null,
        null // if is dismiss only has action.
    );
  }

  // - - functional methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  private void showDialog(Drawable iconDrawable,
                          RoundedDrawableParams iconDrawableParams,
                          @NonNull String title,
                          @Nullable String message,
                          String positiveBtnTitle,
                          String negativeBtnTitle,
                          SwDialog.SwDialogOnButtonClickListener clickListener) {
    if (dialog != null && dialog.isShowing()) {
    }
    else {
      dialog = new SwDialog.Builder(this)
          .icon(iconDrawable, iconDrawableParams)
          .title(title)
          .message(message)
          .positiveButton(positiveBtnTitle)
          .negativeButton(negativeBtnTitle)
          .buttonClickListener(clickListener)
          .build();
      dialog.show();
    }
  }

}
