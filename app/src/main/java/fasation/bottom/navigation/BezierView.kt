/*
 * Space Navigation library for Android
 * Copyright (c) 2016 Arman Chatikyan (https://github.com/armcha/Space-Navigation-View).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fasation.bottom.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.widget.RelativeLayout
import fasation.bottom.navigation.R

@SuppressLint("ViewConstructor")
class BezierView @JvmOverloads constructor(context: Context, backgroundColor: Int = 0) : RelativeLayout(context) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path: Path = Path()
    private var backgroundColor:Int? = backgroundColor

    private var bezierWidth = 0
    private var bezierHeight = 0
    private var startX = 0
    private var startY = 0
    var startPointX = 0
    var startPointY = 0
    private var isLinear = false

    init {
        paint.strokeWidth = 0f
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setBackgroundColor(ContextCompat.getColor(context, R.color.fasation_bottom_navigation_background_color))
    }

    override fun onDraw(canvas: Canvas) {

        val offsetX = (bezierWidth * 0.05).toInt()
        val offsetY = 0

        paint.color = backgroundColor!!
        path.reset()

        startPointX = startX - 2 * offsetX
        startPointY = startY + bezierHeight

        val endPointX = startX + bezierWidth + 2 * offsetX
        val endPointY = startY + bezierHeight

        path.moveTo(startPointX.toFloat(), startPointY.toFloat())

        if (!isLinear) {

            val x1 = startX + bezierWidth / 4 - 3 * offsetX
            val y1 = startY + bezierHeight
            val x2 = startX + bezierWidth / 4 - 4 * offsetX
            val y2 = startY + offsetY

            val x3 = startX + bezierWidth / 2
            val y3 = startY + offsetY

            val x4 = (startX.toDouble() + bezierWidth / 4.0 * 3 + (4 * offsetX).toDouble()).toInt()
            val y4 = startY + offsetY
            val x5 = startX + bezierWidth * 3 / 4 + 4 * offsetX
            val y5 = startY + bezierHeight

            path.cubicTo(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), x3.toFloat(), y3.toFloat())
            path.cubicTo(x4.toFloat(), y4.toFloat(), x5.toFloat(), y5.toFloat(), endPointX.toFloat(), endPointY.toFloat())
        }

        canvas.drawPath(path, paint)
    }

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param isLinear     True, if curves are not needed
     */
    fun build(bezierWidth: Int, bezierHeight: Int, isLinear: Boolean) {
        this.bezierWidth = bezierWidth
        this.bezierHeight = bezierHeight
        this.isLinear = isLinear
    }

    /**
     * Change bezier view background color
     *
     * @param backgroundColor Target color
     */
    fun changeBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        invalidate()
    }

    fun setWidth(width: Int) {
        bezierWidth = width
    }

    fun setHeight(height: Int) {
        bezierHeight = height
    }

    fun setStartX(xPosition: Int) {
        startX = xPosition
    }

    fun setStartY(yPosition: Int) {
        startY = yPosition
    }
}

