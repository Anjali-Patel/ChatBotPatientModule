package com.app.feish.application.Patient;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.feish.application.R;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatBot extends AppCompatActivity {
    private WebView wv1;
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter adp;
    private ListView list;
    private EditText chatText;
    private Button send;
    Context context=this;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private boolean side = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        send = (Button) findViewById(R.id.btn);
        list = (ListView) findViewById(R.id.listview);
        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat);
        list.setAdapter(adp);
        chatText = (EditText) findViewById(R.id.chat_text);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);

        adp.registerDataSetObserver(new DataSetObserver() {

            public void OnChanged() {

                super.onChanged();

                list.setSelection(adp.getCount() - 1);
            }
        });


      /*  wv1=(WebView)findViewById(R.id.webview);




        wv1.setWebViewClient(new MyBrowser());

        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setDomStorageEnabled(true);

        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //wv1.loadUrl("https://www.tutorialspoint.com/android/android_webview_layout.htm");
        String userId = "152544845";
       final String summary = "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/static/style.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
                "\t<style>\n" +
                "\tbody {\n" +
                "    font-family: Garamond;\n" +
                "}\n" +
                "\n" +
                "h1 {\n" +
                "    color: black;\n" +
                "    margin-bottom: 0;\n" +
                "    margin-top: 0;\n" +
                "    text-align: center;\n" +
                "    font-size: 40px;\n" +
                "}\n" +
                "\n" +
                "h3 {\n" +
                "    color: black;\n" +
                "    font-size: 20px;\n" +
                "    margin-top: 3px;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                "#chatbox {\n" +
                "    margin-left: auto;\n" +
                "    margin-right: auto;\n" +
                "    width: 40%;\n" +
                "    margin-top: 60px;\n" +
                "}\n" +
                "\n" +
                "#userInput {\n" +
                "    margin-left: auto;\n" +
                "    margin-right: auto;\n" +
                "    width: 40%;\n" +
                "    margin-top: 60px;\n" +
                "}\n" +
                "\n" +
                "#textInput {\n" +
                "    width: 87%;\n" +
                "    border: none;\n" +
                "    border-bottom: 3px solid #009688;\n" +
                "    font-family: monospace;\n" +
                "    font-size: 17px;\n" +
                "}\n" +
                "\n" +
                "#buttonInput {\n" +
                "    padding: 3px;\n" +
                "    font-family: monospace;\n" +
                "    font-size: 17px;\n" +
                "}\n" +
                "\n" +
                ".userText {\n" +
                "    color: white;\n" +
                "    font-family: monospace;\n" +
                "    font-size: 17px;\n" +
                "    text-align: right;\n" +
                "    line-height: 30px;\n" +
                "}\n" +
                "\n" +
                ".userText span {\n" +
                "    background-color: #009688;\n" +
                "    padding: 10px;\n" +
                "    border-radius: 2px;\n" +
                "}\n" +
                "\n" +
                ".botText {\n" +
                "    color: white;\n" +
                "    font-family: monospace;\n" +
                "    font-size: 17px;\n" +
                "    text-align: left;\n" +
                "    line-height: 30px;\n" +
                "}\n" +
                "\n" +
                ".botText span {\n" +
                "    background-color: #EF5350;\n" +
                "    padding: 10px;\n" +
                "    border-radius: 2px;\n" +
                "}\n" +
                "\n" +
                "#tidbit {\n" +
                "    position:absolute;\n" +
                "    bottom:0;\n" +
                "    right:0;\n" +
                "    width: 300px;\n" +
                "}</style>\n" +
                "  </head>\n" +
                "  \n" +
                "  <body>\n" +
                "    <div>\n" +
                "      <div id=\"chatbox\">\n" +
                "        <p class=\"botText\"><span></span></p>\n" +
                "      </div>\n" +
                "      <div id=\"userInput\">\n" +
                "        <input id=\"textInput\" type=\"text\" name=\"msg\" placeholder=\"Message\">\n" +
                "        <input id=\"buttonInput\" type=\"submit\" value=\"Send\">\n" +
                "      </div>\n" +
                "      <script>\n" +
                "        function getBotResponse() {\n" +
                "          var rawText = $(\"#textInput\").val();\n" +
                "          var userHtml = '<p class=\"userText\"><span>' + rawText + '</span></p>';\n" +
                "          $(\"#textInput\").val(\"\");\n" +
                "          $(\"#chatbox\").append(userHtml);\n" +
                "          document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});\n" +
                "          $.get(\"http://192.168.1.16:8055/get\", { msg : rawText, 'userName':'vishakha','userId':112548 }).done(function(data) {\n" +
               "\t\t\tvar d = JSON.parse(data)\n" +
               "            var botHtml = '<p class=\"botText\"><span>' + d[\"text\"] + '</span></p>';\n" +
               "            $(\"#chatbox\").append(botHtml);\n" +
               "            document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});\n" +
               "          });\n" +
               "        }\n" +
               "        $(\"#textInput\").keypress(function(e) {\n" +
               "            if(e.which == 13) {\n" +
               "                getBotResponse();\n" +
               "            }\n" +
               "        });\n" +
               "        $(\"#buttonInput\").click(function() {\n" +
               "          getBotResponse();\n" +
               "        })\n" +
               "      </script>\n" +
               "    </div>\n" +
               "  </body>\n" +
               "</html>";
        //webView.LoadDataWithBaseURL("file:///android_asset/",somehtml, "text/html", "UTF-8",null);
       // wv1.loadData(summary, "text/html", null);
       wv1.loadDataWithBaseURL("", summary, "text/html", "utf-8",null);

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
          //  view.loadUrl(url);
            return true;
        }*/
    }



    private boolean sendChatMessage() {
        final String text = chatText.getText().toString();
        adp.add(new ChatMessage(false, text));
        //call addchatbot and pass chat message
        Log.i("eeeeeeeeeeeeeeeeeeeee","fffffffffffffffffffffff");
        AddChatBot(text);
        chatText.setText("");

       // side = !side;
        return true;
    }

    void AddChatBot(String message)
    {
        OkHttpClient client = new OkHttpClient();
        final Request request = AddData(message);
        Log.i("", "onClick: " + request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String body = response.body().string();
                Log.i("response",body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new GsonBuilder().create();
                            JsonObject res = gson.fromJson(body,JsonObject.class);
                            adp.add(new ChatMessage(true, res.get("text").getAsString()));
                            Log.i("message",gson.toJson(body));

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }


    private Request AddData(String message) {
        String userid =Prefhelper.getInstance(ChatBot.this).getUserid();
        String username = PatientDashboard.userName;
        return new Request.Builder()
               // .url("http://192.168.1.16:8055/get?msg="+message+"&userName="+username+"&userId="+userid)
                .url("http://ec2-18-188-39-103.us-east-2.compute.amazonaws.com:49100/get?msg="+message+"&userName="+username+"&userId="+userid)
                .get()
                .build();
    }
}
