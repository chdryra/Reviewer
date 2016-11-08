/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HideableViewUi<V extends View, Value extends ReviewListReference> extends ConditionalViewUi<V, Value>{
    public HideableViewUi(ViewUi<V, Value> viewUi, View view) {
        super(viewUi, view, View.GONE, new ConditionalUpdate<Value>() {
            @Override
            public void update(final View view, final int visibility, final ViewUi<?, Value> ui) {
                ReviewItemReference<DataSize> size = ui.getValue().getSize();
                size.dereference(new DataReference.DereferenceCallback<DataSize>() {
                    @Override
                    public void onDereferenced(DataValue<DataSize> value) {
                        if(value.hasValue() && value.getData().getSize() > 0) {
                            ui.update();
                        } else {
                            view.setVisibility(visibility);
                        }
                    }
                });
            }
        });
    }
}
