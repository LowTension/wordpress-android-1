package cl.skar.wordpress;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import org.json.*;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity 
{
	private Toolbar toolbar;
	RecyclerView rv;
	ArrayList<WP_Post> listPosts = new ArrayList<WP_Post>();
	SwipeRefreshLayout srl;
	ReciclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
				@Override
				public void onRefresh()
				{
					// TODO: Implement this method
					cargarPosts();
				}
			});
		srl.setColorSchemeResources(R.color.material_blue_grey_900, R.color.material_deep_teal_200, R.color.md_indigo_400);
		rv = (RecyclerView) srl.findViewById(R.id.rv);
		LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
		rv.setLayoutManager(llm);
		cargarPosts();
		handleIntent(getIntent());
	}

	protected void onNewIntent(Intent intent)
	{
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent)
	{
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
            String query = intent.getStringExtra(SearchManager.QUERY);
            // Do work using string
            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
			(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
			(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
			searchManager.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);
		searchView.setIconifiedByDefault(true);
		searchView.setMaxWidth(1000);


		return true;
	}

	private void cargarPosts()
	{
		Context c = (!srl.isRefreshing()) ?this: null;
		listPosts.clear();
		JSONTask jt = new JSONTask(c){
			@Override
			public void onPostExecute(String s)
			{
				JSONArray posts = null;
				try
				{
					posts = new JSONArray(s);
				}
				catch (JSONException e)
				{
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					Log.e("MainActivity", e.getMessage(), e);

				}
				for (int i= 0; i < posts.length(); i++)
				{
					try
					{
						JSONObject post = posts.getJSONObject(i);
						int id = post.getInt("id");
						String title = post.getJSONObject("title").getString("rendered");
						//Toast.makeText(getApplicationContext(),post.toString(),Toast.LENGTH_LONG).show();
						String excerpt = post.getJSONObject("excerpt").getString("rendered");
						String publishDate = post.getString("date");
						//Toast.makeText(getApplicationContext(),title+excerpt+publishDate,Toast.LENGTH_LONG).show();
						listPosts.add(new WP_Post(id, title, excerpt, publishDate));

					}
					catch (JSONException e)
					{
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
						Log.e("MainActivity", e.getMessage(), e);

					}
				}
				//if(adapter != null) adapter.clear();
				adapter = new ReciclerViewAdapter(listPosts);
				adapter.notifyDataSetChanged();
				rv.setAdapter(adapter);
				if (pd != null && pd.isShowing())
				{
					pd.dismiss();
				}
				else
				{
					srl.setRefreshing(false);
				}
			}
		};
		jt.execute("http://www.diasfertiles.cl/wp-json/wp/v2/posts");
	}

}
