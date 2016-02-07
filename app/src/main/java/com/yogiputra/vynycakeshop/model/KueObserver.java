package com.yogiputra.vynycakeshop.model;

import android.content.Context;

import java.util.Observable;

/**
 * Created by koba on 11/23/15.
 */
public class KueObserver extends Observable {
    public static String NEED_TO_REFRESH="refresh";

    public void refresh(){
        setChanged();
        notifyObservers(NEED_TO_REFRESH);
    }



}
