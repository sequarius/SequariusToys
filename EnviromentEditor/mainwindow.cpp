#include "mainwindow.h"
#include<QDebug>
#include<QMessageBox>
#include<QDateTime>
#include<QDir>
MainWindow::MainWindow(QWidget *parent) :
        QWidget(parent) {
    this->setWindowTitle("环境变量编辑器");
    /***************************************************
     全局布局控制
     */
    hblMain = new QHBoxLayout(this);
    vblRight = new QVBoxLayout(this);
    vblLeft = new QVBoxLayout(this);
    hblMain->addLayout(vblLeft);
    hblMain->addLayout(vblRight);
    /*******************************************************
     左侧布局
     */
    lbTableView = new QLabel("当前系统环境变量：", this);
    vblLeft->addWidget(lbTableView);
    tvValueList = new QTableView();
    vblLeft->addWidget(tvValueList);
    lbDetailTittle = new QLabel("变量信息：", this);
    vblLeft->addWidget(lbDetailTittle);
    lbPathDetail = new QLabel("变量信息：", this);
    vblLeft->addWidget(lbPathDetail);
    lbPathDetail->setGeometry(QRect(328, 240, 329, 27 * 4));  //四倍行距
    lbPathDetail->setWordWrap(true);  //自动换行
    lbPathDetail->setAlignment(Qt::AlignTop);
    /*******************************************************
     右侧布局
     */
    lbPathDescribe = new QLabel("Path设置：", this);
    vblRight->addWidget(lbPathDescribe);
    pbShowPath = new QPushButton("显示Path信息");  //显示path
    vblRight->addWidget(pbShowPath);
    pbEiditPath = new QPushButton("编辑Path路径");  //修改path
    vblRight->addWidget(pbEiditPath);

    //*****************************************************************

    lbBackDescribe = new QLabel("环境适配:");
    vblRight->addWidget(lbBackDescribe);
    pbSetBack = new QPushButton("备份当前环境");  //备份环境
    vblRight->addWidget(pbSetBack);
    pbUseBack = new QPushButton("还原到指定环境");  //还原环境
    vblRight->addWidget(pbUseBack);
    pbCheckIN = new QPushButton("导入特定环境变量");  //导入环境
    vblRight->addWidget(pbCheckIN);
    pbCheckOut = new QPushButton("导出指定环境变量"); //导出环境
    vblRight->addWidget(pbCheckOut);

    //*****************************************************************

    lbEiditDescribe = new QLabel("变量操作：");
    vblRight->addWidget(lbEiditDescribe);
    pbNewValue = new QPushButton("新建环境变量");  //新建变量
    vblRight->addWidget(pbNewValue);
    pbEiditValue = new QPushButton("编辑选中变量");  //编辑变量
    vblRight->addWidget(pbEiditValue);
    pbDelValue = new QPushButton("删除选中变量");  //删除变量
    vblRight->addWidget(pbDelValue);

    //*****************************************************************
    lbEiditFunction = new QLabel(" ");
    vblRight->addWidget(lbEiditFunction);
    pbAbout = new QPushButton("关于…");//关于
    vblRight->addWidget(pbAbout);
    pbExit = new QPushButton("退出"); //退出
    vblRight->addWidget(pbExit);

    /******************************************
     //        环境变量池
     */
    //显示变量
    slotRefrehValue();

    //通过是否可以新建环境变量判断是否以管理员身份启动
    reg->setValue("testAdminPermissions", "test");
    if (reg->value("testAdminPermissions", false).toBool()) {

        reg->remove("testAdminPermissions");
        isAdimin = true;
        //判断back目录是否存在，若没有提示用户备份！
        QDir dir;
        if (!dir.exists("./back/")) {
            switch (QMessageBox::information(this, "备份提醒",
                    "程序当前目录下找不到back文件夹，您可能从未对环境变量进行备份，为了保证操作安全,强烈建议您备份当前环境变量以减少误操作所带来的困扰！",
                    "立即备份！", "暂不需要！", 0, 1)) {
            case 0:
                slotSetBack();
                break;
            case 1:
                break;

            }

        }
    } else {
        QMessageBox::information(NULL, "未获得UAC管理员权限",
                "未使用UAC管理员权限开启本程序，由于Windows系统的限制，你可能只能查看变量内容而无法对其编辑");
        isAdimin = false;

    }

    //显示选中变量概要
    connect(tvValueList, SIGNAL(clicked(QModelIndex)), this,
            SLOT(slotGetClickedLine()));

    //槽
    //显示Path信息
    connect(pbShowPath, SIGNAL(clicked()), this, SLOT(slotShowPath()));
    //添加按钮槽
    connect(pbNewValue, SIGNAL(clicked()), this, SLOT(slotCreateWindow()));
    connect(pbEiditValue, SIGNAL(clicked()), this, SLOT(slotEiditWindow()));
    connect(pbEiditPath, SIGNAL(clicked()), this, SLOT(slotPathWindow()));

    //删除按钮槽
    connect(pbDelValue, SIGNAL(clicked()), this, SLOT(slotRemoveValue()));
    //备份
    connect(pbSetBack, SIGNAL(clicked()), this, SLOT(slotSetBack()));
    //还原
    connect(pbUseBack, SIGNAL(clicked()), this, SLOT(SlotGetBackWindow()));
    //导出
    connect(pbCheckOut, SIGNAL(clicked()), this, SLOT(slotExportWindow()));
    //导入
    connect(pbCheckIN, SIGNAL(clicked()), this, SLOT(slotImportWindow()));
    //退出
    connect(pbExit, SIGNAL(clicked()), this, SLOT(close()));
    //关于
    connect(pbAbout, SIGNAL(clicked()), this, SLOT(slotAboutWindow()));
}

