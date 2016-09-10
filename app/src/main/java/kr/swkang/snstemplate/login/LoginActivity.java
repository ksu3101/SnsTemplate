package kr.swkang.snstemplate.login;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.utils.widgets.TextWatcherAdapter;
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
  private final TextWatcherAdapter textWatcherAdapter = new TextWatcherAdapter() {
    @Override
    public void afterTextChanged(Editable s) {
      if (etEmail != null && etPassword != null && btnLogin != null) {
        if (btnLogin.getState() != StateButton.STATE_WAITING) {
          if (etEmail.getText().length() > 0 && etPassword.getText().length() > 0) {
            btnLogin.setState(StateButton.STATE_ENABLED);
          }
          else {
            btnLogin.setState(StateButton.STATE_DISABLED);
          }
        }
      }
    }
  };

  @BindView(R.id.login_container)
  RelativeLayout    container;
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
  @BindView(R.id.login_facebook_container)
  LinearLayout      containerFbLogin;
  @BindView(R.id.login_facebook_login_btn)
  LoginButton       fbBtnLogin;

  private AnimationDrawable animDrawable;

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

    animDrawable = (AnimationDrawable) (container.getBackground() != null && container.getBackground() instanceof AnimationDrawable ?
        container.getBackground() : null);
    if (animDrawable != null) {
      animDrawable.setEnterFadeDuration(10000);
      animDrawable.setExitFadeDuration(10000);
    }

    etEmail.addTextChangedListener(textWatcherAdapter);
    etPassword.addTextChangedListener(textWatcherAdapter);

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

  @Override
  protected void onResume() {
    super.onResume();
    if (animDrawable != null && !animDrawable.isRunning()) {
      animDrawable.start();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (animDrawable != null && animDrawable.isRunning()) {
      animDrawable.stop();
    }
  }

  @OnClick({R.id.login_btn_login, R.id.login_tv_signup, R.id.login_facebook_container})
  public void onClick(View view) {
    if (view.getId() == R.id.login_btn_login) {
      // hide keyboards
      Utils.hideSoftKeyboard(this);
      final String email = etEmail.getText()
                                  .toString();
      final String pw = etPassword.getText()
                                  .toString();
      startLoginJobBeforeValidationInputs(email, pw);
    }

    else if (view.getId() == R.id.login_tv_signup) {
      startActivity_JoinUser();
    }

    else if (view.getId() == R.id.login_facebook_container) {
      fbBtnLogin.performClick();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // process Facebook login result codes.
    getApplications().getFacebookLoginManager().onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  /**
   * EditText의 포커스를 주고 일정 딜레이 후 키보드를 등장하게 한다.
   *
   * @param et        EditText instance.
   * @param clearText if true will be text clear.
   */
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

  /**
   * 입력된 로그인값들을 확인 후 로그인을 실행 한다. 입력된 값들에 오류가 있을 시 다이얼로그를
   * 출력 한다.
   *
   * @param email email text.
   * @param pw    password text.
   */
  private void startLoginJobBeforeValidationInputs(final String email, final String pw) {
    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pw)) {
      etEmail.setEnabled(false);
      etPassword.setEnabled(false);
      tvBtnSignUp.setClickable(false);
      containerFbLogin.setEnabled(false);
      btnLogin.setState(StateButton.STATE_WAITING);

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
                new SwDialog.OnDialogButtonClickListener() {
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
      containerFbLogin.setEnabled(true);
      btnLogin.setState(StateButton.STATE_ENABLED);
    }
  }

}
