package cl.skar.wordpress;
import android.support.v7.app.*;
import android.os.*;
import android.widget.*;

public class ViewPostActivity extends AppCompatActivity
{
	int postID;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_post);
		Bundle extra = getIntent().getExtras();
		if(extra != null){
			postID = extra.get("postID");
		}
		Toast.makeText(getApplicationContext(),"post id: "+postID,Toast.LENGTH_SHORT).show();
	}
	
}
