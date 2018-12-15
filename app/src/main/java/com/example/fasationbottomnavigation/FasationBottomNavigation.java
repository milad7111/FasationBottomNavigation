package com.example.fasationbottomnavigation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FasationBottomNavigation extends ConstraintLayout {

    //region Declare Constants
    private static final int NOT_DEFINED = -777;
    private static final boolean SET_BIGGER_SIZE = true;
    private static final boolean SET_SMALLER_SIZE = false;
    private static final int ITEM_EXPANDABLE_LAYOUT_ADDED_SUCCESSFULLY = 1000;
    private static final int SELECTED_ITEM_EXPANDABLE_DISABLE = 2000;
    private static final int SELECTED_ITEM_EXPANDABLE_FAILED = 3000;
    //endregion Declare Constants

    //region Declare Variables
    private int lastSelectedIndex = -1;
    private int newSelectedIndex = -1;
    private int selectedItemOffset = 26; //dp
    private int defaultItemOffset = 16; //dp
    private int selectedItemHorizontallyOffset;
    private int defaultSelectedItem = 2;

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
    private ObjectAnimator moveSelectedItemBackgroundAnimator;

    private ValueAnimator expandSelectedItemFrameLayoutAnimator;
    private ValueAnimator moveDeSelectedItemAnimator;
    private ValueAnimator moveSelectedItemAnimator;

    private Animation lastSelectedViewReSizeAnimation;
    private Animation newSelectedViewReSizeAnimation;
    //endregion Declare Objects

    //region Declare Views
    View rootView;
    View lastSelectedView;
    View newSelectedView;

    ImageView imgNavigationBackgroundSelectedItem;

    ImageView firstCustomItemView;
    ImageView secondCustomItemView;
    ImageView thirdCustomItemView;
    ImageView fourthCustomItemView;
    ImageView fifthCustomItemView;

    RelativeLayout emptyRelativeLayout;
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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!runDefault)
            initDefaultItem(defaultSelectedItem);

        runDefault = true;
    }

    //region Declare Methods
    private void init(final Context context) {

        rootView = inflate(context, R.layout.fasation_bottom_navigation, this);
        emptyRelativeLayout = rootView.findViewById(R.id.empty_layout);
//        bottomFrameLayout = rootView.findViewById(R.id.bottom_frame_layout);

        imgNavigationBackgroundSelectedItem = rootView.findViewById(R.id.img_navigation_background_selected_item);

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
                    handleExpandableItem();
                    prepareSelectedItemBackgroundAnimation();
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
                    prepareSelectedItemBackgroundAnimation();
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
                    prepareSelectedItemBackgroundAnimation();
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
                    prepareSelectedItemBackgroundAnimation();
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
                    prepareSelectedItemBackgroundAnimation();
                    runAnimationOnClickItem();
                    invalidate();
                }
            }
        });
    }

    private void handleExpandableItem() {
//        final ConstraintLayout.LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, convertDpToPx(context, 200));
//        expandSelectedItemFrameLayoutAnimator = ValueAnimator.ofInt(0, convertDpToPx(context, 200));
//        expandSelectedItemFrameLayoutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mLayoutParams.height = (Integer) valueAnimator.getAnimatedValue();
//                bottomFrameLayout.setLayoutParams(mLayoutParams);
//                bottomFrameLayout.requestLayout();
//            }
//        });
//        expandSelectedItemFrameLayoutAnimator.setDuration(1000);
    }

    private void prepareDeSelectItemAnimation() {

        lastSelectedView = getViewBasedIndex(lastSelectedIndex);

        if (lastSelectedView != null) {
            if (moveDeSelectedItemAnimator != null && moveDeSelectedItemAnimator.isRunning())
                moveDeSelectedItemAnimator.end();

            final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) lastSelectedView.getLayoutParams();
            moveDeSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, defaultItemOffset));
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

        if (moveSelectedItemAnimator != null && moveSelectedItemAnimator.isRunning())
            moveSelectedItemAnimator.end();

        final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) newSelectedView.getLayoutParams();
        moveSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, defaultItemOffset) + convertDpToPx(context, selectedItemOffset));
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

    private void prepareSelectedItemBackgroundAnimation() {
        int xCurrentPosition = imgNavigationBackgroundSelectedItem.getLeft();
        int xNewPosition = (int) (selectedItemHorizontallyOffset + newSelectedIndex * emptyRelativeLayout.getWidth() * 0.95 / 5);

        moveSelectedItemBackgroundAnimator = ObjectAnimator.ofFloat(imgNavigationBackgroundSelectedItem, "translationX", xNewPosition - xCurrentPosition);
        moveSelectedItemBackgroundAnimator.setDuration(200);
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
        if (moveSelectedItemBackgroundAnimator != null)
            moveSelectedItemBackgroundAnimator.start();

//        if (expandSelectedItemFrameLayoutAnimator != null)
//            expandSelectedItemFrameLayoutAnimator.start();

        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator.start();

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator.start();

        if (lastSelectedView != null)
            lastSelectedView.startAnimation(lastSelectedViewReSizeAnimation);

        if (newSelectedView != null)
            newSelectedView.startAnimation(newSelectedViewReSizeAnimation);
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
        prepareSelectItemAnimation(getViewBasedIndex(defaultSelectedItemPosition));
        runAnimationOnClickItem();
        selectedItemHorizontallyOffset = (int) (imgNavigationBackgroundSelectedItem.getLeft() - 2 * emptyRelativeLayout.getWidth() * 0.95 / 5);
        prepareSelectedItemBackgroundAnimation();
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
