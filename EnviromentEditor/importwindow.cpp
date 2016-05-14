#include "importwindow.h"
#include<QFileDialog>
#include<QDebug>
#include<QMessageBox>
ImportWindow::ImportWindow(QWidget *parent, MainWindow* obj) :
        QWidget(parent) {
    this->setWindowTitle("导入特定环境");
    mw = obj;
    /*
     MainWindow* mw;

     QVBoxLayout* vblMain;
     QHBoxLayout* hblRow1;
     QHBoxLayout* hblRow2;
     QHBoxLayout* hblRow3;
     QHBoxLayout* hblRow4;

     QLabel* lbDescribe;
     QLabel* lbFileInfo;

     QPushButton* pbChoseFile;
     QPushButton* pbUseBack;
     QPushButton* pbCancel;

     QCheckBox* cbImportNote;
     QCheckBox* cbInsertExist;

     QString strBackPath;
     */
    vblMain = new QVBoxLayout(this);
    hblRow1 = new QHBoxLayout(this);
    hblRow2 = new QHBoxLayout(this);
    hblRow3 = new QHBoxLayout(this);
    hblRow4 = new QHBoxLayout(this);

    lbDescribe = new QLabel("选择的导入文件:");
    hblRow1->addWidget(lbDescribe);
    pbChoseFile = new QPushButton("浏览", this);
    hblRow1->addWidget(pbChoseFile);
    vblMain->addLayout(hblRow1);

    lbFileInfo = new QLabel(" ");
    hblRow2->addWidget(lbFileInfo);
    vblMain->addLayout(hblRow2);
//    lbFileInfo->setGeometry(QRect(328, 240, 329, 27*4));  //四倍行距
//    lbFileInfo->setWordWrap(true);//自动换行
//    lbFileInfo->setAlignment(Qt::AlignTop);

    cbImportNote = new QCheckBox("同时导入备注信息");
    cbImportNote->setChecked(true);
    hblRow3->addWidget(cbImportNote);
    cbInsertExist = new QCheckBox("覆盖已存在的变量");
    cbInsertExist->setChecked(false);
    hblRow3->addWidget(cbInsertExist);
    vblMain->addLayout(hblRow3);
    QLabel* lbBlank = new QLabel("");
    vblMain->addWidget(lbBlank);

    pbUseBack = new QPushButton("确认导入", this);
    pbUseBack->setDisabled(true);
    hblRow4->addWidget(pbUseBack);
    pbCancel = new QPushButton("返回", this);
    hblRow4->addWidget(pbCancel);
    vblMain->addLayout(hblRow4);

    /*
     槽
     */
    //退出
    connect(pbCancel, SIGNAL(clicked()), this, SLOT(close()));
    //选择文件
    connect(pbChoseFile, SIGNAL(clicked()), this, SLOT(slotChoseFile()));
    //还原环境
    connect(pbUseBack, SIGNAL(clicked()), this, SLOT(SlotUseBack()));
}
void ImportWindow::closeEvent(QCloseEvent *event) {
    qDebug() << event;
    mw->setDisabled(false);
}
void ImportWindow::slotChoseFile() {
    strBackPath = QFileDialog::getOpenFileName(this, "选择特定环境的env的文件",
            "./export", "Files(*.env)");
    if (!strBackPath.isEmpty()) {
        lbFileInfo->setText(strBackPath);
        pbUseBack->setDisabled(false);
    }
}

void ImportWindow::SlotUseBack() {
    if (mw->isAdimin == true) {
        back = new QSettings(strBackPath, QSettings::IniFormat);
        QStringList strlName = back->allKeys();
        qDebug() << strlName[0];
        if (cbInsertExist->isChecked()) {
            for (int i = 0; i < strlName.length(); i++) {
                QString strName = strlName[i];
                QString strNameValue = back->value(strName).toString();
                QStringList strtemp = strNameValue.split("--//");
                QString value = strtemp[0];
                QString note = strtemp[1];
                mw->reg->setValue(strName, value);
                if (cbImportNote->isChecked()) {
                    mw->note->setValue(strName, note);
                }
            }
            QMessageBox::information(NULL, "操作成功！", "所有环境变量已被成功导入！");

        } else {
            QString unInsertValue; //记录未还原的变量串
            int count = 0;
            for (int i = 0; i < strlName.length(); i++) {
                QString strName = strlName[i];
                QString strNameValue = back->value(strName).toString();
                QStringList strtemp = strNameValue.split("--//"); //分割标记符
                QString value = strtemp[0];
                QString note = strtemp[1];
                qDebug() << strNameValue;

                if ((mw->key.indexOf(strName)) == -1) //如果在key的StringList里没有strName
                        {
                    mw->reg->setValue(strName, value);
                    if (cbImportNote->isChecked()) {
                        mw->note->setValue(strName, note);
                    }
                    count++;
                } else {
                    unInsertValue = unInsertValue + strName + "\n";
                }

            }
            QMessageBox::information(NULL, "操作成功！",
                    "本次操作共成功还原了" + QString::number(count) + "个环境变量,还有"
                            + QString::number(strlName.length() - count)
                            + "个变量因为和已有环境变量冲突未能成功导入，它们是:\n" + unInsertValue);

        }
        mw->slotRefrehValue(); //刷新主窗体变量窗口
    } else {
        QMessageBox::information(NULL, "操作失败！", "请使用管理员权限启动本程序再进行尝试！");
    }
//    QString value=back->value(strlName[0]).toString();
//    qDebug()<<value;
//    int index=mw->key.indexOf("test");
//    qDebug()<<index;
}

