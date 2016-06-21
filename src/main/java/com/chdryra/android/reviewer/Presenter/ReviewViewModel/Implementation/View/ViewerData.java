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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ValueBinder;
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
public class ViewerData<T extends GvData> extends GridDataWrapperBasic<T> {
    private ReviewReference mReference;
    private GvDataType<T> mType;
    private GvDataList<T> mCache;
    private ValueBinder<? super T> mBinder;

    protected ViewerData(ReviewReference reference, GvDataType<T> type, GvDataList<T> initial) {
        mReference = reference;
        mType = type;
        mCache = initial;
    }

    @NonNull
    private static GvReviewId getId(ReviewReference reference) {
        return new GvReviewId(reference.getReviewId());
    }

    protected ReviewReference getReference() {
        return mReference;
    }

    protected ValueBinder<? super T> getBinder() {
        return mBinder;
    }

    protected void setBinder(ValueBinder<? super T> binder) {
        mBinder = binder;
    }

    protected void setData(GvDataList<T> data) {
        mCache = data;
        notifyDataObservers();
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
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
        return mType;
    }

    public static class Tags extends ViewerData<GvTag> {
        private ReferenceBinders.TagsBinder mBinder;

        public Tags(ReviewReference reference, final GvConverterDataTags converter) {
            super(reference, GvTag.TYPE, new GvTagList(getId(reference)));
            mBinder = new ReferenceBinders.TagsBinder() {
                @Override
                public void onValue(IdableList<? extends DataTag> value) {
                    setData(converter.convert(value));
                }
            };
            getReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Criteria extends ViewerData<GvCriterion> {
        private ReferenceBinders.CriteriaBinder mBinder;

        public Criteria(ReviewReference reference, final GvConverterCriteria converter) {
            super(reference, GvCriterion.TYPE, new GvCriterionList(getId(reference)));
            mBinder = new ReferenceBinders.CriteriaBinder() {
                @Override
                public void onValue(IdableList<? extends DataCriterion> value) {
                    setData(converter.convert(value));
                }
            };
            getReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Images extends ViewerData<GvImage> {
        private ReferenceBinders.ImagesBinder mBinder;

        public Images(ReviewReference reference, final GvConverterImages converter) {
            super(reference, GvImage.TYPE, new GvImageList(getId(reference)));
            mBinder = new ReferenceBinders.ImagesBinder() {
                @Override
                public void onValue(IdableList<? extends DataImage> value) {
                    setData(converter.convert(value));
                }
            };
            getReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Comments extends ViewerData<GvComment> {
        private ReferenceBinders.CommentsBinder mBinder;

        public Comments(ReviewReference reference, final GvConverterComments converter) {
            super(reference, GvComment.TYPE, new GvCommentList(getId(reference)));
            mBinder = new ReferenceBinders.CommentsBinder() {
                @Override
                public void onValue(IdableList<? extends DataComment> value) {
                    setData(converter.convert(value));
                }
            };
            getReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Locations extends ViewerData<GvLocation> {
        private ReferenceBinders.LocationsBinder mBinder;

        public Locations(ReviewReference reference, final GvConverterLocations converter) {
            super(reference, GvLocation.TYPE, new GvLocationList(getId(reference)));
            mBinder = new ReferenceBinders.LocationsBinder() {
                @Override
                public void onValue(IdableList<? extends DataLocation> value) {
                    setData(converter.convert(value));
                }
            };
            getReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Facts extends ViewerData<GvFact> {
        private ReferenceBinders.FactsBinder mBinder;

        public Facts(ReviewReference reference, final GvConverterFacts converter) {
            super(reference, GvFact.TYPE, new GvFactList(getId(reference)));
            mBinder = new ReferenceBinders.FactsBinder() {
                @Override
                public void onValue(IdableList<? extends DataFact> value) {
                    setData(converter.convert(value));
                }
            };
            getReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getReference().unbind(mBinder);
            super.onDetach();
        }
    }
}
