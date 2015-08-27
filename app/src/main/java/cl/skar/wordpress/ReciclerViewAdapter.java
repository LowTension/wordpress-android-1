package cl.skar.wordpress;

import android.content.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class ReciclerViewAdapter extends RecyclerView.Adapter<ReciclerViewAdapter.PostViewHolder>
{
	ArrayList<WP_Post> listPosts;

	ReciclerViewAdapter(ArrayList<WP_Post> posts)
	{
		listPosts = posts;
	}

	@Override
	public ReciclerViewAdapter.PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posts_card_view, viewGroup, false);
		PostViewHolder pvh = new PostViewHolder(v, listPosts);
		return pvh;
	}

	@Override
	public void onBindViewHolder(ReciclerViewAdapter.PostViewHolder holder, int i)
	{
		holder.postTitle.setText(listPosts.get(i).title);
		holder.postExcerpt.setText(Html.fromHtml(listPosts.get(i).excerpt));
		holder.postPublishDate.setText("Publicado el: " + listPosts.get(i).publishDate);
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return listPosts.size();
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView)
	{
		super.onAttachedToRecyclerView(recyclerView);
	}


	public static class PostViewHolder extends RecyclerView.ViewHolder
	{      
		CardView cv;
		TextView postTitle;
		TextView postExcerpt;
		TextView postPublishDate;

		PostViewHolder(View itemView, final ArrayList<WP_Post> posts)
		{
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			postTitle = (TextView)itemView.findViewById(R.id.postTitle);
			postExcerpt = (TextView)itemView.findViewById(R.id.postExcerpt);
			postPublishDate = (TextView)itemView.findViewById(R.id.publishDate);
			itemView.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						// TODO: Implement this method
						int id = posts.get(getPosition()).id;
						Intent intent = new Intent(p1.getContext(), ViewPostActivity.class);
						intent.putExtra("postID", id);
						p1.getContext().startActivity(intent);
					}
				});
		}
    }
}
