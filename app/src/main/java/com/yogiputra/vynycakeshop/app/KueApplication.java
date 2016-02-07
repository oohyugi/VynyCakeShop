package com.yogiputra.vynycakeshop.app;

import android.app.Application;


import com.yogiputra.vynycakeshop.model.KueObserver;

/**
 * Created by koba on 11/23/15.
 */
public class KueApplication extends Application {
    KueObserver kueObserver;
    @Override
    public void onCreate(){
        kueObserver=new KueObserver();

    }
    public KueObserver getKueObserver(){
        return kueObserver;
    }
}
