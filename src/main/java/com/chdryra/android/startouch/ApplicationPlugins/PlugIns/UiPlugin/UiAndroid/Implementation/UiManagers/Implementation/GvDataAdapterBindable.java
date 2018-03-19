/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.os.Handler;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;


/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAdapterBindable<Value extends HasReviewId, Gv extends GvData, Vh extends
        ViewHolder>
        extends GvDataAdapter<Gv> implements Bindable<IdableList<Value>> {
    private final DataConverter<Value, Gv, ? extends GvDataList<Gv>> mConverter;

    public GvDataAdapterBindable(DataConverter<Value, Gv, ? extends GvDataList<Gv>> converter,
                                 int cellWidth, int cellHeight,
                                 ViewHolderFactory<Vh> factory) {
        super(converter.convert(new IdableDataList<Value>()),
                cellWidth, cellHeight, null, factory);
        mConverter = converter;
    }

    @Override
    public void update(IdableList<Value> value) {
        setData(mConverter.convert(value));
        notifyChange();
    }

    @Override
    public void onInvalidated() {
        update(new IdableDataList<Value>());
    }

    private void notifyChange() {
        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        };

        handler.post(r);
    }
}
