/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BindersManager {
    private Collection<ReferenceBinders.CoversBinder> mCoversBinders;
    private Collection<ReferenceBinders.TagsBinder> mTagsBinders;
    private Collection<ReferenceBinders.CriteriaBinder> mCriteriaBinders;
    private Collection<ReferenceBinders.ImagesBinder> mImagesBinders;
    private Collection<ReferenceBinders.CommentsBinder> mCommentsBinders;
    private Collection<ReferenceBinders.LocationsBinder> mLocationsBinders;
    private Collection<ReferenceBinders.FactsBinder> mFactsBinders;

    private Collection<ReferenceBinders.SizeBinder> mTagsSizeBinders;
    private Collection<ReferenceBinders.SizeBinder> mCriteriaSizeBinders;
    private Collection<ReferenceBinders.SizeBinder> mImagesSizeBinders;
    private Collection<ReferenceBinders.SizeBinder> mCommentsSizeBinders;
    private Collection<ReferenceBinders.SizeBinder> mLocationsSizeBinders;
    private Collection<ReferenceBinders.SizeBinder> mFactsSizeBinders;

    public BindersManager() {
        mCoversBinders = new ArrayList<>();
        mTagsBinders = new ArrayList<>();
        mCriteriaBinders = new ArrayList<>();
        mImagesBinders = new ArrayList<>();
        mCommentsBinders = new ArrayList<>();
        mLocationsBinders = new ArrayList<>();
        mFactsBinders = new ArrayList<>();

        mTagsSizeBinders = new ArrayList<>();
        mCriteriaSizeBinders = new ArrayList<>();
        mImagesSizeBinders = new ArrayList<>();
        mCommentsSizeBinders = new ArrayList<>();
        mLocationsSizeBinders = new ArrayList<>();
        mFactsSizeBinders = new ArrayList<>();
    }

    public Collection<ReferenceBinders.CoversBinder> getCoversBinders() {
        return mCoversBinders;
    }

    public Collection<ReferenceBinders.TagsBinder> getTagsBinders() {
        return mTagsBinders;
    }

    public Collection<ReferenceBinders.CriteriaBinder> getCriteriaBinders() {
        return mCriteriaBinders;
    }

    public Collection<ReferenceBinders.ImagesBinder> getImagesBinders() {
        return mImagesBinders;
    }

    public Collection<ReferenceBinders.CommentsBinder> getCommentsBinders() {
        return mCommentsBinders;
    }

    public Collection<ReferenceBinders.LocationsBinder> getLocationsBinders() {
        return mLocationsBinders;
    }

    public Collection<ReferenceBinders.FactsBinder> getFactsBinders() {
        return mFactsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getTagsSizeBinders() {
        return mTagsSizeBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getCriteriaSizeBinders() {
        return mCriteriaSizeBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getImagesSizeBinders() {
        return mImagesSizeBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getCommentsSizeBinders() {
        return mCommentsSizeBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getLocationsSizeBinders() {
        return mLocationsSizeBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getFactsSizeBinders() {
        return mFactsSizeBinders;
    }

    
    public void bind(final ReferenceBinders.CoversBinder binder) {
        if(!mCoversBinders.contains(binder)) mCoversBinders.add(binder);
    }
    
    public void bind(final ReferenceBinders.TagsBinder binder) {
        if(!mTagsBinders.contains(binder)) mTagsBinders.add(binder);
    }

    public void bind(final ReferenceBinders.CriteriaBinder binder) {
        if(!mCriteriaBinders.contains(binder)) mCriteriaBinders.add(binder);
    }

    public void bind(final ReferenceBinders.ImagesBinder binder) {
        if(!mImagesBinders.contains(binder)) mImagesBinders.add(binder);
    }

    public void bind(final ReferenceBinders.CommentsBinder binder) {
        if(!mCommentsBinders.contains(binder)) mCommentsBinders.add(binder);
    }

    public void bind(final ReferenceBinders.LocationsBinder binder) {
        if(!mLocationsBinders.contains(binder)) mLocationsBinders.add(binder);
    }

    public void bind(final ReferenceBinders.FactsBinder binder) {
        if(!mFactsBinders.contains(binder)) mFactsBinders.add(binder);
    }

    public void bindToTags(final ReferenceBinders.SizeBinder binder) {
        if(!mTagsSizeBinders.contains(binder)) mTagsSizeBinders.add(binder);
    }

    public void bindToCriteria(final ReferenceBinders.SizeBinder binder) {
        if(!mCriteriaSizeBinders.contains(binder)) mCriteriaSizeBinders.add(binder);
    }

    public void bindToImages(final ReferenceBinders.SizeBinder binder) {
        if(!mImagesSizeBinders.contains(binder)) mImagesSizeBinders.add(binder);
    }

    public void bindToComments(final ReferenceBinders.SizeBinder binder) {
        if(!mCommentsSizeBinders.contains(binder)) mCommentsSizeBinders.add(binder);
    }

    public void bindToLocations(final ReferenceBinders.SizeBinder binder) {
        if(!mLocationsSizeBinders.contains(binder)) mLocationsSizeBinders.add(binder);
    }

    public void bindToFacts(final ReferenceBinders.SizeBinder binder) {
        if(!mFactsSizeBinders.contains(binder)) mFactsSizeBinders.add(binder);
    }

    public void unbind(ReferenceBinders.CoversBinder binder) {
        if(mCoversBinders.contains(binder)) mCoversBinders.remove(binder);
    }

    public void unbind(ReferenceBinders.TagsBinder binder) {
        if(mTagsBinders.contains(binder)) mTagsBinders.remove(binder);
    }

    public void unbind(ReferenceBinders.CriteriaBinder binder) {
        if(mCriteriaBinders.contains(binder)) mCriteriaBinders.remove(binder);
    }

    public void unbind(ReferenceBinders.ImagesBinder binder) {
        if(mImagesBinders.contains(binder)) mImagesBinders.remove(binder);
    }

    public void unbind(ReferenceBinders.CommentsBinder binder) {
        if(mCommentsBinders.contains(binder)) mCommentsBinders.remove(binder);
    }

    public void unbind(ReferenceBinders.LocationsBinder binder) {
        if(mLocationsBinders.contains(binder)) mLocationsBinders.remove(binder);
    }

    public void unbind(ReferenceBinders.FactsBinder binder) {
        if(mFactsBinders.contains(binder)) mFactsBinders.remove(binder);
    }

    public void unbindFromTags(ReferenceBinders.SizeBinder binder) {
        if(mTagsSizeBinders.contains(binder)) mTagsSizeBinders.remove(binder);
    }

    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        if(mCriteriaSizeBinders.contains(binder)) mCriteriaSizeBinders.remove(binder);
    }

    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        if(mImagesSizeBinders.contains(binder)) mImagesSizeBinders.remove(binder);
    }

    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        if(mCommentsSizeBinders.contains(binder)) mCommentsSizeBinders.remove(binder);
    }

    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        if(mLocationsSizeBinders.contains(binder)) mLocationsSizeBinders.remove(binder);
    }

    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        if(mFactsSizeBinders.contains(binder)) mFactsSizeBinders.remove(binder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BindersManager)) return false;

        BindersManager that = (BindersManager) o;

        if (!mCoversBinders.equals(that.mCoversBinders)) return false;
        if (!mTagsBinders.equals(that.mTagsBinders)) return false;
        if (!mCriteriaBinders.equals(that.mCriteriaBinders)) return false;
        if (!mImagesBinders.equals(that.mImagesBinders)) return false;
        if (!mCommentsBinders.equals(that.mCommentsBinders)) return false;
        if (!mLocationsBinders.equals(that.mLocationsBinders)) return false;
        if (!mFactsBinders.equals(that.mFactsBinders)) return false;
        if (!mTagsSizeBinders.equals(that.mTagsSizeBinders)) return false;
        if (!mCriteriaSizeBinders.equals(that.mCriteriaSizeBinders)) return false;
        if (!mImagesSizeBinders.equals(that.mImagesSizeBinders)) return false;
        if (!mCommentsSizeBinders.equals(that.mCommentsSizeBinders)) return false;
        if (!mLocationsSizeBinders.equals(that.mLocationsSizeBinders)) return false;
        return mFactsSizeBinders.equals(that.mFactsSizeBinders);

    }

    @Override
    public int hashCode() {
        int result = mCoversBinders.hashCode();
        result = 31 * result + mTagsBinders.hashCode();
        result = 31 * result + mCriteriaBinders.hashCode();
        result = 31 * result + mImagesBinders.hashCode();
        result = 31 * result + mCommentsBinders.hashCode();
        result = 31 * result + mLocationsBinders.hashCode();
        result = 31 * result + mFactsBinders.hashCode();
        result = 31 * result + mTagsSizeBinders.hashCode();
        result = 31 * result + mCriteriaSizeBinders.hashCode();
        result = 31 * result + mImagesSizeBinders.hashCode();
        result = 31 * result + mCommentsSizeBinders.hashCode();
        result = 31 * result + mLocationsSizeBinders.hashCode();
        result = 31 * result + mFactsSizeBinders.hashCode();
        return result;
    }
}
