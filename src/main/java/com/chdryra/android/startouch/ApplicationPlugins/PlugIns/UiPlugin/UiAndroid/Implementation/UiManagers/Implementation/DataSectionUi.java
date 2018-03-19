/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.widget.LinearLayout;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class DataSectionUi<T extends HasReviewId> extends
        FormattedSectionUi<IdableList<T>> {

    protected abstract void updateView(IdableList<T> data);

    protected abstract void setEmpty(String label);

    public DataSectionUi(LinearLayout view, String title) {
        super(view, title);
    }

    protected void setView(IdableList<T> data) {
        if (data.size() > 0) {
            updateView(data);
        } else {
            setEmpty(Strings.Formatted.NONE);
        }
    }

    @Override
    public void update(IdableList<T> value) {
        setView(value);
    }

    @Override
    public void onInvalidated() {
        setView(new IdableDataList<T>());
    }
}
