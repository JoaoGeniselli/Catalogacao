package br.com.jgeniselli.catalogacaolem.common.form;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelCoordinate extends FormFieldModel {

    private double latitude;
    private double longitude;

    public FormFieldModelCoordinate(int id, int order, String title) {
        super(id, order, title);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.COORDINATE;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void requestCoordinates() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new CoordinatesRequestEvent());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onCoordinatesResponseEvent(CoordinatesResponseEvent event) {
        setLatitude(event.getLatitude());
        setLongitude(event.getLongitude());
    }
}
