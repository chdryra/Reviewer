/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter;

import android.content.Context;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Factories.FactoryDataAggregatorParams;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.ApplicationContexts.Implementation.PresenterContextBasic;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryVhBuildReviewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvConverter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataComparators;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePresenterContext extends PresenterContextBasic {
    public ReleasePresenterContext(Context context,
                                   ModelContext modelContext,
                                   ViewContext viewContext,
                                   SocialContext socialContext,
                                   NetworkContext networkContext,
                                   PersistenceContext persistenceContext,
                                   DeviceContext deviceContext,
                                   DataComparatorsPlugin comparatorsPlugin,
                                   DataAggregatorsPlugin aggregatorsPlugin,
                                   DataValidator validator) {
        super(modelContext, viewContext, socialContext, networkContext, persistenceContext);

        setLaunchablesFactory(viewContext);

        setFactoryGvData(new FactoryGvData());

        ConverterGv gvConverter = newConverterGv(persistenceContext.getReviewsSource());
        setAdaptersFactory(modelContext, persistenceContext.getReviewsSource(),
                gvConverter, aggregatorsPlugin.getAggregatorsApi());

        GvDataComparators.initialise(comparatorsPlugin.getComparatorsApi());

        setReviewBuilderAdapterFactory(context, modelContext, deviceContext, gvConverter,
                getGvDataFactory(), validator);
    }

    private ConverterGv newConverterGv(ReviewsSource source) {
        FactoryGvConverter converterFactory = new FactoryGvConverter(source);
        return converterFactory.newGvConverter();
    }


    private void setAdaptersFactory(ModelContext modelContext,
                                    ReviewsSource reviewsSource,
                                    ConverterGv gvConverter,
                                    DataAggregatorsApi aggregator) {
        FactoryDataAggregatorParams paramsFactory = new FactoryDataAggregatorParams();
        DataAggregatorParams params = paramsFactory.getDefaultParams();
        GvDataAggregator aggregater = new GvDataAggregator(aggregator, params, gvConverter,
                modelContext.getVisitorsFactory(), modelContext.getNodeTraversersFactory());
        FactoryReviewViewAdapter factory
                = new FactoryReviewViewAdapter(getFactoryReviewView(),
                modelContext.getReviewsFactory(),
                modelContext.getBindersFactory(),
                aggregater,
                reviewsSource,
                gvConverter);

        setFactoryReviewViewAdapter(factory);
    }

    private void setLaunchablesFactory(ViewContext viewContext) {
        FactoryReviewView launchableFactory
                = new FactoryReviewView(viewContext.getUiConfig(),
                new FactoryReviewViewParams());
        setFactoryReviewView(launchableFactory);
    }

    private void setReviewBuilderAdapterFactory(Context context,
                                                ModelContext modelContext,
                                                DeviceContext deviceContext,
                                                ConverterGv converter,
                                                FactoryGvData dataFactory,
                                                DataValidator validator) {
        FactoryReviewBuilder factoryReviewBuilder
                = new FactoryReviewBuilder(converter,
                validator,
                modelContext.getTagsManager(),
                modelContext.getReviewsFactory(),
                new FactoryDataBuilder(dataFactory));

        String dir = deviceContext.getImageStorageDirectory();
        FactoryFileIncrementor incrementorFactory
                = new FactoryFileIncrementor(deviceContext.getImageStoragePath(),
                dir, dir.toLowerCase());

        FactoryReviewBuilderAdapter<?> builderAdapterFactory
                = new FactoryReviewBuilderAdapter<>(factoryReviewBuilder,
                new FactoryDataBuildersGridUi(),
                new FactoryVhBuildReviewData(),
                validator,
                new FactoryDataBuilderAdapter(),
                incrementorFactory,
                new FactoryImageChooser(context));

        setFactoryBuilderAdapter(builderAdapterFactory);
    }
}
