/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.ReferenceWrapper;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataSizeList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerReviewReference extends ViewerNodeBasic<GvDataSize> {
    private static final GvDataType<GvDataSize> TYPE = GvDataSize.TYPE;
    private static final int NUM_DATA = 6;

    private ConverterGv mConverter;
    private TagsManager mTagsManager;
    private FactoryReviewViewAdapter mAdapterFactory;
    private Map<DataType, DataSizeBinder> mBinders;

    private int mNumInitialised = 0;

    private enum DataType {
        TAGS(GvTag.TYPE),
        CRITERIA(GvCriterion.TYPE),
        IMAGES(GvImage.TYPE),
        COMMENTS(GvComment.TYPE),
        LOCATIONS(GvLocation.TYPE),
        FACTS(GvFact.TYPE);
        private boolean mInitialised = false;
        private GvDataType<?> mType;

        DataType(GvDataType<?> type) {
            mType = type;
        }

        public GvDataType<?> getType() {
            return mType;
        }

        public void setType(GvDataType<?> type) {
            mType = type;
        }

        boolean isInitialised() {
            return mInitialised;
        }

        void initialise() {
            mInitialised = true;
        }
    }

    public ViewerReviewReference(ReferenceWrapper reference,
                                 ConverterGv converter,
                                 TagsManager tagsManager,
                                 FactoryReviewViewAdapter adapterFactory) {
        super(reference, TYPE);
        mConverter = converter;
        mTagsManager = tagsManager;
        mAdapterFactory = adapterFactory;
        mBinders = new HashMap<>();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        bind();
    }

    @Override
    protected void onDetach() {
        unbind();
        super.onDetach();
    }

    @Override
    protected GvDataSizeList makeGridData() {
        ReferenceWrapper reference = getReference();
        ReviewId reviewId = reference.getReviewId();
        GvReviewId id = new GvReviewId(reviewId);

        GvDataSizeList data = new GvDataSizeList(id);
        for(DataSizeBinder binder : mBinders.values()) {
            GvDataSize size;
            if(binder.isInitialised()){
                size = new GvDataSize(id, binder.getSize(), binder.getType());
            } else {
                size = new GvDataSize(id, binder.getType());
            }

            data.add(size);
        }

        return data;
    }

    @Override
    public ReviewStamp getStamp() {
        ReviewNode node = getReviewNode();
        return ReviewStamp.newStamp(node.getAuthor(), node.getPublishDate());
    }

    @Override
    public boolean isExpandable(GvDataSize datum) {
        GvDataSizeList cache = (GvDataSizeList) getCache();
        return cache != null && cache.contains(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvDataSize datum) {
        if (isExpandable(datum)) {
            return mAdapterFactory.newDataToDataAdapter(getReviewNode(),
                    (GvDataCollection<? extends GvData>) datum);
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }

    private void bind() {
        mBinders.put(DataType.TAGS, new DataSizeBinder(DataType.TAGS));
        mBinders.put(DataType.CRITERIA, new DataSizeBinder(DataType.CRITERIA));
        mBinders.put(DataType.IMAGES, new DataSizeBinder(DataType.IMAGES));
        mBinders.put(DataType.COMMENTS, new DataSizeBinder(DataType.COMMENTS));
        mBinders.put(DataType.LOCATIONS, new DataSizeBinder(DataType.LOCATIONS));
        mBinders.put(DataType.FACTS, new DataSizeBinder(DataType.FACTS));

        ReferenceWrapper reference = getReference();
        reference.bindToTags(mBinders.get(DataType.TAGS));
        reference.bindToCriteria(mBinders.get(DataType.CRITERIA));
        reference.bindToImages(mBinders.get(DataType.IMAGES));
        reference.bindToComments(mBinders.get(DataType.COMMENTS));
        reference.bindToLocations(mBinders.get(DataType.LOCATIONS));
        reference.bindToFacts(mBinders.get(DataType.FACTS));
    }

    private ReferenceWrapper getReference() {
        return (ReferenceWrapper) getReviewNode();
    }

    private void unbind() {
        ReferenceWrapper reference = getReference();
        reference.unbindFromTags(mBinders.get(DataType.TAGS));
        reference.unbindFromCriteria(mBinders.get(DataType.CRITERIA));
        reference.unbindFromImages(mBinders.get(DataType.IMAGES));
        reference.unbindFromComments(mBinders.get(DataType.COMMENTS));
        reference.unbindFromLocations(mBinders.get(DataType.LOCATIONS));
        reference.unbindFromFacts(mBinders.get(DataType.FACTS));
    }

    private void notifyObservers(DataType type) {
        if (!type.isInitialised()) {
            type.initialise();
            mNumInitialised++;
        }

        if (dataIsInitialised()) notifyDataObservers();
    }

    private boolean dataIsInitialised() {
        return mNumInitialised == NUM_DATA;
    }

    private class DataSizeBinder implements ReferenceBinders.SizeBinder {
        private final DataType mType;
        private int mSize = 0;

        public DataSizeBinder(DataType type) {
            mType = type;
        }

        @Override
        public void onValue(Integer value) {
            mSize = value;
            notifyObservers(mType);
        }

        public GvDataType<?> getType() {
            return mType.getType();
        }

        public boolean isInitialised() {
            return mType.isInitialised();
        }

        public int getSize() {
            return mSize;
        }
    }
}
