package kr.swkang.snstemplate.utils.common;

import android.app.Application;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

/**
 * @author KangSung-Woo
 * @since 2016/09/07
 */
public class SwApplication
    extends Application {

  private static SwApplication   instance;
  private        CallbackManager fbLoginManaber;

  public static synchronized SwApplication getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    initFacebookSdk();
  }

  private void initFacebookSdk() {
    FacebookSdk.sdkInitialize(getApplicationContext());
    fbLoginManaber = CallbackManager.Factory.create();
  }

  public CallbackManager getFacebookLoginManager() {
    return this.fbLoginManaber;
  }

}
