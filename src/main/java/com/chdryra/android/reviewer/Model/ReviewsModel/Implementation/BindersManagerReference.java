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
public class BindersManagerReference {
    private Collection<ReferenceBinders.CoversBinder> mCoversBinders;
    private Collection<ReferenceBinders.TagsBinder> mTagsBinders;
    private Collection<ReferenceBinders.CriteriaBinder> mCriteriaBinders;
    private Collection<ReferenceBinders.ImagesBinder> mImagesBinders;
    private Collection<ReferenceBinders.CommentsBinder> mCommentsBinders;
    private Collection<ReferenceBinders.LocationsBinder> mLocationsBinders;
    private Collection<ReferenceBinders.FactsBinder> mFactsBinders;

    private Collection<ReferenceBinders.SizeBinder> mNumTagsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumCriteriaBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumImagesBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumCommentsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumLocationsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumFactsBinders;

    public BindersManagerReference() {
        mCoversBinders = new ArrayList<>();
        mTagsBinders = new ArrayList<>();
        mCriteriaBinders = new ArrayList<>();
        mImagesBinders = new ArrayList<>();
        mCommentsBinders = new ArrayList<>();
        mLocationsBinders = new ArrayList<>();
        mFactsBinders = new ArrayList<>();

        mNumTagsBinders = new ArrayList<>();
        mNumCriteriaBinders = new ArrayList<>();
        mNumImagesBinders = new ArrayList<>();
        mNumCommentsBinders = new ArrayList<>();
        mNumLocationsBinders = new ArrayList<>();
        mNumFactsBinders = new ArrayList<>();
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


    public Collection<ReferenceBinders.SizeBinder> getNumTagsBinders() {
        return mNumTagsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumCriteriaBinders() {
        return mNumCriteriaBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumImagesBinders() {
        return mNumImagesBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumCommentsBinders() {
        return mNumCommentsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumLocationsBinders() {
        return mNumLocationsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumFactsBinders() {
        return mNumFactsBinders;
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
        if(!mNumTagsBinders.contains(binder)) mNumTagsBinders.add(binder);
    }

    public void bindToCriteria(final ReferenceBinders.SizeBinder binder) {
        if(!mNumCriteriaBinders.contains(binder)) mNumCriteriaBinders.add(binder);
    }

    public void bindToImages(final ReferenceBinders.SizeBinder binder) {
        if(!mNumImagesBinders.contains(binder)) mNumImagesBinders.add(binder);
    }

    public void bindToComments(final ReferenceBinders.SizeBinder binder) {
        if(!mNumCommentsBinders.contains(binder)) mNumCommentsBinders.add(binder);
    }

    public void bindToLocations(final ReferenceBinders.SizeBinder binder) {
        if(!mNumLocationsBinders.contains(binder)) mNumLocationsBinders.add(binder);
    }

    public void bindToFacts(final ReferenceBinders.SizeBinder binder) {
        if(!mNumFactsBinders.contains(binder)) mNumFactsBinders.add(binder);
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
        if(mNumTagsBinders.contains(binder)) mNumTagsBinders.remove(binder);
    }

    public void unbindFromCriteria(ReferenceBinders.SizeBinder binder) {
        if(mNumCriteriaBinders.contains(binder)) mNumCriteriaBinders.remove(binder);
    }

    public void unbindFromImages(ReferenceBinders.SizeBinder binder) {
        if(mNumImagesBinders.contains(binder)) mNumImagesBinders.remove(binder);
    }

    public void unbindFromComments(ReferenceBinders.SizeBinder binder) {
        if(mNumCommentsBinders.contains(binder)) mNumCommentsBinders.remove(binder);
    }

    public void unbindFromLocations(ReferenceBinders.SizeBinder binder) {
        if(mNumLocationsBinders.contains(binder)) mNumLocationsBinders.remove(binder);
    }

    public void unbindFromFacts(ReferenceBinders.SizeBinder binder) {
        if(mNumFactsBinders.contains(binder)) mNumFactsBinders.remove(binder);
    }
}
