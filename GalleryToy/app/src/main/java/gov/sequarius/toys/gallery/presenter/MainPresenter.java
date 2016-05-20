package gov.sequarius.toys.gallery.presenter;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import gov.sequarius.toys.gallery.R;
import gov.sequarius.toys.gallery.module.main.MainView;
import gov.sequarius.toys.gallery.task.ScanHandler;
import gov.sequarius.toys.gallery.task.ScanImageFileTask;
import gov.sequarius.toys.gallery.utils.FileFilterBySuffix;
import gov.sequarius.toys.gallery.utils.SimpleQueue;

/**
 * Simple presenter for service
 * Created by Sequarius on 2016/5/10.
 */
public class MainPresenter {
    private static final int POOL_SIZE = 15;
    private static final int CORE_POOL_SIZE = 10;
    private static final int KEEP_ALIVE_TIME = 1;
    private Context mContext;
    private MainView mView;
    private List<File> mDataSet;

    public MainPresenter(Context mContext, MainView mView, List<File> mDataSet) {
        this.mContext = mContext;
        this.mView = mView;
        this.mDataSet = mDataSet;
    }

    /**
     * get image files by thread pool
     */
    public void getExternalImageFile() {
        //create thread pool
        final ExecutorService executorService =
                new ThreadPoolExecutor(
                        CORE_POOL_SIZE,
                        POOL_SIZE,
                        KEEP_ALIVE_TIME,
                        TimeUnit.MINUTES,
                        new LinkedBlockingQueue<Runnable>(),
                        new ThreadPoolExecutor.CallerRunsPolicy());
        //get external storage dir
        File externalCacheDir = Environment.getExternalStorageDirectory();
        mDataSet.clear();
        //start traversal dirs
        executorService.execute(new ScanImageFileTask(externalCacheDir, executorService,
                mDataSet, new ScanHandler() {
            @Override
            //simple call back when traversal completed
            public void onFinish(long finishTime) {
                mView.notifyDataSetChanged();
                mView.makeCommonSnake(mContext.getString(R.string.message_format_load_result,
                        mDataSet.size(), finishTime - getStartTime()));
                mView.setProgressBarVisible(false);
                //shutdown thread pool
                executorService.shutdown();

            }
        }));
    }
    /**
     * get image files by single thread
     */
    public void getExternalImageBySingleThread() {
        mView.setProgressBarVisible(true);
        new singleThreadScanTask().start();
    }

    private class singleThreadScanTask extends Thread {
        @Override
        public void run() {
            File externalCacheDir = Environment.getExternalStorageDirectory();
            mDataSet.clear();
            long startTime = System.currentTimeMillis();
            FileFilter filter = new FileFilterBySuffix(ScanHandler.SUFFIX_JPG);
            SimpleQueue<File> queue = new SimpleQueue<>();
            File[] files = externalCacheDir.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    queue.add(file);
                } else {
                    if (filter.accept(file)) {
                        mDataSet.add(file);
                    }
                }

            }
            while (!queue.isEmpty()) {
                File subDir = queue.peek();
                File[] subFiles = subDir.listFiles();
                if (subFiles != null) {
                    for (File file : subFiles) {
                        if (file.isDirectory()) {
                            queue.add(file);
                        } else {
                            if (filter.accept(file)) {
                                mDataSet.add(file);
                            }
                        }

                    }
                }
            }
            mView.notifyDataSetChanged();
            mView.makeCommonSnake(mContext.getString(R.string.message_load_result_single_thread,
                    mDataSet.size(), System.currentTimeMillis() - startTime));
            mView.setProgressBarVisible(false);
        }
    }

}