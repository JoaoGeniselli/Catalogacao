package br.com.jgeniselli.catalogacaolem.common.form.model;

import android.content.Context;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.form.utils.ThumbnailUtil;
import br.com.jgeniselli.catalogacaolem.common.models.PhotoModel;

/**
 * Created by jgeniselli on 15/09/2017.
 */

public class UploadFilesAdapter extends RecyclerView.Adapter<UploadFilesAdapter.UploadFileViewHolder> {

    private Context context;

    private List<PhotoModel> filesToUpload = new ArrayList<>();

    public UploadFilesAdapter(Context context) {
        this.context = context;
    }

    public void addFile(PhotoModel fileToUpload) {
        this.filesToUpload.add(fileToUpload);
        notifyDataSetChanged();
    }

    public List<PhotoModel> getFilesToUpload() {
        return filesToUpload;
    }

    @Override
    public UploadFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.line_view_image_item, parent, false);
        return new UploadFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadFileViewHolder holder, int position) {
        holder.bind(filesToUpload.get(position));
    }

    @Override
    public int getItemCount() {
        return filesToUpload != null ? filesToUpload.size() : 0;
    }

    class UploadFileViewHolder extends RecyclerView.ViewHolder {

        private TextView textNameFile;
        private ImageButton buttonRemoveAttach;
        private ImageView imageThumbnail;

        public UploadFileViewHolder(View itemView) {
            super(itemView);
//            this.textNameFile = (TextView) itemView.findViewById(R.id.textNameFile);
//            this.buttonRemoveAttach = (ImageButton) itemView.findViewById(R.id.buttonRemoveAttach);
//            this.imageThumbnail = (ImageView) itemView.findViewById(R.id.imageThumbnail);
        }

        private void setThumbailImageView(Uri file) {
            RoundedBitmapDrawable thumbnail = ThumbnailUtil.getThumbailFromUri(context, file);
            if (thumbnail != null)
                imageThumbnail.setImageDrawable(thumbnail);
        }

        public void bind(final PhotoModel fileUpload) {

//            ChooseFileManager chooseFileManager = new ChooseFileManager();
//            textNameFile.setText(chooseFileManager.getNameFile(context, fileUpload.getUri()));
//
//            setThumbailImageView(fileUpload.getUri());
//            buttonRemoveAttach.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (filesToUpload.size() > getLayoutPosition()) {
//                        filesToUpload.remove(getLayoutPosition());
//                        notifyDataSetChanged();
//                    }
//                }
//            });
        }
    }
}
