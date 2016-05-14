#ifndef EXPORTWINDOW_H
#define EXPORTWINDOW_H

#include <QWidget>
#include<mainwindow.h>
#include<QCheckBox>
#include<QPushButton>
#include<QVBoxLayout>
#include<QHBoxLayout>
class ExportWindow : public QWidget
{
    Q_OBJECT
public:

    friend class MainWindow;
    explicit ExportWindow(QWidget *parent = 0,MainWindow* obj=0);
private:
    MainWindow* mw;
    QCheckBox* cbValue;
    QPushButton* pbComfirm;
    QPushButton* pbCancel;
    QVBoxLayout* vblROW;
    QHBoxLayout* hblLine;
    QHBoxLayout* hblButton;
    
    QSettings* back;
    void closeEvent(QCloseEvent *event);
signals:
    
public slots:
    void slotExport();

};

#endif // EXPORTWINDOW_H
