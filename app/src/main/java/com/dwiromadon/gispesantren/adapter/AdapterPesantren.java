package com.dwiromadon.gispesantren.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.model.ModelPesantren;
import com.dwiromadon.gispesantren.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPesantren extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelPesantren> item;

    public AdapterPesantren(Activity activity, List<ModelPesantren> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_pesantren, null);


        TextView namaPetshop = (TextView) convertView.findViewById(R.id.txtNamaPesantren);
        ImageView gambar     = (ImageView) convertView.findViewById(R.id.gambarPesantren);
        TextView notelp      = (TextView) convertView.findViewById(R.id.txtNoTelp);
        TextView alamat      = (TextView) convertView.findViewById(R.id.txtAlamat);
        namaPetshop.setText(item.get(position).getNamaPesantren());
        notelp.setText(item.get(position).getNomorTelp());
        alamat.setText(item.get(position).getAlamat());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }
}
