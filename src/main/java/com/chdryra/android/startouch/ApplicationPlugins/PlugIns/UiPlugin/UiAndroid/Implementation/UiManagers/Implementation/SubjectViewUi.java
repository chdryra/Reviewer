/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectViewUi extends SubjectRvUi<TextView> {
    public SubjectViewUi(final ReviewView<?> reviewView, TextView view) {
        super(view, reviewView.getParams().getSubjectParams(), new ViewUi.ReferenceValueGetter<String>() {
            @Override
            public String getValue() {
                return reviewView.getSubject();
            }
        });
    }
}
