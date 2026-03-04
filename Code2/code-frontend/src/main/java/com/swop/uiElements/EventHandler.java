package com.swop.uiElements;

import com.swop.Event;

public abstract interface EventHandler<T extends Event> {

    void handleEvent(T event);
}