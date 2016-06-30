/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 21/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReferenceBinder implements DataReviewInfo {
    private ReviewReference mReference;

    private ReferenceBinders.CoversBinder mCovers;
    private ReferenceBinders.TagsBinder mTags;
    private ReferenceBinders.CriteriaBinder mCriteria;
    private ReferenceBinders.ImagesBinder mImages;
    private ReferenceBinders.CommentsBinder mComments;
    private ReferenceBinders.LocationsBinder mLocations;
    private ReferenceBinders.FactsBinder mFacts;

    private ReferenceBinders.SizeBinder mTagsSize;
    private ReferenceBinders.SizeBinder mCriteriaSize;
    private ReferenceBinders.SizeBinder mImagesSize;
    private ReferenceBinders.SizeBinder mCommentsSize;
    private ReferenceBinders.SizeBinder mLocationsSize;
    private ReferenceBinders.SizeBinder mFactsSize;

    private List<DataBinder> mDataBinders;
    private List<DataSizeBinder> mSizeBinders;

    private boolean mIsBound = false;

    public interface DataBinder extends
            ReviewReference.CoversCallback,
            ReviewReference.TagsCallback,
            ReviewReference.CriteriaCallback,
            ReviewReference.ImagesCallback,
            ReviewReference.CommentsCallback,
            ReviewReference.LocationsCallback,
            ReviewReference.FactsCallback {
    }

    public interface DataSizeBinder extends
            ReviewReference.TagsSizeCallback,
            ReviewReference.CriteriaSizeCallback,
            ReviewReference.ImagesSizeCallback,
            ReviewReference.CommentsSizeCallback,
            ReviewReference.LocationsSizeCallback,
            ReviewReference.FactsSizeCallback {

    }

    protected interface BinderMethod<T> {
        void execute(T binder);
    }

    public ReferenceBinder(ReviewReference reference) {
        mReference = reference;

        mCovers = new Covers();

        mTags = new Tags();
        mCriteria = new Criteria();
        mImages = new Images();
        mComments = new Comments();
        mLocations = new Locations();
        mFacts = new Facts();

        mTagsSize = new TagsSize();
        mCriteriaSize = new CriteriaSize();
        mImagesSize = new ImagesSize();
        mCommentsSize = new CommentsSize();
        mLocationsSize = new LocationsSize();
        mFactsSize = new FactsSize();

        mDataBinders = new ArrayList<>();
        mSizeBinders = new ArrayList<>();
    }

    public ReviewReference getReference() {
        return mReference;
    }

    public void registerDataBinder(DataBinder binder) {
        if (!mDataBinders.contains(binder)) mDataBinders.add(binder);
        bindOrFire(binder);
    }

    public void unregisterDataBinder(DataBinder binder) {
        if (mDataBinders.contains(binder)) mDataBinders.remove(binder);
        unbindIfNecessary();
    }

    public void registerSizeBinder(DataSizeBinder binder) {
        if (!mSizeBinders.contains(binder)) mSizeBinders.add(binder);
        bindOrFire(binder);
    }

    public void unregisterSizeBinder(DataSizeBinder binder) {
        if (mSizeBinders.contains(binder)) mSizeBinders.remove(binder);
        unbindIfNecessary();
    }

    public void getFacts(final ReviewReference.FactsCallback callback) {
        mReference.getData(new ReviewReference.FactsCallback() {
            @Override
            public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
                callback.onFacts(facts, message);
            }
        });
    }

    public void getLocations(final ReviewReference.LocationsCallback callback) {
        mReference.getData(new ReviewReference.LocationsCallback() {
            @Override
            public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage
                    message) {
                callback.onLocations(locations, message);
            }
        });
    }

    public void getComments(final ReviewReference.CommentsCallback callback) {
        mReference.getData(new ReviewReference.CommentsCallback() {
            @Override
            public void onComments(IdableList<? extends DataComment> comments, CallbackMessage
                    message) {
                callback.onComments(comments, message);
            }
        });
    }

    public void getImages(final ReviewReference.ImagesCallback callback) {
        mReference.getData(new ReviewReference.ImagesCallback() {
            @Override
            public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
                callback.onImages(images, message);
            }
        });
    }

    public void getCriteria(final ReviewReference.CriteriaCallback callback) {
        mReference.getData(new ReviewReference.CriteriaCallback() {
            @Override
            public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage
                    message) {
                callback.onCriteria(criteria, message);
            }
        });
    }

    public void getTags(final ReviewReference.TagsCallback callback) {
        mReference.getData(new ReviewReference.TagsCallback() {
            @Override
            public void onTags(IdableList<? extends DataTag> tags, CallbackMessage message) {
                callback.onTags(tags, message);
            }
        });
    }

    public void getCovers(final ReviewReference.CoversCallback callback) {
        mReference.getData(new ReviewReference.CoversCallback() {
            @Override
            public void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message) {
                callback.onCovers(covers, message);
            }
        });
    }

    public void getNumTags(final ReviewReference.TagsSizeCallback callback) {
        mReference.getSize(new ReviewReference.TagsSizeCallback() {
            @Override
            public void onNumTags(DataSize size, CallbackMessage message) {
                callback.onNumTags(size, message);
            }
        });
    }

    public void getNumCriteria(final ReviewReference.CriteriaSizeCallback callback) {
        mReference.getSize(new ReviewReference.CriteriaSizeCallback() {
            @Override
            public void onNumCriteria(DataSize size, CallbackMessage message) {
                callback.onNumCriteria(size, message);
            }
        });
    }

    public void getNumImages(final ReviewReference.ImagesSizeCallback callback) {
        mReference.getSize(new ReviewReference.ImagesSizeCallback() {
            @Override
            public void onNumImages(DataSize size, CallbackMessage message) {
                callback.onNumImages(size, message);
            }
        });
    }

    public void getNumComments(final ReviewReference.CommentsSizeCallback callback) {
        mReference.getSize(new ReviewReference.CommentsSizeCallback() {
            @Override
            public void onNumComments(DataSize size, CallbackMessage message) {
                callback.onNumComments(size, message);
            }
        });
    }

    public void getNumLocations(final ReviewReference.LocationsSizeCallback callback) {
        mReference.getSize(new ReviewReference.LocationsSizeCallback() {
            @Override
            public void onNumLocations(DataSize size, CallbackMessage message) {
                callback.onNumLocations(size, message);
            }
        });
    }

    public void getNumFacts(final ReviewReference.FactsSizeCallback callback) {
        mReference.getSize(new ReviewReference.FactsSizeCallback() {
            @Override
            public void onNumFacts(DataSize size, CallbackMessage message) {
                callback.onNumFacts(size, message);
            }
        });
    }

    protected void fireForBinder(final DataBinder callback) {
        getCovers(callback);
        getTags(callback);
        getCriteria(callback);
        getImages(callback);
        getComments(callback);
        getLocations(callback);
        getFacts(callback);
    }

    protected void fireForBinder(final DataSizeBinder callback) {
        getNumTags(callback);
        getNumCriteria(callback);
        getNumImages(callback);
        getNumComments(callback);
        getNumLocations(callback);
        getNumFacts(callback);
    }

    protected void bind() {
        mIsBound = true;
        mReference.bind(mCovers);
        mReference.bind(mTags);
        mReference.bind(mCriteria);
        mReference.bind(mImages);
        mReference.bind(mComments);
        mReference.bind(mLocations);
        mReference.bind(mFacts);
        mReference.bindToTags(mTagsSize);
        mReference.bindToCriteria(mCriteriaSize);
        mReference.bindToImages(mImagesSize);
        mReference.bindToComments(mCommentsSize);
        mReference.bindToLocations(mLocationsSize);
        mReference.bindToFacts(mFactsSize);
    }

    protected void unbind() {
        mIsBound = false;
        mReference.unbind(mCovers);
        mReference.unbind(mTags);
        mReference.unbind(mCriteria);
        mReference.unbind(mImages);
        mReference.unbind(mComments);
        mReference.unbind(mLocations);
        mReference.unbind(mFacts);
        mReference.unbindFromTags(mTagsSize);
        mReference.unbindFromCriteria(mCriteriaSize);
        mReference.unbindFromImages(mImagesSize);
        mReference.unbindFromComments(mCommentsSize);
        mReference.unbindFromLocations(mLocationsSize);
        mReference.unbindFromFacts(mFactsSize);
    }

    protected <T> void notifyBinders(Iterable<T> binders, BinderMethod<T> method) {
        for (T binder : binders) {
            method.execute(binder);
        }
    }

    @Override
    public DataSubject getSubject() {
        return mReference.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReference.getRating();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReference.getPublishDate();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mReference.getAuthor();
    }

    @Override
    public ReviewId getReviewId() {
        return mReference.getReviewId();
    }

    private <T extends DataBinder> void bindOrFire(T binder) {
        if (!mIsBound) {
            bind();
        } else {
            fireForBinder(binder);
        }
    }

    private void bindOrFire(DataSizeBinder binder) {
        if (!mIsBound) {
            bind();
        } else {
            fireForBinder(binder);
        }
    }

    private void unbindIfNecessary() {
        if (mIsBound && mDataBinders.size() == 0 && mSizeBinders.size() == 0) unbind();
    }

    private void notifyDataBinders(BinderMethod<DataBinder> method) {
        notifyBinders(mDataBinders, method);
    }

    private void notifySizeBinders(BinderMethod<DataSizeBinder> method) {
        notifyBinders(mSizeBinders, method);
    }

    private class Covers implements ReferenceBinders.CoversBinder {
        @Override
        public void onValue(final IdableList<? extends DataImage> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onCovers(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Tags implements ReferenceBinders.TagsBinder {
        @Override
        public void onValue(final IdableList<? extends DataTag> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onTags(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Criteria implements ReferenceBinders.CriteriaBinder {
        @Override
        public void onValue(final IdableList<? extends DataCriterion> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onCriteria(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Images implements ReferenceBinders.ImagesBinder {
        @Override
        public void onValue(final IdableList<? extends DataImage> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onImages(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Comments implements ReferenceBinders.CommentsBinder {
        @Override
        public void onValue(final IdableList<? extends DataComment> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onComments(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Locations implements ReferenceBinders.LocationsBinder {
        @Override
        public void onValue(final IdableList<? extends DataLocation> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onLocations(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Facts implements ReferenceBinders.FactsBinder {
        @Override
        public void onValue(final IdableList<? extends DataFact> value) {
            notifyDataBinders(new BinderMethod<DataBinder>() {
                @Override
                public void execute(DataBinder binder) {
                    binder.onFacts(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class TagsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<DataSizeBinder>() {
                @Override
                public void execute(DataSizeBinder binder) {
                    binder.onNumTags(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class CriteriaSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<DataSizeBinder>() {
                @Override
                public void execute(DataSizeBinder binder) {
                    binder.onNumCriteria(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class ImagesSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<DataSizeBinder>() {
                @Override
                public void execute(DataSizeBinder binder) {
                    binder.onNumImages(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class CommentsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<DataSizeBinder>() {
                @Override
                public void execute(DataSizeBinder binder) {
                    binder.onNumComments(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class LocationsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<DataSizeBinder>() {
                @Override
                public void execute(DataSizeBinder binder) {
                    binder.onNumLocations(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class FactsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<DataSizeBinder>() {
                @Override
                public void execute(DataSizeBinder binder) {
                    binder.onNumFacts(size, CallbackMessage.ok());
                }
            });
        }
    }
}
