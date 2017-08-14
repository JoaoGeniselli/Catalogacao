package br.com.jgeniselli.catalogacaolem.citiesSync;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import br.com.jgeniselli.catalogacaolem.R;

@EActivity
public class CitySelectionActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    @ViewById
    EditText stateField;

    @ViewById
    EditText cityField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        stateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    @UiThread
    public void showPicker() {

        final Dialog d = new Dialog(this);
        d.setTitle(R.string.select_it);
        d.setContentView(R.layout.dialog_picker);
        Button cancelButton = (Button) d.findViewById(R.id.button1);
        Button okButton = (Button) d.findViewById(R.id.button2);
        final NumberPicker picker = (NumberPicker) d.findViewById(R.id.numberPicker1);
        picker.setMaxValue(100);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(false);
        picker.setOnValueChangedListener(this);
        picker.setDisplayedValues(new String[] {"São Paulo", "Rio de Janeiro" });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }
}
