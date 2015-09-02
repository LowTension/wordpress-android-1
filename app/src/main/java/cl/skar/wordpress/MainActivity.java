package cl.skar.wordpress;

import android.content.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.widget.*;
import java.util.*;
import org.json.*;

import android.support.v7.widget.Toolbar;
import android.view.*;

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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

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
