package com.nevermore.demo.xobservable;

/**
 * @author xct
 * create on: 2023/4/13 13:29
 */
public interface NotifyArg<O extends XObserver> {
    void onNotify(O o);
}
