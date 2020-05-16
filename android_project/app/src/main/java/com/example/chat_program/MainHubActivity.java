package com.example.chat_program;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainHubActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    TextView UserName;
    String fileinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hub);

        final String FileName = "UserProfile.txt";

        String line = "";
        //getting list context
        //final ArrayList<String> Fromfile = new ArrayList<String>();
        try{
            InputStream Profile_info = openFileInput(FileName);
            InputStreamReader reader = new InputStreamReader(Profile_info);
            BufferedReader input = new BufferedReader(reader);
            //String line = "";
            line = input.readLine();
            if (reader != null)
                reader.close();
            if (Profile_info != null)
                Profile_info.close();
            if (input != null)
                input.close();

            //int size = chat_history.available();
            //byte[] buffer = new byte[size];
            //chat_history.read(buffer);
            //chat_history.close();
            //list_context.add(new String(buffer));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        fileinput = line;

        UserName = (TextView) findViewById(R.id.Mainhub_profile_title);
        UserName.setText(fileinput);

        expandableListView = (ExpandableListView) findViewById(R.id.Mainhub_exlist);
        expandableListDetail = ExpandableListinput.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                */
                if(expandableListTitle.get(groupPosition).equals("Daily News"))
                {
                    startActivity(new Intent(MainHubActivity.this, DailyNewsActivity.class));
                }
                if(expandableListTitle.get(groupPosition).equals("Friend List"))
                {
                    if(expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).equals("+ Add a new friend"))
                    {
                        startActivity(new Intent(MainHubActivity.this, AddFriendActivity.class));
                    }
                    else
                    {
                        Intent intent = new Intent();
                        intent.setClass(MainHubActivity.this,ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key",expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                        bundle.putString("user",fileinput);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    //startActivity(new Intent(MainHubActivity.this, ProfileActivity.class));
                }
                if(expandableListTitle.get(groupPosition).equals("Group List"))
                {
                    if(expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).equals("+ Create a new group"))
                    {
                        //startActivity(new Intent(MainHubActivity.this, CreateGroupActivity.class));
                        Intent forResult = new Intent(MainHubActivity.this, CreateGroupActivity.class);
                        int requestcode = 1;
                        startActivityForResult(forResult,requestcode);
                        onActivityResult(1,1,forResult);

                    }
                    else
                    {
                        //startActivity(new Intent(MainHubActivity.this, ChatActivity.class));
                        Intent intent = new Intent();
                        intent.setClass(MainHubActivity.this,ChatActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key",expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                        bundle.putString("user",fileinput);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });

    }
    public void change_activity_mainhub(View view)
    {
        int settingID = view.getId();
        switch (settingID)
        {
            case R.id.Mainhub_button_chatlist:

                Intent intent = new Intent();
                intent.setClass(MainHubActivity.this,ListActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putString("key",);
                bundle.putString("user",fileinput);
                intent.putExtras(bundle);
                startActivity(intent);

                //startActivity(new Intent(this, ListActivity.class));
                Toast.makeText(this, "chatlist", Toast.LENGTH_LONG).show();
                break;
            case R.id.Mainhub_button_setting:
                startActivity(new Intent(this, SettingActivity.class));
                Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String new_name = data.getStringExtra("name");
        if(new_name != null)
        {
            if(new_name.equals("0"))
            {
                //
            }
            else
            {
                List<String> new_list = expandableListDetail.get("Group List");
                new_list.remove(new_list.indexOf("+ Create a new group"));
                new_list.add(new_name);
                new_list.add("+ Create a new group");
                expandableListDetail.remove("Group List");
                expandableListDetail.put("Group List",new_list);
            }

        }
        else
        {
            //
        }
    }
}



class ExpandableListinput {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> FriendList = new ArrayList<String>();
        FriendList.add("Internation trash");
        FriendList.add("Alice");
        FriendList.add("jason");
        FriendList.add("IDK");
        FriendList.add("man from ?");
        FriendList.add("+ Add a new friend");

        List<String> GroupList = new ArrayList<String>();
        GroupList.add("international trash");
        GroupList.add("WTF");
        GroupList.add("Germany");
        GroupList.add("Netherlands");
        GroupList.add("Italy");
        GroupList.add("+ Create a new group");

        List<String> DailyNews = new ArrayList<String>();
        DailyNews.add("The event of Jim pan Outbreak");
        DailyNews.add("The aftermath of Outbreak");
        DailyNews.add("What threat did Jim pan perpose?");
        DailyNews.add("President respond to the Jim pam Outbreak");


        expandableListDetail.put("Friend List", FriendList);
        expandableListDetail.put("Group List", GroupList);
        expandableListDetail.put("Daily News", DailyNews);
        return expandableListDetail;
    }
}


class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.Exlist_item);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.Exlist_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void setNewItems() {

        notifyDataSetChanged();
    }

}