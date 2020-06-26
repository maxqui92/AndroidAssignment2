package com.example.assignment.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Database.PhoneBookDB;
import com.example.assignment.Entities.Contact;
import com.example.assignment.Helper.SwipeHelper;
import com.example.assignment.Library.MyHash;
import com.example.assignment.R;
import com.example.assignment.ViewModel.HashModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListPage extends AppCompatActivity implements MainListRecyclerViewAdapter.ContactRecordListener,
        SearchView.OnQueryTextListener {

    private HashModel hashModel;
    private RecyclerView recyclerViewMainList;
    MainListRecyclerViewAdapter adapter;
    private String TAG = this.getClass().getSimpleName();
    private Contact deletingContact;
    private int ContactIndex;
    AlertDialog.Builder confirm;

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("List Page");
        setContentView(R.layout.activity_list_page);
        addButton();
        PhoneBookDB.initializeDb(this);

        hashModel = new ViewModelProvider(this).get(HashModel.class);

        if(hashModel.myHash == null) {
            Log.d(TAG, "ViewModel has not been created yet.");
            // get data from db, and hash it.
            ArrayList<Contact> allContacts = (ArrayList<Contact>) PhoneBookDB.getInstance(this).contactDao().getAllContacts();
            hashModel.myHash = new MyHash();
            hashModel.myHash.buildHashTable(allContacts);
        } else {
            Log.d(TAG, "ViewModel has been created.");
        }

        recyclerViewMainList = findViewById(R.id.recycler_vew_main_list);
        recyclerViewMainList.getAdapter();
        // 3 step to init RecyclerView
        // step 1. create adapter
        adapter = new MainListRecyclerViewAdapter(hashModel.myHash.toList(false), this);
        // step 2. set adapter
        recyclerViewMainList.setAdapter(adapter);
        // step 3. set LayoutManager
        recyclerViewMainList.setLayoutManager(new LinearLayoutManager(this));

        // edit page
        SwipeHelper swipeHelper = new SwipeHelper(this, recyclerViewMainList,200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List buffer) {
                buffer.add(new MyButton(ListPage.this, "Update", 30, R.drawable.ic_edit_icon,
                        Color.parseColor("#7FFFD4"),
                        pos -> {
                            Intent intent = new Intent(ListPage.this, EditPage.class);
                            Contact contact = adapter.getContact().get(pos);
                            intent.putExtra("selContact", contact.id);
                            startActivityForResult(intent, 1);
                        }));
            }
        };

        setAllNavBtnClickListener();
        delete();
        sortAZ();
        sortZA();
        call();
    }

    // navigate the RecyclerView to the position based on key
    private void navBtnClick(int key) {
        if (key < 0 || key > 26) {
            return;
        }
        // calculate the offset based on key
        int offset = hashModel.myHash.calcOffsetByKey(key);
        Log.d(TAG, "offset( " + key + ") = " + offset);

        // scroll the view
        ((LinearLayoutManager)recyclerViewMainList.getLayoutManager()).scrollToPositionWithOffset(offset, 0);
    }

    private void setAllNavBtnClickListener(){
        // 26 buttons and their click functions are created statically
        findViewById(R.id.btn_main_nav_ee).setOnClickListener(v -> ListPage.this.navBtnClick(0));
        findViewById(R.id.btn_main_nav_a).setOnClickListener(v -> ListPage.this.navBtnClick(1));
        findViewById(R.id.btn_main_nav_b).setOnClickListener(v -> ListPage.this.navBtnClick(2));
        findViewById(R.id.btn_main_nav_c).setOnClickListener(v -> ListPage.this.navBtnClick(3));
        findViewById(R.id.btn_main_nav_d).setOnClickListener(v -> ListPage.this.navBtnClick(4));
        findViewById(R.id.btn_main_nav_e).setOnClickListener(v -> ListPage.this.navBtnClick(5));
        findViewById(R.id.btn_main_nav_f).setOnClickListener(v -> ListPage.this.navBtnClick(6));
        findViewById(R.id.btn_main_nav_g).setOnClickListener(v -> ListPage.this.navBtnClick(7));
        findViewById(R.id.btn_main_nav_h).setOnClickListener(v -> ListPage.this.navBtnClick(8));
        findViewById(R.id.btn_main_nav_i).setOnClickListener(v -> ListPage.this.navBtnClick(9));
        findViewById(R.id.btn_main_nav_j).setOnClickListener(v -> ListPage.this.navBtnClick(10));
        findViewById(R.id.btn_main_nav_k).setOnClickListener(v -> ListPage.this.navBtnClick(11));
        findViewById(R.id.btn_main_nav_l).setOnClickListener(v -> ListPage.this.navBtnClick(12));
        findViewById(R.id.btn_main_nav_m).setOnClickListener(v -> ListPage.this.navBtnClick(13));
        findViewById(R.id.btn_main_nav_n).setOnClickListener(v -> ListPage.this.navBtnClick(14));
        findViewById(R.id.btn_main_nav_o).setOnClickListener(v -> ListPage.this.navBtnClick(15));
        findViewById(R.id.btn_main_nav_p).setOnClickListener(v -> ListPage.this.navBtnClick(16));
        findViewById(R.id.btn_main_nav_q).setOnClickListener(v -> ListPage.this.navBtnClick(17));
        findViewById(R.id.btn_main_nav_r).setOnClickListener(v -> ListPage.this.navBtnClick(18));
        findViewById(R.id.btn_main_nav_s).setOnClickListener(v -> ListPage.this.navBtnClick(19));
        findViewById(R.id.btn_main_nav_t).setOnClickListener(v -> ListPage.this.navBtnClick(20));
        findViewById(R.id.btn_main_nav_u).setOnClickListener(v -> ListPage.this.navBtnClick(21));
        findViewById(R.id.btn_main_nav_v).setOnClickListener(v -> ListPage.this.navBtnClick(22));
        findViewById(R.id.btn_main_nav_w).setOnClickListener(v -> ListPage.this.navBtnClick(23));
        findViewById(R.id.btn_main_nav_x).setOnClickListener(v -> ListPage.this.navBtnClick(24));
        findViewById(R.id.btn_main_nav_y).setOnClickListener(v -> ListPage.this.navBtnClick(25));
        findViewById(R.id.btn_main_nav_z).setOnClickListener(v -> ListPage.this.navBtnClick(26));
    }

    private void reverseNavBtn(boolean reverse){
        if(reverse){
            TextView z = findViewById(R.id.btn_main_nav_ee);
            z.setText("Z");
            z.setOnClickListener(v -> ListPage.this.navBtnClick(26));

            TextView y = findViewById(R.id.btn_main_nav_a);
            y.setText("Y");
            y.setOnClickListener(v -> ListPage.this.navBtnClick(25));

            TextView x = findViewById(R.id.btn_main_nav_b);
            x.setText("X");
            x.setOnClickListener(v -> ListPage.this.navBtnClick(24));

            TextView w = findViewById(R.id.btn_main_nav_c);
            w.setText("W");
            w.setOnClickListener(v -> ListPage.this.navBtnClick(23));

            TextView V = findViewById(R.id.btn_main_nav_d);
            V.setText("V");
            V.setOnClickListener(v -> ListPage.this.navBtnClick(22));

            TextView u = findViewById(R.id.btn_main_nav_e);
            u.setText("U");
            u.setOnClickListener(v -> ListPage.this.navBtnClick(21));

            TextView t = findViewById(R.id.btn_main_nav_f);
            t.setText("T");
            t.setOnClickListener(v -> ListPage.this.navBtnClick(20));

            TextView s = findViewById(R.id.btn_main_nav_g);
            s.setText("S");
            s.setOnClickListener(v -> ListPage.this.navBtnClick(19));

            TextView r = findViewById(R.id.btn_main_nav_h);
            r.setText("R");
            r.setOnClickListener(v -> ListPage.this.navBtnClick(18));

            TextView q = findViewById(R.id.btn_main_nav_i);
            q.setText("Q");
            q.setOnClickListener(v -> ListPage.this.navBtnClick(17));

            TextView p = findViewById(R.id.btn_main_nav_j);
            p.setText("P");
            p.setOnClickListener(v -> ListPage.this.navBtnClick(16));

            TextView o = findViewById(R.id.btn_main_nav_k);
            o.setText("O");
            o.setOnClickListener(v -> ListPage.this.navBtnClick(15));

            TextView n = findViewById(R.id.btn_main_nav_l);
            n.setText("N");
            n.setOnClickListener(v -> ListPage.this.navBtnClick(14));

            TextView m = findViewById(R.id.btn_main_nav_m);
            m.setText("M");
            m.setOnClickListener(v -> ListPage.this.navBtnClick(13));

            TextView l = findViewById(R.id.btn_main_nav_n);
            l.setText("L");
            l.setOnClickListener(v -> ListPage.this.navBtnClick(12));

            TextView k = findViewById(R.id.btn_main_nav_o);
            k.setText("K");
            k.setOnClickListener(v -> ListPage.this.navBtnClick(11));

            TextView j = findViewById(R.id.btn_main_nav_p);
            j.setText("J");
            j.setOnClickListener(v -> ListPage.this.navBtnClick(10));

            TextView i = findViewById(R.id.btn_main_nav_q);
            i.setText("I");
            i.setOnClickListener(v -> ListPage.this.navBtnClick(9));

            TextView h = findViewById(R.id.btn_main_nav_r);
            h.setText("H");
            h.setOnClickListener(v -> ListPage.this.navBtnClick(8));

            TextView g = findViewById(R.id.btn_main_nav_s);
            g.setText("G");
            g.setOnClickListener(v -> ListPage.this.navBtnClick(7));

            TextView f = findViewById(R.id.btn_main_nav_t);
            f.setText("F");
            f.setOnClickListener(v -> ListPage.this.navBtnClick(6));

            TextView e = findViewById(R.id.btn_main_nav_u);
            e.setText("E");
            e.setOnClickListener(v -> ListPage.this.navBtnClick(5));

            TextView d = findViewById(R.id.btn_main_nav_v);
            d.setText("D");
            d.setOnClickListener(v -> ListPage.this.navBtnClick(4));

            TextView c = findViewById(R.id.btn_main_nav_w);
            c.setText("C");
            c.setOnClickListener(v -> ListPage.this.navBtnClick(3));

            TextView b = findViewById(R.id.btn_main_nav_x);
            b.setText("B");
            b.setOnClickListener(v -> ListPage.this.navBtnClick(2));

            TextView a = findViewById(R.id.btn_main_nav_y);
            a.setText("A");
            a.setOnClickListener(v -> ListPage.this.navBtnClick(1));

            TextView ha = findViewById(R.id.btn_main_nav_z);
            ha.setText("#");
            ha.setOnClickListener(v -> ListPage.this.navBtnClick(0));
        }
        else{
            TextView ha = findViewById(R.id.btn_main_nav_ee);
            ha.setText("#");
            ha.setOnClickListener(v -> ListPage.this.navBtnClick(0));

            TextView a = findViewById(R.id.btn_main_nav_a);
            a.setText("A");
            a.setOnClickListener(v -> ListPage.this.navBtnClick(1));

            TextView b = findViewById(R.id.btn_main_nav_b);
            b.setText("B");
            b.setOnClickListener(v -> ListPage.this.navBtnClick(2));

            TextView c = findViewById(R.id.btn_main_nav_c);
            c.setText("C");
            c.setOnClickListener(v -> ListPage.this.navBtnClick(3));

            TextView d = findViewById(R.id.btn_main_nav_d);
            d.setText("D");
            d.setOnClickListener(v -> ListPage.this.navBtnClick(4));

            TextView e = findViewById(R.id.btn_main_nav_e);
            e.setText("E");
            e.setOnClickListener(v -> ListPage.this.navBtnClick(5));

            TextView f = findViewById(R.id.btn_main_nav_f);
            f.setText("F");
            f.setOnClickListener(v -> ListPage.this.navBtnClick(6));

            TextView g = findViewById(R.id.btn_main_nav_g);
            g.setText("G");
            g.setOnClickListener(v -> ListPage.this.navBtnClick(7));

            TextView h = findViewById(R.id.btn_main_nav_h);
            h.setText("H");
            h.setOnClickListener(v -> ListPage.this.navBtnClick(8));

            TextView i = findViewById(R.id.btn_main_nav_i);
            i.setText("I");
            i.setOnClickListener(v -> ListPage.this.navBtnClick(9));

            TextView j = findViewById(R.id.btn_main_nav_j);
            j.setText("J");
            j.setOnClickListener(v -> ListPage.this.navBtnClick(10));

            TextView k = findViewById(R.id.btn_main_nav_k);
            k.setText("K");
            k.setOnClickListener(v -> ListPage.this.navBtnClick(11));

            TextView l = findViewById(R.id.btn_main_nav_l);
            l.setText("L");
            l.setOnClickListener(v -> ListPage.this.navBtnClick(12));

            TextView m = findViewById(R.id.btn_main_nav_m);
            m.setText("M");
            m.setOnClickListener(v -> ListPage.this.navBtnClick(13));

            TextView n = findViewById(R.id.btn_main_nav_n);
            n.setText("N");
            n.setOnClickListener(v -> ListPage.this.navBtnClick(14));

            TextView o = findViewById(R.id.btn_main_nav_o);
            o.setText("O");
            o.setOnClickListener(v -> ListPage.this.navBtnClick(15));

            TextView p = findViewById(R.id.btn_main_nav_p);
            p.setText("P");
            p.setOnClickListener(v -> ListPage.this.navBtnClick(16));

            TextView q = findViewById(R.id.btn_main_nav_q);
            q.setText("Q");
            q.setOnClickListener(v -> ListPage.this.navBtnClick(17));

            TextView r = findViewById(R.id.btn_main_nav_r);
            r.setText("R");
            r.setOnClickListener(v -> ListPage.this.navBtnClick(18));

            TextView s = findViewById(R.id.btn_main_nav_s);
            s.setText("S");
            s.setOnClickListener(v -> ListPage.this.navBtnClick(19));

            TextView t = findViewById(R.id.btn_main_nav_t);
            t.setText("T");
            t.setOnClickListener(v -> ListPage.this.navBtnClick(20));

            TextView u = findViewById(R.id.btn_main_nav_u);
            u.setText("U");
            u.setOnClickListener(v -> ListPage.this.navBtnClick(21));

            TextView V = findViewById(R.id.btn_main_nav_v);
            V.setText("V");
            V.setOnClickListener(v -> ListPage.this.navBtnClick(22));

            TextView w = findViewById(R.id.btn_main_nav_w);
            w.setText("W");
            w.setOnClickListener(v -> ListPage.this.navBtnClick(23));

            TextView x = findViewById(R.id.btn_main_nav_x);
            x.setText("X");
            x.setOnClickListener(v -> ListPage.this.navBtnClick(24));

            TextView y = findViewById(R.id.btn_main_nav_y);
            y.setText("Y");
            y.setOnClickListener(v -> ListPage.this.navBtnClick(25));

            TextView z = findViewById(R.id.btn_main_nav_z);
            z.setText("Z");
            z.setOnClickListener(v -> ListPage.this.navBtnClick(26));
        }
    }

    private void addButton(){
        FloatingActionButton add = findViewById(R.id.floatingAddBtn);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(ListPage.this, AddPage.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onClickOfContact(int position) {
        Intent intent = new Intent(ListPage.this, DetailPage.class);
        Contact contact = adapter.getContact().get(position);;
        intent.putExtra("selContact", contact.id);
        startActivity(intent);
    }

    @Override
    public void onLongClickOfContact(int position){
        ContactIndex = position;
        deletingContact = adapter.getContact().get(position);
    }

    public void delete()
    {
        findViewById(R.id.floatingDelBtn).setOnDragListener((v, event) -> {
            int dragEvent = event.getAction();
            confirm = new AlertDialog.Builder(this);

            switch(dragEvent){

                case DragEvent.ACTION_DROP:
                    //Alert Box Message
                    confirm.setMessage("Do you wish to delete the contact?");

                    //Alert Box "YES"
                    confirm.setPositiveButton("YES", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        PhoneBookDB.getInstance(getApplicationContext()).contactDao().deleteContact(deletingContact);
                        refresh();
                        Toast.makeText(ListPage.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                    });
                    //Alert Box "NO"
                    confirm.setNegativeButton("NO", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                    confirm.create();
                    confirm.show();
                    break;
            }
            return true;
        });
    }

    public void sortAZ()
    {
        findViewById(R.id.floatingAzBtn).setOnClickListener(v -> {
            adapter.reloadContactList(hashModel.myHash.toList(false));
            reverseNavBtn(false);
        });
    }

    public void sortZA()
    {
        findViewById(R.id.floatingZaBtn).setOnClickListener(v -> {
            adapter.reloadContactList(hashModel.myHash.toList(true));
            reverseNavBtn(true);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Contact> searchList = new ArrayList<>();
        for (Contact contact : PhoneBookDB.getInstance(getApplicationContext()).contactDao().getAllContacts())
        {
            if(contact.getfName().toLowerCase().contains(userInput)){
                searchList.add(contact);
            }
        }

        adapter.reloadContactList(searchList);
        return true;
    }

    public void refresh() {
        hashModel = new ViewModelProvider(this).get(HashModel.class);
        ArrayList<Contact> allContacts = (ArrayList<Contact>) PhoneBookDB.getInstance(this).contactDao().getAllContacts();
        hashModel.myHash = new MyHash();
        hashModel.myHash.buildHashTable(allContacts);
        adapter.reloadContactList(hashModel.myHash.toList(false));
    }

    public void call() {
        FloatingActionButton call = findViewById(R.id.floatingCallBtn);
        call.setOnClickListener(v -> {
            Intent intent = new Intent(ListPage.this, PhoneCallPage.class);
            startActivity(intent);
        });
    }
}
