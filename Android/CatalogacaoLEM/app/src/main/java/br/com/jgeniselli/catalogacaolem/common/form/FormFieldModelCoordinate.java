package br.com.jgeniselli.catalogacaolem.common.form;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.jgeniselli.catalogacaolem.common.models.Coordinate;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class FormFieldModelCoordinate extends FormFieldModel {

    private double latitude;
    private double longitude;

    public FormFieldModelCoordinate(int id, int order, String title, String tag) {
        super(id, order, title, tag);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.COORDINATE;
    }

    public void setupFromCoordinate(Coordinate coordinate) {
        if (coordinate != null) {
            setLatitude(coordinate.getLatitude());
            setLongitude(coordinate.getLongitude());
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (!isRequired()) {
            setErrored(false);
        } else if (validCoordinate(latitude) &&  validCoordinate(longitude)) {
            setErrored(false);
        }
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        if (!isRequired()) {
            setErrored(false);
        } else if (validCoordinate(latitude) &&  validCoordinate(longitude)) {
            setErrored(false);
        }
        this.longitude = longitude;
    }

    public void requestCoordinates() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new CoordinatesRequestEvent());
    }

    @Override
    public boolean validate() {
        if (isRequired() && (validCoordinate(latitude) &&  validCoordinate(longitude))) {
            return true;
        }
        setErrored(true);
        return false;
    }

    private boolean validCoordinate(double coordinate) {
        if (coordinate < 0) {
            return (coordinate >= -90.0 && coordinate != 0.0);
        } else {
            return (coordinate <= 90.0 && coordinate != 0.0);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onCoordinatesResponseEvent(CoordinatesResponseEvent event) {
        setLatitude(event.getLatitude());
        setLongitude(event.getLongitude());
        EventBus.getDefault().unregister(this);
    }
}
