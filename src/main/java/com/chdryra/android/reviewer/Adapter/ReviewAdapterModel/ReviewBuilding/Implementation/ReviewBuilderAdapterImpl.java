package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Utils.FactoryFileIncrementor;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataListImpl;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataTypesList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderAdapterImpl<GC extends GvDataListImpl<?>>
        extends ReviewViewAdapterBasic<GC>
        implements ReviewBuilderAdapter<GC> {
    private static final ArrayList<GvDataType<? extends GvData>> TYPES = GvDataTypesList.BUILD_TYPES;

    private final DataBuildersMap mDataBuilders;
    private final BuildScreenGridUi<GC> mGridUi;
    private final FactoryDataBuilderAdapter mDataBuilderAdapterFactory;
    private final FactoryFileIncrementor mIncrementorFactory;
    private FileIncrementor mIncrementor;
    private final FactoryImageChooser mImageChooserFactory;
    private final ReviewBuilder mBuilder;
    private final DataValidator mDataValidator;
    private GvTag mSubjectTag;

    //Constructors
    public ReviewBuilderAdapterImpl(ReviewBuilder builder,
                                BuildScreenGridUi<GC> gridUi,
                                DataValidator dataValidator,
                                FactoryDataBuilderAdapter dataBuilderAdapterFactory,
                                FactoryFileIncrementor incrementorFactory,
                                FactoryImageChooser imageChooserFactory) {
        mBuilder = builder;
        mDataValidator = dataValidator;
        mDataBuilderAdapterFactory = dataBuilderAdapterFactory;
        mDataBuilders = new DataBuildersMap();
        mGridUi = gridUi;
        gridUi.setParentAdapter(this);
        mIncrementorFactory = incrementorFactory;
        mImageChooserFactory = imageChooserFactory;
        newIncrementor();
    }

    //public methods

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return mGridUi.getGridWrapper().getGvDataType();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mBuilder.setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public ImageChooser getImageChooser() {
        return mImageChooserFactory.newImageChooser(mIncrementor);
    }

    @Override
    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mDataBuilders.get(dataType);
    }

    @Override
    public boolean hasTags() {
        return mBuilder.getData(GvTag.TYPE).size() > 0;
    }

    @Override
    public Review publishReview() {
        return mBuilder.buildReview();
    }

    @Override
    public ReviewBuilder getBuilder() {
        return mBuilder;
    }

    //private methods
    private <T extends GvData> DataBuilderAdapter<T> newDataBuilderAdapter(GvDataType<T> dataType) {
        return mDataBuilderAdapterFactory.newDataBuilderAdapter(dataType, this);
    }

    private GvTag adjustTagsIfNecessary(GvTag toRemove, String toAdd) {
        String camel = TextUtils.toCamelCase(toAdd);
        GvTag newTag = new GvTag(camel);
        if (newTag.equals(toRemove)) return toRemove;

        DataBuilderAdapter<GvTag> tagBuilder = getDataBuilderAdapter(GvTag
                .TYPE);
        GvTagList tags = (GvTagList) tagBuilder.getGridData();
        boolean added = mDataValidator.validateString(camel) && !tags.contains(newTag)
                && tagBuilder.add(newTag);
        tagBuilder.delete(toRemove);
        tagBuilder.setData();

        return added ? newTag : null;
    }

    private void newIncrementor() {
        mIncrementor = mIncrementorFactory.newJpgFileIncrementor(mBuilder.getSubject());
    }

    //Overridden
    @Override
    public String getSubject() {
        return mBuilder.getSubject();
    }

    @Override
    public void setSubject(String subject) {
        mBuilder.setSubject(subject);
        newIncrementor();
        mSubjectTag = adjustTagsIfNecessary(mSubjectTag, subject);
    }

    @Override
    public GvDataListImpl<GC> getGridData() {
        return mGridUi.getGridWrapper();
    }

    @Override
    public float getRating() {
        return mBuilder.getRating();
    }

    @Override
    public void setRating(float rating) {
        mBuilder.setRating(rating);
    }

    @Override
    public GvImageList getCovers() {
        return ((GvImageList) mBuilder.getData(GvImage.TYPE)).getCovers();
    }

    private class DataBuildersMap {
        private final Map<GvDataType<? extends GvData>, DataBuilderAdapter<? extends GvData>>
                mDataBuilders;

        private DataBuildersMap() {
            mDataBuilders = new HashMap<>();
            for (GvDataType<? extends GvData> type : TYPES) {
                mDataBuilders.put(type, newDataBuilderAdapter(type));
            }
        }

        //TODO make type safe although it is really....
        private <T extends GvData> DataBuilderAdapter<T> get(GvDataType<T> type) {
            return (DataBuilderAdapter<T>) mDataBuilders.get(type);
        }
    }
}
