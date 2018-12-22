package com.example.fasationbottomnavigation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.devs.vectorchildfinder.VectorChildFinder;

public class FasationBottomNavigation extends ConstraintLayout implements View.OnClickListener {

    //region Declare Constants
    private static final int NOT_DEFINED = -777;
    private static final boolean SET_BIGGER_SIZE = true;
    private static final boolean SET_SMALLER_SIZE = false;
    //endregion Declare Constants

    //region Declare Variables
    private int emptyRelativeLayoutHeight = 40; //dp
    private int defaultItemPadding = 8; //dp
    private int defaultItemOffset = 0; //dp
    private int selectedItemHorizontallyOffset;

    private float imageBiggerScale = 100.0f / 80;

    private int defaultItemIconSize = 24; //dp

    private int defaultSelectedItemIndex = 2;
    private int drawableSelectedItemIndex = 2;
    private int lastSelectedIndex = -1;
    private int newSelectedIndex = defaultSelectedItemIndex;

    private int bezierWidth = 0;
    private int bezierHeight = 0;

    private int firstItemIconWidth = 22; //dp
    private int firstItemIconHeight = defaultItemIconSize; //dp
    private float firstItemOffset = emptyRelativeLayoutHeight - 1 * defaultItemPadding - firstItemIconHeight * imageBiggerScale / 2; //dp

    private int secondItemIconWidth = 24; //dp
    private int secondItemIconHeight = defaultItemIconSize; //dp
    private float secondItemOffset = emptyRelativeLayoutHeight - 1 * defaultItemPadding - secondItemIconHeight * imageBiggerScale / 2; //dp

    private int thirdItemIconWidth = defaultItemIconSize; //dp
    private int thirdItemIconHeight = defaultItemIconSize; //dp
    private float thirdItemOffset = emptyRelativeLayoutHeight - 1 * defaultItemPadding - thirdItemIconHeight * imageBiggerScale / 2; //dp

    private int fourthItemIconWidth = 21; //dp
    private int fourthItemIconHeight = defaultItemIconSize; //dp
    private float fourthItemOffset = emptyRelativeLayoutHeight - 1 * defaultItemPadding - fourthItemIconHeight * imageBiggerScale / 2; //dp

    private int fifthItemIconWidth = defaultItemIconSize; //dp
    private int fifthItemIconHeight = 18; //dp
    private float fifthItemOffset = emptyRelativeLayoutHeight - 1 * defaultItemPadding - fifthItemIconHeight * imageBiggerScale / 2; //dp

    private double centerMainWidth = 0.90;
    private double leftMainWidth = (1.0 - centerMainWidth) / 2.0;

    private boolean defaultItemSelectedStatus = false;
    //endregion Declare Variables

    //region Declare Objects
    private Context context;

    private ObjectAnimator moveSelectedItemBackgroundAnimator;

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

    ImageView firstBadgeImageView;
    ImageView secondBadgeImageView;
    ImageView thirdBadgeImageView;
    ImageView fourthBadgeImageView;
    ImageView fifthBadgeImageView;

    private RelativeLayout emptyRelativeLayout;
    private BezierView centerContent;
    private FasationBottomNavigationItemsClickListener listener;
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

    private boolean firstItemSolidStatus = false;
    private boolean secondItemSolidStatus = false;
    private boolean thirdItemSolidStatus = false;
    private boolean fourthItemSolidStatus = false;
    private boolean fifthItemSolidStatus = false;
    private int horizontallyOffset;
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

        if (!defaultItemSelectedStatus)
            initDefaultItem(defaultSelectedItemIndex);
        defaultItemSelectedStatus = true;

