/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterDataTags;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterDateReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterFacts;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterSubjects;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDateList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReferenceList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSubjectList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerMetaData<T extends GvData> extends ViewerData<T> {
    private FactoryReviewViewAdapter mAdapterFactory;

    protected ViewerMetaData(MetaReference reference,
                             GvDataList<T> initial,
                             FactoryReviewViewAdapter adapterFactory) {
        super(reference, initial);
        mAdapterFactory = adapterFactory;
    }

    @NonNull
    private static GvReviewId getId(ReviewReference reference) {
        return new GvReviewId(reference.getReviewId());
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mAdapterFactory.newReviewsListAdapter(getGridData());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(T datum) {
        return isExpandable(datum) ? mAdapterFactory.newReviewsListAdapter(datum) : null;
    }

    @Override
    public boolean isExpandable(T datum) {
        return getGridData().contains(datum);
    }


    protected MetaReference getMetaReference() {
        return (MetaReference) super.getReference();
    }
    
    public static class Reviews extends ViewerMetaData<GvReference> {
        private MetaBinders.ReviewsBinder mBinder;

        public Reviews(MetaReference reference,
                       final GvConverterReferences converter,
                       FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvReferenceList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.ReviewsBinder() {
                @Override
                public void onValue(IdableList<ReviewReference> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Authors extends ViewerMetaData<GvAuthor> {
        private MetaBinders.AuthorsBinder mBinder;

        public Authors(MetaReference reference,
                       final GvConverterAuthors converter,
                       FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvAuthorList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.AuthorsBinder() {
                @Override
                public void onValue(IdableList<? extends DataAuthorReview> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Subjects extends ViewerMetaData<GvSubject> {
        private MetaBinders.SubjectsBinder mBinder;

        public Subjects(MetaReference reference,
                        final GvConverterSubjects converter,
                        FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvSubjectList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.SubjectsBinder() {
                @Override
                public void onValue(IdableList<? extends DataSubject> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Dates extends ViewerMetaData<GvDate> {
        private MetaBinders.DatesBinder mBinder;

        public Dates(MetaReference reference,
                     final GvConverterDateReviews converter,
                     FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvDateList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.DatesBinder() {
                @Override
                public void onValue(IdableList<? extends DataDateReview> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Tags extends ViewerMetaData<GvTag> {
        private MetaBinders.TagsBinder mBinder;

        public Tags(MetaReference reference,
                     final GvConverterDataTags converter,
                     FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvTagList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.TagsBinder() {
                @Override
                public void onValue(IdableList<? extends DataTag> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Criteria extends ViewerMetaData<GvCriterion> {
        private MetaBinders.CriteriaBinder mBinder;

        public Criteria(MetaReference reference,
                    final GvConverterCriteria converter,
                    FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvCriterionList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.CriteriaBinder() {
                @Override
                public void onValue(IdableList<? extends DataCriterion> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Images extends ViewerMetaData<GvImage> {
        private MetaBinders.ImagesBinder mBinder;

        public Images(MetaReference reference,
                    final GvConverterImages converter,
                    FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvImageList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.ImagesBinder() {
                @Override
                public void onValue(IdableList<? extends DataImage> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Comments extends ViewerMetaData<GvComment> {
        private MetaBinders.CommentsBinder mBinder;

        public Comments(MetaReference reference,
                      final GvConverterComments converter,
                      FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvCommentList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.CommentsBinder() {
                @Override
                public void onValue(IdableList<? extends DataComment> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Locations extends ViewerMetaData<GvLocation> {
        private MetaBinders.LocationsBinder mBinder;

        public Locations(MetaReference reference,
                      final GvConverterLocations converter,
                      FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvLocationList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.LocationsBinder() {
                @Override
                public void onValue(IdableList<? extends DataLocation> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }

    public static class Facts extends ViewerMetaData<GvFact> {
        private MetaBinders.FactsBinder mBinder;

        public Facts(MetaReference reference,
                      final GvConverterFacts converter,
                      FactoryReviewViewAdapter adapterFactory) {
            super(reference, new GvFactList(getId(reference)), adapterFactory);
            mBinder = new MetaBinders.FactsBinder() {
                @Override
                public void onValue(IdableList<? extends DataFact> value) {
                    setData(converter.convert(value));
                }
            };
            getMetaReference().bind(mBinder);
        }

        @Override
        protected void onDetach() {
            getMetaReference().unbind(mBinder);
            super.onDetach();
        }
    }
}
