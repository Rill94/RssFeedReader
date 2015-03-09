package com.example.valerie.rssfeedreader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnItemClickListener{
    private ListView lv;
    private FeedAdapter adapter;
    private List<SyndEntry> list;
    private EditText rss;
    private EditText filter;
    private Button button;


    private View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<SyndEntry>();
        adapter = new FeedAdapter(this, list);

        lv = (ListView) findViewById(R.id.lv);
        footer = getLayoutInflater().inflate(R.layout.listview_footer, null);

        lv.setOnItemClickListener(this);

        button = (Button) findViewById(R.id.searchbtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rss = (EditText) findViewById(R.id.rss);
                filter = (EditText) findViewById(R.id.filter);
                new LoadFeedAsyncTask().execute(rss.getText().toString(), filter.getText().toString());
            }
        });

    }


    private class LoadFeedAsyncTask extends AsyncTask<String, Void, List<SyndEntry>> {
        @Override
        protected void onPreExecute() {
            list.clear();
            lv.addFooterView(footer); // обязательно до 'setAdapter'
            lv.setAdapter(adapter);


        }

        @SuppressWarnings("unchecked")
        @Override
        protected List<SyndEntry> doInBackground(String... params) {
            URL url;

                try {
                    url = new URL(params[0]);
                    SyndFeedInput input = new SyndFeedInput();
                    SyndFeed feed = input.build(new XmlReader(url));
                    List entradas = feed.getEntries();


                    Iterator it = entradas.iterator();
                    while(it.hasNext()){
                        SyndEntry aux = (SyndEntry) it.next();
                        if (!params[1].isEmpty())
                        {
                            if ((aux.getTitle().toLowerCase().contains(params[1].toLowerCase())) ||
                                    (aux.getDescription().getValue().toLowerCase().contains(params[1].toLowerCase())))
                            {
                                list.add(aux);
                            }
                        }
                        else {
                            list.add(aux);}
                    }


                } catch (MalformedURLException e) {
                    list.add(0, null);
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (FeedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            finally {
                    runOnUiThread(new Runnable(){
                        public void run(){
                            adapter.notifyDataSetChanged();
                        }
                    });
                }


            return Collections.EMPTY_LIST;
        }


        @Override
        protected void onPostExecute(List<SyndEntry> data) {
            lv.removeFooterView(footer);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> lv, View view, int position, long id) {
        Intent it = new Intent(this, RSSContentActivity.class);
        SyndEntry se = list.get(position);
        Post post = new Post(se.getTitle(), se.getDescription().getValue(), se.getEnclosures(),
                se.getPublishedDate(), se.getCategories());
        it.putExtra("rss", post);
        startActivity(it);

    }

}