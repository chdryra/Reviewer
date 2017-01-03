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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class DataSectionUi<T extends HasReviewId, Ref extends ReviewListReference<T, ?>>
        extends FormattedSectionUi<Ref> {

    protected abstract void updateView(IdableList<T> data);

    protected abstract void setEmpty(String label);

    public DataSectionUi(LinearLayout view, ValueGetter<Ref> getter, String title) {
        super(view, getter, title);
    }

    protected void setView(IdableList<T> data) {
        if(data.size() > 0) {
            updateView(data);
        } else {
            setEmpty(Strings.Formatted.NONE);
        }
    }

    @Override
    public void update() {
        setEmpty(Strings.Formatted.LOADING);
        getValue().dereference(new DataReference.DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<T>> value) {
                if(value.hasValue()) setView(value.getData());
            }
        });
    }

}
