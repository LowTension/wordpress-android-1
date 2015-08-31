package cl.skar.wordpress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
			postID = Integer.parseInt(extra.get("postID").toString());
		}
		Toast.makeText(getApplicationContext(),"post id: "+postID,Toast.LENGTH_SHORT).show();
	}
	
}