        drawSelectedItemBackground(canvas, drawableSelectedItemIndex);
    }

    private void drawSelectedItemBackground(Canvas canvas, int index) {
        if (index != -1 && !isItemSolid(index)) {
            centerContent.setStartX(horizontallyOffset + index * bezierWidth);
            centerContent.draw(canvas);
            prepareSelectedItemBackgroundAnimation();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_navigation_items_first:
            case R.id.ctl_navigation_items_first:
                handleItemClick(0);
                break;
            case R.id.img_navigation_items_second:
            case R.id.ctl_navigation_items_second:
                handleItemClick(1);
                break;
            case R.id.img_navigation_items_third:
            case R.id.ctl_navigation_items_third:
                handleItemClick(2);
                break;
            case R.id.img_navigation_items_fourth:
            case R.id.ctl_navigation_items_fourth:
                handleItemClick(3);
                break;
            case R.id.img_navigation_items_fifth:
            case R.id.ctl_navigation_items_fifth:
                handleItemClick(4);
                break;
        }
    }
    //endregion Main Callbacks

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

        firstBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_first);
        secondBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_second);
        thirdBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_third);
        fourthBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_fourth);
        fifthBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_fifth);

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

    /**
     * This method select default item from bottom navigation and calculate offset from left of screen for future use
     *
     * @param defaultSelectedItemPosition An int value as index of default selected item start from zero to last index
     * @return void
     */
    public void initDefaultItem(int defaultSelectedItemPosition) {
        newSelectedIndex = defaultSelectedItemPosition;
        prepareSelectItemAnimation();
        setItemsIconColor();
        runAnimationOnClickItem();
        selectedItemHorizontallyOffset = (int) (imgNavigationBackgroundSelectedItem.getLeft() - 2 * emptyRelativeLayout.getWidth() * centerMainWidth / 5);

        bezierWidth = (int) (centerMainWidth * emptyRelativeLayout.getWidth() / 5);
        bezierHeight = getHeight() - emptyRelativeLayout.getHeight() - convertDpToPx(context, 8);

        centerContent.setWidth(bezierWidth);
        centerContent.setHeight(bezierHeight);
        centerContent.setStartY(convertDpToPx(context, 8));

        horizontallyOffset = (int) (leftMainWidth * emptyRelativeLayout.getWidth());
    }

    private void handleItemClick(int selectedItemIndex) {
        if (newSelectedIndex != selectedItemIndex) {
            if (!isItemSolid(newSelectedIndex))
                lastSelectedIndex = newSelectedIndex;

            newSelectedIndex = selectedItemIndex;

            if (!isItemSolid(newSelectedIndex)) {
                drawableSelectedItemIndex = selectedItemIndex;
                handleItemAnimations();
            }
        }

        listener.onFasationBottomNavigationItemClick(newSelectedIndex);
    }

    private void handleItemAnimations() {
        prepareDeSelectItemAnimation();
        prepareSelectItemAnimation();
        prepareSelectedItemBackgroundAnimation();
        setItemsIconColor();
        runAnimationOnClickItem();
    }

    private void prepareDeSelectItemAnimation() {

        lastSelectedImageView = getImageViewViewBasedIndex(lastSelectedIndex);
        lastSelectedParentView = getParentViewBasedIndex(lastSelectedIndex);

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
        newSelectedParentView = getParentViewBasedIndex(newSelectedIndex);

        if (newSelectedParentView != null) {
            if (moveSelectedItemAnimator != null && moveSelectedItemAnimator.isRunning())
                moveSelectedItemAnimator.end();

            final ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) newSelectedParentView.getLayoutParams();
            moveSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, getSelectedItemOffsetBasedIndex(newSelectedIndex)));
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
    }

    private void prepareSelectedItemBackgroundAnimation() {

        int xCurrentPosition = imgNavigationBackgroundSelectedItem.getLeft();
        int xNewPosition = (int) (selectedItemHorizontallyOffset + newSelectedIndex * emptyRelativeLayout.getWidth() * centerMainWidth / 5);

        moveSelectedItemBackgroundAnimator = ObjectAnimator.ofFloat(imgNavigationBackgroundSelectedItem, "translationX", xNewPosition - xCurrentPosition);
        moveSelectedItemBackgroundAnimator.setDuration(200);
    }

    private void setImageSizeAnimation(View view, int duration, boolean finalSizeStatus) {
        if (finalSizeStatus == SET_SMALLER_SIZE) {
            lastSelectedViewReSizeAnimation = new ResizeAnimation(view,
                    convertDpToPx(context, getIconWidthBasedIndex(lastSelectedIndex)), convertDpToPx(context, getIconHeightBasedIndex(lastSelectedIndex)));
            lastSelectedViewReSizeAnimation.setDuration(duration);
        } else if (finalSizeStatus == SET_BIGGER_SIZE) {
            newSelectedViewReSizeAnimation = new ResizeAnimation(view,
                    (int) (convertDpToPx(context, getIconWidthBasedIndex(newSelectedIndex)) * imageBiggerScale), (int) (convertDpToPx(context, getIconHeightBasedIndex(newSelectedIndex)) * imageBiggerScale));
            newSelectedViewReSizeAnimation.setDuration(duration);
        }
    }

    private void runAnimationOnClickItem() {
        if (moveSelectedItemBackgroundAnimator != null)
            moveSelectedItemBackgroundAnimator.start();

        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator.start();

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator.start();

        if (lastSelectedImageView != null)
            lastSelectedImageView.startAnimation(lastSelectedViewReSizeAnimation);

        if (newSelectedImageView != null)
            newSelectedImageView.startAnimation(newSelectedViewReSizeAnimation);
    }

    public void enableBadgeOnItem(int badgeIndex) throws NullPointerException {
        if (getBadgeImageViewBasedIndex(badgeIndex) != null)
            getBadgeImageViewBasedIndex(badgeIndex).setVisibility(VISIBLE);
        else
            throw new NullPointerException("badgeIndex is out of bound. badgeIndex must be in range of bottom sheet indexes.");
    }

    public void disableBadgeOnItem(int badgeIndex) throws NullPointerException {
        if (getBadgeImageViewBasedIndex(badgeIndex) != null)
            getBadgeImageViewBasedIndex(badgeIndex).setVisibility(GONE);
        else
            throw new NullPointerException("badgeIndex is out of bound. badgeIndex must be in range of bottom sheet indexes.");
    }

    private View getParentViewBasedIndex(int index) {
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

    private View getBadgeImageViewBasedIndex(int index) {
        switch (index) {
            case 0:
                return firstBadgeImageView;
            case 1:
                return secondBadgeImageView;
            case 2:
                return thirdBadgeImageView;
            case 3:
                return fourthBadgeImageView;
            case 4:
                return fifthBadgeImageView;
            default:
                return null;
        }
    }

    private int getIconWidthBasedIndex(int index) {
        switch (index) {
            case 0:
                return firstItemIconWidth;
            case 1:
                return secondItemIconWidth;
            case 2:
                return thirdItemIconWidth;
            case 3:
                return fourthItemIconWidth;
            case 4:
                return fifthItemIconWidth;
            default:
                return -1;
        }
    }

    private int getIconHeightBasedIndex(int index) {
        switch (index) {
            case 0:
                return firstItemIconHeight;
            case 1:
                return secondItemIconHeight;
            case 2:
                return thirdItemIconHeight;
            case 3:
                return fourthItemIconHeight;
            case 4:
                return fifthItemIconHeight;
            default:
                return -1;
        }
    }

    private float getSelectedItemOffsetBasedIndex(int index) {
        switch (index) {
            case 0:
                return firstItemOffset;
            case 1:
                return secondItemOffset;
            case 2:
                return thirdItemOffset;
            case 3:
                return fourthItemOffset;
            case 4:
                return fifthItemOffset;
            default:
                return -1;
        }
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
                return R.drawable.ic_layer;
            case 1:
                return R.drawable.ic_route;
            case 2:
                return R.drawable.ic_search;
            case 3:
                return R.drawable.ic_notification;
            case 4:
                return R.drawable.ic_drawer_menu;
            default:
                return -1;
        }
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

    private void setItemsIconColor() {

        VectorChildFinder vector;

        if (lastSelectedIndex != -1) {
            vector = new VectorChildFinder(context, getDrawableIdBasedIndex(lastSelectedIndex), (ImageView) lastSelectedImageView);
            vector.findPathByName("main_path").setFillColor(getResources().getColor(R.color.fasation_bottom_navigation_inactive_item_icon_color));
        }

        vector = new VectorChildFinder(context, getDrawableIdBasedIndex(newSelectedIndex), (ImageView) newSelectedImageView);
        vector.findPathByName("main_path").setFillColor(getResources().getColor(R.color.fasation_bottom_navigation_active_item_icon_color));
    }

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

    public void onDestroy() {
        if (moveSelectedItemBackgroundAnimator != null)
            moveSelectedItemBackgroundAnimator.end();

        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator.end();

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator.end();

        if (lastSelectedImageView != null && lastSelectedImageView.getAnimation() != null)
            lastSelectedImageView.getAnimation().cancel();

        if (newSelectedImageView != null && newSelectedImageView.getAnimation() != null)
            newSelectedImageView.getAnimation().cancel();
    }

    public void onItemClickListener(@NonNull FasationBottomNavigationItemsClickListener listener) {
        this.listener = listener;

        if (defaultItemSelectedStatus)
            this.listener.onFasationBottomNavigationItemClick(newSelectedIndex);
    }
    //endregion Declare Methods

    //region Getter & Setter
    public void setItemSolidStatus(int index, boolean solidStatus) {
        switch (index) {
            case 0:
                firstItemSolidStatus = solidStatus;
            case 1:
                secondItemSolidStatus = solidStatus;
            case 2:
                thirdItemSolidStatus = solidStatus;
            case 3:
                fourthItemSolidStatus = solidStatus;
            case 4:
                fifthItemSolidStatus = solidStatus;
        }
    }

    public boolean isItemSolid(int index) {
        switch (index) {
            case 0:
                return firstItemSolidStatus;
            case 1:
                return secondItemSolidStatus;
            case 2:
                return thirdItemSolidStatus;
            case 3:
                return fourthItemSolidStatus;
            case 4:
                return fifthItemSolidStatus;
            default:
                return false;
        }
    }
    //endregion Getter & Setter

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
