package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataConverters {
    private TagsManager mTagsManager;

    public FactoryDataConverters(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    public DataConverters newDataConverters() {
        return new ConvertersMdGv(newMdConverter(), newGvConverter());
    }
    
    private ConverterMd newMdConverter() {
        MdConverterComments converterComments = new MdConverterComments();
        MdConverterUrl converterUrl = new MdConverterUrl();
        MdConverterFacts converterFacts = new MdConverterFacts(converterUrl);
        MdConverterImages converterImages = new MdConverterImages();
        MdConverterLocations converterLocations = new MdConverterLocations();
        MdConverterCriteria converterCriteria = new MdConverterCriteria();
        
        return new ConverterMd(converterComments, converterFacts, converterImages, 
                converterLocations, converterUrl, converterCriteria);
    }
    
    private ConverterGv newGvConverter() {
        GvConverterComments converterComments = new GvConverterComments();
        GvConverterUrls converterUrl = new GvConverterUrls();
        GvConverterFacts converterFacts = new GvConverterFacts(converterUrl);
        GvConverterImages converterImages = new GvConverterImages(new GvConverterDates());
        GvConverterLocations converterLocations = new GvConverterLocations();
        GvConverterCriteria converterCriteria = new GvConverterCriteria();
        GvConverterSubjects converterSubjects = new GvConverterSubjects();
        GvConverterAuthors converterAuthors = new GvConverterAuthors();
        GvConverterDateReviews converterDates = new GvConverterDateReviews();
        GvConverterReviews converterReview = new GvConverterReviews(mTagsManager, converterImages,
                converterComments, converterLocations, converterDates, converterAuthors);


        return new ConverterGv(converterComments, converterFacts, converterImages,
                converterLocations, converterUrl, converterCriteria, converterReview,
                converterSubjects, converterAuthors, converterDates);
    }
}
