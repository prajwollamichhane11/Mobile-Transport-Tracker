package org.mobilett.version1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BusList extends ArrayAdapter<Artist>
{
    private Activity context;
    private List<Artist> artistList;


    public BusList (Activity context, List<Artist> artistList)
    {
        super(context, R.layout.list_layout, artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewbusno = (TextView)listViewItem.findViewById(R.id.textView);
        TextView textViewpassword = (TextView)listViewItem.findViewById(R.id.textView2);

        Artist artist = artistList.get(position);
        textViewbusno.setText(artist.getBUS_NO());
        textViewpassword.setText(artist.getPASSWORD());

        return listViewItem;
    }
}
