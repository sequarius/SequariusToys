package com.sequarius.microblog.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sequarius on 2015/6/8.
 */
public class TimeUtil {
    private static final long MINUTES = 60000; // 一分钟
    private static final long HOURS = 3600000;// 一小时
    private static final long DAYS = 86400000;// 一天

    public static String converTimeString(long time){
        long timeDif = System.currentTimeMillis() - time;
        if (timeDif < MINUTES) {
            StringBuffer sb = new StringBuffer();
            sb.append(timeDif / 1000 + 1).append("秒前");
            return sb.toString();
        } else if (timeDif < HOURS) {
            StringBuffer sb = new StringBuffer();
            sb.append(timeDif / 1000 / 60).append("分钟前");
            String resultStr = sb.toString();
            return sb.toString();
        } else if (timeDif < DAYS) {
            StringBuffer sb = new StringBuffer();
            sb.append(timeDif / 1000 / 60 / 60).append("小时前");
            return sb.toString();
        } else {
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM月dd日");
            return timeFormat.format(new Date(time));
        }
    }

}
