package gov.sequarius.toys.gallery.task;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * a task using for thread pool traversal
 * Created by Sequarius on 2016/5/10.
 */
public class ScanImageFileTask implements Runnable {

    private File mDirectory;
    private ExecutorService mExecutor;
    private List<File> mResultFileSet;
    private ScanHandler mHandler;

    public ScanImageFileTask(File directory, ExecutorService executor, List<File> resultFileSet, ScanHandler handler) {
        this.mHandler = handler;
        handler.addTaskCount();
        this.mDirectory = directory;
        this.mExecutor = executor;
        this.mResultFileSet = resultFileSet;
    }

    @Override
    public void run() {
        File[] files = mDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    mExecutor.execute(new ScanImageFileTask(file, mExecutor, mResultFileSet, mHandler));
                } else {
                    if (mHandler.getFileFilter().accept(file)) {
                        mResultFileSet.add(file);
                    }
                }
            }
        }
        //traversal finish
        if (mHandler.devideTaskCount() == 0) {
            mHandler.onFinish(System.currentTimeMillis());
        }
    }
}
