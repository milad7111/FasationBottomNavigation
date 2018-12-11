package com.example.fasationbottomnavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FasationBottomNavigation extends ConstraintLayout {

    //region Declare Constants
    private static final int NOT_DEFINED = -777;
    private static final boolean SET_BIGGER_SIZE = true;
    private static final boolean SET_SMALLER_SIZE = false;
    //endregion Declare Constants

    //region Declare Variables
    private int lastSelectedIndex = -1;
    private int newSelectedIndex = -1;
    private int selectedItemOffset = 70;
    private int bezierWidth = 0;
    private int bezierHeight = 0;
    private int bottomSheetItemsCount = 5;
    private int horizontallyOffset = 0;

    private boolean runDefault = false;
    //endregion Declare Variables

    //region Declarer Arrays & Lists
    //endregion Declarer Arrays & Lists

    //region Declare Objects
    private Context context;
    private Paint mPaint = new Paint();
    private Canvas mCanvas;
    RectF mRectF;

    private ValueAnimator moveSelectedItemAnimator;
    private ValueAnimator moveDeSelectedItemAnimator;

    private Animation lastSelectedViewReSizeAnimation;
    private Animation newSelectedViewReSizeAnimation;
    //endregion Declare Objects

    //region Declare Views
    View rootView;
    View lastSelectedView;
    View newSelectedView;

    ImageView firstCustomItemView;
    ImageView secondCustomItemView;
    ImageView thirdCustomItemView;
    ImageView fourthCustomItemView;
    ImageView fifthCustomItemView;

    RelativeLayout emptyRelativeLayout;
    private BezierView centerContent;
    //endregion Declare Views

    //region Custom Attributes
    private int fasation_background_color = NOT_DEFINED;
    private int fasation_height = NOT_DEFINED;

    private int fasation_normal_active_item_icon_mix_size = NOT_DEFINED;
    private int fasation_normal_inactive_item_icon_mix_size = NOT_DEFINED;
    private int fasation_normal_active_item_icon_only_size = NOT_DEFINED;
    private int fasation_normal_inactive_item_icon_only_size = NOT_DEFINED;

    private int fasation_active_item_background_color = NOT_DEFINED;
    private int fasation_inactive_item_background_color = NOT_DEFINED;

    private int fasation_active_item_icon_color = NOT_DEFINED;
    private int fasation_inactive_item_icon_color = NOT_DEFINED;

    private int fasation_item_text_size = NOT_DEFINED;
    //endregion Custom Attributes

    //region Constructor
    public FasationBottomNavigation(Context context) {
        super(context);
        this.context = context;
        this.setWillNotDraw(false);
        init(context);
    }

    public FasationBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setWillNotDraw(false);
        init(context);
    }
    //endregion Constructor

    //region Overrides
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        ValueAnimator anim = ObjectAnimator.ofInt(0, 100);
//        anim.setDuration(5000);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                // calling invalidate(); will trigger onDraw() to execute
//                invalidate();
//            }
//        });
//        anim.start();

        if (newSelectedIndex != -1) {
            bezierWidth = (int) (0.95 * emptyRelativeLayout.getWidth() / 5);
            bezierHeight = emptyRelativeLayout.getHeight();

            horizontallyOffset = (int) (0.025 * emptyRelativeLayout.getWidth());

            centerContent.setWidth(bezierWidth);
            centerContent.setHeight(bezierHeight);
            centerContent.setStartY(0);

            for (int i = 0; i < bottomSheetItemsCount; i++) {
                centerContent.setStartX(horizontallyOffset + newSelectedIndex * bezierWidth);
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                centerContent.draw(canvas);
            }
        }
    }

    /**
     * Creating bezier view with params
     *
     * @return created bezier view
     */
    private BezierView buildBezierView() {
        BezierView bezierView = new BezierView(context, ContextCompat.getColor(context, R.color.colorAccent));
        bezierView.build(bezierWidth, bezierHeight, false);
        return bezierView;
    }
    //endregion Overrides

    //region Declare Methods
    private void init(final Context context) {

        rootView = inflate(context, R.layout.fasation_bottom_navigation, this);
        emptyRelativeLayout = rootView.findViewById(R.id.empty_layout);

        centerContent = buildBezierView();

        firstCustomItemView = rootView.findViewById(R.id.img_navigation_items_first);
        secondCustomItemView = rootView.findViewById(R.id.img_navigation_items_second);
        thirdCustomItemView = rootView.findViewById(R.id.img_navigation_items_third);
        fourthCustomItemView = rootView.findViewById(R.id.img_navigation_items_fourth);
        fifthCustomItemView = rootView.findViewById(R.id.img_navigation_items_fifth);

        firstCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 0) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 0;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                    invalidate();
                }
            }
        });

        secondCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 1) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 1;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                    invalidate();
                }
            }
        });

        thirdCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 2) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 2;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                    invalidate();
                }
            }
        });

        fourthCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 3) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 3;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                    invalidate();
                }
            }
        });

        fifthCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 4) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 4;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                    invalidate();
                }
            }
        });
    }

    private void prepareDeSelectItemAnimation() {
        lastSelectedView = getViewBasedIndex(lastSelectedIndex);

        if (lastSelectedView != null) {
            final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) lastSelectedView.getLayoutParams();
            moveDeSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, mLayoutParams.bottomMargin - selectedItemOffset);
            moveDeSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mLayoutParams.bottomMargin = (Integer) valueAnimator.getAnimatedValue();
                    lastSelectedView.requestLayout();
                }
            });
            moveDeSelectedItemAnimator.setDuration(500);

            setImageSizeAnimation(lastSelectedView, 200, SET_SMALLER_SIZE);
        }
    }

    private void prepareSelectItemAnimation(final View view) {
        newSelectedView = view;

        final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) newSelectedView.getLayoutParams();
        moveSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, mLayoutParams.bottomMargin + selectedItemOffset);
        moveSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mLayoutParams.bottomMargin = (Integer) valueAnimator.getAnimatedValue();
                newSelectedView.requestLayout();
            }
        });
        moveSelectedItemAnimator.setDuration(500);

        setImageSizeAnimation(newSelectedView, 200, SET_BIGGER_SIZE);
    }

    private View getViewBasedIndex(int index) {
        switch (index) {
            case 0:
                return firstCustomItemView;
            case 1:
                return secondCustomItemView;
            case 2:
                return thirdCustomItemView;
            case 3:
                return fourthCustomItemView;
            case 4:
                return fifthCustomItemView;
            default:
                return null;
        }
    }

    private void runAnimationOnClickItem() {
        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator.start();

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator.start();

        if (runDefault) {
            if (lastSelectedView != null)
                lastSelectedView.startAnimation(lastSelectedViewReSizeAnimation);

            if (newSelectedView != null)
                newSelectedView.startAnimation(newSelectedViewReSizeAnimation);
        }

        runDefault = true;
    }

    private void setImageSizeAnimation(View view, int duration, boolean finalSizeStatus) {
        if (finalSizeStatus == SET_SMALLER_SIZE) {
            lastSelectedViewReSizeAnimation = new ResizeAnimation(view, (int) (view.getWidth() * 0.75), (int) (view.getHeight() * 0.75));
            lastSelectedViewReSizeAnimation.setDuration(duration);
        } else if (finalSizeStatus == SET_BIGGER_SIZE) {
            newSelectedViewReSizeAnimation = new ResizeAnimation(view, (int) (view.getWidth() * 100 / 75), (int) (view.getHeight() * 100 / 75));
            newSelectedViewReSizeAnimation.setDuration(duration);
        }
    }

    public void initDefaultItem(int defaultSelectedItemPosition) {
        newSelectedIndex = defaultSelectedItemPosition;
        prepareSelectItemAnimation(getViewBasedIndex(defaultSelectedItemPosition));
        runAnimationOnClickItem();
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public int convertDpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
    //endregion Declare Methods

//        if (attrs != null) {
//            Resources resources = getResources();
//
//            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FasationRelativeBottomNavigation);
//
//            fasation_background_color = typedArray.getColor(
//                    com.example.relativebottomnavigation.R.styleable.FasationRelativeBottomNavigation_fasation_background_color,
//                    resources.getColor(com.example.relativebottomnavigation.R.color.fasation_background_color));
//
//            fasation_height = typedArray.getColor(
//                    com.example.relativebottomnavigation.R.styleable.FasationRelativeBottomNavigation_fasation_height,
//                    resources.getDimensionPixelSize(com.example.relativebottomnavigation.R.dimen.fasation_height));
//
//            fasation_normal_active_item_icon_mix_size = typedArray.getDimensionPixelSize(
//                    R.styleable.FasationRelativeBottomNavigation_fasation_normal_active_item_icon_mix_size,
//                    resources.getDimensionPixelSize(com.example.relativebottomnavigation.R.dimen.fasation_normal_active_item_icon_mix_size));
//
//            fasation_normal_inactive_item_icon_mix_size = typedArray.getDimensionPixelSize(
//                    R.styleable.FasationRelativeBottomNavigation_fasation_normal_inactive_item_icon_mix_size,
//                    resources.getDimensionPixelSize(com.example.relativebottomnavigation.R.dimen.fasation_normal_inactive_item_icon_mix_size));
//
//            fasation_normal_active_item_icon_only_size = typedArray.getDimensionPixelSize(
//                    R.styleable.FasationRelativeBottomNavigation_fasation_normal_active_item_icon_only_size,
//                    resources.getDimensionPixelSize(com.example.relativebottomnavigation.R.dimen.fasation_normal_active_item_icon_only_size));
//
//            fasation_normal_inactive_item_icon_only_size = typedArray.getDimensionPixelSize(
//                    R.styleable.FasationRelativeBottomNavigation_fasation_normal_inactive_item_icon_only_size,
//                    resources.getDimensionPixelSize(com.example.relativebottomnavigation.R.dimen.fasation_normal_inactive_item_icon_only_size));
//
//            fasation_active_item_background_color = typedArray.getColor(
//                    com.example.relativebottomnavigation.R.styleable.FasationRelativeBottomNavigation_fasation_active_item_background_color,
//                    resources.getColor(com.example.relativebottomnavigation.R.color.fasation_active_item_background_color));
//
//            fasation_inactive_item_background_color = typedArray.getColor(
//                    com.example.relativebottomnavigation.R.styleable.FasationRelativeBottomNavigation_fasation_inactive_item_background_color,
//                    resources.getColor(com.example.relativebottomnavigation.R.color.fasation_inactive_item_background_color));
//
//            fasation_active_item_icon_color = typedArray.getColor(
//                    com.example.relativebottomnavigation.R.styleable.FasationRelativeBottomNavigation_fasation_active_item_icon_color,
//                    resources.getColor(com.example.relativebottomnavigation.R.color.fasation_active_item_icon_color));
//
//            fasation_inactive_item_icon_color = typedArray.getColor(
//                    com.example.relativebottomnavigation.R.styleable.FasationRelativeBottomNavigation_fasation_inactive_item_icon_color,
//                    resources.getColor(com.example.relativebottomnavigation.R.color.fasation_inactive_item_icon_color));
//
//            fasation_item_text_size = typedArray.getDimensionPixelSize(
//                    R.styleable.FasationRelativeBottomNavigation_fasation_item_text_size,
//                    resources.getDimensionPixelSize(com.example.relativebottomnavigation.R.dimen.fasation_item_text_size));
//
//            typedArray.recycle();
//        }
//
//        ImageButton itemView = new ImageButton(context);
//
//        itemView.setBackgroundColor(Color.rgb(78, 56, 59));
//
//        LayoutParams mLayoutParams = new LayoutParams(45, 45);
//
////        mLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT);
////        mLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//
//        itemView.setLayoutParams(mLayoutParams);
//
//        addView(itemView);
//        addView(itemView);
//        addView(itemView);


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        /**
//         * Set default colors and sizes
//         */
//        if (fasation_background_color == NOT_DEFINED)
//            fasation_background_color = ContextCompat.getColor(context, com.example.relativebottomnavigation.R.color.fasation_background_color);
//
//        if (fasation_height == NOT_DEFINED)
//            fasation_height = (int) getResources().getDimension(com.example.relativebottomnavigation.R.dimen.fasation_height);
//
//        if (fasation_active_item_background_color == NOT_DEFINED)
//            fasation_active_item_background_color = ContextCompat.getColor(context, com.example.relativebottomnavigation.R.color.fasation_active_item_background_color);
//
//        if (fasation_inactive_item_background_color == NOT_DEFINED)
//            fasation_inactive_item_background_color = ContextCompat.getColor(context, com.example.relativebottomnavigation.R.color.fasation_inactive_item_background_color);
//
//        if (fasation_active_item_icon_color == NOT_DEFINED)
//            fasation_active_item_icon_color = ContextCompat.getColor(context, com.example.relativebottomnavigation.R.color.fasation_active_item_icon_color);
//
//        if (fasation_inactive_item_icon_color == NOT_DEFINED)
//            fasation_inactive_item_icon_color = ContextCompat.getColor(context, com.example.relativebottomnavigation.R.color.fasation_inactive_item_icon_color);
//
//        if (fasation_normal_active_item_icon_mix_size == NOT_DEFINED)
//            fasation_normal_active_item_icon_mix_size = (int) getResources().getDimension(com.example.relativebottomnavigation.R.dimen.fasation_normal_active_item_icon_mix_size);
//
//        if (fasation_normal_inactive_item_icon_mix_size == NOT_DEFINED)
//            fasation_normal_inactive_item_icon_mix_size = (int) getResources().getDimension(com.example.relativebottomnavigation.R.dimen.fasation_normal_inactive_item_icon_mix_size);
//
//        if (fasation_normal_active_item_icon_only_size == NOT_DEFINED)
//            fasation_normal_active_item_icon_only_size = (int) getResources().getDimension(com.example.relativebottomnavigation.R.dimen.fasation_normal_active_item_icon_only_size);
//
//        if (fasation_normal_inactive_item_icon_only_size == NOT_DEFINED)
//            fasation_normal_inactive_item_icon_only_size = (int) getResources().getDimension(com.example.relativebottomnavigation.R.dimen.fasation_normal_inactive_item_icon_only_size);
//
//        if (fasation_item_text_size == NOT_DEFINED)
//            fasation_item_text_size = (int) getResources().getDimension(com.example.relativebottomnavigation.R.dimen.fasation_item_text_size);
//
//        /**
//         * Set main layout size and color
//         */
//        ViewGroup.LayoutParams params = getLayoutParams();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = fasation_height;
//        setBackgroundColor(fasation_background_color);
//        setLayoutParams(params);
//    }
}
