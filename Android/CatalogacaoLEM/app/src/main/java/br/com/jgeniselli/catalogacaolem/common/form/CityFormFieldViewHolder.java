package br.com.jgeniselli.catalogacaolem.common.form;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.location.CityModel;

/**
 * Created by jgeniselli on 27/08/17.
 */

public class CityFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelCity> implements View.OnClickListener {

    private WeakReference<EditText> editText;
    private WeakReference<TextInputLayout> inputLayout;
    private WeakReference<Button> selectButton;

    private WeakReference<FormFieldModelCity> model;

    public CityFormFieldViewHolder(View itemView) {
        super(itemView);

        editText = new WeakReference<>((EditText) itemView.findViewById(R.id.editText));
        inputLayout = new WeakReference<>((TextInputLayout) itemView.findViewById(R.id.text_input_layout));
        selectButton = new WeakReference<>((Button) itemView.findViewById(R.id.select_button));
        selectButton.get().setText(R.string.select_city);

        selectButton.get().setOnClickListener(this);
    }

    @Override
    public void bind(FormFieldModelCity model) {
        this.model = new WeakReference<>(model);
        inputLayout.get().setHint(model.getTitle());
        if (model.getCityModel() != null) {
            CityModel city = model.getCityModel();
            editText.get().setText(city.getState().getInitials() + " - " + city.getName());
        } else {
            editText.get().setText("");
        }

        if (model.isErrored()) {
            editText.get().setError(editText.get().getContext().getString(R.string.required_city_alert));
        }
    }

    @Override
    public int preferedViewId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (this.model != null) {
            this.model.get().requestCity();
        }
    }
}
