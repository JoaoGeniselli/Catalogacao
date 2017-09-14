package br.com.jgeniselli.catalogacaolem.common.form.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.model.FormFieldModelNumber;

/**
 * Created by jgeniselli on 27/08/17.
 */

public class NumberFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelNumber> implements TextWatcher {

    private WeakReference<EditText> editText;

    private WeakReference<FormFieldModelNumber> model;

    public NumberFormFieldViewHolder(View itemView) {
        super(itemView);

        editText = new WeakReference<>((EditText) itemView.findViewById(R.id.editText));

        editText.get().addTextChangedListener(this);
    }

    @Override
    public void bind(FormFieldModelNumber model) {

    }

    @Override
    public int preferedViewId() {
        return 0;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (model != null) {

        }
    }

    @Override
    public void afterTextChanged(Editable s) {}
}
