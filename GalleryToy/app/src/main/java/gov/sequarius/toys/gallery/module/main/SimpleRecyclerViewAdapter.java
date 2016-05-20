package gov.sequarius.toys.gallery.module.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.net.URI;
import java.util.List;

import gov.sequarius.toys.gallery.R;

/**
 * Created by Sequarius on 2016/5/10.
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleViewHolder> {
    private List<File> mDataSet;
    private Context mContext;

    public SimpleRecyclerViewAdapter(List<File> mDataSet, Context mContext) {
        this.mDataSet = mDataSet;
        this.mContext = mContext;

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_images, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.setName(mDataSet.get(position).getName());
        holder.setImage(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private SimpleDraweeView imImage;

        public void setName(String name) {
            tvName.setText(name);
        }

        public void setImage(File imageFile) {

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(imageFile))
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setResizeOptions(new ResizeOptions(400, 300))
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(imImage.getController())
                    .build();
            imImage.setController(controller);
        }

        SimpleViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            imImage = (SimpleDraweeView) view.findViewById(R.id.imImage);
        }
    }
}
