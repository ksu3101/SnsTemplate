package kr.swkang.snstemplate.utils.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import kr.swkang.snstemplate.R;
import kr.swkang.snstemplate.utils.Utils;

/**
 * @author KangSung-Woo
 * @since 2016/08/23
 */
public class StateButton
    extends RelativeLayout {
  public static final int STATE_ENABLED  = 1;
  public static final int STATE_WAITING  = 2;
  public static final int STATE_DISABLED = 3;

  @IntDef(flag = true, value = {
      STATE_ENABLED,
      STATE_WAITING,
      STATE_DISABLED
  })
  @Retention(RetentionPolicy.SOURCE)
  public @interface ButtonState {
  }

  @ButtonState
  private int            state;
  private String         enableText;
  private String         waitingText;
  private String         disableText;
  private Drawable       btnBg;
  private ColorStateList btnTextColor;
  private ColorStateList pbColorList;
  private Drawable       pbDrawable;
  private boolean        isPbVisible;
  private int            btnTextSize;
  private Rect           btnPaddings;
  private boolean        isInitialized;

  public StateButton(Context context) {
    this(context, null);
  }

  public StateButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StateButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void initDefaultValues() {
    this.enableText = "";
    this.disableText = "";
    this.btnBg = null;
    this.btnTextColor = null;
    this.pbColorList = ColorStateList.valueOf(Color.WHITE);
    this.pbDrawable = null;
    this.isPbVisible = false;
    this.state = STATE_ENABLED;
    setEnabled(true);
    setClickable(true);
  }

  private void init(@NonNull Context context, AttributeSet attrs) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = inflater.inflate(R.layout.statebutton_stub, this, false);
    addView(v);

    initDefaultValues();
    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateButton);

      enableText = a.getString(R.styleable.StateButton_sb_btn_text_enable);
      waitingText = a.getString(R.styleable.StateButton_sb_btn_text_wait);
      disableText = a.getString(R.styleable.StateButton_sb_btn_text_disabled);

      btnBg = a.getDrawable(R.styleable.StateButton_sb_btn_selector);
      btnTextColor = a.getColorStateList(R.styleable.StateButton_sb_btn_textcolor);
      btnTextSize = a.getDimensionPixelSize(
          R.styleable.StateButton_sb_btn_textsize,
          (int) Utils.convertDpiToPixel(context.getResources(), 14)
      );

      pbColorList = a.getColorStateList(R.styleable.StateButton_sb_progressbar_color);
      pbDrawable = a.getDrawable(R.styleable.StateButton_sb_progressbar_drawable);

      isPbVisible = a.getBoolean(R.styleable.StateButton_sb_progressbar_visible, false);

      // button paddings
      btnPaddings = new Rect();
      btnPaddings.left = a.getDimensionPixelSize(
          R.styleable.StateButton_sb_btn_paddingLeft,
          (int) Utils.convertDpiToPixel(context.getResources(), 34)
      );
      btnPaddings.top = a.getDimensionPixelSize(
          R.styleable.StateButton_sb_btn_paddingTop,
          0
      );
      btnPaddings.right = a.getDimensionPixelSize(
          R.styleable.StateButton_sb_btn_paddingRight,
          (int) Utils.convertDpiToPixel(context.getResources(), 34));
      btnPaddings.bottom = a.getDimensionPixelSize(
          R.styleable.StateButton_sb_btn_paddingBottom,
          0
      );

      final int stateValue = a.getInt(R.styleable.StateButton_sb_state, STATE_ENABLED);
      switch (stateValue) {
        case STATE_WAITING:
          state = STATE_WAITING;
          break;
        case STATE_DISABLED:
          state = STATE_DISABLED;
          break;
        default:
          state = STATE_ENABLED;
          break;
      }

      a.recycle();
    }
    updateLayouts();
    this.isInitialized = true;
  }

  public void setState(@ButtonState int buttonState) {
    this.state = buttonState;
    updateLayouts();
  }

  @ButtonState
  public int getState() {
    return this.state;
  }

  public void setText(CharSequence text) {
    this.enableText = (text != null ? text.toString() : "");
    this.waitingText = (text != null ? text.toString() : "");
    this.disableText = (text != null ? text.toString() : "");
    updateLayouts();
  }

  public String getEnableText() {
    return this.enableText;
  }

  public String getWaitingText() {
    return this.waitingText;
  }

  public String getDisableText() {
    return this.disableText;
  }

  public void setEnableText(CharSequence enableText) {
    this.enableText = (enableText != null ? enableText.toString() : "");
    updateLayouts();
  }

  public void setWaitingText(CharSequence waitingText) {
    this.waitingText = (waitingText != null ? waitingText.toString() : "");
    updateLayouts();
  }

  public void setDisableText(CharSequence disableText) {
    this.disableText = (disableText != null ? disableText.toString() : "");
    updateLayouts();
  }

  public void setVisibleProgressBar(boolean isVisible) {
    this.isPbVisible = isVisible;
    updateLayouts();
  }

  private void updateLayouts() {
    Button btn = (Button) findViewById(R.id.statebutton_btn);
    ProgressBar pb = (ProgressBar) findViewById(R.id.statebutton_pb);

    if (btnPaddings != null) {
      btn.setPadding(btnPaddings.left, btnPaddings.top, btnPaddings.right, btnPaddings.bottom);
    }
    if (btnTextColor != null) {
      btn.setTextColor(btnTextColor);
    }
    if (btnBg != null) {
      btn.setBackgroundDrawable(btnBg);
    }

    if (state == STATE_DISABLED) {
      btn.setText(disableText != null ? disableText : "");
      setEnabled(false);
    }
    else if (state == STATE_WAITING) {
      btn.setText(waitingText != null ? waitingText : "");
      setEnabled(false);
    }
    else {
      btn.setText(enableText != null ? enableText : "");
      setEnabled(true);
    }
    updateLayoutsProgressBar(pb);
  }

  private void updateLayoutsProgressBar(@NonNull ProgressBar pb) {
    if (isPbVisible) {
      if (state == STATE_WAITING) {
        pb.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          if (pbColorList != null) {
            pb.setIndeterminateTintList(pbColorList);
          }
          if (pbDrawable != null) {
            pb.setIndeterminateDrawable(pbDrawable);
          }
        }
      }
      else {
        pb.setVisibility(View.GONE);
      }
    }
    else {
      pb.setVisibility(View.GONE);
    }
  }

}
