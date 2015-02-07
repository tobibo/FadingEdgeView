package au.com.tosa.fadingEdgeView.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    public static final int LIST_ITEM_COUNT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        // Defined Array values to show in ListView
        String[] values = new String[LIST_ITEM_COUNT];
        for (int i=0;i<LIST_ITEM_COUNT;i++) {
           values[i] = "List tem nr: " + (i+1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }
}
