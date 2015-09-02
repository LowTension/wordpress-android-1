package cl.skar.wordpress;

import android.app.*;
import android.content.*;
import android.os.*;

public class JSONTask extends AsyncTask<String,Void,String>
{

	ProgressDialog pd;
	Context c;
	
	public JSONTask(Context context){
		this.c = context;
	}
	
	public JSONTask(){}
	@Override
	protected void onPreExecute()
	{
		// TODO: Implement this method
		super.onPreExecute();
		if(this.c != null){
			pd = new ProgressDialog(c);
			pd.setMessage("Cargando...");
			pd.setCancelable(false);
			pd.show();
		}
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
