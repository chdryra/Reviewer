package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemDeleteRequester extends GridItemLauncher<GvReviewOverview>
        implements DialogAlertFragment.DialogAlertListener{
    private static final int DIALOG_ALERT = RequestCodeGenerator.getCode("DeleteReview");
    private DeleteRequestListener mDeleteRequestListener;
    private GvReviewOverview mToDelete;

    public interface DeleteRequestListener {
        void onDeleteRequested(ReviewId reviewId);
    }

    public GridItemDeleteRequester(FactoryReviewViewLaunchable launchableFactory,
                                   LaunchableUiLauncher launcher,
                                   DeleteRequestListener deleteRequestListener) {
        super(launchableFactory, launcher);
        mDeleteRequestListener = deleteRequestListener;
    }

    //Overridden
    @Override
    public void onLongClickExpandable(GvReviewOverview item,
                                      int position, View v, ReviewViewAdapter<?> expanded) {
        if (expanded != null) {
            String alert = getActivity().getResources().getString(R.string.alert_delete_review);
            mToDelete = item;
            DialogShower.showAlert(alert, getActivity(), DIALOG_ALERT, DialogAlertFragment
                    .ALERT_TAG, new Bundle());
        }
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if(requestCode == DIALOG_ALERT) {
            mDeleteRequestListener.onDeleteRequested(mToDelete.getReviewId());
        }
    }
}
