package com.nevermore.demo.xobservable;

import java.util.Observable;

/**
 * @author xct
 * create on: 2023/4/13 13:31
 */
public class XObservable<O extends XObserver> extends Observable {

    public void notify(NotifyArg<O> arg) {
        setChanged();
        notifyObservers(arg);
    }
}
