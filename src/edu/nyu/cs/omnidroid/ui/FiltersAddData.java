package edu.nyu.cs.omnidroid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.nyu.cs.omnidroid.R;
import edu.nyu.cs.omnidroid.util.AGParser;
import edu.nyu.cs.omnidroid.util.UGParser;

/**
 * Activity used to present a list of filters to apply to this OmniHandler. Filters allow the user
 * to only apply this OmniHandler if the filter data matches the data in provided by the Event that
 * was caught.
 * 
 * @author acase
 * 
 */
public class FiltersAddData extends Activity implements OnClickListener {
  // Standard Menu options (Android menus require int)
  private static final int MENU_HELP = 0;

  // User Selected Data
  String eventApp;
  String eventName;
  String filterType;

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.filters_add_data);

    // Get data set so far
    getIntentData();

    // Connect to the "Done" button
    Button done = (Button) findViewById(R.id.filters_add_done);
    done.setOnClickListener(this);
  }

  /**
   * A wrapper for the getIntent() function to store the appropriate data that should be present:
   * eventApp, eventName, and filterType.
   */
  private void getIntentData() {
    Intent i = getIntent();
    Bundle extras = i.getExtras();
    eventApp = extras.getString(AGParser.KEY_APPLICATION);
    eventName = extras.getString(UGParser.KEY_EVENT_TYPE);
    filterType = extras.getString(UGParser.KEY_FILTER_TYPE);
  }

  /**
   * Creates the options menu items
   * 
   * @param menu
   *          - the options menu to create
   */
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_HELP, 0, R.string.help).setIcon(android.R.drawable.ic_menu_help);
    return true;
  }

  /**
   * Handles menu item selections
   */
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_HELP:
      help();
      return true;
    }
    return false;
  }

  /**
   * Call our Help dialog
   */
  private void help() {
    // TODO (acase): Create a help dialog for this activity
  }

  /*
   * (non-Javadoc)
   * @see android.view.View.OnClickListener#onClick(android.view.View)
   */
  public void onClick(View v) {
    // Pass the data back to Filters
    // TODO(acase): Use auto-complete text instead
    // AutoCompleteTextView data = (AutoCompleteTextView) findViewById(R.id.filters_add_data);
    EditText data = (EditText) findViewById(R.id.filters_add_data);
    String filterData = data.getText().toString();

    Intent i = new Intent();
    i.setClass(this.getApplicationContext(), Filters.class);
    i.putExtra(AGParser.KEY_APPLICATION, eventApp);
    i.putExtra(UGParser.KEY_EVENT_TYPE, eventName);
    i.putExtra(UGParser.KEY_FILTER_TYPE, filterType);
    i.putExtra(UGParser.KEY_FILTER_DATA, filterData);
    setResult(Constants.RESULT_SUCCESS, i);
    finish();
  }

}