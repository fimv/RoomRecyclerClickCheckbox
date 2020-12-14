package com.example.roomrecyclerclickcheckbox;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomrecyclerclickcheckbox.model.DataItem;
import java.util.ArrayList;
import java.util.List;


public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {

    private List<DataItem> mDataItemList;
    public   List<Long> numberCheckedItemList;

    private final ListFragment.OnListFragmentInteractionListener mListener;

    boolean[] checked;


    public ListRecyclerViewAdapter(ListFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    public void setListData(List<DataItem> dataItemList) {
        //setup new list
        if (numberCheckedItemList == null) {
            numberCheckedItemList = new ArrayList<>();
        }

        if (mDataItemList == null) {
            mDataItemList = new ArrayList<>();
        }

        mDataItemList.clear();
        mDataItemList.addAll(dataItemList);

        checked = new boolean[mDataItemList.size()];

        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.mItem = mDataItemList.get(position);
        holder.mNumberView.setText(String.valueOf(position + 1));
        holder.mNameView.setText(mDataItemList.get(position).getName());
        holder.mSectionView.setText(mDataItemList.get(position).getSection());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListClickItem(holder.mItem);
                }
            }
        });

        final long itemId = holder.mItem.getId();
        //OnClick delete the item from list
        holder.mImageDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentDeleteItemById(itemId);
                //   mDeleteListener.onListFragmentDeleteItemById(mCheckedItemList);

            }
        });

        holder.mChekboxCheckView.setChecked(checked[position]);
        holder.mChekboxCheckView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (numberCheckedItemList == null) {
                    numberCheckedItemList = new ArrayList<>();
                }

                if (((CheckBox) v).isChecked()) {
                    numberCheckedItemList.add(itemId);
                }
                checked[position] = !checked[position];
            }
        });
    }

    public boolean[] getChecked(){
        return checked;
    }

    @Override
    public int getItemCount() {
        if (mDataItemList != null) {
            return mDataItemList.size();
        } else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mNumberView;
        private final TextView mNameView;
        private final TextView mSectionView;
        private final ImageView mImageDeleteView;
        private final CheckBox mChekboxCheckView;

        private DataItem mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mNumberView = (TextView) view.findViewById(R.id.item_number);
            mNameView = (TextView) view.findViewById(R.id.item_name);
            mSectionView = (TextView) view.findViewById(R.id.item_section);
            mImageDeleteView = (ImageView) view.findViewById(R.id.image_delete);
            mChekboxCheckView = (CheckBox) view.findViewById(R.id.checkBox);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
