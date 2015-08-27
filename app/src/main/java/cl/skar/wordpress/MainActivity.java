package cl.skar.wordpress;

import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.widget.*;
import java.util.*;
import org.json.*;

import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity 
{
	private Toolbar toolbar;
	RecyclerView rv;
	ArrayList<WP_Post> listPosts = new ArrayList<WP_Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		JSONTask jt = new JSONTask(this){
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
					Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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
						listPosts.add(new WP_Post(id,title, excerpt, publishDate));

					}
					catch (JSONException e)
					{
						Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
						Log.e("MainActivity", e.getMessage(), e);
						
					}
				}
				ReciclerViewAdapter adapter = new ReciclerViewAdapter(listPosts);
				rv.setAdapter(adapter);
				pd.dismiss();
			}
		};
		rv = (RecyclerView) findViewById(R.id.rv);
		rv.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
		rv.setLayoutManager(llm);
		jt.execute("http://www.diasfertiles.cl/wp-json/wp/v2/posts");
		

	}

}
