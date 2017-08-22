package br.com.jgeniselli.catalogacaolem.common.form;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 22/08/17.
 */

public class TextFormFieldViewHolder extends FormFieldViewHolder<FormFieldModelText> {

    private EditText editText;

    public TextFormFieldViewHolder(View itemView) {
        super(itemView);
        editText = (EditText) itemView.findViewById(R.id.editText);
    }

    @Override
    public void bind(FormFieldModelText model) {
        editText.setHint(model.getTitle());
        editText.setText(model.getContent());
    }

    @Override
    public int preferedViewId() {
        return R.layout.line_view_form_text;
    }
}
