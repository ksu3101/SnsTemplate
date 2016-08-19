package kr.swkang.snstemplate.login;

import android.support.annotation.NonNull;
import android.util.Log;

import kr.swkang.snstemplate.login.model.LoginResultCode;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.BaseView;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class LoginActivityPresenter
    extends BasePresenter {
  private View view;

  public LoginActivityPresenter(@NonNull View activity) {
    this.view = activity;
  }

  public void startLoginJobs(@NonNull String email, @NonNull String pw) {
    Log.d(getTag(LoginActivityPresenter.class), "/// email = " + email + ", password = " + pw);
  }

  public interface View
      extends BaseView {
    void resultOfLogin(@NonNull LoginResultCode resultCode);
  }
}
