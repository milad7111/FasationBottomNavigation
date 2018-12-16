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
package com.example.fasationbottomnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;

@SuppressLint("ViewConstructor")
class BezierView extends RelativeLayout {

    private Paint paint;
    private Path path;

    private int bezierWidth = 0, bezierHeight = 0;
    private int startX = 0, startY = 0;
    private int backgroundColor;
    int startPointX = 0, startPointY = 0;

    private Context context;
    private boolean isLinear = false;


    BezierView(Context context, int backgroundColor) {
        super(context);
        this.context = context;
        this.backgroundColor = backgroundColor;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setBackgroundColor(ContextCompat.getColor(context, R.color.fasation_bottom_navigation_background_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int offsetX = (int) (bezierWidth * 0.05);
        int offsetY = 0;

        paint.setColor(backgroundColor);
        path.reset();

        startPointX = startX - 2 * offsetX;
        startPointY = startY + bezierHeight;

        int endPointX = startX + bezierWidth + 2 * offsetX;
        int endPointY = startY + bezierHeight;

        path.moveTo(startPointX, startPointY);

        if (!isLinear) {

            int x1 = startX + bezierWidth / 4 - 3 * offsetX, y1 = startY + bezierHeight;
            int x2 = startX + bezierWidth / 4 - 4 * offsetX, y2 = startY + offsetY;

            int x3 = startX + bezierWidth / 2, y3 = startY + offsetY;

            int x4 = (int) (startX + bezierWidth * 3.0 / 4 + 4 * offsetX), y4 = startY + offsetY;
            int x5 = startX + bezierWidth * 3 / 4 + 3 * offsetX, y5 = startY + bezierHeight;

            path.cubicTo(x1, y1, x2, y2, x3, y3);
            path.cubicTo(x4, y4, x5, y5, endPointX, endPointY);
        }

        canvas.drawPath(path, paint);
    }

    /**
     * Build bezier view with given width and height
     *
     * @param bezierWidth  Given width
     * @param bezierHeight Given height
     * @param isLinear     True, if curves are not needed
     */
    void build(int bezierWidth, int bezierHeight, boolean isLinear) {
        this.bezierWidth = bezierWidth;
        this.bezierHeight = bezierHeight;
        this.isLinear = isLinear;
    }

    /**
     * Change bezier view background color
     *
     * @param backgroundColor Target color
     */
    void changeBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public void setWidth(int width) {
        bezierWidth = width;
    }

    public void setHeight(int height) {
        bezierHeight = height;
    }

    public void setStartX(int xPosition) {
        startX = xPosition;
    }

    public void setStartY(int yPosition) {
        startY = yPosition;
    }

    public Path getPath(){
        return path;
    }

    public int xCoordinate() {
        return startPointX;
    }

    public int yCoordinate() {
        return startY;
    }
}

