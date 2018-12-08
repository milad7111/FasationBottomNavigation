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

        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);

        rootView = inflate(context, R.layout.fasation_bottom_navigation, this);

        imgSchNavigationItemsSwitcherFifth = rootView.findViewById(R.id.img_sch_navigation_items_switcher_fifth);
        imgSchNavigationItemsSwitcherFifth.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(context);
            }
        });
        imgSchNavigationItemsSwitcherFifth.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pencil_selected_24));
        imgSchNavigationItemsSwitcherFifth.setInAnimation(fadeInAnimation);
//        imgSchNavigationItemsSwitcherFifth.setOutAnimation(fadeOutAnimation);

        firstCustomItemView = rootView.findViewById(R.id.img_first);
        secondCustomItemView = rootView.findViewById(R.id.img_second);
        thirdCustomItemView = rootView.findViewById(R.id.img_third);
        fourthCustomItemView = rootView.findViewById(R.id.img_fourth);
//        fifthCustomItemView = rootView.findViewById(R.id.img_fifth);

        emptyRelativeLayout = rootView.findViewById(R.id.empty_layout);

        firstCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedIndex = newSelectedIndex;
                newSelectedIndex = 0;
                prepareDeSelectItemAnimation();
                prepareSelectItemAnimation(view);
                prepareCollapseItemAnimation();
                setLastSelectedItemImage();
                setNewSelectedItemImage();
                runAnimationOnClickItem();
            }
        });

        secondCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedIndex = newSelectedIndex;
                newSelectedIndex = 1;
                prepareDeSelectItemAnimation();
                prepareSelectItemAnimation(view);
                prepareCollapseItemAnimation();
                setLastSelectedItemImage();
                setNewSelectedItemImage();
                runAnimationOnClickItem();
            }
        });

        thirdCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedIndex = newSelectedIndex;
                newSelectedIndex = 2;
                prepareDeSelectItemAnimation();
                prepareSelectItemAnimation(view);
                prepareCollapseItemAnimation();
                setLastSelectedItemImage();
                setNewSelectedItemImage();
                runAnimationOnClickItem();
            }
        });

        fourthCustomItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedIndex = newSelectedIndex;
                newSelectedIndex = 3;
                prepareDeSelectItemAnimation();
                prepareSelectItemAnimation(view);
                prepareExpandItemAnimation();
                setLastSelectedItemImage();
                setNewSelectedItemImage();
                runAnimationOnClickItem();
            }
        });

        imgSchNavigationItemsSwitcherFifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedIndex = newSelectedIndex;
                newSelectedIndex = 4;
                prepareDeSelectItemAnimation();
                prepareSelectItemAnimation(view);
                prepareExpandItemAnimation();
                setLastSelectedItemImage();
                setNewSelectedItemImage();
                runAnimationOnClickItem();
            }
        });

//        fifthCustomItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lastSelectedIndex = newSelectedIndex;
//                newSelectedIndex = 4;
//                prepareDeSelectItemAnimation();
//                prepareSelectItemAnimation(view);
//                prepareCollapseItemAnimation();
//                setLastSelectedItemImage();
//                setNewSelectedItemImage();
//                runAnimationOnClickItem();
//            }
//        });
    }

    private void prepareDeSelectItemAnimation() {
        final View deSelectedView = getViewBasedIndex(lastSelectedIndex);

        moveDeSelectedItemAnimator = ValueAnimator.ofInt(deSelectedView.getPaddingBottom(), 24);
        moveDeSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                deSelectedView.setPadding(deSelectedView.getPaddingLeft(), deSelectedView.getPaddingTop(), deSelectedView.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
            }
        });
        moveDeSelectedItemAnimator.setDuration(500);

        imgSchNavigationItemsSwitcherFifth.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pencil_selected_24));

//        setImageSizeAnimation(deSelectedView, 1000, SET_SMALLER_SIZE);
    }

    private void prepareSelectItemAnimation(final View view) {
        moveSelectedItemAnimator = ValueAnimator.ofInt(view.getPaddingBottom(), 76);
        moveSelectedItemAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
            }
        });
        moveSelectedItemAnimator.setDuration(500);

        ((ImageSwitcher) view).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pencil_selected_48));

//        setImageSizeAnimation(view, 1000, SET_BIGGER_SIZE);
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
                return imgSchNavigationItemsSwitcherFifth;
            default:
                return null;
        }
    }

    private void runAnimationOnClickItem() {
        moveDeSelectedItemAnimator.start();
        moveSelectedItemAnimator.start();
//        expandSelectedItemAnimator.start();
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