void MainWindow::slotRefrehValue() {
    reg =
            new QSettings(
                    "HKEY_LOCAL_MACHINE\\SYSTEM\\ControlSet001\\Control\\Session Manager\\Environment",
                    QSettings::NativeFormat);
    key = reg->allKeys();
    note = new QSettings("./note/ValueNote.ini", QSettings::IniFormat);
    simTable = new QStandardItemModel();
    simTable->setColumnCount(3);
//    simTable->setColumnCount(3);
    simTable->setHeaderData(0, Qt::Horizontal, QString::fromLocal8Bit("变量名"));
    simTable->setHeaderData(1, Qt::Horizontal, QString::fromLocal8Bit("变量值"));
    simTable->setHeaderData(2, Qt::Horizontal, QString::fromLocal8Bit("备注"));
    tvValueList->setModel(simTable);
    tvValueList->setSelectionBehavior(QAbstractItemView::SelectRows);   //设置整行选中
    tvValueList->setEditTriggers(QAbstractItemView::NoEditTriggers);    //设置无法编辑
    tvValueList->setSortingEnabled(true);    //排序
    for (int i = 0; i < key.length(); i++) {
        simTable->setItem(i, 0, new QStandardItem(key[i]));
        simTable->setItem(i, 1,
                new QStandardItem(reg->value(key[i]).toString()));
        simTable->setItem(i, 2,
                new QStandardItem(note->value(key[i]).toString()));
    }
}
void MainWindow::slotGetClickedLine() {
    int row = tvValueList->currentIndex().row();
    QString str1 = simTable->data(simTable->index(row, 0)).toString(); //第n行第1列的内容
    QString str2 = simTable->data(simTable->index(row, 1)).toString(); //第n行第2列的内容
    QString str3 = simTable->data(simTable->index(row, 2)).toString();
    qDebug() << str1;
    lbPathDetail->setText(
            "变量名：" + str1 + "\n值：" + str2 + "\n" + "备注：" + str3 + "\n");
}
void MainWindow::slotShowPath() {
    QString str = reg->value("path").toString();
    QStringList str2 = str.split(";");
    QString str3;
    int count = 0;
    for (int i = 0; i < str2.length(); i++) {
        str3 = str3 + str2[i] + "\n";
        count++;
    }

    qDebug() << str2[3];
    QMessageBox::information(NULL, "当前Path路径",
            "当前Path一共存在" + QString::number(count) + "个Path，详情如下：\n" + str3);
}

