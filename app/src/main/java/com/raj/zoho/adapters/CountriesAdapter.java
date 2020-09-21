package com.raj.zoho.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.raj.zoho.R;
import com.raj.zoho.network.model.Countries;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder> {
    private final CountriesAdapter.ListItemOnClickListener mOnClickListener;
    private List<Countries> list;

    public CountriesAdapter(List<Countries> list, CountriesAdapter.ListItemOnClickListener mOnClickListener) {
        this.list = list;
        if(list!=null){
            System.out.println("the list in adpet is "+list.size());

        }
        this.mOnClickListener = mOnClickListener;
    }

    /***
     * this method used to add list into existing list
     * @param mList contains latest list which is fetched based on page wise;
     */
//    public void addAllItem(final List<Countries> mList) {
//        System.out.println("the list in adapter is "+mList.size());
//
//        if (list == null && mList != null && mList.size() > 0) {
//            list = new ArrayList<>();
//            list.addAll(mList);
//            this.notifyDataSetChanged();
//        }
//    }

    /***
     * clear all the items of list;
     */
    public void clearAllItems() {
        if (list != null && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public CountriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countries_item_list_view, parent, false);

        return new CountriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CountriesAdapter.MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    /***
     * listener for viewHolder's items
     */
    public interface ListItemOnClickListener {
        void onListItemClick(Countries selectedObject, View view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView flagImage;
        TextView countryName;

        MyViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            countryName = view.findViewById(R.id.countryName);

            flagImage = view.findViewById(R.id.flagImage);
//            flagImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            itemView.setOnClickListener(this);
        }

        private void bindView(int position) {
            System.out.println("the list flag value is"+list.get(position).getFlag());
            Activity activity = (Activity) itemView.getContext();
            SvgLoader.pluck()
                    .with(activity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(list.get(position).getFlag(), flagImage);
            countryName.setText(list.get(position).getName());
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(list.get(getAdapterPosition()), v.findViewById(R.id.flagImage));
        }



    }

}
