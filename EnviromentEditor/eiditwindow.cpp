#include<QDebug>
#include "EiditWindow.h"
//#include<QMainWindow>
#include<QMessageBox>
#include<mainwindow.h>
EiditWindow::EiditWindow(QWidget *parent, MainWindow* obj) :
        QWidget(parent) {
    this->setWindowTitle("编辑环境变量");
    bCheckBox = false;
    mw = obj;
    cwLayout = new QVBoxLayout(this);
    row1 = new QHBoxLayout;
//    获取选中变量
    int row = mw->tvValueList->currentIndex().row();
    QString strName =
            mw->simTable->data(mw->simTable->index(row, 0)).toString(); //第n行第1列的内容
    QString StrValue =
            mw->simTable->data(mw->simTable->index(row, 1)).toString(); //第n行第2列的内容
    lbName = new QLabel("变量名：");
    row1->addWidget(lbName);
    leName = new QLineEdit(strName);
    row1->addWidget(leName);
    cwLayout->addLayout(row1);
    row2 = new QHBoxLayout;
    lbValue = new QLabel("变量值：");
    row2->addWidget(lbValue);
    leValue = new QLineEdit(StrValue);
    row2->addWidget(leValue);
    cwLayout->addLayout(row2);
    row3 = new QHBoxLayout;
    lbNote = new QLabel("变量备注：");
    row3->addWidget(lbNote);
    leNote = new QLineEdit();
    row3->addWidget(leNote);
    cwLayout->addLayout(row3);
    cbAdd2Path = new QCheckBox("同时将该变量添加到Path");
    cwLayout->addWidget(cbAdd2Path);

    row4 = new QHBoxLayout;
    lbToPath = new QLabel(" ");
    lbToPath->hide();
    row4->addWidget(lbToPath);
    leToPath = new QLineEdit("");
    leToPath->hide();
    row4->addWidget(leToPath);
    cwLayout->addLayout(row4);

    row5 = new QHBoxLayout;
    pbConfirm = new QPushButton("确认修改");
    row5->addWidget(pbConfirm);
    pbCancel = new QPushButton("返回");
    row5->addWidget(pbCancel);
    cwLayout->addLayout(row5);

    /*
     槽
     */
    //退出实现
    connect(pbCancel, SIGNAL(clicked()), this, SLOT(close()));
    //添加Path复选框链接
    connect(cbAdd2Path,SIGNAL(clicked(bool)),this,SLOT(slotShowAddSetting(bool)));
    //path标签补全实现
    connect(leName, SIGNAL(textChanged(QString)), this, SLOT(slotPathAdded()));
    //保存修改实现
    connect(pbConfirm, SIGNAL(clicked()), this, SLOT(slotSaveChange()));
}
void EiditWindow::closeEvent(QCloseEvent *event) {

    mw->setDisabled(false);
    qDebug() << event;
}
void EiditWindow::slotShowAddSetting(bool b) {
    if (b == true) {
        lbToPath->show();
        leToPath->show();
        bCheckBox = true;
    } else {
        lbToPath->hide();
        leToPath->hide();
        bCheckBox = false;
    }
}

void EiditWindow::slotPathAdded() {
    qDebug() << __FUNCTIONW__;
    QString str = leName->text();
    str = "%" + str + "%\\";
    lbToPath->setText(str);
    qDebug() << str;
}

void EiditWindow::slotSaveChange() {
    QString strName = leName->text();
    QString strValue = leValue->text();
    QString strNote = leNote->text();
    mw->reg->setValue(strName, strValue);
    mw->note->setValue(strName, strNote);
    if (bCheckBox == true) {
        QString strpath = mw->reg->value("path").toString();
        QString addPath = leToPath->text();
        strpath = "%" + strName + "%\\" + addPath + ";" + strpath;
        mw->reg->setValue("Path", strpath);
    }
    if (mw->reg->value(strName, false).toBool()) {
        QMessageBox::information(NULL, "操作成功", "变量" + strName + "已修改！");
    } else {
        QMessageBox::information(NULL, "操作失败",
                "变量" + strName + "未成功添加至环境变量！请以管理员权限运行本程序！");
    }

    delete mw->reg; //释放原始reg内存
    delete mw->simTable; //释放原来的SimTable内存
    delete mw->note; //释放note的内存
    mw->slotRefrehValue();
    this->close();
    delete this; //释放窗体内存
}
