package kr.swkang.snstemplate.join;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.join.model.ValidationUserInfoResultCode;
import kr.swkang.snstemplate.utils.common.BaseActivity;
import kr.swkang.snstemplate.utils.mvp.BasePresenter;
import kr.swkang.snstemplate.utils.mvp.models.UserInfo;

/**
 * @author KangSung-Woo
 * @since 2016/08/19
 */
public class JoinUserActivity
    extends BaseActivity
    implements JoinUserActivityPresenter.View {

  private JoinUserActivityPresenter presenter;

  @Override
  public BasePresenter attachPresenter() {
    this.presenter = new JoinUserActivityPresenter(this);
    return presenter;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.join_activity);
    ButterKnife.bind(this);

  }

  @Override
  public void resultOfSignUp(@NonNull UserInfo signedUserInfo, @NonNull ValidationUserInfoResultCode resultCode) {

  }

}
