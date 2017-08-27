package br.com.jgeniselli.catalogacaolem.common.form;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

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

    public CoordinatesFormFieldViewHolder(View itemView) {
        super(itemView);

        textView = new WeakReference<>((TextView) itemView.findViewById(R.id.textView));
        latitudeEditText = new WeakReference<>((EditText) itemView.findViewById(R.id.latitude_edit_text));
        longitudeEditText = new WeakReference<>((EditText) itemView.findViewById(R.id.longitude_edit_text));
        selectLocationButton = new WeakReference<>((Button) itemView.findViewById(R.id.select_button));

        selectLocationButton.get().setOnClickListener(this);
    }

    @Override
    public void bind(FormFieldModelCoordinate model) {
        this.model = new WeakReference<>(model);

        textView.get().setText(model.getTitle());
        latitudeEditText.get().setText(String.format("%02.6f", model.getLatitude()));
        longitudeEditText.get().setText(String.format("%02.6f", model.getLongitude()));
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
