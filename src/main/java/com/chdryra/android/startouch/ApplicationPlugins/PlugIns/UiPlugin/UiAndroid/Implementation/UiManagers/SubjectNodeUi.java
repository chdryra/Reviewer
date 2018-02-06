/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectNodeUi extends TextUi<TextView> {
    public SubjectNodeUi(TextView view, final ReviewNode node, @Nullable final Command onClick) {
        super(view, new ReferenceValueGetter<String>() {
            @Override
            public String getValue() {
                return node.getSubject().getSubject();
            }
        });
        if(onClick != null) setOnClickCommand(onClick);
    }

    @Override
    public void update() {
        String subject = getReferenceValue();
        TextView view = getView();
        if(subject.length() > 0) {
            view.setText(subject);
        } else {
            view.setTypeface(view.getTypeface(), Typeface.ITALIC);
            view.setText(Strings.Formatted.NO_SUBJECT);
        }
    }
}
