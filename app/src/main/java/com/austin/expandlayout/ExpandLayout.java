package com.austin.expandlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Austin on 2017/3/5.
 * This is a ExpandLayout which contains  at least a textview(expandable) and a imageview(indicator to animate);
 * Extending LinearLayout and with right default gravity, we can put any view inside,
 * Notice that the expandable textview's and indicator's id has already defined in ids.xml
 */

public class ExpandLayout extends LinearLayout {


//    private int mDefaultColor = Color.BLACK;
//    private int mTextColor = mDefaultColor;
//    private int mDefaultTextSize = 13;
//    private int mTextSize = mDefaultTextSize;
    private int mDefaultLines = 2;
    private TextView mExpandTextView;
    private ImageView mIndicatorView;
    private long mAnimationDuration = 500;
    private int lineCount;

    public ExpandLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ExpandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        setGravity(Gravity.RIGHT);
        if(attrs!=null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandLayout);
            mDefaultLines = a.getInteger(R.styleable.ExpandLayout_defaultLines, mDefaultLines);
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View expandView = this.findViewById(R.id.expandlayout_textview);
        if (expandView instanceof TextView) {
            mExpandTextView = (TextView) expandView;
        }

        final View indicatorView = findViewById(R.id.expandlayout_imageview);
        if (indicatorView instanceof ImageView) {
            mIndicatorView = (ImageView) indicatorView;
        }

//        mExpandTextView.setTextColor(mTextColor);
//        mExpandTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        //mExpandTextView.setHeight(mExpandTextView.getLineHeight() * mDefaultLines);

        post(new Runnable() {
            @Override
            public void run() {
                lineCount = mExpandTextView.getLineCount();
                mIndicatorView.setVisibility(lineCount > mDefaultLines ? VISIBLE : INVISIBLE);

                if (lineCount > mDefaultLines) {
                    mExpandTextView.setMaxLines(mDefaultLines);
                    mExpandTextView.setEllipsize(TextUtils.TruncateAt.END);
                }
            }
        });

        initListener();
    }

    private void initListener() {
        setOnClickListener(new OnClickListener() {
            boolean isExpandState;
            @Override
            public void onClick(View view) {
                isExpandState = !isExpandState;

                if (mIndicatorView.getVisibility() == INVISIBLE) {
                    return;
                }
                mIndicatorView.clearAnimation();
                final int daltaheight;
                final int startHeight = mExpandTextView.getHeight();
                if (!isExpandState) {
                    daltaheight = mExpandTextView.getLineHeight() * mDefaultLines - startHeight;
                    RotateAnimation rotateAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(mAnimationDuration);
                    rotateAnimation.setFillAfter(true);
                    mIndicatorView.setAnimation(rotateAnimation);
                }else{
                    daltaheight = mExpandTextView.getLineHeight() * lineCount - startHeight;
                    RotateAnimation rotateAnimation = new RotateAnimation(0,-180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(mAnimationDuration);
                    rotateAnimation.setFillAfter(true);
                    mIndicatorView.setAnimation(rotateAnimation);
                }

                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        mExpandTextView.setHeight((int) (startHeight + daltaheight * interpolatedTime));
                    }
                };

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (!isExpandState) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    mExpandTextView.setMaxLines(mDefaultLines);
                                    mExpandTextView.setEllipsize(TextUtils.TruncateAt.END);
                                }
                            });
                        }
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                animation.setDuration(mAnimationDuration);

                mExpandTextView.startAnimation(animation);


            }
        });
    }


    /**
     * 设置内容
     * @param mText
     */
    public void setmText(String mText) {
        mExpandTextView.setText(mText);

    }


    /**
     * 设置文字颜色
     * @param mTextColor
     */
    public void setmTextColor(int mTextColor) {
        mExpandTextView.setTextColor(mTextColor);
    }


    /**
     * 设置文字大小
     * @param mTextSize
     */
    public void setmTextSize(int mTextSize) {
        mExpandTextView.setTextSize(mTextSize);
    }

    /**
     * 获得可伸缩的TextView
     * @return
     */
    public TextView getmExpandTextView() {
        return mExpandTextView;
    }


    /**
     * 获得可旋转的ImageView
     * @return
     */
    public ImageView getmIndicatorView() {
        return mIndicatorView;
    }


}




















