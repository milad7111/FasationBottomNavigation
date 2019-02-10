package fasation.bottom.navigation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.RelativeLayout

import com.devs.vectorchildfinder.VectorChildFinder
import fasation.bottom.navigation.R

class FasationBottomNavigation @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr),
        View.OnClickListener {

    //region Declare Constants
    private val SET_BIGGER_SIZE = true
    private val SET_SMALLER_SIZE = false
    //endregion Declare Constants

    //region Declare Variables
    private val emptyRelativeLayoutHeight = 40 //dp
    private val defaultItemPadding = 8 //dp
    private var defaultItemOffset = 0 //dp
    private var selectedItemHorizontallyOffset: Int = 0

    private val imageBiggerScale = 100.0f / 80

    private val defaultItemIconSize = 24 //dp

    private val defaultSelectedItemIndex = 2
    private var drawableSelectedItemIndex = 2
    private var lastSelectedIndex = -1
    private var newSelectedIndex = defaultSelectedItemIndex

    private var bezierWidth = 0
    private var bezierHeight = 0

    private val firstItemIconWidth = 22 //dp
    private val firstItemIconHeight = defaultItemIconSize //dp
    private val firstItemOffset = emptyRelativeLayoutHeight.toFloat() - defaultItemPadding.toFloat() - firstItemIconHeight * imageBiggerScale / 2 //dp

    private val secondItemIconWidth = 24 //dp
    private val secondItemIconHeight = defaultItemIconSize //dp
    private val secondItemOffset = emptyRelativeLayoutHeight.toFloat() - defaultItemPadding.toFloat() - secondItemIconHeight * imageBiggerScale / 2 //dp

    private val thirdItemIconWidth = defaultItemIconSize //dp
    private val thirdItemIconHeight = defaultItemIconSize //dp
    private val thirdItemOffset = emptyRelativeLayoutHeight.toFloat() - defaultItemPadding.toFloat() - thirdItemIconHeight * imageBiggerScale / 2 //dp

    private val fourthItemIconWidth = 21 //dp
    private val fourthItemIconHeight = defaultItemIconSize //dp
    private val fourthItemOffset = emptyRelativeLayoutHeight.toFloat() - defaultItemPadding.toFloat() - fourthItemIconHeight * imageBiggerScale / 2 //dp

    private val fifthItemIconWidth = defaultItemIconSize //dp
    private val fifthItemIconHeight = 18 //dp
    private val fifthItemOffset = emptyRelativeLayoutHeight.toFloat() - defaultItemPadding.toFloat() - fifthItemIconHeight * imageBiggerScale / 2 //dp

    private val centerMainWidth = 0.90
    private val leftMainWidth = (1.0 - centerMainWidth) / 2.0

    private var defaultItemSelectedStatus = false
    private var firstItemSolidStatus = false
    private var secondItemSolidStatus = false
    private var thirdItemSolidStatus = false
    private var fourthItemSolidStatus = false
    private var fifthItemSolidStatus = false
    //endregion Declare Variables

    //region Declare Objects
    private var moveSelectedItemBackgroundAnimator: ObjectAnimator? = null

    private var moveDeSelectedItemAnimator: ValueAnimator? = null
    private var moveSelectedItemAnimator: ValueAnimator? = null

    private var lastSelectedViewReSizeAnimation: Animation? = null
    private var newSelectedViewReSizeAnimation: Animation? = null
    //endregion Declare Objects

    //region Declare Views
    private var lastSelectedImageView: ImageView? = null
    private var newSelectedImageView: ImageView? = null

    private var lastSelectedParentView: View? = null
    private var newSelectedParentView: View? = null

    private var imgNavigationBackgroundSelectedItem: ImageView? = null

    private var firstCustomItemView: ConstraintLayout? = null
    private var secondCustomItemView: ConstraintLayout? = null
    private var thirdCustomItemView: ConstraintLayout? = null
    private var fourthCustomItemView: ConstraintLayout? = null
    private var fifthCustomItemView: ConstraintLayout? = null

    private var firstImageView: ImageView? = null
    private var secondImageView: ImageView? = null
    private var thirdImageView: ImageView? = null
    private var fourthImageView: ImageView? = null
    private var fifthImageView: ImageView? = null

    private var firstBadgeImageView: ImageView? = null
    private var secondBadgeImageView: ImageView? = null
    private var thirdBadgeImageView: ImageView? = null
    private var fourthBadgeImageView: ImageView? = null
    private var fifthBadgeImageView: ImageView? = null

    private var emptyRelativeLayout: RelativeLayout? = null
    private var centerContent: BezierView? = null
    private var listener: FasationBottomNavigationItemsClickListener? = null
    //endregion Declare Views

    //region Custom Attributes
    private var horizontallyOffset: Int = 0
    //endregion Custom Attributes

    //region Constructor
    init {
        this.setWillNotDraw(false)
        initMain(context)
    }
    //endregion Constructor

    //region Main Callbacks
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!defaultItemSelectedStatus)
            initDefaultItem(defaultSelectedItemIndex)
        defaultItemSelectedStatus = true

        drawSelectedItemBackground(canvas, drawableSelectedItemIndex)
    }

    private fun drawSelectedItemBackground(canvas: Canvas, index: Int) {
        if (index != -1 && !isItemSolid(index)) {
            centerContent!!.setStartX(horizontallyOffset + index * bezierWidth)
            centerContent!!.draw(canvas)
            prepareSelectedItemBackgroundAnimation()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.img_navigation_items_first, R.id.ctl_navigation_items_first -> handleItemClick(0)
            R.id.img_navigation_items_second, R.id.ctl_navigation_items_second -> handleItemClick(1)
            R.id.img_navigation_items_third, R.id.ctl_navigation_items_third -> handleItemClick(2)
            R.id.img_navigation_items_fourth, R.id.ctl_navigation_items_fourth -> handleItemClick(3)
            R.id.img_navigation_items_fifth, R.id.ctl_navigation_items_fifth -> handleItemClick(4)
        }
    }
    //endregion Main Callbacks

    //region Declare Methods
    private fun initMain(context: Context) {

        val rootView = View.inflate(context, R.layout.fasation_bottom_navigation, this)

        emptyRelativeLayout = rootView.findViewById(R.id.empty_layout)
        centerContent = buildBezierView()

        firstCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_first)
        secondCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_second)
        thirdCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_third)
        fourthCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_fourth)
        fifthCustomItemView = rootView.findViewById(R.id.ctl_navigation_items_fifth)

        imgNavigationBackgroundSelectedItem = rootView.findViewById(R.id.img_navigation_background_selected_item)

        firstImageView = rootView.findViewById(R.id.img_navigation_items_first)
        secondImageView = rootView.findViewById(R.id.img_navigation_items_second)
        thirdImageView = rootView.findViewById(R.id.img_navigation_items_third)
        fourthImageView = rootView.findViewById(R.id.img_navigation_items_fourth)
        fifthImageView = rootView.findViewById(R.id.img_navigation_items_fifth)

        firstBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_first)
        secondBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_second)
        thirdBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_third)
        fourthBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_fourth)
        fifthBadgeImageView = rootView.findViewById(R.id.img_badge_navigation_items_fifth)

        firstCustomItemView!!.setOnClickListener(this)
        secondCustomItemView!!.setOnClickListener(this)
        thirdCustomItemView!!.setOnClickListener(this)
        fourthCustomItemView!!.setOnClickListener(this)
        fifthCustomItemView!!.setOnClickListener(this)

        firstImageView!!.setOnClickListener(this)
        secondImageView!!.setOnClickListener(this)
        thirdImageView!!.setOnClickListener(this)
        fourthImageView!!.setOnClickListener(this)
        fifthImageView!!.setOnClickListener(this)
    }

    fun initDefaultItem(defaultSelectedItemPosition: Int) {
        newSelectedIndex = defaultSelectedItemPosition
        prepareSelectItemAnimation()
        setItemsIconColor()
        runAnimationOnClickItem()
        selectedItemHorizontallyOffset = (imgNavigationBackgroundSelectedItem!!.left - 2.0 * emptyRelativeLayout!!.width.toDouble() * centerMainWidth / 5).toInt()

        bezierWidth = (centerMainWidth * emptyRelativeLayout!!.width / 5).toInt()
        bezierHeight = height - emptyRelativeLayout!!.height - convertDpToPx(context, 8f)

        centerContent!!.width = bezierWidth
        centerContent!!.height = bezierHeight
        centerContent!!.setStartY(convertDpToPx(context, 8f))

        horizontallyOffset = (leftMainWidth * emptyRelativeLayout!!.width).toInt()
    }

    private fun handleItemClick(selectedItemIndex: Int) {
        if (newSelectedIndex != selectedItemIndex) {
            if (!isItemSolid(newSelectedIndex))
                lastSelectedIndex = newSelectedIndex

            newSelectedIndex = selectedItemIndex

            if (!isItemSolid(newSelectedIndex)) {
                drawableSelectedItemIndex = selectedItemIndex
                handleItemAnimations()
            }
        }

        listener!!.onFasationBottomNavigationItemClick(newSelectedIndex)
    }

    private fun handleItemAnimations() {
        prepareDeSelectItemAnimation()
        prepareSelectItemAnimation()
        prepareSelectedItemBackgroundAnimation()
        setItemsIconColor()
        runAnimationOnClickItem()
    }

    private fun prepareDeSelectItemAnimation() {

        lastSelectedImageView = getImageViewViewBasedIndex(lastSelectedIndex)
        lastSelectedParentView = getParentViewBasedIndex(lastSelectedIndex)

        if (lastSelectedParentView != null) {
            if (moveDeSelectedItemAnimator != null && moveDeSelectedItemAnimator!!.isRunning)
                moveDeSelectedItemAnimator!!.end()

            val mLayoutParams = lastSelectedParentView!!.layoutParams as ConstraintLayout.LayoutParams
            moveDeSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, defaultItemOffset.toFloat()))
            moveDeSelectedItemAnimator!!.addUpdateListener { valueAnimator ->
                mLayoutParams.bottomMargin = valueAnimator.animatedValue as Int
                lastSelectedParentView!!.requestLayout()
            }
            moveDeSelectedItemAnimator!!.duration = 500

            setImageSizeAnimation(lastSelectedImageView, 200, SET_SMALLER_SIZE)
        }
    }

    private fun prepareSelectItemAnimation() {

        newSelectedImageView = getImageViewViewBasedIndex(newSelectedIndex)
        newSelectedParentView = getParentViewBasedIndex(newSelectedIndex)

        if (newSelectedParentView != null) {
            if (moveSelectedItemAnimator != null && moveSelectedItemAnimator!!.isRunning)
                moveSelectedItemAnimator!!.end()

            val mLayoutParams = newSelectedParentView!!.layoutParams as ConstraintLayout.LayoutParams
            moveSelectedItemAnimator = ValueAnimator.ofInt(mLayoutParams.bottomMargin, convertDpToPx(context, getSelectedItemOffsetBasedIndex(newSelectedIndex)))
            moveSelectedItemAnimator!!.addUpdateListener { valueAnimator ->
                mLayoutParams.bottomMargin = valueAnimator.animatedValue as Int
                newSelectedParentView!!.requestLayout()
            }
            moveSelectedItemAnimator!!.duration = 500

            setImageSizeAnimation(newSelectedImageView, 200, SET_BIGGER_SIZE)
        }
    }

    private fun prepareSelectedItemBackgroundAnimation() {

        val xCurrentPosition = imgNavigationBackgroundSelectedItem!!.left.toFloat()
        val xNewPosition = (selectedItemHorizontallyOffset + newSelectedIndex.toDouble() * emptyRelativeLayout!!.width.toDouble() * centerMainWidth / 5).toFloat()

        moveSelectedItemBackgroundAnimator = ObjectAnimator.ofFloat(imgNavigationBackgroundSelectedItem, "translationX", xNewPosition - xCurrentPosition)
        moveSelectedItemBackgroundAnimator!!.duration = 200
    }

    private fun setImageSizeAnimation(view: View?, duration: Int, finalSizeStatus: Boolean) {
        if (finalSizeStatus == SET_SMALLER_SIZE) {
            lastSelectedViewReSizeAnimation = ResizeAnimation(view!!,
                    convertDpToPx(context, getIconWidthBasedIndex(lastSelectedIndex).toFloat()), convertDpToPx(context, getIconHeightBasedIndex(lastSelectedIndex).toFloat()))
            lastSelectedViewReSizeAnimation!!.duration = duration.toLong()
        } else if (finalSizeStatus == SET_BIGGER_SIZE) {
            newSelectedViewReSizeAnimation = ResizeAnimation(view!!,
                    (convertDpToPx(context, getIconWidthBasedIndex(newSelectedIndex).toFloat()) * imageBiggerScale).toInt(), (convertDpToPx(context, getIconHeightBasedIndex(newSelectedIndex).toFloat()) * imageBiggerScale).toInt())
            newSelectedViewReSizeAnimation!!.duration = duration.toLong()
        }
    }

    private fun runAnimationOnClickItem() {
        if (moveSelectedItemBackgroundAnimator != null)
            moveSelectedItemBackgroundAnimator!!.start()

        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator!!.start()

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator!!.start()

        if (lastSelectedImageView != null)
            lastSelectedImageView!!.startAnimation(lastSelectedViewReSizeAnimation)

        if (newSelectedImageView != null)
            newSelectedImageView!!.startAnimation(newSelectedViewReSizeAnimation)
    }

    @Throws(NullPointerException::class)
    fun enableBadgeOnItem(badgeIndex: Int) {
        if (getBadgeImageViewBasedIndex(badgeIndex) != null)
            getBadgeImageViewBasedIndex(badgeIndex)!!.visibility = View.VISIBLE
        else
            throw NullPointerException("badgeIndex is out of bound. badgeIndex must be in range of bottom sheet indexes.")
    }

    @Throws(NullPointerException::class)
    fun disableBadgeOnItem(badgeIndex: Int) {
        if (getBadgeImageViewBasedIndex(badgeIndex) != null)
            getBadgeImageViewBasedIndex(badgeIndex)!!.visibility = View.GONE
        else
            throw NullPointerException("badgeIndex is out of bound. badgeIndex must be in range of bottom sheet indexes.")
    }

    private fun getParentViewBasedIndex(index: Int): View? {
        when (index) {
            0 -> return firstCustomItemView
            1 -> return secondCustomItemView
            2 -> return thirdCustomItemView
            3 -> return fourthCustomItemView
            4 -> return fifthCustomItemView
            else -> return null
        }
    }

    private fun getBadgeImageViewBasedIndex(index: Int): View? {
        when (index) {
            0 -> return firstBadgeImageView
            1 -> return secondBadgeImageView
            2 -> return thirdBadgeImageView
            3 -> return fourthBadgeImageView
            4 -> return fifthBadgeImageView
            else -> return null
        }
    }

    private fun getIconWidthBasedIndex(index: Int): Int {
        when (index) {
            0 -> return firstItemIconWidth
            1 -> return secondItemIconWidth
            2 -> return thirdItemIconWidth
            3 -> return fourthItemIconWidth
            4 -> return fifthItemIconWidth
            else -> return -1
        }
    }

    private fun getIconHeightBasedIndex(index: Int): Int {
        when (index) {
            0 -> return firstItemIconHeight
            1 -> return secondItemIconHeight
            2 -> return thirdItemIconHeight
            3 -> return fourthItemIconHeight
            4 -> return fifthItemIconHeight
            else -> return -1
        }
    }

    private fun getSelectedItemOffsetBasedIndex(index: Int): Float {
        when (index) {
            0 -> return firstItemOffset
            1 -> return secondItemOffset
            2 -> return thirdItemOffset
            3 -> return fourthItemOffset
            4 -> return fifthItemOffset
            else -> return -1f
        }
    }

    private fun getImageViewViewBasedIndex(index: Int): ImageView? {
        when (index) {
            0 -> return firstImageView
            1 -> return secondImageView
            2 -> return thirdImageView
            3 -> return fourthImageView
            4 -> return fifthImageView
            else -> return null
        }
    }

    private fun getDrawableIdBasedIndex(index: Int): Int {
        when (index) {
            0 -> return R.drawable.ic_layer
            1 -> return R.drawable.ic_route
            2 -> return R.drawable.ic_search
            3 -> return R.drawable.ic_notification
            4 -> return R.drawable.ic_drawer_menu
            else -> return -1
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPx(context: Context?, dp: Float): Int {
        return (dp * context!!.resources.displayMetrics.density).toInt()
    }

    private fun setItemsIconColor() {

        var vector: VectorChildFinder

        if (lastSelectedIndex != -1) {
            vector = VectorChildFinder(context!!, getDrawableIdBasedIndex(lastSelectedIndex), lastSelectedImageView!!)
            vector.findPathByName("main_path").fillColor = resources.getColor(R.color.fasation_bottom_navigation_inactive_item_icon_color)
        }

        vector = VectorChildFinder(context!!, getDrawableIdBasedIndex(newSelectedIndex), newSelectedImageView!!)
        vector.findPathByName("main_path").fillColor = resources.getColor(R.color.fasation_bottom_navigation_active_item_icon_color)
    }

    private fun buildBezierView(): BezierView {
        val bezierView = BezierView(context, ContextCompat.getColor(context!!, R.color.fasation_bottom_navigation_background_color))
        bezierView.build(bezierWidth, bezierHeight, false)
        return bezierView
    }

    fun onDestroy() {
        if (moveSelectedItemBackgroundAnimator != null)
            moveSelectedItemBackgroundAnimator!!.end()

        if (moveDeSelectedItemAnimator != null)
            moveDeSelectedItemAnimator!!.end()

        if (moveSelectedItemAnimator != null)
            moveSelectedItemAnimator!!.end()

        if (lastSelectedImageView != null && lastSelectedImageView!!.animation != null)
            lastSelectedImageView!!.animation.cancel()

        if (newSelectedImageView != null && newSelectedImageView!!.animation != null)
            newSelectedImageView!!.animation.cancel()
    }

    fun onItemClickListener(listener: FasationBottomNavigationItemsClickListener) {
        this.listener = listener

        if (defaultItemSelectedStatus)
            this.listener!!.onFasationBottomNavigationItemClick(newSelectedIndex)
    }
    //endregion Declare Methods

    //region Getter & Setter
    fun setItemSolidStatus(index: Int, solidStatus: Boolean) {
        when (index) {
            0 -> {
                firstItemSolidStatus = solidStatus
                secondItemSolidStatus = solidStatus
                thirdItemSolidStatus = solidStatus
                fourthItemSolidStatus = solidStatus
                fifthItemSolidStatus = solidStatus
            }
            1 -> {
                secondItemSolidStatus = solidStatus
                thirdItemSolidStatus = solidStatus
                fourthItemSolidStatus = solidStatus
                fifthItemSolidStatus = solidStatus
            }
            2 -> {
                thirdItemSolidStatus = solidStatus
                fourthItemSolidStatus = solidStatus
                fifthItemSolidStatus = solidStatus
            }
            3 -> {
                fourthItemSolidStatus = solidStatus
                fifthItemSolidStatus = solidStatus
            }
            4 -> fifthItemSolidStatus = solidStatus
        }
    }

    fun isItemSolid(index: Int): Boolean {
        when (index) {
            0 -> return firstItemSolidStatus
            1 -> return secondItemSolidStatus
            2 -> return thirdItemSolidStatus
            3 -> return fourthItemSolidStatus
            4 -> return fifthItemSolidStatus
            else -> return false
        }
    }

    fun setDefaultItemOffset(defaultItemOffset: Int) {
        this.defaultItemOffset = defaultItemOffset
    }

    companion object {

        //region Declare Constants
        private val SET_BIGGER_SIZE = true
        private val SET_SMALLER_SIZE = false
    }
    //endregion Getter & Setter
}
