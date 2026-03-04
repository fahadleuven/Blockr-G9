package com.swop;

import java.awt.*;

import static javax.swing.UIManager.getColor;

public class GameWorldSection extends SimpleWindowSection {
    public GameWorldSection(Point pos, int width, int height) {
        super(pos, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(position.x, position.y, width-20, height);

        drawScrollBar(g);

    }

    public void drawScrollBar(Graphics g){
        //outer
        g.setColor(Color.GRAY);
        g.fillRect(position.x+width - scrollBarWidth, position.y, scrollBarWidth, height);

        //inner
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(position.x+width - scrollBarWidth + getScrollBarWidthInnerDiff, position.y + getScrollBarHeightInnerDiff , scrollBarWidth - 2 * getScrollBarWidthInnerDiff, height - 2 * getScrollBarHeightInnerDiff);
    }

    void draw(Graphics g, GameWorld gameWorld) {
        draw(g);
        gameWorld.paint(g, position);
    }

    int scrollBarWidth = 20;
    int getScrollBarWidthInnerDiff = 4;
    int getScrollBarHeightInnerDiff = 20;
}
