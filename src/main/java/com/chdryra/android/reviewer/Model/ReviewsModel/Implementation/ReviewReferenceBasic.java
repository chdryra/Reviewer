/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewReferenceBasic implements ReviewReference {
    private BindersManager mBinders;

    public ReviewReferenceBasic(BindersManager binders) {
        mBinders = binders;
    }

    public BindersManager getBindersManager() {
        return mBinders;
    }

    protected void notifyCoversBinders() {
        getData(new CoverCallback() {
            @Override
            public void onCover(DataImage cover, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.CoverBinder binder : mBinders.getCoverBinders()) {
                        binder.onReferenceValue(cover);
                    }
                }
            }
        });
    }

    protected void notifyTagsBinders() {
        getData(new TagsCallback() {
            @Override
            public void onTags(IdableList<? extends DataTag> Tags, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.TagsBinder binder : mBinders.getTagsBinders()) {
                        binder.onReferenceValue(Tags);
                    }
                }
            }
        });
    }

    protected void notifyCriteriaBinders() {
        getData(new CriteriaCallback() {
            @Override
            public void onCriteria(IdableList<? extends DataCriterion> Criteria, CallbackMessage
                    message) {
                if (!message.isError()) {
                    for (ReferenceBinders.CriteriaBinder binder : mBinders.getCriteriaBinders()) {
                        binder.onReferenceValue(Criteria);
                    }
                }
            }
        });
    }

    protected void notifyImagesBinders() {
        getData(new ImagesCallback() {
            @Override
            public void onImages(IdableList<? extends DataImage> Images, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.ImagesBinder binder : mBinders.getImagesBinders()) {
                        binder.onReferenceValue(Images);
                    }
                }
            }
        });
    }

    protected void notifyCommentsBinders() {
        getData(new CommentsCallback() {
            @Override
            public void onComments(IdableList<? extends DataComment> Comments, CallbackMessage
                    message) {
                if (!message.isError()) {
                    for (ReferenceBinders.CommentsBinder binder : mBinders.getCommentsBinders()) {
                        binder.onReferenceValue(Comments);
                    }
                }
            }
        });
    }

    protected void notifyLocationsBinders() {
        getData(new LocationsCallback() {
            @Override
            public void onLocations(IdableList<? extends DataLocation> Locations, CallbackMessage
                    message) {
                if (!message.isError()) {
                    for (ReferenceBinders.LocationsBinder binder : mBinders.getLocationsBinders()) {
                        binder.onReferenceValue(Locations);
                    }
                }
            }
        });
    }

    protected void notifyFactsBinders() {
        getData(new FactsCallback() {
            @Override
            public void onFacts(IdableList<? extends DataFact> Facts, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.FactsBinder binder : mBinders.getFactsBinders()) {
                        binder.onReferenceValue(Facts);
                    }
                }
            }
        });
    }

    protected void notifyNumTagsBinders() {
        getSize(new TagsSizeCallback() {
            @Override
            public void onNumTags(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumTagsBinders()) {
                        binder.onReferenceValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumCriteriaBinders() {
        getSize(new CriteriaSizeCallback() {
            @Override
            public void onNumCriteria(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumCriteriaBinders()) {
                        binder.onReferenceValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumImagesBinders() {
        getSize(new ImagesSizeCallback() {
            @Override
            public void onNumImages(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumImagesBinders()) {
                        binder.onReferenceValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumCommentsBinders() {
        getSize(new CommentsSizeCallback() {
            @Override
            public void onNumComments(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumCommentsBinders()) {
                        binder.onReferenceValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumLocationsBinders() {
        getSize(new LocationsSizeCallback() {
            @Override
            public void onNumLocations(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumLocationsBinders()) {
                        binder.onReferenceValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumFactsBinders() {
        getSize(new FactsSizeCallback() {
            @Override
            public void onNumFacts(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumFactsBinders()) {
                        binder.onReferenceValue(size);
                    }
                }
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.CoverBinder binder) {
        mBinders.bind(binder);
        getData(new CoverCallback() {
            @Override
            public void onCover(DataImage cover, CallbackMessage message) {
                binder.onReferenceValue(cover);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.TagsBinder binder) {
        mBinders.bind(binder);
        getData(new TagsCallback() {
            @Override
            public void onTags(IdableList<? extends DataTag> tags, CallbackMessage message) {
                binder.onReferenceValue(tags);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.CriteriaBinder binder) {
        mBinders.bind(binder);
        getData(new CriteriaCallback() {
            @Override
            public void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage
                    message) {
                binder.onReferenceValue(criteria);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.ImagesBinder binder) {
        mBinders.bind(binder);
        getData(new ImagesCallback() {
            @Override
            public void onImages(IdableList<? extends DataImage> images, CallbackMessage message) {
                binder.onReferenceValue(images);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.CommentsBinder binder) {
        mBinders.bind(binder);
        getData(new CommentsCallback() {
            @Override
            public void onComments(IdableList<? extends DataComment> comments, CallbackMessage
                    message) {
                binder.onReferenceValue(comments);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.LocationsBinder binder) {
        mBinders.bind(binder);
        getData(new LocationsCallback() {
            @Override
            public void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage
                    message) {
                binder.onReferenceValue(locations);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.FactsBinder binder) {
        mBinders.bind(binder);
        getData(new FactsCallback() {
            @Override
            public void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message) {
                binder.onReferenceValue(facts);
            }
        });
    }

    @Override
    public void bindToTags(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToTags(binder);
        getSize(new TagsSizeCallback() {
            @Override
            public void onNumTags(DataSize size, CallbackMessage message) {
                binder.onReferenceValue(size);
            }
        });
    }

    @Override
    public void bindToCriteria(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToCriteria(binder);
        getSize(new CriteriaSizeCallback() {
            @Override
            public void onNumCriteria(DataSize size, CallbackMessage message) {
                binder.onReferenceValue(size);
            }
        });
    }

    @Override
    public void bindToImages(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToImages(binder);
        getSize(new ImagesSizeCallback() {
            @Override
            public void onNumImages(DataSize size, CallbackMessage message) {
                binder.onReferenceValue(size);
            }
        });
    }

    @Override
    public void bindToComments(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToComments(binder);
        getSize(new CommentsSizeCallback() {
            @Override
            public void onNumComments(DataSize size, CallbackMessage message) {
                binder.onReferenceValue(size);
            }
        });
    }

    @Override
    public void bindToLocations(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToLocations(binder);
        getSize(new LocationsSizeCallback() {
            @Override
            public void onNumLocations(DataSize size, CallbackMessage message) {
                binder.onReferenceValue(size);
            }
        });
    }

    @Override
    public void bindToFacts(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToFacts(binder);
        getSize(new FactsSizeCallback() {
            @Override
            public void onNumFacts(DataSize size, CallbackMessage message) {
                binder.onReferenceValue(size);
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.CoverBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromTags(binder);
    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromCriteria(binder);
    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromImages(binder);
    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromComments(binder);
    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromLocations(binder);
    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromFacts(binder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewReferenceBasic)) return false;

        ReviewReferenceBasic that = (ReviewReferenceBasic) o;

        return mBinders.equals(that.mBinders);

    }

    @Override
    public int hashCode() {
        return mBinders.hashCode();
    }
}
