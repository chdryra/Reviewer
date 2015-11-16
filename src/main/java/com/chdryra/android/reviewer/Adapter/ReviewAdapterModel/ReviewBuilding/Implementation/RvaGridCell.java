package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Screens.Interfaces.GridDataObservable;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RvaGridCell<T extends GvData> extends GvDataList<T>
        implements GridDataObservable.GridDataObserver {
    public static final Creator<RvaGridCell> CREATOR = new Creator<RvaGridCell>() {
        //Overridden
        public RvaGridCell createFromParcel(Parcel in) {
            return new RvaGridCell(in);
        }

        public RvaGridCell[] newArray(int size) {
            return new RvaGridCell[size];
        }
    };

    public static final GvDataType<RvaGridCell> TYPE =
            new GvDataType<>(RvaGridCell.class, "create", "create");

    private ReviewViewAdapter<T> mDataAdapter;
    private FactoryVhDataCollection mViewHolderFactory;

    RvaGridCell(GvDataType<T> dataType,
                ReviewViewAdapter<T> dataAdapter,
                FactoryVhDataCollection viewHolderFactory) {
        super(dataType, null);
        mDataAdapter = dataAdapter;
        mViewHolderFactory = viewHolderFactory;
        mDataAdapter.registerGridDataObserver(this);
    }

    private RvaGridCell(Parcel in) {
        super(in);
    }

    //Overridden
    @Override
    public String getStringSummary() {
        return getGvDataType().getDataName();
    }

    @Override
    public ViewHolder getViewHolder() {
        return mViewHolderFactory.newViewHolder();
    }

    @Override
    public void onGridDataChanged() {
        mData = mDataAdapter.getGridData().toArrayList();
    }
}
