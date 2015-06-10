/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.mygenerallibrary.DialogDeleteConfirm;
import com.chdryra.android.reviewer.Controller.Administrator;
import com.chdryra.android.reviewer.Controller.ReviewBuilder;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreen {
    public static ReviewView newScreen(Context context, GvDataType dataType) {
        ReviewBuilder.DataBuilder builder = Administrator.get(context).getReviewBuilder()
                .getDataBuilder(dataType);

        ReviewView view = new ReviewView.Editor(builder);

        setActions(view, dataType, context.getResources().getString(R.string.button_add) + " " +
                dataType.getDatumName());

        if (dataType == GvImageList.TYPE) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            view.getParams().setCellHeight(half).setCellWidth(half);
        }

        return view;
    }

    private static void setActions(ReviewView view, GvDataType dataType,
            String buttonTitle) {
        view.setAction(new RatingBar());
        view.setAction(newBannerButtonAction(dataType, buttonTitle));
        view.setAction(newGridItemAction(dataType));
        view.setAction(newMenuAction(dataType));
    }

    private static ReviewViewAction.MenuAction newMenuAction(GvDataType dataType) {
        if (dataType == GvCommentList.TYPE) {
            return new EditScreenComments.Menu();
        } else if (dataType == GvChildList.TYPE) {
            return new EditScreenChildren.Menu();
        } else {
            return new Menu(dataType.getDataName());
        }
    }

    private static ReviewViewAction.GridItemAction newGridItemAction(GvDataType
            dataType) {
        if (dataType == GvCommentList.TYPE) {
            return new EditScreenComments.GridItem();
        } else if (dataType == GvImageList.TYPE) {
            return new EditScreenImages.GridItem();
        } else if (dataType == GvLocationList.TYPE) {
            return new EditScreenLocations.GridItem();
        } else if (dataType == GvFactList.TYPE) {
            return new EditScreenFacts.GridItem();
        } else {
            return new GridItem(ConfigGvDataUi.getConfig(dataType).getEditorConfig());
        }
    }

    private static ReviewViewAction.BannerButtonAction newBannerButtonAction(
            GvDataType dataType, String title) {
        if (dataType == GvImageList.TYPE) {
            return new EditScreenImages.BannerButton(title);
        } else if (dataType == GvLocationList.TYPE) {
            return new EditScreenLocations.BannerButton(title);
        } else if (dataType == GvFactList.TYPE) {
            return new EditScreenFacts.BannerButton(title);
        } else if (dataType == GvCommentList.TYPE) {
            return new EditScreenComments.BannerButton(title);
        } else {
            return new BannerButton(ConfigGvDataUi.getConfig(dataType).getAdderConfig()
                    , title);
        }
    }

    @SuppressWarnings("EmptyMethod")
    public static class BannerButton extends ReviewViewAction.BannerButtonAction {
        private static final String TAG = "ActionBannerButtonAddListener";

        private final ConfigGvDataUi.LaunchableConfig mConfig;
        private       Fragment                        mListener;

        protected BannerButton(ConfigGvDataUi.LaunchableConfig config, String title) {
            super(title);
            mConfig = config;
            setListener(new AddListener(config.getGVType()) {
            });
        }

        @Override
        public void onClick(View v) {
            LauncherUi.launch(mConfig.getLaunchable(), getListener(), getRequestCode(),
                    mConfig.getTag(), new Bundle());
        }

        protected Fragment getListener() {
            return mListener;
        }

        protected void setListener(Fragment listener) {
            mListener = listener;
            super.registerActionListener(listener, TAG);
        }

        //TODO make type safe
        protected boolean addData(GvData data) {
            boolean added = getDataBuilder().add(data);
            getReviewView().updateUi();
            return added;
        }

        protected int getRequestCode() {
            return mConfig.getRequestCode();
        }

        protected void showAlertDialog(String alert, int requestCode) {
            DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, new Bundle());
            DialogShower.show(dialog, getListener(), requestCode, DialogAlertFragment.ALERT_TAG);
        }

        protected void onDialogAlertNegative(int requestCode) {

        }

        protected void onDialogAlertPositive(int requestCode) {

        }

        private ReviewBuilder.DataBuilder getDataBuilder() {
            return ((ReviewBuilder.DataBuilder) getAdapter());
        }

        // /Dialogs expected to communicate directly with target fragments so using "invisible"
        // fragment as listener.
        //Restrictions on how fragments are constructed mean I have to use an abstract class...
        protected abstract class AddListener extends Fragment
                implements DialogAddGvData.GvDataAddListener,
                DialogAlertFragment.DialogAlertListener {

            private GvDataList<GvData> mAdded;

            private AddListener(GvDataType dataType) {
                mAdded = FactoryGvData.newList(dataType);
            }

            //TODO make type safe
            @Override
            public boolean onGvDataAdd(GvData data) {
                boolean success = addData(data);
                if (success) mAdded.add(data);
                return success;
            }

            @Override
            public void onGvDataCancel() {
                for (GvData added : mAdded) {
                    getDataBuilder().delete(added);
                }
            }

            @Override
            public void onGvDataDone() {
                mAdded = FactoryGvData.newList(mAdded.getGvDataType());
            }

            @Override
            public void onAlertNegative(int requestCode, Bundle args) {
                onDialogAlertNegative(requestCode);
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == getRequestCode() && data != null
                        && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                    onGvDataAdd(GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data));
                }
            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                onDialogAlertPositive(requestCode);
            }


        }
    }

    @SuppressWarnings("EmptyMethod")
    public static class GridItem extends ReviewViewAction.GridItemAction {
        private static final String TAG = "GridItemEditListener";
        private final ConfigGvDataUi.LaunchableConfig mConfig;
        private       Fragment                        mListener;
        private       ReviewView.Editor               mEditor;

        protected GridItem(ConfigGvDataUi.LaunchableConfig config) {
            mConfig = config;
            setListener(new EditListener() {
            });
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewView.Editor.cast(getReviewView());
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            Bundle args = new Bundle();
            GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);

            LauncherUi.launch(mConfig.getLaunchable(), mListener, getLaunchableRequestCode(),
                    mConfig.getTag(), args);
        }

        protected Fragment getListener() {
            return mListener;
        }

        protected void setListener(Fragment listener) {
            mListener = listener;
            super.registerActionListener(listener, TAG);
        }

        protected int getLaunchableRequestCode() {
            return mConfig.getRequestCode();
        }

        //TODO make type safe
        protected void editData(GvData oldDatum, GvData newDatum) {
            getDataBuilder().replace(oldDatum, newDatum);
            getReviewView().updateUi();
        }

        //TODO make type safe
        protected void deleteData(GvData datum) {
            getDataBuilder().delete(datum);
            getReviewView().updateUi();
        }

        protected void showAlertDialog(String alert, int requestCode, GvData item) {
            Bundle args = new Bundle();
            if (item != null) {
                GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, item, args);
            }
            DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, args);
            DialogShower.show(dialog, getListener(), requestCode, DialogAlertFragment.ALERT_TAG);
        }

        protected void onDialogAlertNegative(int requestCode, Bundle args) {

        }

        protected void onDialogAlertPositive(int requestCode, Bundle args) {

        }

        protected ReviewView.Editor getEditor() {
            return mEditor;
        }

        private ReviewBuilder.DataBuilder getDataBuilder() {
            return ((ReviewBuilder.DataBuilder) getAdapter());
        }

        protected abstract class EditListener extends Fragment
                implements DialogEditGvData.GvDataEditListener,
                DialogAlertFragment.DialogAlertListener {

            @Override
            public void onGvDataDelete(GvData data) {
                deleteData(data);
            }

            @Override
            public void onGvDataEdit(GvData oldDatum, GvData newDatum) {
                editData(oldDatum, newDatum);
            }

            @Override
            public void onAlertNegative(int requestCode, Bundle args) {
                onDialogAlertNegative(requestCode, args);
            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                onDialogAlertPositive(requestCode, args);
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == getLaunchableRequestCode() && data != null) {
                    GvData oldDatum = GvDataPacker.unpackItem(GvDataPacker
                            .CurrentNewDatum.CURRENT, data);
                    if (ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
                        onGvDataEdit(oldDatum, GvDataPacker.unpackItem(GvDataPacker
                                .CurrentNewDatum.NEW, data));
                    } else if (ActivityResultCode.get(resultCode) == ActivityResultCode.DELETE) {
                        onGvDataDelete(oldDatum);
                    }
                }
            }
        }
    }

    public static class Menu extends ReviewViewAction.MenuAction {
        public static final  int                MENU_DELETE_ID = R.id.menu_item_delete;
        public static final  int                MENU_DONE_ID   = R.id.menu_item_done;
        public static final  ActivityResultCode RESULT_DELETE  = ActivityResultCode.DELETE;
        public static final  ActivityResultCode RESULT_DONE    = ActivityResultCode.DONE;
        private static final int                MENU           = R.menu.menu_delete_done;
        private static final int                DELETE_CONFIRM = 314;
        private static final String             TAG            = "ActionMenuDeleteDoneGridListener";

        private final MenuActionItem mDeleteAction;
        private final MenuActionItem mDoneAction;

        private final String  mDeleteWhat;
        private final boolean mDismissOnDelete;
        private final boolean mDismissOnDone;

        private final Fragment          mListener;
        private       boolean           mRatingIsAverage;
        private       ReviewView.Editor mEditor;

        public Menu(String title) {
            this(title, title);
        }

        public Menu(String title, String deleteWhat) {
            this(title, deleteWhat, false);
        }

        public Menu(String title, String deleteWhat, boolean dismissOnDelete) {
            this(title, deleteWhat, dismissOnDelete, true, MENU);
        }

        public Menu(String title, String deleteWhat, boolean dismissOnDelete,
                boolean dismissOnDone, int menuId) {
            super(menuId, title, true);
            mDeleteWhat = deleteWhat;
            mDismissOnDelete = dismissOnDelete;
            mDismissOnDone = dismissOnDone;

            mDeleteAction = new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    if (hasDataToDelete()) showDeleteConfirmDialog();
                }
            };

            mDoneAction = new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    doDoneSelected();
                    sendResult(RESULT_DONE);
                }
            };

            addMenuItems();
            mListener = new DeleteConfirmListener() {
            };
            registerActionListener(mListener, TAG);
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewView.Editor.cast(getReviewView());
            mRatingIsAverage = getBuilder().getParentBuilder().isRatingAverage();
        }

        @Override
        protected void addMenuItems() {
            addDefaultDeleteActionItem(MENU_DELETE_ID);
            addDefaultDoneActionItem(MENU_DONE_ID);
        }

        @Override
        protected void doUpSelected() {
            ReviewBuilder builder = getBuilder().getParentBuilder();
            builder.setRatingIsAverage(mRatingIsAverage);
            builder.resetDataBuilder(getGridData().getGvDataType());
            super.doUpSelected();
        }

        protected void addDefaultDeleteActionItem(int deleteId) {
            addMenuActionItem(getDeleteAction(), deleteId, false);
        }

        protected void addDefaultDoneActionItem(int doneId) {
            addMenuActionItem(getDoneAction(), doneId, mDismissOnDone);
        }

        protected MenuActionItem getDeleteAction() {
            return mDeleteAction;
        }

        protected MenuActionItem getDoneAction() {
            return mDoneAction;
        }

        protected void doDeleteSelected() {
            if (hasDataToDelete()) {
                getBuilder().deleteAll();
                getReviewView().updateUi();
                if (mDismissOnDelete) {
                    sendResult(RESULT_DELETE);
                    getActivity().finish();
                }
            }
        }

        protected ReviewBuilder.DataBuilder getBuilder() {
            return (ReviewBuilder.DataBuilder) getAdapter();
        }

        protected ReviewView.Editor getEditor() {
            return mEditor;
        }

        private void doDoneSelected() {
            ReviewView view = getReviewView();
            ReviewBuilder.DataBuilder builder = getBuilder();

            builder.setData();
            builder.setSubject(view.getSubject());
            builder.getParentBuilder().setRatingIsAverage(mEditor.isRatingAverage());
            builder.setRating(view.getRating());
        }

        private void showDeleteConfirmDialog() {
            String deleteWhat = "all " + mDeleteWhat;
            DialogDeleteConfirm.showDialog(deleteWhat, mListener, DELETE_CONFIRM,
                    getActivity().getFragmentManager());
        }

        private boolean hasDataToDelete() {
            return getGridData() != null && getGridData().size() > 0;
        }

        private abstract class DeleteConfirmListener extends Fragment implements DialogAlertFragment
                .DialogAlertListener {
            @Override
            public void onAlertNegative(int requestCode, Bundle args) {

            }

            @Override
            public void onAlertPositive(int requestCode, Bundle args) {
                if (requestCode == DELETE_CONFIRM) doDeleteSelected();
            }
        }

    }

    public static class RatingBar extends ReviewViewAction.RatingBarAction {
        private ReviewView.Editor mEditor;

        protected RatingBar() {

        }

        @Override
        public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                boolean fromUser) {
            mEditor.setRating(rating);
            if (fromUser) mEditor.setRatingAverage(false);
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewView.Editor.cast(getReviewView());
        }


    }
}
