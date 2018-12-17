package com.example.fasationbottomnavigation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.devs.vectorchildfinder.VectorChildFinder;

public class FasationBottomNavigation extends ConstraintLayout implements View.OnClickListener {

    //region Declare Constants
    private static final int NOT_DEFINED = -777;
    private static final boolean SET_BIGGER_SIZE = true;
    private static final boolean SET_SMALLER_SIZE = false;
    private static final int ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY = 1000;
    private static final int SELECTED_ITEM_EXPANDABLE_DISABLE = 2000;
    private static final int SELECTED_ITEM_EXPANDABLE_FAILED = 3000;
    //endregion Declare Constants

    //region Declare Variables
    private int selectedItemOffset = 26; //dp
    private int defaultItemOffset = 0; //dp
    private int selectedItemHorizontallyOffset;
    private int defaultSelectedItem = 2;
    private int lastSelectedIndex = -1;
    private int newSelectedIndex = defaultSelectedItem;
    private int bezierWidth = 0;
    private int bezierHeight = 0;
    private int positionIndex = 0;
    private double leftMainWidth = 0.05;
    private double centerMainWidth = 0.90;

    private boolean runDefault = false;
    private boolean firstItemExpandable = false;
    private boolean secondItemExpandable = false;
    private boolean thirdItemExpandable = false;
    private boolean fourthItemExpandable = false;
    private boolean fifthItemExpandable = false;
    //endregion Declare Variables

    //region Declarer Arrays & Lists
    //endregion Declarer Arrays & Lists

    //region Declare Objects
    private Context context;

    private ObjectAnimator frontMoveSelectedItemBackgroundAnimator;

    private ValueAnimator moveDeSelectedItemAnimator;
    private ValueAnimator moveSelectedItemAnimator;

    private Animation lastSelectedViewReSizeAnimation;
    private Animation newSelectedViewReSizeAnimation;
    //endregion Declare Objects

    //region Declare Views
    View rootView;

    ImageView lastSelectedImageView;
    ImageView newSelectedImageView;

    View lastSelectedParentView;
    View newSelectedParentView;

    ImageView imgNavigationBackgroundSelectedItem;

    ConstraintLayout firstCustomItemView;
    ConstraintLayout secondCustomItemView;
    ConstraintLayout thirdCustomItemView;
    ConstraintLayout fourthCustomItemView;
    ConstraintLayout fifthCustomItemView;

    ImageView firstImageView;
    ImageView secondImageView;
    ImageView thirdImageView;
    ImageView fourthImageView;
    ImageView fifthImageView;

    RelativeLayout emptyRelativeLayout;
    private BezierView centerContent;
    FrameLayout bottomFrameLayout;
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

