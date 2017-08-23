package br.com.jgeniselli.catalogacaolem.common.form;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class TextFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelText> {

    private EditText editText;
    private TextInputLayout inputLayout;

    public TextFormFieldViewHolder(View itemView) {
        super(itemView);
        editText = (EditText) itemView.findViewById(R.id.editText);
        inputLayout = (TextInputLayout) itemView.findViewById(R.id.text_input_layout);
    }

    @Override
    public void bind(FormFieldModelText model) {
        inputLayout.setHint(model.getTitle());
        editText.setText(model.getContent());
    }

    @Override
    public int preferedViewId() {
        return R.layout.line_view_form_text;
    }
}
