/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class NodeDataUi<T extends HasReviewId,
        R extends ReviewListReference<T, ?>, V extends View> extends ViewUi<V,R> {
    protected abstract void updateView(IdableList<T> data);

    protected abstract void setEmpty();

    public NodeDataUi(V view, ValueGetter<R> getter, @Nullable Command onClick) {
        super(view, getter);
        if(onClick != null) setOnClickCommand(onClick);
    }

    protected void setView(IdableList<T> data) {
        if(data.size() > 0) {
            updateView(data);
        } else {
            setEmpty();
        }
    }

    @Override
    public void update() {
        getValue().dereference(new DataReference.DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<T>> value) {
                if(value.hasValue()) setView(value.getData());
            }
        });
    }
}