    //region Main Callbacks
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!runDefault)
            initDefaultItem(defaultSelectedItem);

        runDefault = true;

        if (newSelectedIndex != -1) {
            bezierWidth = (int) (centerMainWidth * emptyRelativeLayout.getWidth() / 5);
            bezierHeight = getHeight() - emptyRelativeLayout.getHeight() - convertDpToPx(context, 8);

            centerContent.setWidth(bezierWidth);
            centerContent.setHeight(bezierHeight);
            centerContent.setStartY(convertDpToPx(context, 8));

            int horizontallyOffset = (int) (leftMainWidth * emptyRelativeLayout.getWidth());

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            centerContent.setStartX(horizontallyOffset + newSelectedIndex * bezierWidth - positionIndex);
            centerContent.draw(canvas);

            prepareSelectedItemBackgroundAnimation();
        }
    }
    //endregion Main Callbacks

    /**
     * Creating bezier view with params
     *
     * @return created bezier view
     */
    private BezierView buildBezierView() {
        BezierView bezierView = new BezierView(context, ContextCompat.getColor(context, R.color.fasation_bottom_navigation_background_color));
        bezierView.build(bezierWidth, bezierHeight, false);
        return bezierView;
    }

    //region Declare Methods
    private void init(final Context context) {

        rootView = inflate(context, R.layout.fasation_bottom_navigation, this);
        emptyRelativeLayout = rootView.findViewById(R.id.empty_layout);
        centerContent = buildBezierView();

        firstCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_first);
        secondCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_second);
        thirdCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_third);
        fourthCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_fourth);
        fifthCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_fifth);

        imgNavigationBackgroundSelectedItem = rootView.findViewById(R.id.img_navigation_background_selected_item);

        firstImageView = rootView.findViewById(R.id.img_navigation_items_first);
        secondImageView = rootView.findViewById(R.id.img_navigation_items_second);
        thirdImageView = rootView.findViewById(R.id.img_navigation_items_third);
        fourthImageView = rootView.findViewById(R.id.img_navigation_items_fourth);
        fifthImageView = rootView.findViewById(R.id.img_navigation_items_fifth);

        firstCustomItemView.setOnClickListener(this);
        secondCustomItemView.setOnClickListener(this);
        thirdCustomItemView.setOnClickListener(this);
        fourthCustomItemView.setOnClickListener(this);
        fifthCustomItemView.setOnClickListener(this);

        firstImageView.setOnClickListener(this);
        secondImageView.setOnClickListener(this);
        thirdImageView.setOnClickListener(this);
        fourthImageView.setOnClickListener(this);
        fifthImageView.setOnClickListener(this);
    }

    private void prepareDeSelectItemAnimation() {

        lastSelectedImageView = getImageViewViewBasedIndex(lastSelectedIndex);
        lastSelectedParentView = getParentViewViewBasedIndex(lastSelectedIndex);

        if (lastSelectedParentView != null) {
            if (moveDeSelectedItemAnimator != null && moveDeSelectedItemAnimator.isRunning())
                moveDeSelectedItemAnimator.end();

            final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) lastSelectedParentView.getLayoutParams();
            moveDeSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, defaultItemOffset));
            moveDeSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mLayoutParams.bottomMargin = (Integer) valueAnimator.getAnimatedValue();
                    lastSelectedParentView.requestLayout();
                }
            });
            moveDeSelectedItemAnimator.setDuration(500);

            setImageSizeAnimation(lastSelectedImageView, 200, SET_SMALLER_SIZE);
        }
    }

    private void prepareSelectItemAnimation() {

        newSelectedImageView = getImageViewViewBasedIndex(newSelectedIndex);
        newSelectedParentView = getParentViewViewBasedIndex(newSelectedIndex);

        if (moveSelectedItemAnimator != null && moveSelectedItemAnimator.isRunning())
            moveSelectedItemAnimator.end();

        final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) newSelectedParentView.getLayoutParams();
        moveSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, defaultItemOffset) + convertDpToPx(context, selectedItemOffset));
        moveSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mLayoutParams.bottomMargin = (Integer) valueAnimator.getAnimatedValue();
                newSelectedParentView.requestLayout();
            }
        });
        moveSelectedItemAnimator.setDuration(500);

        setImageSizeAnimation(newSelectedImageView, 200, SET_BIGGER_SIZE);
    }

    private View getParentViewViewBasedIndex(int index) {
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

    private void prepareSelectedItemBackgroundAnimation() {

        int xCurrentPosition = imgNavigationBackgroundSelectedItem.getLeft();
        int xNewPosition = (int) (selectedItemHorizontallyOffset + newSelectedIndex * emptyRelativeLayout.getWidth() * centerMainWidth / 5);

        frontMoveSelectedItemBackgroundAnimator = ObjectAnimator.ofFloat(imgNavigationBackgroundSelectedItem, "translationX", xNewPosition - xCurrentPosition);
        frontMoveSelectedItemBackgroundAnimator.setDuration(200);
    }

    private ImageView getImageViewViewBasedIndex(int index) {
        switch (index) {
            case 0:
                return firstImageView;
            case 1:
                return secondImageView;
            case 2:
                return thirdImageView;
            case 3:
                return fourthImageView;
            case 4:
                return fifthImageView;
            default:
                return null;
        }
    }

    private int getDrawableIdBasedIndex(int index) {
        switch (index) {
            case 0:
                return R.drawable.home_24;
            case 1:
                return R.drawable.near_me_24;
            case 2:
                return R.drawable.magnify_24;
            case 3:
                return R.drawable.heart_24;
            case 4:
                return R.drawable.pencil_24;
            default:
                return -1;
        }
    }

    private void runAnimationOnClickItem() {
        setFloatingActionButtonIconWithColor();
        invalidate();
        if (frontMoveSelectedItemBackgroundAnimator != null)
            frontMoveSelectedItemBackgroundAnimator.start();

        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator.start();

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator.start();

        if (lastSelectedImageView != null)
            lastSelectedImageView.startAnimation(lastSelectedViewReSizeAnimation);

        if (newSelectedImageView != null)
            newSelectedImageView.startAnimation(newSelectedViewReSizeAnimation);
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

    /**
     * This method select default item from bottom navigation and calculate offset from left of screen for future use
     *
     * @param defaultSelectedItemPosition An int value as index of default selected item start from zero to last index
     * @return void
     */
    public void initDefaultItem(int defaultSelectedItemPosition) {
        newSelectedIndex = defaultSelectedItemPosition;
        prepareSelectItemAnimation();
        runAnimationOnClickItem();
        selectedItemHorizontallyOffset = (int) (imgNavigationBackgroundSelectedItem.getLeft() - 2 * emptyRelativeLayout.getWidth() * centerMainWidth / 5);
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

    public void setItemExpandable(int index, boolean itemExpandable) {
        switch (index) {
            case 0:
                this.firstItemExpandable = itemExpandable;
                break;
            case 1:
                this.secondItemExpandable = itemExpandable;
                break;
            case 2:
                this.thirdItemExpandable = itemExpandable;
                break;
            case 3:
                this.fourthItemExpandable = itemExpandable;
                break;
            case 4:
                this.fifthItemExpandable = itemExpandable;
                break;
        }
    }

    public int setItemExpandableLayout(int index, int layoutId) {

        switch (index) {
            case 0:
                if (this.firstItemExpandable) {
                    bottomFrameLayout.removeAllViews();
                    bottomFrameLayout.addView(getViewById(layoutId));
                    return ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY;
                } else
                    return SELECTED_ITEM_EXPANDABLE_DISABLE;
            case 1:
                if (this.secondItemExpandable) {
                    bottomFrameLayout.removeAllViews();
                    bottomFrameLayout.addView(getViewById(layoutId));
                    return ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY;
                } else
                    return SELECTED_ITEM_EXPANDABLE_DISABLE;
            case 2:
                if (this.thirdItemExpandable) {
                    bottomFrameLayout.removeAllViews();
                    bottomFrameLayout.addView(getViewById(layoutId));
                    return ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY;
                } else
                    return SELECTED_ITEM_EXPANDABLE_DISABLE;
            case 3:
                if (this.fourthItemExpandable) {
                    bottomFrameLayout.removeAllViews();
                    bottomFrameLayout.addView(getViewById(layoutId));
                    return ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY;
                } else
                    return SELECTED_ITEM_EXPANDABLE_DISABLE;
            case 4:
                if (this.fifthItemExpandable) {
                    bottomFrameLayout.removeAllViews();
                    bottomFrameLayout.addView(getViewById(layoutId));
                    return ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY;
                } else
                    return SELECTED_ITEM_EXPANDABLE_DISABLE;
            default:
                return SELECTED_ITEM_EXPANDABLE_FAILED;
        }
    }

    private void setFloatingActionButtonIconWithColor() {

        VectorChildFinder vector;

        if (lastSelectedIndex != -1) {
            vector = new VectorChildFinder(context, getDrawableIdBasedIndex(lastSelectedIndex), (ImageView) lastSelectedImageView);
            vector.findPathByName("main_path").setFillColor(getResources().getColor(R.color.fasation_inactive_item_icon_color));
        }

        vector = new VectorChildFinder(context, getDrawableIdBasedIndex(newSelectedIndex), (ImageView) newSelectedImageView);
        vector.findPathByName("main_path").setFillColor(getResources().getColor(R.color.fasation_active_item_icon_color));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_navigation_items_first:
            case R.id.ctl_navigation_items_first:
                if (newSelectedIndex != 0) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 0;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation();
                    prepareSelectedItemBackgroundAnimation();
                    runAnimationOnClickItem();
                    invalidate();
                }
                break;
            case R.id.img_navigation_items_second:
            case R.id.ctl_navigation_items_second:
                if (newSelectedIndex != 1) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 1;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation();
                    prepareSelectedItemBackgroundAnimation();
                    runAnimationOnClickItem();
                    invalidate();
                }
                break;
            case R.id.img_navigation_items_third:
            case R.id.ctl_navigation_items_third:
                if (newSelectedIndex != 2) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 2;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation();
                    prepareSelectedItemBackgroundAnimation();
                    runAnimationOnClickItem();
                    invalidate();
                }
                break;
            case R.id.img_navigation_items_fourth:
            case R.id.ctl_navigation_items_fourth:
                if (newSelectedIndex != 3) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 3;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation();
                    prepareSelectedItemBackgroundAnimation();
                    runAnimationOnClickItem();
                    invalidate();
                }
                break;
            case R.id.img_navigation_items_fifth:
            case R.id.ctl_navigation_items_fifth:
                if (newSelectedIndex != 4) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 4;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation();
                    prepareSelectedItemBackgroundAnimation();
                    runAnimationOnClickItem();
                    invalidate();
                }
                break;
        }
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
