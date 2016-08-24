package kr.swkang.snstemplate.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.utils.Utils;
import kr.swkang.snstemplate.utils.common.BaseActivity;
import kr.swkang.snstemplate.utils.common.dialogs.SwDialog;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.models.UserInfo;
import kr.swkang.snstemplate.utils.widgets.ClearableEditText;
import kr.swkang.snstemplate.utils.widgets.StateButton;
import kr.swkang.spannabletextview.SpannableTextView;
import kr.swkang.spannabletextview.utils.SwClickableSpan;

/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public class LoginActivity
    extends BaseActivity
    implements LoginActivityPresenter.View {

  private LoginActivityPresenter presenter;

  @BindView(R.id.login_stv_find_pw)
  SpannableTextView stvFindPassword;
  @BindView(R.id.login_et_email)
  ClearableEditText etEmail;
  @BindView(R.id.login_et_password)
  ClearableEditText etPassword;
  @BindView(R.id.login_btn_login)
  StateButton       btnLogin;
  @BindView(R.id.login_tv_signup)
  TextView          tvBtnSignUp;

  @Override
  public BasePresenter attachPresenter() {
    this.presenter = new LoginActivityPresenter(this);
    return presenter;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);
    ButterKnife.bind(this);

    stvFindPassword.addSpan(
        new SpannableTextView.Span(getString(R.string.find_pw_desc1))
            .textSizeSP(13)
            .textColorRes(R.color.transparent_heavy_white)
            .build()
    );
    stvFindPassword.addSpan(
        new SpannableTextView.Span(getString(R.string.find_pw_desc2))
            .textSizeSP(13)
            .linkTextColorRes(R.color.transparent_heavy_white)
            .click(
                new SwClickableSpan(
                    ContextCompat.getColor(LoginActivity.this, R.color.flat_sun),
                    ContextCompat.getColor(LoginActivity.this, R.color.flat_sun_sel)
                ) {
                  @Override
                  public void onClick(View view) {
                    // TODO : move to reset password activity
                    Log.d("LoginActivity", "// span link clicked..");
                  }

                  @Override
                  public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                  }
                }
            )
            .build()
    );
    stvFindPassword.addSpan(
        new SpannableTextView.Span(getString(R.string.find_pw_desc3))
            .textSizeSP(13)
            .textColorRes(R.color.transparent_heavy_white)
            .build()
    );

  }

  @OnClick({R.id.login_btn_login, R.id.login_tv_signup})
  public void onClick(View view) {
    if (view.getId() == R.id.login_btn_login) {
      // hide keyboards
      Utils.hideSoftKeyboard(this);
      final String email = etEmail.getText()
                                  .toString();
      final String pw = etPassword.getText()
                                  .toString();
      checkInputsAndProcess(email, pw);
    }

    else if (view.getId() == R.id.login_tv_signup) {
      startActivity_JoinUser();
    }

  }

  private void setFocusEditText(@NonNull final EditText et, boolean clearText) {
    if (clearText) et.setText("");
    new Handler().postDelayed(
        new Runnable() {
          @Override
          public void run() {
            Utils.showSoftKeyboard(LoginActivity.this, et);
          }
        }
        , 150);
  }

  private void checkInputsAndProcess(final String email, final String pw) {
    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pw)) {
      etEmail.setEnabled(false);
      etPassword.setEnabled(false);
      tvBtnSignUp.setClickable(false);
      btnLogin.setButtonState(StateButton.STATE_WAITING);

      // start Login jobs
      presenter.startLoginJobs(email, pw);

    }
    else {
      String dlgTitle = getString(R.string.error_input_email_title);
      String dlgMsg = null;

      if (TextUtils.isEmpty(email)) {
        dlgMsg = getString(R.string.error_input_email_message);
      }
      else {
        dlgMsg = getString(R.string.error_input_pw_message);
      }
      if (!isShowingDialog()) {
        dialog = new SwDialog.Builder(this)
            .title(dlgTitle)
            .message(dlgMsg)
            .positiveButton(getString(R.string.c_ok))
            .buttonClickListener(
                new SwDialog.SwDialogOnButtonClickListener() {
                  @Override
                  public void onClicked(SwDialog dialog, View v) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(email)) {
                      setFocusEditText(etEmail, true);
                    }
                    else {
                      setFocusEditText(etPassword, true);
                    }
                  }
                }
            )
            .build();
        dialog.show();
      }
    }
  }

  @Override
  public void resultOfLogin(@NonNull UserInfo logginedUserInfo, @NonNull LoginResultCode resultCode) {
    this.processResultOfLogin(resultCode);

  }

  @Override
  public void processResultOfLogin(@NonNull LoginResultCode resultCode) {
    super.processResultOfLogin(resultCode);

    if (resultCode != LoginResultCode.SUCCESS) {
      etEmail.setEnabled(true);
      etPassword.setEnabled(true);
      tvBtnSignUp.setClickable(true);
      btnLogin.setButtonState(StateButton.STATE_ENABLED);
    }
  }

}
