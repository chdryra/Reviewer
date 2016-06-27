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

    @Override
    public void bind(final ReferenceBinders.CoversBinder binder) {
        mBinders.bind(binder);
        getCovers(new CoversCallback() {
            @Override
            public void onCovers(IdableList<DataImage> covers, CallbackMessage message) {
                binder.onValue(covers);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.TagsBinder binder) {
        mBinders.bind(binder);
        getTags(new TagsCallback() {
            @Override
            public void onTags(IdableList<DataTag> tags, CallbackMessage message) {
                binder.onValue(tags);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.CriteriaBinder binder) {
        mBinders.bind(binder);
        getCriteria(new CriteriaCallback() {
            @Override
            public void onCriteria(IdableList<DataCriterion> criteria, CallbackMessage message) {
                binder.onValue(criteria);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.ImagesBinder binder) {
        mBinders.bind(binder);
        getImages(new ImagesCallback() {
            @Override
            public void onImages(IdableList<DataImage> images, CallbackMessage message) {
                binder.onValue(images);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.CommentsBinder binder) {
        mBinders.bind(binder);
        getComments(new CommentsCallback() {
            @Override
            public void onComments(IdableList<DataComment> comments, CallbackMessage message) {
                binder.onValue(comments);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.LocationsBinder binder) {
        mBinders.bind(binder);
        getLocations(new LocationsCallback() {
            @Override
            public void onLocations(IdableList<DataLocation> locations, CallbackMessage message) {
                binder.onValue(locations);
            }
        });
    }

    @Override
    public void bind(final ReferenceBinders.FactsBinder binder) {
        mBinders.bind(binder);
        getFacts(new FactsCallback() {
            @Override
            public void onFacts(IdableList<DataFact> facts, CallbackMessage message) {
                binder.onValue(facts);
            }
        });
    }

    @Override
    public void bindToTags(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToTags(binder);
        getTagsSize(new TagsSizeCallback() {
            @Override
            public void onNumTags(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToCriteria(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToCriteria(binder);
        getCriteriaSize(new CriteriaSizeCallback() {
            @Override
            public void onNumCriteria(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToImages(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToImages(binder);
        getImagesSize(new ImagesSizeCallback() {
            @Override
            public void onNumImages(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToComments(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToComments(binder);
        getCommentsSize(new CommentsSizeCallback() {
            @Override
            public void onNumComments(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToLocations(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToLocations(binder);
        getLocationsSize(new LocationsSizeCallback() {
            @Override
            public void onNumLocations(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToFacts(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToFacts(binder);
        getFactsSize(new FactsSizeCallback() {
            @Override
            public void onNumFacts(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.CoversBinder binder) {
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
}
