package br.com.jgeniselli.catalogacaolem.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.jgeniselli.catalogacaolem.R;

/**
 * Created by jgeniselli on 16/08/17.
 */

public class DefaultLineViewHolder extends RecyclerView.ViewHolder implements BindableView<String> {

    TextView textView;

    public DefaultLineViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        textView.setText("");
    }

    @Override
    public void bind(String content) {
        if (content == null) {
            return;
        }
        textView.setText(content);
    }
}
