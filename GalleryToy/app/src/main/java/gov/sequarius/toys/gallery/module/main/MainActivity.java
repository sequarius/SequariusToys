package gov.sequarius.toys.gallery.module.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gov.sequarius.toys.gallery.R;
import gov.sequarius.toys.gallery.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {
    private MainPresenter mPresenter;
    private RecyclerView recyclerView;
    private SimpleRecyclerViewAdapter mAdapter;
    private List<File> mDataSet;
    private SwipeRefreshLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(getBaseContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    public void notifyDataSetChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mContainer = (SwipeRefreshLayout) findViewById(R.id.container);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mDataSet = new ArrayList<>();
        mAdapter = new SimpleRecyclerViewAdapter(mDataSet, getBaseContext());
        recyclerView.setAdapter(mAdapter);
        mPresenter = new MainPresenter(getBaseContext(), this, mDataSet);
        mContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getExternalImageFile();
            }
        });
        mPresenter.getExternalImageFile();
        setProgressBarVisible(true);
    }

    @Override
    public void makeCommonSnake(final String message) {
        Snackbar.make(mContainer, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setProgressBarVisible(final boolean visible) {
        mContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mContainer.setRefreshing(visible);
            }
        }, 50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_load_by_single_task) {
            mPresenter.getExternalImageBySingleThread();
            return true;
        }else if(id==R.id.menu_item_load_by_thread_pool){
            mPresenter.getExternalImageFile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
