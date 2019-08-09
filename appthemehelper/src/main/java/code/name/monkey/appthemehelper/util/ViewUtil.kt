package code.name.monkey.appthemehelper.util

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.ColorInt


object ViewUtil {

    fun removeOnGlobalLayoutListener(v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
        v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }

    fun setBackgroundCompat(view: View, drawable: Drawable?) {
        view.background = drawable
    }

    fun setBackgroundTransition(view: View, newDrawable: Drawable): TransitionDrawable {
        val transition = DrawableUtil.createTransitionDrawable(view.background, newDrawable)
        setBackgroundCompat(view, transition)
        return transition
    }

    fun setBackgroundColorTransition(view: View, @ColorInt newColor: Int): TransitionDrawable {
        val oldColor = view.background

        val start = oldColor ?: ColorDrawable(view.solidColor)
        val end = ColorDrawable(newColor)

        val transition = DrawableUtil.createTransitionDrawable(start, end)

        setBackgroundCompat(view, transition)

        return transition
    }
}
