package gov.sequarius.toys.gallery.utils;


import java.io.File;
import java.io.FileFilter;


/**
 * common file suffix filter
 * Created by Sequarius on 2016/5/10.
 */
public class FileFilterBySuffix implements FileFilter {

    private String suffix;

    public FileFilterBySuffix(String suffix) {
        super();
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File pathname) {

        return pathname.getName().endsWith(suffix);
    }
}
