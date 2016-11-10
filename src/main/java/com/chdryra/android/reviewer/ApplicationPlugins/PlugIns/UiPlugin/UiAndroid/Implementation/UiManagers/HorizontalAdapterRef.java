/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalAdapterRef<Value extends HasReviewId, Gv extends GvData, Vh extends ViewHolder>
        extends HorizontalAdapter<Gv, Vh> implements ViewUi.ValueGetter<RefDataList<Value>>{
    private final RefDataList<Value> mReference;
    private final DataConverter<Value, Gv, ? extends GvDataList<Gv>> mConverter;

    private boolean mDereferenced = false;

    public HorizontalAdapterRef(RefDataList<Value> reference,
                                DataConverter<Value, Gv, ? extends GvDataList<Gv>> converter,
                                ViewHolderFactory<Vh> factory,
                                CellDimensionsCalculator.Dimensions dims) {
        super(converter.convert(new IdableDataList<Value>(reference.getReviewId())),
                factory, dims.getCellWidth(), dims.getCellHeight());
        mReference = reference;
        mConverter = converter;
    }

    @Override
    public RefDataList<Value> getValue() {
        return mReference;
    }

    @Override
    public int getItemCount() {
        if(!mDereferenced) dereference();
        return super.getItemCount();
    }

    private void dereference() {
        mDereferenced = true;
        mReference.dereference(new DataReference.DereferenceCallback<IdableList<Value>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<Value>> value) {
                if(value.hasValue()) {
                    setData(mConverter.convert(value.getData()));
                }
            }
        });
    }
}
