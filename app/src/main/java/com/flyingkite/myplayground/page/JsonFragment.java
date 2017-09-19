package com.flyingkite.myplayground.page;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.flyingkite.myplayground.R;
import com.flyingkite.myplayground.data.Card;
import com.flyingkite.myplayground.page.host.BaseFragment;
import com.flyingkite.myplayground.page.json.CardLibrary;
import com.flyingkite.util.IOUtil;
import com.flyingkite.util.Say;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFragment extends BaseFragment {
    private Button parse;
    private Spinner paths;

    private RecyclerView recycler;
    private CardLibrary cardLib;

    private static final String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    @Override
    protected int getPageLayoutId() {
        return R.layout.page_json;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Say.LogF("folder = " + folder);
        recycler = (RecyclerView) findViewById(R.id.jsonRecycler);
        cardLib = new CardLibrary(recycler);

        parse = (Button) findViewById(R.id.jsonParse);
        parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = (String) paths.getSelectedItem();
                //parse(new File(path));
                Card[] cards = parseToAdapter(new File(path));
                cardLib.setDataSet(cards);
            }

            private Card[] parseToAdapter(File file) {
                Reader reader = null;
                Card[] cards;
                try {
                    reader = getReader(file);
                    Gson gson = new Gson();
                    cards = gson.fromJson(reader, Card[].class);
                    Say.LogF("%s cards", cards == null ? 0 : cards.length);
                } finally {
                    IOUtil.closeIt(reader);
                }
                return cards;
            }

            private void test() {
                Gson gson = new Gson();
                Card x = new Card();
                x.display_num = "123";

                Card y = new Card();
                y.display_num = "456";

                Card z = new Card();
                z.display_num = "789";

                String s;
                s = gson.toJson(x);
                Say.LogF("x = " + s);

                s = gson.toJson(y);
                Say.LogF("y = " + s);

                s = gson.toJson(z);
                Say.LogF("z = " + s);
            }
        });

        paths = (Spinner) findViewById(R.id.jsonSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
                , android.R.layout.simple_dropdown_item_1line, PATHS);
        paths.setAdapter(adapter);
    }

    private static final String[] PATHS = new String[] {
              folder + ".tosguide/card.json"
            , "France"
            , "Italy"
            , "Germany"
            , "Spain"
    };

    private void parse(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            Say.LogF("invalid file = " + file);
            return;
        }

        Reader reader = null;
        JsonReader jr = null;
        try {
            reader = getReader(file);
            jr = new JsonReader(reader);
            parseContent(jr);
            Say.LogF("------");
            //reader = getReader(file);
            //parseByGson(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeIt(reader);
            IOUtil.closeIt(jr);
        }
    }

    private InputStreamReader getReader(File file) {
        try {
            return new InputStreamReader(new FileInputStream(file), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseByGson(Reader reader) {
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(reader, Card[].class);

        Say.LogF("%s cards", cards == null ? 0 : cards.length);
        if (cards != null) {
            int n = 5;
            for (int i = 0; i < n; i++) {
                Card c = cards[i];
                Say.LogF("  name = /%s/", c.name);
                Say.LogF("  number = /%s/", c.number);
                Say.LogF("  levelInit = /%s/", c.levelInit);
                Say.LogF("  levelInitHp = /%s/", c.levelInitHP);
                Say.LogF("  -----   ");
            }
        }
    }

    private List<Map<String, String>> parseContent(JsonReader reader) throws IOException {
        List<Map<String, String>> messages = new ArrayList<>();

        Say.LogF("---- V");
        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(parseItem(reader));
        }
        reader.endArray();
        Say.LogF("---- ^");
        return messages;
    }

    private Map<String, String> parseItem(JsonReader reader) throws IOException {
        Map<String, String> map = new HashMap<>();
        //Say.LogF("  ---- V");
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            String value = reader.nextString();
            //Say.LogF("  %s -> %s", name, value);
            //Say.LogF("    public String %s;", name);
            map.put(name, value);
        }
        reader.endObject();
        //Say.LogF("  ---- ^");
        return map;
    }

}
