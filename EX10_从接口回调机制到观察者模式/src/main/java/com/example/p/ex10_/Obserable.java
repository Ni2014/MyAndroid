package com.example.p.ex10_;

/**
 * Created by P on 2015/6/16.
 */
public interface Obserable {

    void addObserver(Observer o);

    void delObserver(Observer o);

    void notifyObservers(int offset);
}
