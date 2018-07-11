package com.example.shivanikoul.webservices;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.networkutil.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    TextView nameTextView,bioTextView;
    EditText userNameEditText;
    Button gobtn;

    String BASE_URL ="https://api.github.com/users/";
    String data = null;

    String githubName,gitBio,Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView =findViewById(R.id.githubImage);
        nameTextView=findViewById(R.id.githubName);
        bioTextView=findViewById(R.id.githubBio);
        userNameEditText=findViewById(R.id.userET);
        gobtn=findViewById(R.id.gobutton);

        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadData().execute();
            }
        });
    }

               class LoadData extends AsyncTask<Void,Void,Void> {

                   @Override
                   protected void onPreExecute() {
                       super.onPreExecute();
                   }

                   @Override
                   protected Void doInBackground(Void... voids) {

                       String userName = userNameEditText.getText().toString();
                       if (userName == null) {
                           return null;
                       }

                       String url = BASE_URL + userName;
                       data = NetworkUtil.makeServiceCall(url);

                       try {
                           JSONObject jsonObject=new JSONObject(data);
                           githubName = jsonObject.getString("name");
                            gitBio=jsonObject.getString("bio");
                           Image =jsonObject.getString("avatar_url");
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
//                       Log.d("test", data);
                       return null;
                   }

                   @Override
                   protected void onPostExecute(Void aVoid) {
                       super.onPostExecute(aVoid);

                       if (githubName.equals("null")) {
                           nameTextView.setText(null);
                       } else {
                           nameTextView.setText(githubName);
                       }
                       if (bioTextView.equals("null")) {
                           bioTextView.setText(null);
                       } else {
                           bioTextView.setText(gitBio);
                       }
                       Glide.with(MainActivity.this).load(Image).into(imageView);
                   }
               }
}
