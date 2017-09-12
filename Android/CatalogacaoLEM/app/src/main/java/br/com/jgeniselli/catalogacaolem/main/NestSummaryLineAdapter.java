package br.com.jgeniselli.catalogacaolem.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;
import io.realm.RealmResults;

/**
 * Created by joaog on 12/08/2017.
 */

public class NestSummaryLineAdapter extends RecyclerView.Adapter<NestSummaryViewHolder> {

    private final RealmResults<AntNest> nests;
    private final View.OnClickListener itemListener;

    public NestSummaryLineAdapter(final RealmResults<AntNest> nests) {

        this.nests = nests;
        itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RecyclerView recycler = (RecyclerView)v.getParent();
//                int itemPosition = recycler.getChildLayoutPosition(v);
//                AntNest item = nests.get(itemPosition);
//
//                Intent toDetails = new Intent(v.getContext(), DetailActivity_.class);
//                toDetails.putExtra("nest", (Serializable) item);
//                v.getContext().startActivity(toDetails);
            }
        };
    }

    @Override
    public NestSummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nest_summary_line_view, parent, false);

        view.setOnClickListener(itemListener);
        return new NestSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NestSummaryViewHolder holder, int position) {
        AntNest currentNest = nests.get(position);
        holder.bind(currentNest);
    }

    @Override
    public int getItemCount() {
        return nests.size();
    }

}

