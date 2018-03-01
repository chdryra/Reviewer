/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.widget.TextView;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class SubjectUi<T extends TextView> extends TextUi<T> {
    private String mTextCache;

    public SubjectUi(T view) {
        super(view);
    }

    @Override
    public void update(String newText) {
        super.update(newText);
        updateTextCache();
    }

    protected void updateTextCache() {
        mTextCache = getValue();
    }

    public String getTextCache() {
        return mTextCache;
    }
}
