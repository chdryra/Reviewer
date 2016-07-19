/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterDataTags;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterFacts;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ViewerReviewData<T extends GvData> extends GridDataWrapperBasic<T> {
    private ReviewReference mReference;
    private GvDataList<T> mCache;

    protected abstract void bind(ReviewReference reference);

    protected abstract void unbind(ReviewReference reference);

    protected ViewerReviewData(ReviewReference reference, GvDataList<T> initial) {
        mReference = reference;
        mCache = initial;
    }

    @NonNull
    private static GvReviewId getId(ReviewReference reference) {
        return new GvReviewId(reference.getReviewId());
    }

    protected void setData(GvDataList<T> data) {
        mCache = data;
        notifyDataObservers();
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        bind(mReference);
    }

    @Override
    protected void onDetach() {
        unbind(mReference);
        super.onDetach();
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(T datum) {
        return null;
    }

    @Override
    public boolean isExpandable(T datum) {
        return false;
    }

    @Override
    public GvDataList<T> getGridData() {
        return mCache;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mCache.getGvDataType();
    }

    public static class Tags extends ViewerReviewData<GvTag> implements ReferenceBinders.TagsBinder{
        private GvConverterDataTags mConverter;

        public Tags(ReviewReference reference, GvConverterDataTags converter) {
            super(reference, new GvTagList(getId(reference)));
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataTag> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(ReviewReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(ReviewReference reference) {
            reference.unbind(this);
        }
    }

    public static class Criteria extends ViewerReviewData<GvCriterion> implements ReferenceBinders.CriteriaBinder{
        private GvConverterCriteria mConverter;

        public Criteria(ReviewReference reference, GvConverterCriteria converter) {
            super(reference, new GvCriterionList(getId(reference)));
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataCriterion> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(ReviewReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(ReviewReference reference) {
            reference.unbind(this);
        }
    }

    public static class Images extends ViewerReviewData<GvImage> implements ReferenceBinders.ImagesBinder{
        private GvConverterImages mConverter;

        public Images(ReviewReference reference, GvConverterImages converter) {
            super(reference, new GvImageList(getId(reference)));
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataImage> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(ReviewReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(ReviewReference reference) {
            reference.unbind(this);
        }
    }

    public static class Comments extends ViewerReviewData<GvComment> implements ReferenceBinders.CommentsBinder{
        private GvConverterComments mConverter;

        public Comments(ReviewReference reference, GvConverterComments converter) {
            super(reference, new GvCommentList(getId(reference)));
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataComment> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(ReviewReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(ReviewReference reference) {
            reference.unbind(this);
        }
    }

    public static class Locations extends ViewerReviewData<GvLocation> implements ReferenceBinders.LocationsBinder{
        private GvConverterLocations mConverter;

        public Locations(ReviewReference reference, GvConverterLocations converter) {
            super(reference, new GvLocationList(getId(reference)));
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataLocation> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(ReviewReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(ReviewReference reference) {
            reference.unbind(this);
        }
    }

    public static class Facts extends ViewerReviewData<GvFact> implements ReferenceBinders.FactsBinder{
        private GvConverterFacts mConverter;

        public Facts(ReviewReference reference, GvConverterFacts converter) {
            super(reference, new GvFactList(getId(reference)));
            mConverter = converter;
        }

        @Override
        public void onValue(IdableList<? extends DataFact> value) {
            setData(mConverter.convert(value));
        }

        @Override
        protected void bind(ReviewReference reference) {
            reference.bind(this);
        }

        @Override
        protected void unbind(ReviewReference reference) {
            reference.unbind(this);
        }
    }
}
