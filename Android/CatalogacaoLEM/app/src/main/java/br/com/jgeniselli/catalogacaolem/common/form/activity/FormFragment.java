package br.com.jgeniselli.catalogacaolem.common.form.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageRemovalRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.ImageResponseEvent;
import br.com.jgeniselli.catalogacaolem.common.form.model.ChooseFileManager;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormLineAdapter;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormModel;
import br.com.jgeniselli.catalogacaolem.common.form.model.SaveFormStrategy;
import br.com.jgeniselli.catalogacaolem.common.form.event.CityRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.CityResponseEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.CoordinatesRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.CoordinatesResponseEvent;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;
import io.realm.Realm;

/**
 * Created by jgeniselli on 08/09/17.
 */

@EFragment(R.layout.fragment_form)
public class FormFragment extends Fragment {

    public static final int CITY_SELECTION_RESULT_ID = 5;
    public static final int COORDINATES_SELECTION_RESULT_ID = 10;
    static final int REQUEST_IMAGE_CAPTURE = 15;

    @ViewById
    RecyclerView formRecycler;

    @ViewById
    Toolbar toolbar;

    FormModel form;

    SaveFormStrategy saveStrategy;

    Realm realm;

    @Bean
    ChooseFileManager chooseFileManager;

    @FragmentArg
    boolean hidesSave;

    public void setup(@FragmentArg FormModel form, @FragmentArg SaveFormStrategy saveStrategy) {
        this.form = form;
        this.saveStrategy = saveStrategy;
        init();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (!hidesSave) {
            inflater.inflate(R.menu.save, menu);
        }
    }

    @AfterViews
    public void afterViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        formRecycler.setLayoutManager(layoutManager);

        init();
    }

    public void init() {
        if (form == null || formRecycler == null) {
            return;
        }
        ((AppCompatActivity)getContext()).setTitle(form.getTitle());
        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);

        setHasOptionsMenu(!hidesSave);
        realm = Realm.getDefaultInstance();

        FormLineAdapter adapter = new FormLineAdapter(getContext(), form);
        formRecycler.setAdapter(adapter);

        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save: {
                finishForm();
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }

    public void finishForm() {
        if (form.validate()) {
            if (saveStrategy != null) {
                saveStrategy.save(form, realm, new SaveFormStrategy.SaveStrategyCallback() {
                    @Override
                    public void onSaveSuccess() {
                        ((Activity)getContext()).finish();
                    }
                    @Override
                    public void onSaveError(int messageId) {
                        showAlert(messageId);
                    }
                });
            }
        } else {
            formRecycler.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (saveStrategy != null) {
            saveStrategy.saveBeforeDestroy(form, realm);
        }
        if (realm != null) {
            realm.close();
            realm = null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == CITY_SELECTION_RESULT_ID) {

        }
    }

    @OnActivityResult(CITY_SELECTION_RESULT_ID)
    public void onCitySelectionResult(int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            Long cityId = data.getLongExtra("addedCityId", 0);

            CityModel city = null;
            if (cityId != 0) {
                city = realm.where(CityModel.class).equalTo("id", cityId).findFirst();
            }
            EventBus.getDefault().post(new CityResponseEvent(city));
        }
    }

    @OnActivityResult(COORDINATES_SELECTION_RESULT_ID)
    public void onCoordinatesSelectionResult (int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            EventBus.getDefault().post(new CoordinatesResponseEvent(latitude, longitude));
        }
    }

    @OnActivityResult(REQUEST_IMAGE_CAPTURE)
    public void onRequestImageCaptureResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri fileSelected = chooseFileManager.getSelectedFile(data);

            if (chooseFileManager.isSupportedFile(getContext(), fileSelected)) {

                PhotoModel photo = new PhotoModel();
                photo.setFileURI(fileSelected);

                EventBus.getDefault().post(new ImageResponseEvent(photo));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityRequestEvent(CityRequestEvent event) {
        CitySelectionActivity_
                .intent(this)
                .startForResult(CITY_SELECTION_RESULT_ID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityResponseEvent(CityResponseEvent event) {
        formRecycler.getAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCoordinatesRequestEvent(CoordinatesRequestEvent event) {
        CoordinatesSelectionActivity_
                .intent(this)
                .startForResult(COORDINATES_SELECTION_RESULT_ID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCoordinatesResponseEvent(CoordinatesResponseEvent event) {
        formRecycler.getAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageRequestEvent(ImageRequestEvent event) {
        requestStoragePermissionToAndroidM();

        if (haveStoragePermission()) {
            startActivityForResult(
                    chooseFileManager.buildIntentFileOrTakePicture(getContext()),
                    REQUEST_IMAGE_CAPTURE);
        }

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        PackageManager packageManager = getContext().getPackageManager();
//        if (takePictureIntent.resolveActivity(packageManager) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageResponseEvent(ImageResponseEvent event) {
        formRecycler.getAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageRemovalRequestEvent(ImageRemovalRequestEvent event) {
        if (event.getFormModel() != null) {
            FormLineAdapter adapter = (FormLineAdapter) formRecycler.getAdapter();
            int index = this.form.getFields().indexOf(event.getFormModel());

            if (index >= 0 && index < this.form.getFields().size()) {
                adapter.notifyItemChanged(index);
            }
        }
    }

    @UiThread
    public void showAlert(int messageId) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(R.string.warning)
                .setMessage(messageId)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)getContext()).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void setMultipleArgments(@FragmentArg FormModel form,
                                    @FragmentArg SaveFormStrategy saveStrategy) {
        this.form = form;
        this.saveStrategy = saveStrategy;
    }

    private void requestStoragePermissionToAndroidM(){
        if (!haveStoragePermission()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private boolean haveStoragePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==  PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
