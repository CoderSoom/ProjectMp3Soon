package com.example.recyclerviewpool.view.fragment.discover.song.lyricsong

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.OvershootInterpolator
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView


class LyricView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), OnTouchListener {
    private var mEmptyView
            : View? = null
    var textView
            : TextView? = null
        private set
    private var mScrollView
            : ScrollView? = null
    private var autoScroll = false
    private var userTouch = false
    private var paddingValue = 0
    private var mPosition = 0
    private var resetType = 1
    private val MSG_USER_TOUCH = 0x349


    var emptyView: View?
        get() = mEmptyView
        set(emptyView) {
            mEmptyView = emptyView
            if (null != mEmptyView && null != textView) {
                if ("" == textView!!.text.toString()
                        .trim { it <= ' ' }
                ) {
                    mEmptyView!!.visibility = VISIBLE
                } else {
                    mEmptyView!!.visibility = GONE
                }
            }
        }


    private fun initWithContext(context: Context) {
        this.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                paddingValue = (height - paddingBottom - paddingTop) / 2
                if (null != textView && null != mScrollView) {
                    textView!!.setPadding(0, paddingValue, 0, paddingValue)
                    mScrollView!!.fullScroll(FOCUS_UP)
                }
                this@LyricView.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
    }


    fun setCurrentPosition(position: Int) {
        if (mPosition != position) {
            mPosition = position
            if (!userTouch) {
                doScroll(position)
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mScrollView = ScrollView(context)
        val scrollViewParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        mScrollView!!.overScrollMode = OVER_SCROLL_NEVER
        mScrollView!!.isVerticalScrollBarEnabled = false
        mScrollView!!.layoutParams = scrollViewParams
        mScrollView!!.isVerticalFadingEdgeEnabled = true
        mScrollView!!.setOnTouchListener(this)
        mScrollView!!.setFadingEdgeLength(220)
        textView = TextView(context)
        textView!!.gravity = Gravity.CENTER
        textView!!.textSize = 16.0f
        textView!!.setLineSpacing(6f, 1.5f)
        textView!!.setPadding(0, paddingValue, 0, paddingValue)
        mScrollView!!.addView(textView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))
        this.addView(mScrollView)
    }


    fun setText(charSequence: CharSequence?) {
        if (textView != null) {
            textView!!.text = charSequence
            if (charSequence == null || "" == charSequence.toString().trim { it <= ' ' }) {
                if (mEmptyView != null) {
                    mEmptyView!!.visibility = VISIBLE
                }
            } else {
                if (mEmptyView != null) {
                    mEmptyView!!.visibility = GONE
                }
            }
        }
    }

    override fun setOnLongClickListener(longClick: OnLongClickListener?) {
        if (textView != null) {
            textView!!.setOnLongClickListener(longClick)
        }
    }


    val lineHeight: Int
        get() {
            Log.e(javaClass.name.toString(), "**********************" + textView!!.lineHeight)
            return textView!!.lineHeight
        }

    val lineCount: Int
        get() = textView!!.lineCount

    override fun getPaddingTop(): Int {
        return textView!!.paddingTop
    }


    private fun doScroll(position: Int) {
        if (null != mScrollView) {
            if (!userTouch) {
                val animator = setupScroll(position)
                animator.start()
            }
        }
    }


    private fun setupScroll(position: Int): Animator {
        val startY = mScrollView!!.scrollY
        val endY = lineHeight * position + paddingTop - height / 2 + lineHeight / 2
        val animator = ValueAnimator.ofInt(startY, endY)
        animator.duration = 600
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            mScrollView!!.smoothScrollTo(0, value)
        }
        return animator
    }

    private var downY = 0f
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> down(event)
            MotionEvent.ACTION_MOVE -> move(event)
            MotionEvent.ACTION_UP -> up(event)
            else -> {
            }
        }
        return false
    }

    private fun down(event: MotionEvent) {
        Log.e("Log.e", "*********************Down")
        downY = event.y
        handler.removeMessages(MSG_USER_TOUCH)
        userTouch = true
    }

    private fun move(event: MotionEvent) {
        val moveY = Math.abs(event.y - downY)
        if (mScrollView!!.scrollY <= 0 && event.y - downY > 0) {

            resetType = 1
            autoScroll = true
            textView!!.setPadding(textView!!.paddingLeft,
                (moveY / 1.8f + textView!!.paddingTop).toInt(),
                textView!!.paddingRight,
                textView!!.paddingBottom)
        }
        if (mScrollView!!.scrollY >= textView!!.height - mScrollView!!.height - mScrollView!!.paddingTop - mScrollView!!.paddingBottom && event.y - downY < 0) {

            resetType = -1
            autoScroll = true
            textView!!.setPadding(textView!!.paddingLeft,
                textView!!.paddingTop,
                textView!!.paddingRight,
                (moveY / 1.2f + textView!!.paddingBottom).toInt())
            mScrollView!!.fullScroll(FOCUS_DOWN)
        }
        downY = event.y
    }

    private fun up(event: MotionEvent) {
        resetViewHeight()
    }

    fun resetViewHeight() {
        if (autoScroll && (textView!!.paddingBottom > paddingValue || textView!!.paddingTop > paddingValue)) {
            reset()
        } else {
            handler.sendEmptyMessageDelayed(MSG_USER_TOUCH,
                1200)
        }
    }


    fun reset() {
        var animator: ValueAnimator? = null
        if (resetType == 1) {
            animator =
                ValueAnimator.ofFloat(textView!!.paddingTop.toFloat(), paddingValue.toFloat())
            animator.interpolator = OvershootInterpolator(0.7f)
            animator.duration = 400
            animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
                val value = animation.animatedValue as Float
                textView!!.setPadding(textView!!.paddingLeft,
                    value.toInt(),
                    textView!!.paddingRight,
                    textView!!.paddingBottom)
                mScrollView!!.fullScroll(FOCUS_UP)
            })
        } else {
            animator =
                ValueAnimator.ofFloat(textView!!.paddingBottom.toFloat(), paddingValue.toFloat())
            animator.interpolator = OvershootInterpolator(0.7f)
            animator.duration = 400
            animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
                val value = animation.animatedValue as Float
                textView!!.setPadding(textView!!.paddingLeft,
                    textView!!.paddingTop,
                    textView!!.paddingRight,
                    value.toInt())
                mScrollView!!.fullScroll(FOCUS_DOWN)
            })
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                handlera.sendEmptyMessageDelayed(MSG_USER_TOUCH,
                    1200)
            }
        })
        animator.start()
    }

    var handlera: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_USER_TOUCH -> userTouch = false
            }
        }
    }

    init {
        initWithContext(context)
    }
}