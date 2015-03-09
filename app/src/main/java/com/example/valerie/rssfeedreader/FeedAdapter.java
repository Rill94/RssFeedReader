package com.example.valerie.rssfeedreader;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntryImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class FeedAdapter extends BaseAdapter {
    Context ctx;
    List<SyndEntry> list;
    LayoutInflater lInflater;
    TextView title;
    TextView description;
    TextView notfound;
    ImageView image;
    Bitmap bitmap;

    public FeedAdapter(Context ctx, List<SyndEntry> list){
        this.ctx = ctx;
        this.list = list;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.descr);
        notfound = (TextView) view.findViewById(R.id.title);
        SyndEntry post = getPost(position);
        if (post==null)
        {
            title.setText("Ничего не найдено");
        }
        else {
            title.setText(post.getTitle());
            description.setText(post.getDescription().getValue());
            List<SyndEnclosureImpl> enclosures = post.getEnclosures();
            URL url = null;
            if (!enclosures.isEmpty()) {
                try {
                    url = new URL(enclosures.get(0).getUrl());
                    new LoadImage(view).execute(url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        return view;
    }

    SyndEntryImpl getPost(int position) {
        return ((SyndEntryImpl) getItem(position));
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        private View view;
        public LoadImage (View view)
        {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap img) {
            image = (ImageView) view.findViewById(R.id.ivImage);
            if(img != null) {
                image.setImageBitmap(img);
            }
        }
    }

}
