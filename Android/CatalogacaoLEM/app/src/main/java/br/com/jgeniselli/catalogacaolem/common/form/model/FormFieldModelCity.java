package br.com.jgeniselli.catalogacaolem.common.form.model;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.jgeniselli.catalogacaolem.common.form.event.CityRequestEvent;
import br.com.jgeniselli.catalogacaolem.common.form.event.CityResponseEvent;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;

/**
 * Created by jgeniselli on 26/08/17.
 */

public class FormFieldModelCity extends FormFieldModel {

    private CityModel cityModel;

    public FormFieldModelCity(int id, int order, String title, String tag) {
        super(id, order, title, tag);
    }

    @Override
    public FormFieldModelType getType() {
        return FormFieldModelType.CITY;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        if (!isRequired()) {
            setErrored(false);
        } else if (cityModel == null) {
            setErrored(true);
        }
        this.cityModel = cityModel;
    }

    public void requestCity() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new CityRequestEvent());
    }

    @Override
    public boolean validate() {
        if (isRequired() && this.cityModel == null) {
            setErrored(true);
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onCityResponseEvent(CityResponseEvent responseEvent) {
        this.setCityModel(responseEvent.getCityModel());
        EventBus.getDefault().unregister(this);
    }
}
