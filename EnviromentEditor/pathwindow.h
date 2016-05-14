#ifndef PATHWINDOW_H
#define PATHWINDOW_H

#include <QWidget>
#include<mainwindow.h>
#include<QTableView>
#include<QPushButton>
#include<QLabel>
#include<QVBoxLayout>
#include<QHBoxLayout>
#include<QStandardItemModel>
#include<QLineEdit>
#include<QSettings>
class PathWindow : public QWidget
{
    Q_OBJECT
public:
    friend class MainWindow;
    explicit PathWindow(QWidget *parent = 0,MainWindow* obj=0);

signals:

public slots:
    void slotRefreshPath();//刷新Path信息
    void slotGetClickedInfo();//获得选中行信息
    void slotCreatePath();//新建Path窗体
    void slotSavePath();//保存Path
    void slotEditPath();//显示编辑区域
    void slotDelPath();//删除Path
    void slotCancel();//取消修改

public:

private:
    MainWindow* mw;

    QTableView* tvPathList;
    //布局
    QHBoxLayout* hblMain;
    QVBoxLayout* vblLeft;
    QVBoxLayout* vblRight;

    //标签
    QLabel* lbDescribe;
    QLabel* lbPathDetail;

    //按钮
    QPushButton* pbNewPath;
    QPushButton* pbDelPath;
    QPushButton* pbEiditPath;
    QPushButton* pbExit;

    QStandardItemModel* simTable;

    /*
        编辑区域
    */
    QLabel* lbPathValue;
    QLabel* lbPathNote;
    QLineEdit* lePathValue;
    QLineEdit* lePathNote;
    QHBoxLayout* hblEidit;
    QPushButton* pbSavaPath;
    QPushButton* pbCancel;

    QSettings* pathNote;
    void closeEvent(QCloseEvent *event);
};

#endif // PATHWINDOW_H
