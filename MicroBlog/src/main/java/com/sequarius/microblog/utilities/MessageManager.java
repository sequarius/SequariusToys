package com.sequarius.microblog.utilities;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Sequarius on 2015/6/6.
 * 处理代码逻辑中的字符串信息
 */
@Component
public class MessageManager {
    private Map<String,String> messages;

    public MessageManager() {
        Properties properties=new Properties();
        try {

            String url = this.getClass().getResource("").getPath();
            String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/messages.properties";
            FileInputStream inputStream =new FileInputStream(new File(path));
            InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
            properties.load(reader);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        messages=new HashMap<String, String>();
        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Object, Object> entry = iterator.next();
            messages.put((String)entry.getKey(),(String)entry.getValue());
        }
    }
    public String getMessage(String key){
        if(messages.containsKey(key)){
            return messages.get(key);
        }else{
            return "undefined message";
        }
    }
    public String getMessage(String key,String... args){
        if(messages.containsKey(key)){
            return String.format(messages.get(key),args);
        }else{
            return "undefined message";
        }

    }
}
