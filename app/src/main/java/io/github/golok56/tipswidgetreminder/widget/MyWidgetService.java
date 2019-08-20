package io.github.golok56.tipswidgetreminder.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MyWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetViewFactory(getApplicationContext());
    }
}
