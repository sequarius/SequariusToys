#include<QApplication>
#include<mainwindow.h>
#include<QTextCodec>
#include<QDebug>
int main(int argc, char** argv) {
    QTextCodec *codec = QTextCodec::codecForName("gb2312");
//    QTextCodec::setCodecForCStrings(codec);
//    QTextCodec::setCodecForLocale(codec);
//    QTextCodec::setCodecForTr(codec);

    QTextCodec::setCodecForTr(codec);

    QTextCodec::setCodecForLocale(QTextCodec::codecForLocale());
    QTextCodec::setCodecForCStrings(QTextCodec::codecForLocale());

    QApplication a(argc, argv);
    MainWindow mw;
    mw.setGeometry(300, 200, 470, 520);
    mw.show();
    return a.exec();
}
