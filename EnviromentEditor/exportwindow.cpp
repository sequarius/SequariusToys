#include "exportwindow.h"
#include<QDebug>
#include<QLabel>
#include<QDateTime>
#include<QMessageBox>
ExportWindow::ExportWindow(QWidget *parent, MainWindow* obj) :
        QWidget(parent) {
    this->setWindowTitle("导出选定环境变量");
    mw = obj;
    int row = mw->key.length() / 5;
    vblROW = new QVBoxLayout(this);
    hblLine = new QHBoxLayout[row + 1];
    cbValue = new QCheckBox[(mw->key.length())];
    QLabel* _discribe = new QLabel("选择需要导出的环境变量：");
    vblROW->addWidget(_discribe);
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < 5; j++) {
            QString strValue = (mw->key[i * 5 + j]);
//           qDebug()<<strValue;
            (cbValue + i * 5 + j)->setText(strValue);
            (hblLine + i)->addWidget((cbValue + i * 5 + j));
        }
        vblROW->addLayout((hblLine + i));
        if (i == (row - 1)) {
            for (int j = 0; j < ((mw->key.length()) - row * 5); j++) {
                QString strValue = (mw->key[row * 5 + j]);
                (cbValue + row * 5 + j)->setText(strValue);
                (hblLine + row)->addWidget((cbValue + row * 5 + j));
            }
            vblROW->addLayout(hblLine + row);
        }
    }
    hblButton = new QHBoxLayout;
    pbComfirm = new QPushButton("确认导出", this);
    hblButton->addWidget(pbComfirm);
    pbCancel = new QPushButton("取消", this);
    hblButton->addWidget(pbCancel);
    vblROW->addLayout(hblButton);
    /*
     **槽
     */
    connect(pbComfirm, SIGNAL(clicked()), this, SLOT(slotExport()));
    connect(pbCancel, SIGNAL(clicked()), this, SLOT(close()));
}
void ExportWindow::closeEvent(QCloseEvent *event) {
    delete[] cbValue;
    delete[] hblLine;
    qDebug() << event;
    mw->setDisabled(false);
}
void ExportWindow::slotExport() {
    QString strTime = (QDateTime::currentDateTime()).toString("yyyyMMddhhmmss");
    QString strFileName = "back" + strTime + ".env";
    QString strPath = "./export/" + strFileName;
    back = new QSettings(strPath, QSettings::IniFormat);
    for (int i = 0; i < mw->key.length(); i++) {
        if ((cbValue + i)->isChecked()) {
//            QString Name=(cbValue+1)->text();
//            QString Value=mw->reg->value(Name).toString()+"--//"+mw->note->value(Name).toString();
//            back->setValue(Name,Value);
            QString Name = mw->key[i];
            QString Value = mw->reg->value(Name).toString() + "--//"
                    + mw->note->value(Name).toString();
            back->setValue(Name, Value);
            qDebug() << Name;
        }

    }
    if (!mw->isAdimin) {
        QMessageBox::information(NULL, "备份失败", "备份尚未完成！请以管理员权限运行本程序再试！");
    } else {

        QMessageBox::information(NULL, "操作成功",
                "已成功将所有环境配置信息的" + strFileName + "备份至主程序目录\\export文件夹下");

    }

}

