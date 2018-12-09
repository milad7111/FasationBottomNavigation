package com.example.fasationbottomnavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

public class FasationBottomNavigation extends ConstraintLayout {

    //region Declare Constants
    private static final int NOT_DEFINED = -777;
    private static final boolean SET_BIGGER_SIZE = true;
    private static final boolean SET_SMALLER_SIZE = false;
    //endregion Declare Constants

    //region Declare Variables
    private int lastSelectedIndex = 2;
    private int newSelectedIndex = 2;

    private boolean fifthImageViewSetSource = true;
    //endregion Declare Variables

    //region Declarer Arrays & Lists
    //endregion Declarer Arrays & Lists

    //region Declare Objects
    private Context context;

    private ViewSwitcher.ViewFactory imageSwitcherFactory;

    Animation fadeInAnimation;
    Animation fadeOutAnimation;

    private ValueAnimator moveSelectedItemAnimator;
    private ValueAnimator changeSizeSelectedItemAnimator;
    private ValueAnimator moveDeSelectedItemAnimator;
    private ValueAnimator changeSizeDeSelectedItemAnimator;
    private ValueAnimator expandSelectedItemAnimator;
    //endregion Declare Objects

    //region Declare Views
    View rootView;

    ImageSwitcher imgSchNavigationItemsSwitcherFirst;
    ImageSwitcher imgSchNavigationItemsSwitcherSecond;
    ImageSwitcher imgSchNavigationItemsSwitcherThird;
    ImageSwitcher imgSchNavigationItemsSwitcherFourth;
    ImageSwitcher imgSchNavigationItemsSwitcherFifth;

    ImageButton firstCustomItemView;
    ImageButton secondCustomItemView;
    ImageButton thirdCustomItemView;
    ImageButton fourthCustomItemView;
    ImageButton fifthCustomItemView;

    RelativeLayout emptyRelativeLayout;
    //endregion Declare Views

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

