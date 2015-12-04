package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories.FactoryGvConverter;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryVhBuildReviewData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Utils.FactoryFileIncrementor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregater;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePresenterContext extends PresenterContextBasic {
    public ReleasePresenterContext(Context context,
                                   ModelContext modelContext,
                                   ViewContext viewContext,
                                   DataAuthor author,
                                   File extDir, String imageDir) {

        setLaunchablesFactory(viewContext);

        setGvDataFactory();

        ConverterGv gvConverter = getConverterGv(modelContext.getTagsManager());
        setAdaptersFactory(modelContext, gvConverter);

        setBuildersFactory(context, modelContext, gvConverter, author, extDir, imageDir);
    }

    private ConverterGv getConverterGv(TagsManager tagsManager) {
        FactoryGvConverter converterFactory = new FactoryGvConverter(tagsManager);
        return converterFactory.newGvConverter();
    }

    private void setBuildersFactory(Context context, ModelContext modelContext,
                                    ConverterGv gvConverter, DataAuthor author,
                                    File extDir, String imageDir) {
        setReviewBuilderAdapterFactory(context, gvConverter, modelContext.getTagsManager(),
                modelContext.getReviewsFactory(), getGvDataFactory(), modelContext.getDataValidator(),
                extDir, imageDir, author.getName());
    }

    private void setAdaptersFactory(ModelContext modelContext, ConverterGv gvConverter) {
        setFactoryReviewViewAdapter(getReviewViewLaunchableFactory(), modelContext.getReviewsProvider(),
                gvConverter, modelContext.getVisitorsFactory(), modelContext.getTreeTraversersFactory());
    }

    private void setGvDataFactory() {
        setFactoryGvData(new FactoryGvData());
    }

    private void setLaunchablesFactory(ViewContext viewContext) {
        FactoryReviewViewLaunchable launchableFactory
                = new FactoryReviewViewLaunchable(viewContext.getUiConfig(),
                viewContext.getUiLauncher(), new FactoryReviewViewParams());
        setFactoryReviewViewLaunchable(launchableFactory);
    }

    private void setReviewBuilderAdapterFactory(Context context,
                                                ConverterGv converter,
                                                TagsManager tagsManager,
                                                FactoryReviews reviewsFactory,
                                                FactoryGvData dataFactory,
                                                DataValidator validator,
                                                File extDir, String dir,
                                                String authorName) {
        FactoryGridUi<? extends GvDataList> gridUi = new FactoryDataBuildersGridUi();
        FactoryVhBuildReviewData factoryVhBuildReviewData = new FactoryVhBuildReviewData();
        FactoryDataBuilderAdapter factoryDataBuilderAdapter = new FactoryDataBuilderAdapter
                (context);
        FactoryImageChooser factoryImageChooser = new FactoryImageChooser(context);
        FactoryDataBuilder dataBuilderFactory = new FactoryDataBuilder(dataFactory);
        FactoryReviewBuilder factoryReviewBuilder = new FactoryReviewBuilder(converter,
                tagsManager, reviewsFactory, dataBuilderFactory, validator);
        FactoryFileIncrementor incrementorFactory = new FactoryFileIncrementor(extDir, dir,
                authorName, validator);
        FactoryReviewBuilderAdapter builderAdapterFactory
                = new FactoryReviewBuilderAdapter(factoryReviewBuilder,
                gridUi,
                factoryVhBuildReviewData,
                validator,
                factoryDataBuilderAdapter, incrementorFactory, factoryImageChooser);

        setFactoryBuilderAdapter(builderAdapterFactory);
    }



    private void setFactoryReviewViewAdapter(FactoryReviewViewLaunchable launchableFactory,
                                             ReviewsFeed provider,
                                             ConverterGv converter,
                                             FactoryVisitorReviewNode visitorFactory,
                                             FactoryReviewTreeTraverser traverserFactory) {
        GvDataAggregater aggregater = new GvDataAggregater(new FactoryDataAggregator(), converter);
        FactoryReviewViewAdapter factory = new FactoryReviewViewAdapter(launchableFactory,
                visitorFactory, traverserFactory, aggregater, provider, converter);
        setFactoryReviewViewAdapter(factory);
    }
}
