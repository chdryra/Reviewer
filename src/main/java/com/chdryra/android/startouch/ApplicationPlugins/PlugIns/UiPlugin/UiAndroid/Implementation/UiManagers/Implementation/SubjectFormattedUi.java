/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectFormattedUi extends SimpleViewUi<TextView, DataSubject> {
    private ReviewId mReviewId;

    public SubjectFormattedUi(TextView view, @Nullable final Command onClick) {
        super(view);
        if(onClick != null) setOnClickCommand(onClick);
    }

    @Override
    DataSubject getViewValue() {
        ReviewId reviewId = mReviewId != null ? mReviewId : new DatumReviewId();
        return new DatumSubject(reviewId, getView().getText().toString().trim());
    }

    @Override
    public void update(DataSubject value) {
        String subject = value.getSubject();
        TextView view = getView();
        if(subject.length() > 0) {
            view.setText(subject);
        } else {
            view.setTypeface(view.getTypeface(), Typeface.ITALIC);
            view.setText(Strings.Formatted.NO_SUBJECT);
        }
        mReviewId = value.getReviewId();
    }
}
