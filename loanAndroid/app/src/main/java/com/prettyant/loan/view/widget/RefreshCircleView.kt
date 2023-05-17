package com.prettyant.loan.view.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.prettyant.loan.R
import kotlin.math.max
import kotlin.math.min

/**
 * 7.0下拉刷新控件
 * Created by uatjw992076 on 2021/2/25.
 */
class RefreshCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_SWEEP_ANGLE = 150
        private const val MIN_SWEEP_ANGLE = 10
        private const val SWEEP_CUT = 4.8f
        private const val CIRCLE_STATUS_LOOP = 1//正在循环
        private const val CIRCLE_STATUS_PLUS = 2//正在加长
        private const val CIRCLE_STATUS_CUT = 3//正在缩短
        private const val CIRCLE_STATUS_CLOSE = 4//正在消失
        private const val CIRCLE_STATUS_OPEN = 5//正在出现
        private const val CIRCLE_STATUS_MOVE = 6//正在移动
    }

    private val rect = RectF()
    private val lineWidth by lazy { TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.33f, context.getResources().getDisplayMetrics()) }
    private var sweepAngle = 0f//初始弧长
    private var startAngle = 0f//起始圆旋转角度
    private var circleRadius = 0f//半径
    private var circleStatus = 0//当前圆状态

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
                .apply {
                    color = ContextCompat.getColor(context, R.color.hint_color)
                    strokeCap = Paint.Cap.ROUND
                    style = Paint.Style.STROKE
                    strokeWidth = lineWidth
                }
    }
    private var animator: ValueAnimator? = null
    private var mFloatAnimator = 0f
    private var closeTime = 20

    private var valueT = 0f

    fun refreshSuccess() {
        circleStatus = CIRCLE_STATUS_CLOSE
        animator?.cancel()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val h = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val t = Math.min(w, h)
        setMeasuredDimension(t, t)
        rect.set(lineWidth / 2, lineWidth / 2, t - lineWidth / 2, t - lineWidth / 2)
        circleRadius = t / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (circleStatus == CIRCLE_STATUS_MOVE || circleStatus == CIRCLE_STATUS_OPEN || circleStatus == CIRCLE_STATUS_CLOSE) {
            rect.left = 0.1f * (1 - mFloatAnimator) * circleRadius + lineWidth / 2
            rect.top = 0.1f * (1 - mFloatAnimator) * circleRadius + lineWidth / 2
            rect.right = (circleRadius * 2) - 5f - 0.1f * (1 - mFloatAnimator) * circleRadius - lineWidth / 2
            rect.bottom = (circleRadius * 2) - 5f - 0.1f * (1 - mFloatAnimator) * circleRadius - lineWidth / 2
            paint.alpha = (mFloatAnimator * 55).toInt() + 200
            paint.strokeWidth = mFloatAnimator * lineWidth / 2 + lineWidth / 2
        } else {
            paint.alpha = 255
            paint.strokeWidth = lineWidth
        }
        if (sweepAngle > 0) {
            canvas.drawArc(rect, startAngle, sweepAngle, false, paint)
            canvas.drawArc(rect, startAngle + 180, sweepAngle, false, paint)
        }
        update()
    }

    /**
     * 更新圆状态
     */
    private fun update() {
        when (circleStatus) {
            CIRCLE_STATUS_OPEN -> {
                mFloatAnimator += 0.01f
                if (mFloatAnimator >= 1) {
                    mFloatAnimator = 1f
                    startTransform()
                } else {
                    updateCircleOpen()
                    postInvalidate()
                }
            }
            CIRCLE_STATUS_CLOSE -> {
                updateCircleClose()
                postInvalidate()
            }
            CIRCLE_STATUS_LOOP -> updateCircleAngle()
            CIRCLE_STATUS_CUT -> {
                sweepAngle -= SWEEP_CUT
                if (sweepAngle <= MIN_SWEEP_ANGLE) {
                    sweepAngle = MIN_SWEEP_ANGLE.toFloat()
                }
                startAngle += SWEEP_CUT
                updateCircleAngle()
            }
            CIRCLE_STATUS_PLUS -> {
                updateCircleAngle()
                sweepAngle += SWEEP_CUT
                if (sweepAngle >= MAX_SWEEP_ANGLE) {
                    sweepAngle = MAX_SWEEP_ANGLE.toFloat()
                }
            }
        }
    }

    private fun updateCircleOpen() {
        startAngle = 360 - (1 - mFloatAnimator) * 360 + MIN_SWEEP_ANGLE * 1.5f
        sweepAngle = MAX_SWEEP_ANGLE - (1 - mFloatAnimator) * MAX_SWEEP_ANGLE
    }

    private fun updateCircleClose() {
        mFloatAnimator = sweepAngle / MAX_SWEEP_ANGLE
        sweepAngle -= SWEEP_CUT
        startAngle += SWEEP_CUT
        if (sweepAngle <= 0) {
            sweepAngle = 0f
            closeTime--
            if (closeTime <= 0) {
                closeTime = 20
                circleStatus = CIRCLE_STATUS_OPEN
            }
        }
        updateCircleAngle()
    }

    private fun updateCircleAngle() {
        startAngle += 5f
        if (startAngle >= 360f) {
            startAngle -= 360f
        }
    }

    fun setColor(color: Int) {
        paint.color = color
    }


    fun transform(f: Float) {
        if (animator?.isRunning == true) {
            return
        }
        mFloatAnimator = min(max(f, 0f), 1f)
        updateCircleOpen()
        circleStatus = CIRCLE_STATUS_MOVE
        invalidate()
    }

    fun stopTransform() {
        animator?.cancel()
        startAngle = 0f
        sweepAngle = 0f
    }

    fun startTransform() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(1f, 0f)
                .apply {
                    duration = 3000
                    repeatCount = 1
                    addUpdateListener {
                        val value = animator!!.animatedValue as Float
                        valueT = value
                        if (value > 0.75f) {
                            circleStatus = CIRCLE_STATUS_LOOP
                        } else if (value > 0.5f) {
                            circleStatus = CIRCLE_STATUS_CUT
                        } else if (value > 0.25f) {
                            circleStatus = CIRCLE_STATUS_LOOP
                        } else {
                            circleStatus = CIRCLE_STATUS_PLUS
                        }
                        invalidate()
                    }
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                            // Don't need to do something
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            circleStatus = CIRCLE_STATUS_CLOSE
                            mFloatAnimator = 1f
                            closeTime = 20
                            postInvalidate()
                            // Don't need to do something
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            // Don't need to do something
                        }

                        override fun onAnimationRepeat(animation: Animator) {
                            // Don't need to override
                        }
                    })
                }
        animator?.start()
    }

}
