package com.nevermore.demo.xobservable;

import java.util.Observable;
import java.util.Observer;

/**
 * @author xct
 * create on: 2023/3/20 15:10
 */
public interface XObserver extends Observer {
    @Override
    default public void update(Observable o, Object arg) {
        if (arg instanceof NotifyArg) {
            ((NotifyArg) arg).onNotify(this);
        }
    }
}
