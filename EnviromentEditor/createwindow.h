#ifndef CREATEWINDOW_H
#define CREATEWINDOW_H
#include <QWidget>
#include<QLineEdit>
#include<QLabel>
#include<QVBoxLayout>
#include<QPushButton>
#include<QHBoxLayout>
#include<QCheckBox>
#include<mainwindow.h>
#include <QCloseEvent>
class CreateWindow : public QWidget
{
    Q_OBJECT
public:
    friend class MainWindow;
    explicit CreateWindow(QWidget *parent = 0,MainWindow* obj=0);

signals:
public slots:
    void slotShowAddSetting(bool b);//显示添加Path详细设置
    void slotPathAdded();//处理输入变量附加到Path标签栏待添加
    void slotSaveChange();//保存修改


private:
//    MainWindow* obj;
    //布局
    QVBoxLayout* cwLayout;//主窗体主布局
    QHBoxLayout* row1;
    QHBoxLayout* row2;
    QHBoxLayout* row3;
    QHBoxLayout* row4;
    QHBoxLayout* row5;


    //标签
    QLabel* lbName;
    QLabel* lbValue;
    QLabel* lbNote;
    QLabel* lbToPath;
    //编辑窗体
    QLineEdit* leName;//名称编辑
    QLineEdit* leValue;//值编辑
    QLineEdit* leNote;//备注编辑
    QLineEdit* leToPath;//path增量

    QCheckBox* cbAdd2Path;//添加到Path复选框
     //按钮
    QPushButton* pbConfirm;//确认按钮
    QPushButton* pbCancel;//取消按钮

    MainWindow* mw;
    bool bCheckBox;
    void closeEvent(QCloseEvent *event);
};

#endif // CREATEWINDOW_H
