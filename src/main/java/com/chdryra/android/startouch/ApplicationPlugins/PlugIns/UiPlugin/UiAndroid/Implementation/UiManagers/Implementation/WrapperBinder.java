/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import com.chdryra.android.corelibrary.OtherUtils.DataGetter;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class WrapperBinder<ObjectType, DataType> extends DataBinder<ObjectType>{
    public WrapperBinder(final Bindable<DataType> bindable,
                         DataReference<ObjectType> reference,
                         final DataGetter<ObjectType, DataType> getter) {
        super(new Bindable<ObjectType>() {
            @Override
            public void update(ObjectType value) {
                bindable.update(getter.getData(value));
            }

            @Override
            public void onInvalidated() {
                bindable.onInvalidated();
            }
        }, reference);
    }
}
