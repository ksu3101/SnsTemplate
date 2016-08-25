package kr.swkang.snstemplate.utils.common.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import kr.swkang.snstemplate.R;
import kr.swkang.swimageview.SwImageView;
import kr.swkang.swimageview.utils.RoundedDrawableParams;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public class SwDialog
    extends Dialog
    implements View.OnClickListener {

  public interface SwDialogOnButtonClickListener {
    void onClicked(SwDialog dialog, View v);
  }

  private Drawable                      iconDrawable;
  private RoundedDrawableParams         iconDrawableParams;
  private String                        title;
  private String                        message;
  private String                        positiveBtnTitle;
  private String                        negativeBtnTitle;
  private SwDialogOnButtonClickListener clickListener;

  private SwDialog(Context context, Builder b) {
    super(context);
    this.iconDrawable = b.iconDrawable;
    this.iconDrawableParams = b.iconDrawableParams;
    this.title = b.title;
    this.message = b.message;
    this.positiveBtnTitle = b.positiveBtnTitle;
    this.negativeBtnTitle = b.negativeBtnTitle;
    this.clickListener = b.clickListener;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // no title
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dialog_swcommon);
    // transparent backgorund
    getWindow().setBackgroundDrawable(new ColorDrawable(0));

    setIconDrawable(iconDrawable, iconDrawableParams);
    setTitleText(title);
    setMessage(message);
    setButtons();
  }

  @Override
  public void setTitle(CharSequence title) {
    this.setTitleText(title);
  }

  @Override
  public void setTitle(@StringRes int titleId) {
    this.setTitleText(getContext().getString(titleId));
  }

  public void showIconDrawable() {
    final SwImageView ivIcon = (SwImageView) findViewById(R.id.swdialog_iv_top);
    ivIcon.setVisibility(View.VISIBLE);
  }

  public void hideIconDrawable() {
    final SwImageView ivIcon = (SwImageView) findViewById(R.id.swdialog_iv_top);
    ivIcon.setVisibility(View.GONE);
  }

  public void setIconDrawable(@Nullable Drawable iconDrawable, @Nullable RoundedDrawableParams params) {
    final SwImageView ivIcon = (SwImageView) findViewById(R.id.swdialog_iv_top);
    if (iconDrawable != null) {
      if (params == null) {
        // set default parameter
        params = new RoundedDrawableParams();
      }
      ivIcon.setRoundedDrawableParams(params);
      ivIcon.setImageDrawable(iconDrawable);
      ivIcon.setVisibility(View.VISIBLE);
    }
    else {
      ivIcon.setVisibility(View.GONE);
    }
  }

  private void setTitleText(@Nullable CharSequence title) {
    final TextView tvTitle = (TextView) findViewById(R.id.swdialog_tv_title);
    if (tvTitle != null) {
      tvTitle.setText(title != null ? title : "");
      this.title = (title != null ? title.toString() : null);
    }
  }

  private void setMessage(@Nullable CharSequence message) {
    final TextView tvDesc = (TextView) findViewById(R.id.swdialog_tv_message);
    if (tvDesc != null) {
      tvDesc.setText(message != null ? message : "");
      this.message = (message != null ? message.toString() : null);
    }
  }

  private void setButtons() {
    final TextView tvPositiveBtn = (TextView) findViewById(R.id.swdialog_btn_left);
    final TextView tvNegativeBtn = (TextView) findViewById(R.id.swdialog_btn_right);
    if (tvPositiveBtn == null || tvNegativeBtn == null) {
      throw new NullPointerException("Dialog button is Null..");
    }

    tvPositiveBtn.setVisibility(View.GONE);
    tvNegativeBtn.setVisibility(View.GONE);

    tvPositiveBtn.setOnClickListener(this);
    tvNegativeBtn.setOnClickListener(this);

    if (positiveBtnTitle != null) {
      tvPositiveBtn.setText(positiveBtnTitle);
      tvPositiveBtn.setBackgroundResource(R.drawable.btn_dlg_left);
      tvPositiveBtn.setVisibility(View.VISIBLE);
    }
    if (negativeBtnTitle != null) {
      tvNegativeBtn.setText(negativeBtnTitle);
      tvNegativeBtn.setBackgroundResource(R.drawable.btn_dlg_right);
      tvNegativeBtn.setVisibility(View.VISIBLE);
    }
    else {
      // change button drawable
      tvPositiveBtn.setBackgroundResource(R.drawable.btn_dlg_full);
    }
  }

  public void setPositiveButtonEnable(boolean isEnable) {
    final TextView tvPositiveBtn = (TextView) findViewById(R.id.swdialog_btn_left);
    tvPositiveBtn.setEnabled(isEnable);
  }

  public void setNegativeButtonEnable(boolean isEnable) {
    final TextView tvNegativeBtn = (TextView) findViewById(R.id.swdialog_btn_right);
    tvNegativeBtn.setEnabled(isEnable);
  }

  @Override
  public void onClick(View v) {
    if (clickListener != null) {
      clickListener.onClicked(this, v);
    }
    else {
      if (v.getId() == R.id.swdialog_btn_left || v.getId() == R.id.swdialog_btn_right) {
        dismiss();
      }
    }
  }

  public static class Builder {
    Context                       context;
    Drawable                      iconDrawable;
    RoundedDrawableParams         iconDrawableParams;
    String                        title;
    String                        message;
    String                        positiveBtnTitle;
    String                        negativeBtnTitle;
    SwDialogOnButtonClickListener clickListener;

    public Builder(@NonNull Context context) {
      this.context = context;
      this.iconDrawable = null;
      this.iconDrawableParams = null;
      this.title = "";
      this.message = null;
      this.positiveBtnTitle = null;
      this.negativeBtnTitle = null;
      this.clickListener = null;
    }

    public Builder icon(@NonNull Drawable iconDrawable, @Nullable RoundedDrawableParams params) {
      this.iconDrawable = iconDrawable;
      this.iconDrawableParams = params;
      return this;
    }

    public Builder icon(@DrawableRes int iconDrawableResId, @Nullable RoundedDrawableParams params) {
      this.iconDrawable = ContextCompat.getDrawable(context, iconDrawableResId);
      this.iconDrawableParams = params;
      return this;
    }

    public Builder title(@Nullable String titleStr) {
      this.title = titleStr;
      return this;
    }

    public Builder title(@StringRes int titleStrResId) {
      this.title = context.getString(titleStrResId);
      return this;
    }

    public Builder message(@Nullable String message) {
      this.message = message;
      return this;
    }

    public Builder message(@StringRes int msgStrResId) {
      this.message = context.getString(msgStrResId);
      return this;
    }

    public Builder positiveButton(@NonNull String btnTitle) {
      this.positiveBtnTitle = btnTitle;
      return this;
    }

    public Builder positiveButton(@StringRes int btnTitleResId) {
      return positiveButton(context.getString(btnTitleResId));
    }

    public Builder negativeButton(@NonNull String btnTitle) {
      this.negativeBtnTitle = btnTitle;
      return this;
    }

    public Builder negativeButton(@StringRes int btnTitleResId) {
      return negativeButton(context.getString(btnTitleResId));
    }

    public Builder buttonClickListener(@NonNull SwDialogOnButtonClickListener clickListener) {
      this.clickListener = clickListener;
      return this;
    }

    public SwDialog build() {
      return new SwDialog(context, this);
    }

    public SwDialog buildAndShow() {
      final SwDialog dlg = new SwDialog(context, this);
      dlg.show();
      return dlg;
    }

  }

}
