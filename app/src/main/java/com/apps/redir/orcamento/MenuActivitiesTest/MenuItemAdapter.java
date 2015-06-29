package com.apps.redir.orcamento.MenuActivitiesTest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.redir.orcamento.R;

import java.util.ArrayList;

/**
 * Created by redir on 6/25/2015.
 */
public class MenuItemAdapter extends BaseAdapter {
    private Context context;

    protected ArrayList<MenuOrcItem> listItems;
    LayoutInflater inflater;

    public MenuItemAdapter(Context context, ArrayList<MenuOrcItem> listItems){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public MenuOrcItem getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listItems.get(position).getImageId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.menu_item,
                    parent, false);

            holder.name = (TextView) convertView
                    .findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MenuOrcItem item = listItems.get(position);
        Log.e("", item.getName());
        holder.name.setText(item.getName());
        //holder.img.setImageResource(item.getImageId());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return listItems.get(position).isClickable();
    }

    private class ViewHolder {
        TextView name;
        ImageView img;
    }
}
