package my.kmu.com.navigationdrawerrrrr;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eomhyeong-geun on 2016. 12. 3..
 */

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter(){}
    @Override
    public int getCount() {
        return listViewItemList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item_layout, parent, false);
        }
        ImageView pictureImg = (ImageView)convertView.findViewById(R.id.list_img);
        TextView idText    = (TextView)convertView.findViewById(R.id.list_text1);
        TextView titleText = (TextView)convertView.findViewById(R.id.list_text2);
        TextView dateText = (TextView)convertView.findViewById(R.id.list_text3);

        ListViewItem listViewItem = listViewItemList.get(position);

        pictureImg.setImageDrawable(listViewItem.getPicture());
        idText.setText(listViewItem.getIdNum()+"");
        titleText.setText(listViewItem.getTitle());
        dateText.setText(listViewItem.getDate());

        return convertView;
    }
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(int id, String title, String date, Drawable picture) {
        ListViewItem item = new ListViewItem();

        item.setIdNum(id);
        item.setPicture(picture);
        item.setTitle(title);
        item.setDate(date);

        listViewItemList.add(item);
    }
}
