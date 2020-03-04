package com.example.xiaojin20135.basemodule.view.button;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

import com.example.xiaojin20135.basemodule.view.ViewUtils;

/*
* @author lixiaojin
* create on 2020-02-29 13:40
* description: 带动画效果的Button
*/
public class AnimationButton extends View {
    //圆角矩形画笔
    private Paint mPaint;
    //文字画笔
    private Paint mTextPaint;
    //对勾画笔
    private Paint mOkPaint;

    //view 的宽度
    private int width;
    //View 的高度
    private int height;
    //圆角半径
    private int circleAngle;
    //默认两圆心之间的距离 = 需要移动的距离
    private int default_two_circle_distance;
    //两圆心之间的距离
    private int two_circle_distance;
    //背景颜色
    private int bg_color = 0xff037BFF;
    //按钮文字
    private String buttonString = "提交";
    //动画执行时间
    private int duration = 500;

    //文字所在矩形
    private Rect mTextRect = new Rect();
    //动画集
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    //矩形到圆角矩形过度的动画
    private ValueAnimator animate_rect_to_angle;
    //矩形到正方形过度的动画
    private ValueAnimator animate_rect_to_square;
    //绘制对勾动画
    private ValueAnimator animate_draw_ok;
    //是否开始绘制对勾
    private boolean mStartDrawOk = false;
    //根据View的大小设置成矩形
    private RectF mRectF = new RectF(); //RectF为矩形工具类，根据四个点绘制出一个矩形
    //路径--用来获取对勾的路径
    private Path mPath = new Path();
    //取路径的长度
    private PathMeasure mPathMeasure;
    //对路径处理实现绘制动画效果
    private PathEffect mPathEffect;
    //点击事件及动画事假完成回调
    private AnimationButtonListener mAnimationButtonListener;


    public AnimationButton(Context context) {
        super(context,null);
        initPaint();
        initEvents();
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        initPaint();
        initEvents();
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initEvents();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw_oval_to_circle(canvas);
        drawText(canvas);
        if(mStartDrawOk){
            canvas.drawPath(mPath,mOkPaint);
        }
    }

    /*
     * @author lixiaojin
     * create on 2020-03-02 14:45
     * description: 初始化画笔
     */
    private void initPaint(){
        // 圆角矩形画笔
        mPaint = ViewUtils.generatePaint(bg_color,4,Paint.Style.FILL);

        //文字画笔
        mTextPaint = ViewUtils.generatePaint(Color.WHITE,4,Paint.Style.FILL);
        mTextPaint.setTextSize(40);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //ok画笔
        mOkPaint = ViewUtils.generatePaint(Color.WHITE,10,Paint.Style.STROKE);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 14:13
    * description: 事件初始化
    */
    private void initEvents(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnimationButtonListener != null){
                    mAnimationButtonListener.onClickListener();
                }
            }
        });

        mAnimatorSet.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                if(mAnimationButtonListener != null){
                    mAnimationButtonListener.animationFinish();
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 11:10
    * description:初始化所有动画
    */
    private void initAnimation(){
        set_rect_to_angle_animation();
        set_rect_to_circle_animation();
        set_draw_ok_animation();

        mAnimatorSet.play(animate_draw_ok)
                .after(animate_rect_to_square)
                .after(animate_rect_to_angle);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 11:25
    * description: 矩形到圆形的过度动画
    */
    private void set_rect_to_angle_animation(){
        //计算值从0到高度的一半
        animate_rect_to_angle  = ValueAnimator.ofInt(0,height / 2);
        animate_rect_to_angle.setDuration(duration);
        animate_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 11:34
    * description:  圆角矩形过度到圆的动画
    */
    private void set_rect_to_circle_animation(){
        animate_rect_to_square = ValueAnimator.ofInt(0,default_two_circle_distance);
        animate_rect_to_square.setDuration(duration);
        animate_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                two_circle_distance = (int) animation.getAnimatedValue();
                int alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance;
                mTextPaint.setAlpha(alpha);
                invalidate();
            }
        });
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 11:43
    * description: 设置对勾的动画
    */
    private void set_draw_ok_animation(){
        animate_draw_ok = ValueAnimator.ofFloat(1,0);
        animate_draw_ok.setDuration(duration);
        animate_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartDrawOk = true;
                float value = (Float) animation.getAnimatedValue();
                mPathEffect = new DashPathEffect(new float[]{mPathMeasure.getLength(),mPathMeasure.getLength()},value * mPathMeasure.getLength());
                mOkPaint.setPathEffect(mPathEffect);
                invalidate();
            }
        });
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        //左右两个圆心的距离
        default_two_circle_distance = (w - h) / 2;
        initOk();
        initAnimation();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 13:42
    * description: 绘制长方形变为圆形
    */
    private void draw_oval_to_circle(Canvas canvas){
        mRectF.left = two_circle_distance;
        mRectF.top = 0;
        mRectF.right = width - two_circle_distance;
        mRectF.bottom = height;

        //画圆角矩形
        canvas.drawRoundRect(mRectF,circleAngle,circleAngle,mPaint);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 13:44
    * description: 绘制文字
    */
    private void drawText(Canvas canvas){
        mTextRect.left = 0;
        mTextRect.top = 0;
        mTextRect.right = width;
        mTextRect.bottom = height;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int baseline = (mTextRect.bottom + mTextRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        //文字绘制到整个布局的中心
        canvas.drawText(buttonString,mTextRect.centerX(),baseline,mTextPaint);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 13:46
    * description: 绘制对勾
    */
    private void initOk(){
        //对勾的路径
        mPath.moveTo(default_two_circle_distance + height / 8 * 3,height / 2);
        mPath.lineTo(default_two_circle_distance + height / 2,height / 5 * 3);
        mPath.lineTo(default_two_circle_distance + height / 3 * 2,height / 5 * 2);
        mPathMeasure = new PathMeasure(mPath,true);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 13:48
    * description: 启动动画
    */
    public void start(){
        mAnimatorSet.start();
    }

    /*
    * @author lixiaojin
    * create on 2020-03-02 13:49
    * description: 动画还原
    */
    public void reset(){
        mStartDrawOk = false;
        circleAngle = 0;
        two_circle_distance = 0;
        default_two_circle_distance = (width - height) / 2;
        mTextPaint.setAlpha(255);
        invalidate();
    }


    public void setAnimationButtonListener(AnimationButtonListener animationButtonListener) {
        mAnimationButtonListener = animationButtonListener;
    }

    /*
    * @author lixiaojin
    * create on 2020-02-29 17:54
    * description: 接口回调
    */
    public interface AnimationButtonListener{
        /*
        * @author lixiaojin
        * create on 2020-02-29 17:54
        * description: 按钮点击事件
        */
        void onClickListener();
        /*
        * @author lixiaojin
        * create on 2020-02-29 17:54
        * description: 动画完成时间
        */
        void animationFinish();
    }


}