    public FasationBottomNavigation(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public FasationBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    private void init(final Context context) {

        imageSwitcherFactory = new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(context);
            }
        };

        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);

        rootView = inflate(context, R.layout.fasation_bottom_navigation, this);

        emptyRelativeLayout = rootView.findViewById(R.id.empty_layout);

        imgSchNavigationItemsSwitcherFirst = rootView.findViewById(R.id.img_sch_navigation_items_switcher_first);
        imgSchNavigationItemsSwitcherSecond = rootView.findViewById(R.id.img_sch_navigation_items_switcher_second);
        imgSchNavigationItemsSwitcherThird = rootView.findViewById(R.id.img_sch_navigation_items_switcher_third);
        imgSchNavigationItemsSwitcherFourth = rootView.findViewById(R.id.img_sch_navigation_items_switcher_fourth);
        imgSchNavigationItemsSwitcherFifth = rootView.findViewById(R.id.img_sch_navigation_items_switcher_fifth);

        imgSchNavigationItemsSwitcherFirst.setFactory(imageSwitcherFactory);
        imgSchNavigationItemsSwitcherSecond.setFactory(imageSwitcherFactory);
        imgSchNavigationItemsSwitcherThird.setFactory(imageSwitcherFactory);
        imgSchNavigationItemsSwitcherFourth.setFactory(imageSwitcherFactory);
        imgSchNavigationItemsSwitcherFifth.setFactory(imageSwitcherFactory);

        imgSchNavigationItemsSwitcherFirst.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_selected_24));
        imgSchNavigationItemsSwitcherSecond.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.near_me_selected_24));
        imgSchNavigationItemsSwitcherThird.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.magnify_selected_24));
        imgSchNavigationItemsSwitcherFourth.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_selected_24));
        imgSchNavigationItemsSwitcherFifth.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pencil_selected_24));

        imgSchNavigationItemsSwitcherFirst.setInAnimation(fadeInAnimation);
        imgSchNavigationItemsSwitcherSecond.setInAnimation(fadeInAnimation);
        imgSchNavigationItemsSwitcherThird.setInAnimation(fadeInAnimation);
        imgSchNavigationItemsSwitcherFourth.setInAnimation(fadeInAnimation);
        imgSchNavigationItemsSwitcherFifth.setInAnimation(fadeInAnimation);

        initItemsPosition(2);

        imgSchNavigationItemsSwitcherFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 0) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 0;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                }
            }
        });

        imgSchNavigationItemsSwitcherSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 1) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 1;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                }
            }
        });

        imgSchNavigationItemsSwitcherThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 2) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 2;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                }
            }
        });

        imgSchNavigationItemsSwitcherFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 3) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 3;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                }
            }
        });

        imgSchNavigationItemsSwitcherFifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newSelectedIndex != 4) {
                    lastSelectedIndex = newSelectedIndex;
                    newSelectedIndex = 4;
                    prepareDeSelectItemAnimation();
                    prepareSelectItemAnimation(view);
                    runAnimationOnClickItem();
                }
            }
        });
    }

    private void initItemsPosition(int defaultSelectedItemPosition) {
        lastSelectedIndex = defaultSelectedItemPosition;
        newSelectedIndex = defaultSelectedItemPosition;
        prepareSelectItemAnimation(getViewBasedIndex(defaultSelectedItemPosition));
        runAnimationOnClickItem();
    }

    private void prepareDeSelectItemAnimation() {
        final View deSelectedView = getViewBasedIndex(lastSelectedIndex);

        moveDeSelectedItemAnimator = ValueAnimator.ofInt(deSelectedView.getPaddingBottom(), convertDpToPx(context, 0));
        moveDeSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                deSelectedView.setPadding(deSelectedView.getPaddingLeft(), deSelectedView.getPaddingTop(), deSelectedView.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
            }
        });
        moveDeSelectedItemAnimator.setDuration(500);

        ((ImageSwitcher) deSelectedView).setImageDrawable(ContextCompat.getDrawable(context, getImageDrawableIdBasedIndex(lastSelectedIndex, SET_SMALLER_SIZE)));
    }

    private void prepareSelectItemAnimation(final View view) {
        moveSelectedItemAnimator = ValueAnimator.ofInt(view.getPaddingBottom(), convertDpToPx(context, 24));
        moveSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
            }
        });
        moveSelectedItemAnimator.setDuration(500);

        ((ImageSwitcher) view).setImageDrawable(ContextCompat.getDrawable(context, getImageDrawableIdBasedIndex(newSelectedIndex, SET_BIGGER_SIZE)));
    }

    private View getViewBasedIndex(int index) {
        switch (index) {
            case 0:
                return imgSchNavigationItemsSwitcherFirst;
            case 1:
                return imgSchNavigationItemsSwitcherSecond;
            case 2:
                return imgSchNavigationItemsSwitcherThird;
            case 3:
                return imgSchNavigationItemsSwitcherFourth;
            case 4:
                return imgSchNavigationItemsSwitcherFifth;
            default:
                return null;
        }
    }

    private int getImageDrawableIdBasedIndex(int index, boolean sizeStatus) {
        switch (index) {
            case 0:
                return sizeStatus == SET_BIGGER_SIZE ? R.drawable.home_selected_48 : R.drawable.home_selected_24;
            case 1:
                return sizeStatus == SET_BIGGER_SIZE ? R.drawable.near_me_selected_48 : R.drawable.near_me_selected_24;
            case 2:
                return sizeStatus == SET_BIGGER_SIZE ? R.drawable.magnify_selected_48 : R.drawable.magnify_selected_24;
            case 3:
                return sizeStatus == SET_BIGGER_SIZE ? R.drawable.heart_selected_48 : R.drawable.heart_selected_24;
            case 4:
                return sizeStatus == SET_BIGGER_SIZE ? R.drawable.pencil_selected_48 : R.drawable.pencil_selected_24;
            default:
                return -1;
        }
    }

    private void runAnimationOnClickItem() {
        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator.start();

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator.start();
    }

    private void prepareExpandItemAnimation() {
//        final LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
//        expandSelectedItemAnimator = ValueAnimator.ofInt(emptyRelativeLayout.getHeight(), 1000);
//        expandSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                emptyRelativeLayout.setLayoutParams(mLayoutParams);
//            }
//        });
//        expandSelectedItemAnimator.setDuration(1000);
    }

    private void prepareCollapseItemAnimation() {
//        final LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        expandSelectedItemAnimator = ValueAnimator.ofInt(emptyRelativeLayout.getHeight(), 168);
//        expandSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                emptyRelativeLayout.setLayoutParams(mLayoutParams);
//            }
//        });
//        expandSelectedItemAnimator.setDuration(1000);
    }

    private void setLastSelectedItemImage() {
//        switch (lastSelectedIndex) {
//            case 0:
//                firstCustomItemView.setImageResource(R.drawable.home_selected_24);
//                break;
//            case 1:
//                secondCustomItemView.setImageResource(R.drawable.near_me_selected_24);
//                break;
//            case 2:
//                thirdCustomItemView.setImageResource(R.drawable.magnify_selected_24);
//                break;
//            case 3:
//                fourthCustomItemView.setImageResource(R.drawable.heart_selected_24);
//                break;
//            case 4:
//                fifthCustomItemView.setImageResource(R.drawable.pencil_selected_24);
//                break;
//            default:
//                break;
//        }
    }

    private void setNewSelectedItemImage() {
//        switch (newSelectedIndex) {
//            case 0:
//                firstCustomItemView.setImageResource(R.drawable.home_selected_48);
//                break;
//            case 1:
//                secondCustomItemView.setImageResource(R.drawable.near_me_selected_48);
//                break;
//            case 2:
//                thirdCustomItemView.setImageResource(R.drawable.magnify_selected_48);
//                break;
//            case 3:
//                fourthCustomItemView.setImageResource(R.drawable.heart_selected_48);
//                break;
//            case 4:
//                fifthCustomItemView.setImageResource(R.drawable.pencil_selected_48);
//                break;
//            default:
//                break;
//        }
    }

    private void setImageSizeAnimation(View view, int duration, boolean finalSizeStatus) {
        ResizeAnimation resizeAnimation = new ResizeAnimation(view, convertDpToPx(context, finalSizeStatus == SET_BIGGER_SIZE ? 48 : 24), convertDpToPx(context, finalSizeStatus == SET_BIGGER_SIZE ? 48 : 24));
        resizeAnimation.setDuration(duration);
        view.startAnimation(resizeAnimation);
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
