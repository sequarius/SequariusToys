#include "pathwindow.h"

#include "PathWindow.h"
#include<mainwindow.h>
#include<QDebug>
PathWindow::PathWindow(QWidget *parent, MainWindow* obj) :
        QWidget(parent) {
    this->setWindowTitle("编辑Path");
    mw = obj;
    /*
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


     */
    hblMain = new QHBoxLayout(this);
    vblLeft = new QVBoxLayout(this);
    vblRight = new QVBoxLayout(this);

    lbDescribe = new QLabel("当前环境中的Path：");
    vblLeft->addWidget(lbDescribe);
    tvPathList = new QTableView(this);
    vblLeft->addWidget(tvPathList);

    hblMain->addLayout(vblLeft);

    pbNewPath = new QPushButton("新建Path", this);
    vblRight->addWidget(pbNewPath);
    pbEiditPath = new QPushButton("编辑选中Path", this);
    vblRight->addWidget(pbEiditPath);
    pbDelPath = new QPushButton("删除选中Path", this);
    vblRight->addWidget(pbDelPath);
    lbPathDetail = new QLabel(" ");
    lbPathDetail->setGeometry(QRect(328, 240, 329, 27 * 4));  //四倍行距
    lbPathDetail->setWordWrap(true);  //自动换行
    lbPathDetail->setAlignment(Qt::AlignTop);
    vblRight->addWidget(lbPathDetail);
    /*
     文本操作区域
     */
    lbPathValue = new QLabel("Path：");
    vblRight->addWidget(lbPathValue);
    lePathValue = new QLineEdit();
    vblRight->addWidget(lePathValue);
    lbPathNote = new QLabel("备注");
    vblRight->addWidget(lbPathNote);
    lePathNote = new QLineEdit();
    vblRight->addWidget(lePathNote);
    hblEidit = new QHBoxLayout(this);
    pbSavaPath = new QPushButton("保存", this);
    hblEidit->addWidget(pbSavaPath);
    pbCancel = new QPushButton("取消", this);
    hblEidit->addWidget(pbCancel);
    vblRight->addLayout(hblEidit);

    lbPathValue->hide();
    lbPathNote->hide();
    lePathValue->hide();
    lePathNote->hide();
    pbSavaPath->hide();
    pbCancel->hide();

    pbExit = new QPushButton("退出", this);
    vblRight->addWidget(pbExit);

    hblMain->addLayout(vblRight);

    slotRefreshPath();

    /*
     **    槽
     */
    //显示选中变量概要
    connect(tvPathList, SIGNAL(clicked(QModelIndex)), this,
            SLOT(slotGetClickedInfo()));
    //新建变量
    connect(pbNewPath, SIGNAL(clicked()), this, SLOT(slotCreatePath()));
    //保存Path
    connect(pbSavaPath, SIGNAL(clicked()), this, SLOT(slotSavePath()));
    //修改Path
    connect(pbEiditPath, SIGNAL(clicked()), this, SLOT(slotEditPath()));
    //删除Path
    connect(pbDelPath, SIGNAL(clicked()), this, SLOT(slotDelPath()));
    //取消修改
    connect(pbCancel, SIGNAL(clicked()), this, SLOT(slotCancel()));
    //返回
    connect(pbExit, SIGNAL(clicked()), this, SLOT(close()));
}
void PathWindow::closeEvent(QCloseEvent *event) {
    qDebug() << event;
    mw->setDisabled(false);
}
void PathWindow::slotRefreshPath() {
    pathNote = new QSettings("./note/PathNote.ini", QSettings::IniFormat);
    QString strPath = mw->reg->value("path").toString();
    QStringList strPath_s = strPath.split(";");

    simTable = new QStandardItemModel();
    simTable->setColumnCount(2);
    simTable->setHeaderData(0, Qt::Horizontal, QString::fromLocal8Bit("路径名"));
    simTable->setHeaderData(1, Qt::Horizontal, QString::fromLocal8Bit("路径备注"));
    tvPathList->setModel(simTable);
    tvPathList->setSelectionBehavior(QAbstractItemView::SelectRows);  //设置整行选中
    tvPathList->setEditTriggers(QAbstractItemView::NoEditTriggers);    //设置无法编辑
    tvPathList->setSortingEnabled(true);    //排序
    for (int i = 0; i < strPath_s.length(); i++) {
        simTable->setItem(i, 0, new QStandardItem(strPath_s[i]));
        QString strNote = pathNote->value(strPath_s[i]).toString();
        simTable->setItem(i, 1, new QStandardItem(strNote));
    }
}
void PathWindow::slotGetClickedInfo() {
    int row = tvPathList->currentIndex().row();
    QString str1 = simTable->data(simTable->index(row, 0)).toString(); //第n行第1列的内容
    QString str2 = simTable->data(simTable->index(row, 1)).toString(); //第n行第2列的内容
//    qDebug()<<str1;
    lbPathDetail->setText("路径名：" + str1 + "\n备注：" + str2 + "\n");
}

void PathWindow::slotCreatePath() {
    lbPathValue->show();
    lbPathNote->show();
    lePathValue->show();
    lePathNote->show();
    pbSavaPath->show();
    pbCancel->show();
    lePathValue->clear();
    lePathNote->clear();

}
void PathWindow::slotSavePath() {
    QString strpath = mw->reg->value("path").toString();
    QString addPath = lePathValue->text();
    QString note = lePathNote->text();
    QStringList strPath_s = strpath.split(";");
    int temp = strPath_s.indexOf(addPath);
    if (temp == -1)    //如果path路径下不存在添加的path
            {
        //    pathNote->setValue(addPath,note);
        strpath = addPath + ";" + strpath;
        mw->reg->setValue("Path", strpath);
    }
    pathNote->setValue(addPath, note);
    lbPathValue->hide();
    lbPathNote->hide();
    lePathValue->hide();
    lePathNote->hide();
    pbSavaPath->hide();
    pbCancel->hide();
    slotRefreshPath();
}

void PathWindow::slotEditPath() {
    slotCreatePath();
    int row = tvPathList->currentIndex().row();
    QString str1 = simTable->data(simTable->index(row, 0)).toString(); //第n行第1列的内容
    QString str2 = simTable->data(simTable->index(row, 1)).toString(); //第n行第2列的内容
    lePathValue->setText(str1);
    lePathNote->setText(str2);
}

void PathWindow::slotDelPath() {
    int row = tvPathList->currentIndex().row();
    QString str1 = simTable->data(simTable->index(row, 0)).toString(); //第n行第1列的内容
    QString strPath = mw->reg->value("path").toString();
    QStringList strPath_s = strPath.split(str1 + ";");
    QString finalPath;
    for (int i = 0; i < strPath_s.length(); i++) {
        finalPath = finalPath + strPath_s[i];
    }
    qDebug() << finalPath;
    mw->reg->setValue("Path", finalPath);    //去除Path字符串里的标记路径
    pathNote->remove(str1);    //取消配置记录中的备注信息
    slotRefreshPath();

}

void PathWindow::slotCancel() {
    lbPathValue->hide();
    lbPathNote->hide();
    lePathValue->hide();
    lePathNote->hide();
    pbSavaPath->hide();
    pbCancel->hide();
    lePathValue->clear();
    lePathNote->clear();

}

