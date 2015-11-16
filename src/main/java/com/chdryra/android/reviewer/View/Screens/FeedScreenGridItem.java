package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreenGridItem extends GiLaunchReviewDataScreen
        implements DialogAlertFragment.DialogAlertListener{
    private static final int DIALOG_ALERT = RequestCodeGenerator.getCode("DeleteReview");

    //Overridden
    @Override
    public void onLongClickExpandable(GvData item, int position, View v, ReviewViewAdapter
            expanded) {
        if (expanded != null) {
            String alert = getActivity().getResources().getString(R.string.alert_delete_review);
            Bundle args = new Bundle();
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
            DialogShower.showAlert(alert, getActivity(), DIALOG_ALERT, DialogAlertFragment
                    .ALERT_TAG);
        }
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if(requestCode == DIALOG_ALERT) {
            GvData datum = GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.CURRENT, args);
            GvReviewOverviewList.GvReviewOverview review = (GvReviewOverviewList
                    .GvReviewOverview) datum;
            ApplicationInstance.getInstance(getActivity()).deleteFromAuthorsFeed(review.getId());
        }
    }
}
