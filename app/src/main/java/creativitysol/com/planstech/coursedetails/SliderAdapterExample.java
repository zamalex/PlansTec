package creativitysol.com.planstech.coursedetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import creativitysol.com.planstech.R;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    imageInterface imageInterface;

    public SliderAdapterExample(Context context,imageInterface imageInterface) {
        this.context = context;
        this.imageInterface = imageInterface;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);


        if (sliderItem!=null&&!sliderItem.isEmpty())
            Picasso.get().load(sliderItem).fit().centerCrop().into(viewHolder.img);


        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sliderItem!=null&&!sliderItem.isEmpty()){
                    imageInterface.onImgClicked(sliderItem);
                }
            }
        });


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemV;
        ImageView img;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.sl_img);

            itemV = itemView;
        }
    }

    public interface imageInterface{
        void onImgClicked(String img);
    }
}