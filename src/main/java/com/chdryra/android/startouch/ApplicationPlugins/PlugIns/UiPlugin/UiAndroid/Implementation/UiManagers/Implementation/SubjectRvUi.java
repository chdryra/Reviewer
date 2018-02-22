/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class SubjectRvUi<T extends TextView> extends SubjectUi<T> {
    private ReviewViewParams.Subject mParams;

    public SubjectRvUi(T view, ReviewViewParams.Subject params, ReferenceValueGetter<String> getter) {
        super(view, getter);
        mParams = params;
        setBackgroundAlpha(mParams.getAlpha());
    }

    public ReviewViewParams.Subject getParams() {
        return mParams;
    }
}
