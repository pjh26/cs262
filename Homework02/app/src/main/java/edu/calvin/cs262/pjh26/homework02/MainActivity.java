package edu.calvin.cs262.pjh26.homework02;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String>{

    private EditText playerInt;
    private TextView textResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerInt = (EditText)findViewById(R.id.playerInt);
        textResults = (TextView)findViewById(R.id.textResults);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchUser(View view) {
        // Get the search int from the input field.
        String queryInt = playerInt.getText().toString();

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty,
        // add the search term to the arguments Bundle and start the loader.
        if (networkInfo != null && networkInfo.isConnected()) {
            textResults.setText(R.string.loading);
            Bundle queryBundle = new Bundle();
            queryBundle.putString("playerInt", queryInt);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        else {
            textResults.setText(R.string.no_network);
        }
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.d("args bundle", args.getString("playerInt"));
        return new BookLoader(this, args.getString("playerInt"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        String queryInt = data.split("%")[0];
        data = data.split("%")[1];

        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(data);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String name = null;
            String email = null;
            String playerid = null;
            textResults.setText("");
            boolean foundItem = false;
            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || (name == null && email == null)) {

                // Get the current item information.
                JSONObject player = itemsArray.getJSONObject(i);

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.

                playerid = player.getString("id");

                try {
                    name = player.getString("name");
                } catch (Exception e) {
                    e.printStackTrace();
                    name = "no name";
                }

                try {
                    email = player.getString("emailAddress");
                } catch (Exception e) {
                    e.printStackTrace();
                    email = "no email";
                }

                if (queryInt.equals("&")) {
                    textResults.append(playerid + ", " + name + ", " + email + "\n");
                    foundItem = true;
                } else {
                    if (queryInt.equals(playerid)) {
                        textResults.append(playerid + ", " + name + ", " + email);
                        foundItem = true;
                        break;
                    }
                }
                // Move to the next item.
                i++;
            }

            if (!foundItem) {
                textResults.append("No players found with id:" + queryInt);
            }

        } catch (Exception e){
            // If onPostExecute does not receive a proper JSON string, update the UI to show failed results.
            textResults.setText(R.string.no_results);
            e.printStackTrace();
        }

    }


    @Override
    public void onLoaderReset(Loader<String> loader) {}

}
