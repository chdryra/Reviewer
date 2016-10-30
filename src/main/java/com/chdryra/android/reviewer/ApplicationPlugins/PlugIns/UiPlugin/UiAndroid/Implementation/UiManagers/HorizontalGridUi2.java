/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class HorizontalGridUi2<T extends GvData> extends HorizontalGridUi<T>{
    public HorizontalGridUi2(Context context,
                             RecyclerView view,
                             ViewHolderFactory<?> vhFactory,
                             ValueGetter<GvDataList<T>> getter,
                             int span,
                             CellDimensionsCalculator.Dimensions dims,
                             final ApplicationInstance app,
                             final Review review) {
        super(context, view, vhFactory, getter, span, dims);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiSuite ui = app.getUi();
                TagsManager tagsManager = app.getRepository().getTagsManager();
                GvDataType<T> dataType = getValue().getGvDataType();
                ReviewView<?> reviewView = ui.newDataView(review, tagsManager, dataType);
                ui.getLauncher().launch(reviewView, new UiLauncherArgs(RequestCodeGenerator.getCode(HorizontalGridUi2.class, dataType.getDataName())));
            }
        });
    }
}
