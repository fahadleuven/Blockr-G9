package com.swop;

import com.swop.uiElements.UIBlock;

import java.awt.*;

public class Scroller extends WindowSectionDecorator {
    public Scroller(WindowSection newWindowSection) {
        super(newWindowSection);
    }

    @Override
    public void draw(Graphics g) {
         tempWindowSection.draw(g);
         drawScrollBar(g);
    }

    @Override
    public void setPosition(Point p) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public boolean isWithin(int x, int y) {
        return false;
    }

    public void drawScrollBar(Graphics g){
        //outer
        g.setColor(Color.GRAY);
        g.fillRect(getPosition().x+getWidth() - scrollBarWidth, getPosition().y, scrollBarWidth, getHeight());

        //inner
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getPosition().x+getWidth() - scrollBarWidth + getScrollBarWidthInnerDiff, getPosition().y + getScrollBarHeightInnerDiff , scrollBarWidth - 2 * getScrollBarWidthInnerDiff, getHeight() - 2 * getScrollBarHeightInnerDiff);
    }

   private  int scrollBarWidth = 20;
    private int getScrollBarWidthInnerDiff = 4;
    private int getScrollBarHeightInnerDiff = 20;
    //new

    protected void updateScrollElemLength() {
        // Use the max method to ensure that the scroll element is large enough to be dragged easily.
        int size = Math.max(20, 180 * getHeight() / getContentSize());
        setScrollElemLength(size);
    }

    /**
     * Enable or disable the scrollbar based on the content size.
     *
     * @post The scrollbar is enabled if the height is smaller than the content size.
     * | new.isEnabled() == getHeight() &lt; getContentSize()
     */
    @Override
    protected void setEnabled() {
        enabled = getContentSize() > 0 && getHeight() - scrollMargin < getContentSize();
    }

    /**
     * Set the height of this scrollbar, update the step size and update the position of the scroll element.
     *
     * @param height The new height of the component.
     * @effect The scrollbar's step size is set to 10% of the height.
     * | setStepSize(0.15 * (height - getScrollElemLength())
     */
   // @Override
//    public void setHeight(int height) {
//        super.setHeight(height);
//        setStepSize((int) Math.rint(0.15 * (height - getScrollElemLength())));
//        dimensionsChanged();
//    }

    /**
     * Set the y coordinate for this scrollbar and update the position of the scroll element.
     *
     * @param y The y coordinate of the top left corner of the component.
     * @effect The setY method of the superclass is called.
     * | super.setY(y)
     * @effect The dimensionsChanged method is called.
     * | dimensionsChanged()
     */
   // @Override
    public void setY(int y) {
       // super.setY(y);
        dimensionsChanged();
    }

    /**
     * Change the offset to the new value determined by the scroll position.
     *
     * @effect The setOffset method is called with the the percentage of the maxOffset that corresponds to the
     * percentage of the scrollbar that is before the scroll element as argument.
     * | setOffset(getScrollPosition() / getHeight() * maxOffset)
     */
    @Override
    protected void updateOffset() {
        double percentage = getBeforeScrollElement() * maxOffset;
        setOffset((int) Math.rint(percentage));
    }

    /**
     * Determine the maximum offset.
     */
    @Override
    protected void setMaxOffset() {
        maxOffset = getContentSize() + scrollMargin - getHeight();
    }

    /**
     * @return The width to be used when drawing the scroll element.
     */
    @Override
    protected int getScrollElementWidth() {
        return getWidth();
    }

    /**
     * @return The height to be used when drawing the scroll element.
     */
    @Override
    protected int getScrollElementHeight() {
        return getScrollElemLength();
    }

    /**
     * @return The x coordinate to be used for drawing the scroll element.
     */
    @Override
    protected int getScrollElementX() {
        return getDrawX();
    }

    /**
     * @return The y coordinate to be used for drawing the scroll element.
     */
    @Override
    protected int getScrollElementY() {
        return getScrollPosition() - getScrollElemLength() / 2;
    }

    /**
     * Set the new scroll element based on a user clicking in the scrollbar.
     *
     * @param x The x coordinate where the user clicked.
     * @param y The y coordinate where the user clicked.
     */
    @Override
    protected void setNewScrollPositionClicked(int x, int y) {
        int newY = isBeforeScrollElement(x, y) ? getScrollPosition() - getStepSize() : getScrollPosition() + getStepSize();
        setScrollPosition(getScrollPositionClosestTo(x, newY));
    }

    /**
     * Calculate the percentage of the content that is before the scroll element.
     */
    @Override
    protected void updateBeforeScrollElement() {
        setBeforeScrollElement(
                1.0 * (getScrollPosition() - getDrawY() - getScrollElemLength() / 2) / (getHeight() - getScrollElemLength()));
    }

    /**
     * Make sure the scroll element is still in the correct position when the height or y coordinate of this
     * vertical scrollbar changes.
     */
    @Override
    protected void placeScrollElement() {
        int y = (int)
                (getDrawY() + getBeforeScrollElement() * (getHeight() - getScrollElemLength()) + getScrollElemLength() / 2.0);
        setScrollPosition(getScrollPositionClosestTo(0, y));
    }

    /**
     * Check if the given x,y coordinate is above (before) or below (after) the scroll element.
     *
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the specified pair is above the scroll element, false otherwise.
     */
    @Override
    protected boolean isBeforeScrollElement(int x, int y) {
        return y < getScrollPosition();
    }

    /**
     * Determine the scroll position that is closest to the given coordinate pair.
     *
     * @param x The x coordinate for which to get the scroll position.
     * @param y The y coordinate for which to get the scroll position.
     * @return The scroll position.
     */
    @Override
    protected int getScrollPositionClosestTo(int x, int y) {
        if (y < getDrawY() + getScrollElemLength() / 2) return getDrawY() + getScrollElemLength() / 2;
        if (y > getDrawY() + getHeight() - getScrollElemLength() / 2)
            return getDrawY() + getHeight() - getScrollElemLength() / 2;
        else return y;
    }
    public final int getDrawY() {
        if (this == null) return getY();
        int drawY = this.getDrawY() + getY();
        if (this instanceof Scrollable && moveWhenScrolling) return drawY - ((Scrollable) this).getYOffset();
        return drawY;
    }

    public final int getY() {
        return this.y;
    }
    private int y;
    private boolean moveWhenScrolling = true;

    public int getDrawX() {
        if (this == null) return getX();
        int drawX = this.getDrawX() + getX();
        if (this instanceof Scrollable && moveWhenScrolling) return drawX - ((Scrollable) this).getXOffset();
        return drawX;
    }
    public final int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    private int x;

}