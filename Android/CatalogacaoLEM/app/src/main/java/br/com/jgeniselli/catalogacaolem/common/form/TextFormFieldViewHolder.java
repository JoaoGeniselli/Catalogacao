package br.com.jgeniselli.catalogacaolem.common.form;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class TextFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelText> {

    private EditText editText;
    private TextInputLayout inputLayout;

    private WeakReference<FormFieldModelText> model;

    public TextFormFieldViewHolder(View itemView) {
        super(itemView);
        editText = (EditText) itemView.findViewById(R.id.editText);
        inputLayout = (TextInputLayout) itemView.findViewById(R.id.text_input_layout);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                if (model != null) {
                    model.get().setContent(sequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void bind(final FormFieldModelText model) {
        this.model = new WeakReference<>(model);
        inputLayout.setHint(model.getTitle());
        editText.setText(model.getContent());

        if (model.isErrored()) {
            editText.setError(editText.getContext().getString(R.string.required_field_alert));
        }
    }

    @Override
    public int preferedViewId() {
        return R.layout.line_view_form_text;
    }
}
