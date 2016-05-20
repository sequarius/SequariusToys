package gov.sequarius.toys.gallery.task;

import java.io.FileFilter;

import gov.sequarius.toys.gallery.utils.FileFilterBySuffix;

/**
 * A handler for ScanImageFileTask
 * Created by Sequarius on 2016/5/10.
 */
public abstract class ScanHandler {
    public static final String SUFFIX_JPG = "jpg";
    private int taskCount;

    private FileFilter fileFilter;

    private long mStartTime=Long.MIN_VALUE;

    public long getStartTime() {
        return mStartTime;
    }

    public ScanHandler() {
        fileFilter=new FileFilterBySuffix(SUFFIX_JPG);
        mStartTime=System.currentTimeMillis();
    }

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public abstract void onFinish(long finishTime);

    public synchronized void addTaskCount() {
        taskCount += 1;
    }

    public synchronized int devideTaskCount() {
        taskCount -= 1;
        return taskCount;
    }
}
