package com.example.project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RemarksAdapter extends ArrayAdapter<Remark> {

    public RemarksAdapter(Context context, List<Remark> remarks) {
        super(context, 0, remarks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Remark remark = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_remark, parent, false);
        }

        TextView textViewRemark = convertView.findViewById(R.id.textViewRemark);
        textViewRemark.setText(remark.getDate() + ": " + remark.getRemark());

        return convertView;
    }
}
