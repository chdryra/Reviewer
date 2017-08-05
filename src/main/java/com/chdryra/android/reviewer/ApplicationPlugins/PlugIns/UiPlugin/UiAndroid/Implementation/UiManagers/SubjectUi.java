/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.widget.TextView;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class SubjectUi<T extends TextView> extends TextUi<T> {
    private boolean mSubjectRefresh = true;
    private String mTextCache;

    public SubjectUi(T view, ValueGetter<String> getter) {
        super(view, getter);
    }

    public void update(boolean force) {
        if(force) {
            super.update();
        } else {
            update();
        }
    }

    protected void setSubjectRefresh(boolean subjectRefresh) {
        mSubjectRefresh = subjectRefresh;
    }

    protected void setText(String newText) {
        getView().setText(newText);
        updateTextCache();
    }

    protected void updateTextCache() {
        mTextCache = getView().getText().toString();
    }

    public String getTextCache() {
        return mTextCache;
    }

    @Override
    public void update() {
        if(mSubjectRefresh) super.update();
    }
}
