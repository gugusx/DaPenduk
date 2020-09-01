package com.guguscode.dapenduk.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guguscode.dapenduk.Model.DataModel;
import com.guguscode.dapenduk.Presenter.DBHandler;
import com.guguscode.dapenduk.Presenter.SessionManager;
import com.guguscode.dapenduk.R;

import java.util.ArrayList;
import java.util.List;

public class LihatData extends AppCompatActivity  {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<DataModel> list = new ArrayList<>();
    DBHandler dbhelp;
    SessionManager sessionManager;

    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        getSupportActionBar().setTitle("DaPenduk");
        getSupportActionBar().setSubtitle("List Data Penduduk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable(R.drawable.population));
        sessionManager = new SessionManager(this);


        dbhelp = new DBHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TambahData.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        list = dbhelp.getAll();
        adapter = new ListAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> implements Filterable {
        Context context;
        List<DataModel> list;
        List<DataModel> listFull;

        public ListAdapter(Context context, List<DataModel> list) {
            this.context = context;
            this.list = list;
            listFull = new ArrayList<>(list);
        }

        @Override
        public ListAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
            return new ListHolder(v);
        }

        @Override
        public void onBindViewHolder(ListAdapter.ListHolder holder, final int position) {
            final DataModel model = list.get(position);
            holder.nm.setText(model.getNama());
            holder.jk.setText(model.getJk());
            holder.alm.setText(model.getAlm());
            holder.tp.setText(model.getTpt());
            holder.tg.setText(model.getTgl());
            holder.pk.setText(model.getPk());
            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LihatData.this, EditData.class);
                    intent.putExtra("ID", model.getId());
                    startActivity(intent);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Yakin Menghapus Data?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbhelp.deleteData(model.getId());
                            dialogInterface.dismiss();
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<DataModel> filteredList = new ArrayList<>();
                    if (constraint == null || constraint.length() == 0){
                        filteredList.addAll(listFull);
                    } else {
                        String filterPattern = constraint.toString().toLowerCase().trim();
                        for (DataModel dataModel : listFull){
                            if (dataModel.getNama().toLowerCase().contains(filterPattern) || dataModel.getAlm().toLowerCase().contains(filterPattern)){
                                filteredList.add(dataModel);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;
                    return results;

                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    list.clear();
                    list.addAll((List)results.values);
                    notifyDataSetChanged();
                }
            };
        }


        public class ListHolder extends RecyclerView.ViewHolder {
            TextView nm, jk, alm, tp, tg, pk;
            ImageView update, delete;

            public ListHolder(View itemView) {
                super(itemView);
                nm = itemView.findViewById(R.id.txtNm);
                jk = itemView.findViewById(R.id.txtJk);
                alm = itemView.findViewById(R.id.txtAlm);
                tp = itemView.findViewById(R.id.txtTpt);
                tg = itemView.findViewById(R.id.txtTgl);
                pk = itemView.findViewById(R.id.txtPk);
                update = itemView.findViewById(R.id.update);
                delete = itemView.findViewById(R.id.delete);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ListAdapter listAdapter = (ListAdapter) adapter;
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                sessionManager.logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
