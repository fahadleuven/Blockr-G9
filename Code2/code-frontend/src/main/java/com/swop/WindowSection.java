package com.swop;

import com.swop.uiElements.UIBlock;

import java.awt.*;

public interface WindowSection {

    void draw(Graphics g);

    void setPosition(Point p);

    void setHeight(int height);

    void setWidth(int width);

    Point getPosition();

    int getHeight();

    int getWidth();


    boolean isWithin(int x, int y);


}
