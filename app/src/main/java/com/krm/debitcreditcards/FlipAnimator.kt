package com.krm.debitcreditcards

import android.graphics.Camera
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import kotlin.math.sin

class FlipAnimator(
    private var fromView: View,
    private var toView: View,
    centerX: Int,
    centerY: Int
) : Animation() {
    private var camera: Camera? = null
    private val centerX: Float = centerX.toFloat()
    private val centerY: Float = centerY.toFloat()
    private var forward = true
    private var visibilitySwapped = false
    var rotationDirection = DIRECTION_X
    var translateDirection = DIRECTION_Z

    fun reverse() {
        forward = false
        val temp = toView
        toView = fromView
        fromView = temp
    }

    override fun initialize(
        width: Int,
        height: Int,
        parentWidth: Int,
        parentHeight: Int
    ) {
        super.initialize(width, height, parentWidth, parentHeight)
        camera = Camera()
    }

    override fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation
    ) {
        val radians = Math.PI * interpolatedTime
        var degrees = (180.0 * radians / Math.PI).toFloat()

        if (interpolatedTime >= 0.5f) {
            degrees -= 180f
            if (!visibilitySwapped) {
                fromView.visibility = View.GONE
                toView.visibility = View.VISIBLE
                visibilitySwapped = true
            }
        }
        if (forward) degrees = -degrees
        val matrix = t.matrix
        camera!!.save()
        when (translateDirection) {
            DIRECTION_Z -> {
                camera!!.translate(0.0f, 0.0f, (150.0 * sin(radians)).toFloat())
            }
            DIRECTION_Y -> {
                camera!!.translate(0.0f, (150.0 * sin(radians)).toFloat(), 0.0f)
            }
            else -> {
                camera!!.translate((150.0 * sin(radians)).toFloat(), 0.0f, 0.0f)
            }
        }
        when (rotationDirection) {
            DIRECTION_Z -> {
                camera!!.rotateZ(degrees)
            }
            DIRECTION_Y -> {
                camera!!.rotateY(degrees)
            }
            else -> {
                camera!!.rotateX(degrees)
            }
        }
        camera!!.getMatrix(matrix)
        camera!!.restore()
        matrix.preTranslate(-centerX, -centerY)
        matrix.postTranslate(centerX, centerY)
    }

    companion object {
        const val DIRECTION_X = 1
        const val DIRECTION_Y = 2
        const val DIRECTION_Z = 3
    }

    init {
        duration = 500
        fillAfter = true
        interpolator = AccelerateDecelerateInterpolator()
    }
}