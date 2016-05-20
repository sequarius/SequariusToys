package gov.sequarius.toys.gallery.module.main;

/**
 * Created by Sequarius on 2016/5/10.
 */
public interface MainView {
    void notifyDataSetChanged();

    void initView();

    void makeCommonSnake(String message);

    void setProgressBarVisible(boolean visible);
}
