/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ValueBinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BindersManagerNode extends BindersManager {
    private Collection<MetaBinders.ReviewsBinder> mReviewsBinders;
    private Collection<MetaBinders.AuthorsBinder> mAuthorsBinders;
    private Collection<MetaBinders.SubjectsBinder> mSubjectsBinders;
    private Collection<MetaBinders.DatesBinder> mDatesBinders;

    private Collection<ReferenceBinders.SizeBinder> mNumReviewsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumAuthorsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumSubjectsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumDatesBinders;

    private List<ReferenceBinder> mBinders;

    private interface BinderMethod {
        void execute(ReferenceBinder managedBinder);
    }

    public BindersManagerNode() {
        mReviewsBinders = new ArrayList<>();
        mAuthorsBinders = new ArrayList<>();
        mSubjectsBinders = new ArrayList<>();
        mDatesBinders = new ArrayList<>();

        mNumReviewsBinders = new ArrayList<>();
        mNumAuthorsBinders = new ArrayList<>();
        mNumSubjectsBinders = new ArrayList<>();
        mNumDatesBinders = new ArrayList<>();

        mBinders = new ArrayList<>();
    }

    public Collection<MetaBinders.ReviewsBinder> getReviewsBinders() {
        return mReviewsBinders;
    }

    public Collection<MetaBinders.AuthorsBinder> getAuthorsBinders() {
        return mAuthorsBinders;
    }

    public Collection<MetaBinders.SubjectsBinder> getSubjectsBinders() {
        return mSubjectsBinders;
    }

    public Collection<MetaBinders.DatesBinder> getDatesBinders() {
        return mDatesBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumReviewsBinders() {
        return mNumReviewsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumAuthorsBinders() {
        return mNumAuthorsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumSubjectsBinders() {
        return mNumSubjectsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumDatesBinders() {
        return mNumDatesBinders;
    }

    public void manageBinder(ReferenceBinder binder) {
        mBinders.add(binder);
        bindWhereNecessary(binder);
    }

    public void unmanageBinder(ReferenceBinder binder) {
        mBinders.remove(binder);
        unbind(binder);
    }

    public void bind(final MetaBinders.ReviewsBinder binder) {
        addAndBind(getReviewsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToReviews();
            }
        });
    }

    public void bind(final MetaBinders.AuthorsBinder binder) {
        addAndBind(getAuthorsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToAuthors();
            }
        });
    }

    public void bind(final MetaBinders.SubjectsBinder binder) {
        addAndBind(getSubjectsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToSubjects();
            }
        });
    }

    public void bind(final MetaBinders.DatesBinder binder) {
        addAndBind(getDatesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToDates();
            }
        });
    }

    public void bindToReviews(final ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumReviewsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumReviews();
            }
        });
    }

    public void bindToAuthors(final ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumAuthorsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumAuthors();
            }
        });
    }

    public void bindToSubjects(final ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumSubjectsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumSubjects();
            }
        });
    }

    public void bindToDates(final ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumDatesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumDates();
            }
        });
    }

    public void unbind(MetaBinders.ReviewsBinder binder) {
        removeAndUnbind(getReviewsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromReviews();
            }
        });
    }

    public void unbind(MetaBinders.AuthorsBinder binder) {
        removeAndUnbind(getAuthorsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromAuthors();
            }
        });
    }

    public void unbind(MetaBinders.SubjectsBinder binder) {
        removeAndUnbind(getSubjectsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromSubjects();
            }
        });
    }

    public void unbind(MetaBinders.DatesBinder binder) {
        removeAndUnbind(getDatesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromDates();
            }
        });
    }

    public void unbindFromReviews(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumReviewsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumReviews();
            }
        });
    }

    public void unbindFromAuthors(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumAuthorsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumAuthors();
            }
        });
    }

    public void unbindFromSubjects(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumSubjectsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumSubjects();
            }
        });
    }

    public void unbindFromDates(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumDatesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumDates();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CoversBinder binder) {
        addAndBind(getCoversBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToCovers();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {
        addAndBind(getTagsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToTags();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {
        addAndBind(getCriteriaBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToCriteria();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {
        addAndBind(getImagesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToImages();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {
        addAndBind(getCommentsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToComments();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {
        addAndBind(getLocationsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToLocations();
            }
        });
    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {
        addAndBind(getFactsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToFacts();
            }
        });
    }

    @Override
    public void bindToTags(ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumTagsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumTags();
            }
        });
    }

    @Override
    public void bindToCriteria(ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumCriteriaBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumCriteria();
            }
        });
    }

    @Override
    public void bindToImages(ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumImagesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumImages();
            }
        });
    }

    @Override
    public void bindToComments(ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumCommentsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumComments();
            }
        });
    }

    @Override
    public void bindToLocations(ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumLocationsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumLocations();
            }
        });
    }

    @Override
    public void bindToFacts(ReferenceBinders.SizeBinder binder) {
        addAndBind(getNumFactsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.bindToNumFacts();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.CoversBinder binder) {
        removeAndUnbind(getCoversBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromCovers();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {
        removeAndUnbind(getTagsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromTags();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        removeAndUnbind(getCriteriaBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromCriteria();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {
        removeAndUnbind(getImagesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromImages();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {
        removeAndUnbind(getCommentsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromComments();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {
        removeAndUnbind(getLocationsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromLocations();
            }
        });
    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {
        removeAndUnbind(getFactsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromFacts();
            }
        });
    }

    @Override
    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumTagsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumTags();
            }
        });
    }

    @Override
    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumCriteriaBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumCriteria();
            }
        });
    }

    @Override
    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumImagesBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumImages();
            }
        });
    }

    @Override
    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumCommentsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumComments();
            }
        });
    }

    @Override
    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumLocationsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumLocations();
            }
        });
    }

    @Override
    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        removeAndUnbind(getNumFactsBinders(), binder, new BinderMethod() {
            @Override
            public void execute(ReferenceBinder managedBinder) {
                managedBinder.unbindFromNumFacts();
            }
        });
    }

    private <T extends ValueBinder> void addAndBind(Collection<T> binders, T binder, BinderMethod
            method) {
        if (!binders.contains(binder)) {
            binders.add(binder);
            if (binders.size() == 1) {
                for (ReferenceBinder referenceBinder : mBinders) {
                    method.execute(referenceBinder);
                }
            }
        }
    }

    private <T extends ValueBinder> void removeAndUnbind(Collection<T> binders, T binder,
                                                         BinderMethod method) {
        if (binders.contains(binder)) {
            binders.remove(binder);
            if (binders.size() == 0) {
                for (ReferenceBinder referenceBinder : mBinders) {
                    method.execute(referenceBinder);
                }
            }
        }
    }

    private void bindWhereNecessary(ReferenceBinder binder) {
        if (getNumReviewsBinders().size() > 0) binder.bindToNumReviews();
        if (getNumAuthorsBinders().size() > 0) binder.bindToNumAuthors();
        if (getNumSubjectsBinders().size() > 0) binder.bindToNumSubjects();
        if (getNumDatesBinders().size() > 0) binder.bindToNumDates();
        if (getNumTagsBinders().size() > 0) binder.bindToNumTags();
        if (getNumCriteriaBinders().size() > 0) binder.bindToNumCriteria();
        if (getNumImagesBinders().size() > 0) binder.bindToNumImages();
        if (getNumCommentsBinders().size() > 0) binder.bindToNumComments();
        if (getNumLocationsBinders().size() > 0) binder.bindToNumLocations();
        if (getNumFactsBinders().size() > 0) binder.bindToNumFacts();
        if (getReviewsBinders().size() > 0) binder.bindToReviews();
        if (getAuthorsBinders().size() > 0) binder.bindToAuthors();
        if (getSubjectsBinders().size() > 0) binder.bindToSubjects();
        if (getDatesBinders().size() > 0) binder.bindToDates();
        if (getTagsBinders().size() > 0) binder.bindToTags();
        if (getCriteriaBinders().size() > 0) binder.bindToCriteria();
        if (getImagesBinders().size() > 0) binder.bindToImages();
        if (getCommentsBinders().size() > 0) binder.bindToComments();
        if (getLocationsBinders().size() > 0) binder.bindToLocations();
        if (getFactsBinders().size() > 0) binder.bindToFacts();
    }

    private void unbind(ReferenceBinder binder) {
        binder.unbindFromNumReviews();
        binder.unbindFromNumAuthors();
        binder.unbindFromNumSubjects();
        binder.unbindFromNumDates();
        binder.unbindFromNumTags();
        binder.unbindFromNumCriteria();
        binder.unbindFromNumImages();
        binder.unbindFromNumComments();
        binder.unbindFromNumLocations();
        binder.unbindFromNumFacts();
        binder.unbindFromReviews();
        binder.unbindFromAuthors();
        binder.unbindFromSubjects();
        binder.unbindFromDates();
        binder.unbindFromTags();
        binder.unbindFromCriteria();
        binder.unbindFromImages();
        binder.unbindFromComments();
        binder.unbindFromLocations();
        binder.unbindFromFacts();
    }
}
