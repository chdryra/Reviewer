package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import android.content.Context;
import android.widget.Toast;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilder;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .InputHandler;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderAdapterImpl <T extends GvData> extends ReviewViewAdapterBasic<T>
    implements DataBuilderAdapter<T> {

    private final Context mContext;
    private final ReviewBuilderAdapter mParentBuilder;
    private final ReviewBuilder mBuilder;
    private final DataBuilder<T> mDataBuilder;
    private final GvDataType<T> mType;

    public DataBuilderAdapterImpl(Context context, GvDataType<T> type,
                                  ReviewBuilderAdapter parentBuilder) {
        mContext = context;
        mType = type;
        mParentBuilder = parentBuilder;
        mBuilder = parentBuilder.getBuilder();
        mDataBuilder = mBuilder.getDataBuilder(mType);
        reset();
    }

    //public methods
    @Override
    public GvDataType<T> getDataType() {
        return mType;
    }

    @Override
    public ReviewBuilderAdapter getParentBuilder() {
        return mParentBuilder;
    }

    @Override
    public boolean isRatingAverage() {
        return mBuilder.isRatingAverage();
    }

    @Override
    public float getAverageRating() {
        if (mType.equals(GvCriterionList.GvCriterion.TYPE)) {
            return ((GvCriterionList) getGridData()).getAverageRating();
        } else {
            return mBuilder.getAverageRating();
        }
    }

    @Override
    public boolean add(T datum) {
        InputHandler.ConstraintResult res = mDataBuilder.add(datum);
        if (res == InputHandler.ConstraintResult.PASSED) {
            this.notifyGridDataObservers();
            return true;
        } else {
            if (res == InputHandler.ConstraintResult.HAS_DATUM) {
                makeToastHasItem(mContext, datum);
            }
            return false;
        }
    }

    @Override
    public void delete(T datum) {
        mDataBuilder.delete(datum);
        this.notifyGridDataObservers();
    }

    @Override
    public void deleteAll() {
        mDataBuilder.deleteAll();
        this.notifyGridDataObservers();
    }

    @Override
    public void replace(T oldDatum, T newDatum) {
        InputHandler.ConstraintResult res = mDataBuilder.replace(oldDatum, newDatum);
        if (res == InputHandler.ConstraintResult.PASSED) {
            this.notifyGridDataObservers();
        } else {
            if (res == InputHandler.ConstraintResult.HAS_DATUM) {
                makeToastHasItem(mContext, newDatum);
            }
        }
    }

    @Override
    public void setData() {
        mDataBuilder.setData();
        getParentBuilder().notifyGridDataObservers();
    }

    @Override
    public void reset() {
        mDataBuilder.resetData();
        this.notifyGridDataObservers();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        getParentBuilder().setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public GvDataList<T> getGridData() {
        return mDataBuilder.getData();
    }

    @Override
    public String getSubject() {
        return getParentBuilder().getSubject();
    }

    public void setSubject(String subject) {
        getParentBuilder().setSubject(subject);
    }

    @Override
    public float getRating() {
        return getParentBuilder().getRating();
    }

    @Override
    public void setRating(float rating) {
        getParentBuilder().setRating(rating);
    }

    @Override
    public GvImageList getCovers() {
        return mType.equals(GvImageList.GvImage.TYPE) ? (GvImageList) getGridData()
                : getParentBuilder().getCovers();
    }

    private void makeToastHasItem(Context context, GvData datum) {
        String toast = context.getResources().getString(R.string.toast_has) + " " + datum
                .getGvDataType().getDatumName();
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}

