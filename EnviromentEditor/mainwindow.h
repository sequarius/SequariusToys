#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include<QHBoxLayout>
#include<QVBoxLayout>
#include<QPushButton>
#include<QLabel>
#include <QListWidget>
#include<QTableView>
#include <QWidget>
#include<QStandardItem>
#include<QSettings>
#include<createwindow.h>
#include<eiditwindow.h>
#include<pathwindow.h>
#include<getbackwindow.h>
#include<exportwindow.h>
#include<importwindow.h>
class MainWindow : public QWidget
{
    Q_OBJECT
public:
    explicit MainWindow(QWidget *parent = 0);
    friend class CreateWindow;
    friend class EiditWindow;
    friend class PathWindow;
    friend class GetBackWindow;
    friend class ExportWindow;
    friend class ImportWindow;


signals:
    
public slots:
    void slotAboutWindow();  //关于信息
    void slotExportWindow();//显示导出窗口
    void SlotGetBackWindow();//显示还原窗口
    void slotSetBack();//显示备份窗口
    void slotPathWindow();//Path窗体
    void slotShowPath();//显示路径
    void slotCreateWindow();//新建变量
    void slotEiditWindow();//编辑变量
    void slotGetClickedLine();//获得选中行的信息
    void slotRefrehValue();//刷新变量区域
    void slotRemoveValue();//删除变量
    void slotImportWindow();//显示导入窗口
private:
    //布局
    QHBoxLayout* hblMain;//主
    QVBoxLayout* vblRight;//右
    QVBoxLayout* vblLeft;//左
    //按钮
    QPushButton* pbShowPath;//显示path
    QPushButton* pbEiditPath;//修改path
    QPushButton* pbSetBack;//备份环境
    QPushButton* pbUseBack;//还原环境
    QPushButton* pbCheckIN;//导入环境
    QPushButton* pbCheckOut;//导出环境
    QPushButton* pbNewValue;//新建变量
    QPushButton* pbDelValue;//删除变量
    QPushButton* pbEiditValue;//编辑变量
    QPushButton* pbAbout;//关于
    QPushButton* pbExit; //退出
    //标签
    QLabel* lbTableView;//当前变量列表
    QLabel* lbDetailTittle;//变量信息标题
    QLabel* lbPathDetail;//变量信息描述
    QLabel* lbPathDescribe;//路径标题
    QLabel* lbBackDescribe;//环境配置
    QLabel* lbEiditDescribe;//变量编辑
    QLabel* lbEiditFunction;//分割
    //变量显示控件
    QTableView* tvValueList;
    QStandardItemModel* simTable;
    QSettings* reg;
    QSettings* note;
    QStringList key;
    QSettings* back;

    CreateWindow* wCreate;
    EiditWindow* wEidit;
    PathWindow* wPath;
    GetBackWindow* wBack;
    ExportWindow* wExport;
    ImportWindow* wImport;

    bool isAdimin;

};

#endif // MAINWINDOW_H
