package cl.skar.wordpress;

public class WP_Post
{
	int id;
	String title;
	String excerpt;
	String publishDate;
	
	public WP_Post(int id,String t,String e,String p){
		this.id = id;
		this.title = t;
		this.excerpt = e;
		this.publishDate = p;
	}
	
}
