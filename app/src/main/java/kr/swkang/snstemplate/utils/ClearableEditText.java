/**
 * Copyright 2015 Alex Yanchenko
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.swkang.snstemplate.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * To change clear icon, set
 * <p/>
 * <pre>
 * android:drawableRight="@drawable/custom_icon"
 * </pre>
 */
public class ClearableEditText
    extends EditText
    implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcherAdapter.TextWatcherListener {

  private Drawable              xD;
  private OnTextClearedListener listener;
  private OnTouchListener       l;
  private OnFocusChangeListener f;

  public ClearableEditText(Context context) {
    super(context);
    init();
  }

  public ClearableEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public void setOnTextClearedListener(OnTextClearedListener listener) {
    this.listener = listener;
  }

  @Override
  public void setOnTouchListener(OnTouchListener l) {
    this.l = l;
  }

  @Override
  public void setOnFocusChangeListener(OnFocusChangeListener f) {
    this.f = f;
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    if (getCompoundDrawables()[2] != null) {
      boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - xD.getIntrinsicWidth());
      if (tappedX) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          setText("");
          if (listener != null) {
            listener.didClearText();
          }
        }
        return true;
      }
    }
    if (l != null) {
      return l.onTouch(v, event);
    }
    return false;
  }

  @Override
  public void onFocusChange(View v, boolean hasFocus) {
    // 2016/04/15 -> 입력된 텍스트가 있으면 무조건 삭제 아이콘 보이게 설정
    setClearIconVisible(isNotEmpty(getText()));

    /*
    if (hasFocus) {
      setClearIconVisible(isNotEmpty(getText()));
    }
    else {
      setClearIconVisible(false);
    }
    */
    if (f != null) {
      f.onFocusChange(v, hasFocus);
    }
  }

  @Override
  public void onTextChanged(EditText view, String text) {
    setClearIconVisible(isNotEmpty(text));

    /*
    if (isFocused()) {
      setClearIconVisible(isNotEmpty(text));
    } */
  }

  private void init() {
    xD = getCompoundDrawables()[2];
    if (xD == null) {
      xD = getResources().getDrawable(android.R.drawable.presence_offline);
    }
    xD.setBounds(0, 0, xD.getIntrinsicWidth(), xD.getIntrinsicHeight());
    setClearIconVisible(false);
    super.setOnTouchListener(this);
    super.setOnFocusChangeListener(this);
    addTextChangedListener(new TextWatcherAdapter(this, this));
  }

  protected void setClearIconVisible(boolean visible) {
    boolean wasVisible = (getCompoundDrawables()[2] != null);
    if (visible != wasVisible) {
      Drawable x = visible ? xD : null;
      setCompoundDrawables(
          getCompoundDrawables()[0],
          getCompoundDrawables()[1],
          x,
          getCompoundDrawables()[3]
      );
    }
  }

  private boolean isNotEmpty(CharSequence str) {
    return !(str == null || str.length() == 0);
  }

  public interface OnTextClearedListener {
    void didClearText();
  }

}
