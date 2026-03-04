package com.swop;

import com.swop.uiElements.UIBlock;

import java.awt.*;
import java.util.Collection;

public class ProgramAreaSection extends SimpleWindowSection {
    public ProgramAreaSection(Point pos, int width, int height) {
        super(pos, width, height);
    }

    public void drawBlock(UIBlock block, Graphics g) {
        g.setColor(block.getColor());
        g.fillPolygon(block.getPolygon());
        g.setColor(Color.BLACK);
        g.drawString(block.getText(), block.getTextPosition().x, block.getTextPosition().y);
    }

    void draw(Graphics g, Collection<UIBlock> uiBlocks) {
        draw(g);

        for (UIBlock block : uiBlocks) {
            drawBlock(block, g);
        }
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(position.x, position.y, width, height);

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

    final int scrollBarWidth = 20;
    final int getScrollBarWidthInnerDiff = 4;


    int getScrollBarHeightInnerDiff = 20;
}
