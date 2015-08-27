package cl.skar.wordpress;
import android.os.*;
import android.app.*;
import android.content.*;

public class JSONTask extends AsyncTask<String,Void,String>
{

	ProgressDialog pd;
	Context c;
	
	public JSONTask(Context context){
		this.c = context;
	}
	
	@Override
	protected void onPreExecute()
	{
		// TODO: Implement this method
		super.onPreExecute();
		pd = new ProgressDialog(c);
		pd.setMessage("Cargando...");
		pd.setCancelable(false);
		pd.show();
	}
	
	@Override
	protected String doInBackground(String... p1)
	{
		// TODO: Implement this method
		//pd.dismiss();
		String asd = new JSON().getJSONString(p1[0],JSON.GET);
		
		return asd;
	}
	
}
