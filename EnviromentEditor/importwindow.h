#ifndef IMPORTWINDOW_H
#define IMPORTWINDOW_H

#include <QWidget>
#include<mainwindow.h>
#include<QLabel>
#include<QPushButton>
#include<QCheckBox>
#include<QVBoxLayout>
#include<QHBoxLayout>
#include<QSettings>
class ImportWindow : public QWidget
{
    Q_OBJECT
public:
    friend class MainWindow;
    explicit ImportWindow(QWidget *parent = 0,MainWindow* obj=0);

signals:
private:
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

    QSettings* back;
    void closeEvent(QCloseEvent *event);
public slots:

    void slotChoseFile();
    void SlotUseBack();

};

#endif // GETBACKWINDOW_H