void MainWindow::slotRemoveValue() {
    int row = tvValueList->currentIndex().row();
    QString strName = simTable->data(simTable->index(row, 0)).toString(); //第n行第1列的内容
    QString strNotification = "确认删除" + strName + "?\n";
    if (strName.isNull()) {
        QMessageBox::information(NULL, "错误！", "未选择任何有效的环境变量！");
    } else {
        switch (QMessageBox::information(this, "删除确认", strNotification, "确认删除",
                "取消操作", 0, 1)) {
        case 0:
            if (isAdimin == false) {
                QMessageBox::information(NULL, "操作失败",
                        "变量" + strName + "未成功删除！请以UCA管理员权限运行本程序！");
            } else {
                reg->remove(strName);
                note->remove(strName);
                delete reg;    //释放原始reg内存
                delete simTable;    //释放原来的SimTable内存
                slotRefrehValue();
                QMessageBox::information(NULL, "操作成功",
                        "变量" + strName + "已成功删除！");
            }
            break;
        case 1:
            break;

        }

    }
}

void MainWindow::slotEiditWindow() {
    qDebug() << __FUNCTIONW__;
    wEidit = new EiditWindow(0, this);
    wEidit->show();
    this->setDisabled(true);
    wEidit->setAttribute(Qt::WA_DeleteOnClose, true);    //窗体对象调用close()方法时释放空间
}
void MainWindow::slotCreateWindow() {
    qDebug() << __FUNCTIONW__;
    wCreate = new CreateWindow(0, this);
    wCreate->setAttribute(Qt::WA_DeleteOnClose, true);
    wCreate->show();
    this->setDisabled(true);

}
void MainWindow::slotPathWindow() {
    wPath = new PathWindow(0, this);
    wPath->show();
    this->setDisabled(true);
    wPath->setAttribute(Qt::WA_DeleteOnClose, true);
    wPath->setGeometry(350, 250, 400, 340);
}
void MainWindow::slotSetBack() {
    QString strTime = (QDateTime::currentDateTime()).toString("yyyyMMddhhmmss");
    QString strFileName = "back" + strTime + ".back";
    QString strPath = "./back/" + strFileName;
    back = new QSettings(strPath, QSettings::IniFormat);
    for (int i = 0; i < key.length(); i++) {
        QString Name = key[i];
        QString Value = reg->value(key[i]).toString() + "--//"
                + note->value(key[i]).toString();
        back->setValue(Name, Value);
    }
    if (!isAdimin) {
        QMessageBox::information(NULL, "备份失败", "备份尚未完成！请以UCA管理员权限运行本程序再试！");
    } else {

        QMessageBox::information(NULL, "操作成功",
                "已成功将所有环境配置信息的" + strFileName + "备份至主程序目录\\back文件夹下");

    }

}
void MainWindow::SlotGetBackWindow() {
    wBack = new GetBackWindow(0, this);
    wBack->show();
    this->setDisabled(true);
    wBack->setAttribute(Qt::WA_DeleteOnClose, true);

}
void MainWindow::slotExportWindow() {
    wExport = new ExportWindow(0, this);
    wExport->show();
    this->setDisabled(true);
    wExport->setAttribute(Qt::WA_DeleteOnClose, true);
}
void MainWindow::slotImportWindow() {
    wImport = new ImportWindow(0, this);
    wImport->show();
    this->setDisabled(true);
    wImport->setAttribute(Qt::WA_DeleteOnClose, true);
}
void MainWindow::slotAboutWindow() {
    QMessageBox::about(NULL, "关于程序",
            "作者：Sequarius\n版本：1.10\n如遇BUG请致函：sequarius@gmail.com\n谢谢使用！");

}
