package br.com.jgeniselli.catalogacaolem.common.form;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 27/08/17.
 */

public class CoordinatesFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelCoordinate> implements Button.OnClickListener {

    private WeakReference<FormFieldModelCoordinate> model;

    private WeakReference<TextView> textView;
    private WeakReference<EditText> latitudeEditText;
    private WeakReference<EditText> longitudeEditText;
    private WeakReference<Button> selectLocationButton;

    private NumberFormat numberFormat;

    public CoordinatesFormFieldViewHolder(View itemView) {
        super(itemView);

        numberFormat = NumberFormat.getInstance(Locale.getDefault());

        numberFormat.setMaximumFractionDigits(14);
        numberFormat.setMinimumFractionDigits(2);

        textView = new WeakReference<>((TextView) itemView.findViewById(R.id.textView));
        latitudeEditText = new WeakReference<>((EditText) itemView.findViewById(R.id.latitude_edit_text));
        longitudeEditText = new WeakReference<>((EditText) itemView.findViewById(R.id.longitude_edit_text));
        selectLocationButton = new WeakReference<>((Button) itemView.findViewById(R.id.select_button));

        selectLocationButton.get().setOnClickListener(this);

        latitudeEditText.get().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (model!= null) {
                    model.get().setLatitude(getDouble(s.toString()));
                }
            }
        });

        longitudeEditText.get().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (model!= null) {
                    model.get().setLongitude(getDouble(s.toString()));
                }
            }
        });
    }

    private double getDouble(String s) {
        double value;
        try {
            value = numberFormat.parse(s).doubleValue();
        } catch (ParseException e) {
            value = 0.0;
        }
        return value;
    }

    @Override
    public void bind(FormFieldModelCoordinate model) {
        this.model = new WeakReference<>(model);

        textView.get().setText(model.getTitle());

        latitudeEditText.get().setText(model.getLatitude() != 0.0 ? String.format("%02.14f", model.getLatitude()) : "");
        longitudeEditText.get().setText(model.getLongitude() != 0.0 ? String.format("%02.14f", model.getLongitude()) : "");

        if (model.isErrored()) {
            if (model.getLatitude() == 0.0) {
                latitudeEditText.get().setError(latitudeEditText
                        .get()
                        .getContext()
                        .getString(R.string.required_field_alert));
            }
            if (model.getLongitude() == 0.0) {
                longitudeEditText.get().setError(longitudeEditText
                        .get()
                        .getContext()
                        .getString(R.string.required_field_alert));
            }
        }
    }

    @Override
    public int preferedViewId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (model != null) {
            model.get().requestCoordinates();
        }
    }
}
