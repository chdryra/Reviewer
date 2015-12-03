package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterAuthors;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterCriteria;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterDataTags;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterDateReviews;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterDates;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterFacts;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterItemTags;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterReviews;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterSubjects;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterUrls;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.MdConverterComments;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.MdConverterCriteria;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.MdConverterFacts;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.MdConverterImages;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.MdConverterLocations;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.MdConverterUrl;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;

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

    public ConverterMd newMdConverter() {
        MdConverterComments converterComments = new MdConverterComments();
        MdConverterUrl converterUrl = new MdConverterUrl();
        MdConverterFacts converterFacts = new MdConverterFacts(converterUrl);
        MdConverterImages converterImages = new MdConverterImages();
        MdConverterLocations converterLocations = new MdConverterLocations();
        MdConverterCriteria converterCriteria = new MdConverterCriteria();
        
        return new ConverterMd(converterComments, converterFacts, converterImages, 
                converterLocations, converterUrl, converterCriteria);
    }
    
    public ConverterGv newGvConverter() {
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
        GvConverterDataTags converterTags = new GvConverterDataTags();
        GvConverterItemTags converterItemTags = new GvConverterItemTags();

        return new ConverterGv(converterComments, converterFacts, converterImages,
                converterLocations, converterUrl, converterCriteria, converterReview,
                converterSubjects, converterAuthors, converterDates, converterTags,
                converterItemTags);
    }
}
