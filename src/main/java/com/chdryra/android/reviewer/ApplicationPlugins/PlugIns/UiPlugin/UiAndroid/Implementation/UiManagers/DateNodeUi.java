/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class DateNodeUi extends FormattedSectionUi<String> {
    public DateNodeUi(LinearLayout section, final ReviewNode node) {
        super(section, new ValueGetter<String>() {
            @Override
            public String getValue() {
                Date date = new Date(node.getPublishDate().getTime());
                return DateFormat.getDateInstance(DateFormat.LONG).format(date);
            }
        }, Strings.FORMATTED.DATE);
    }

    @Override
    public void update() {
        getValueView().setText(getValue());
    }
}
