package gov.sequarius.service;


import com.alibaba.fastjson.JSONObject;
import gov.sequarius.domain.ClassInfo;
import gov.sequarius.domain.EPCookieJar;
import gov.sequarius.domain.OKHttpJar;
import gov.sequarius.domain.entry.AAOAcount;
import gov.sequarius.domain.entry.User;
import gov.sequarius.repository.AAOAcountRepository;
import gov.sequarius.repository.UserRepository;
import gov.sequarius.utils.CommonUtils;
import gov.sequarius.utils.Constant;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sequarius on 2016/4/14.
 */
@Service
public class TeachingEvaluationService {
    private String __VIEWSTATE;
    private String __VIEWSTATEGENERATOR;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AAOAcountRepository aaoAcountRepository;

    /**
     * @param username
     * @param password
     * @return 0 success /4 password error /2 locked/ -1 request deny
     */
    public OKHttpJar login(String username, String password) {
        OKHttpJar OKHttpJar = new OKHttpJar();
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new EPCookieJar()).build();
        OKHttpJar.setClient(client);
        String sign = String.valueOf(System.currentTimeMillis());
        FormBody formBody = new FormBody.Builder().add("Action", "Login")
                .add("userName", username)
                .add("pwd", CommonUtils.getMD5String((username + sign + CommonUtils.getMD5String(password.trim()))))
                .add("sign", sign).build();
        Request request = new Request.Builder().url(Constant.AAO_HOST + "/Common/Handler/UserLogin.ashx").post(formBody).build();
        JSONObject object = new JSONObject();
        OKHttpJar.setJsonObject(object);
        try {
            Response response = client.newCall(request).execute();
            Integer resultCode = Integer.valueOf(response.body().string());
            OKHttpJar.setResultCode(resultCode);

            switch (resultCode) {
                case 0:
                    break;
                case 2:
                    object.put("result", false);
                    object.put("message", "教务账号已被封停！");
                    break;
                case 4:
                    object.put("result", false);
                    object.put("message", "教务账号或者密码错误，请重新绑定教务系统账号！");
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            OKHttpJar.setResultCode(-1);
            object.put("result", false);
            object.put("message", "教务账号已被封停，请与教务处联系！");
            e.printStackTrace();
        }
        return OKHttpJar;
    }

    public List<ClassInfo> getEvaluationList(OKHttpJar OKHttpJar) {
        Request request = new Request.Builder().url(Constant.AAO_HOST + "/TeachingEvaluation/List.aspx").get().build();
        List<ClassInfo> classInfos = new ArrayList<>();
        try {
            OkHttpClient client = OKHttpJar.getClient();
            Response response = client.newCall(request).execute();
            String string = response.body().string();


//            System.out.println(string);
            Document parse = Jsoup.parse(string);
            Elements links = parse.getElementsByTag("a");
            for (Element link : links) {
                String linkHref = link.attr("href");
                if (linkHref.contains("Eval.aspx?id=")) {
                    classInfos.add(new ClassInfo(linkHref.replace("Eval.aspx?id=", "")));
                }
//                String linkText = link.text();
//                System.out.println(linkHref);
            }
            Elements TeacherElements = parse.getElementsByAttributeValueContaining("style", "width:200px;");
            for (int i = 0; i < TeacherElements.size(); i++) {
                classInfos.get(i).setTeacher(TeacherElements.get(i).text());
            }
            Elements ClassNameElements = parse.getElementsByAttributeValueContaining("style", "width: 300px;");
            for (int i = 0; i < ClassNameElements.size(); i++) {
                classInfos.get(i).setClassName(ClassNameElements.get(i).text());
            }
            Elements statusElements = parse.getElementsByClass("btn_conn1");
            for (int i = 0; i < statusElements.size(); i++) {
                if (statusElements.get(i).text().equals("查看")) {
                    classInfos.get(i).setEvaluated(true);
                }
            }
            for (int i = 0; i < classInfos.size(); i++) {
                classInfos.get(i).setClassId(getClassID(client, classInfos.get(i)));
            }


        } catch (IOException e) {
            OKHttpJar.setResultCode(-1);
            e.printStackTrace();
        }
        return classInfos;
    }

    public String getClassID(OkHttpClient client, ClassInfo info) {
        Request request = new Request.Builder().url(Constant.AAO_HOST + "/TeachingEvaluation/Eval.aspx?id=" + info.getId()).get().build();
        String reslut = null;
        try {
            Response response = client.newCall(request).execute();
            Document parse = Jsoup.parse(response.body().string());
            Elements elements = parse.getElementsByAttributeValue("name", "teachclassid");
            for (Element element : elements) {
                reslut = element.attr("value");
            }
            __VIEWSTATEGENERATOR = parse.getElementById("__VIEWSTATEGENERATOR").attr("value");
            __VIEWSTATE = parse.getElementById("__VIEWSTATE").attr("value");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reslut;
    }

    public OKHttpJar evaluation(OKHttpJar OKHttpJar, String classId, String id, String rank) {
        if (__VIEWSTATEGENERATOR == null || __VIEWSTATE == null) {
            getEvaluationList(OKHttpJar);
        }
        FormBody formBody1 = new FormBody.Builder().add("__VIEWSTATE", __VIEWSTATE)
                .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                .add("id", id)
                .add("teachclassid", classId)
                .add("item_1", rank)
                .add("item_2", rank)
                .add("item_3", rank)
                .add("item_4", rank)
                .add("item_5", rank)
                .add("item_6", rank)
                .add("remark", "")
                .add("item_7", rank).build();
        Request request = new Request.Builder().url("http://202.115.133.173:8080/TeachingEvaluation/mDeal.ashx?Action=DoEval").post(formBody1).build();
        OkHttpClient client = OKHttpJar.getClient();
        try {
            Response response = client.newCall(request).execute();
            OKHttpJar.setResultCode(0);
        } catch (IOException e) {
            OKHttpJar.setResultCode(-1);
            e.printStackTrace();
        }
        return OKHttpJar;
    }

    public AAOAccountResult getUserInfoFromRemote(String username, String token) {
        AAOAccountResult result = new AAOAccountResult();
        User user = userRepository.findByUsername(username);
        JSONObject object = new JSONObject();
        result.setMessage(object);
        object.put("result", false);
        if (user == null) {
            object.put("message", "非法用户");
            return result;
        }
        if (!user.getToken().equals(token)) {
            object.put("message", "登陆已过期，请重新登陆");
            return result;
        }
        AAOAcount aaoAcount = aaoAcountRepository.findOne(user.getId());
        if (aaoAcount == null || aaoAcount.getUsername().equals("") || aaoAcount.getPassword().equals("")) {
            object.put("message", "账号尚未绑定教务系统，请先完成绑定！");
            return result;
        }
        result.setAaoAcount(aaoAcount);
        return result;
    }

    public class AAOAccountResult {
        private JSONObject message;
        private AAOAcount aaoAcount;

        public JSONObject getMessage() {
            return message;
        }

        public void setMessage(JSONObject message) {
            this.message = message;
        }

        public AAOAcount getAaoAcount() {
            return aaoAcount;
        }

        public void setAaoAcount(AAOAcount aaoAcount) {
            this.aaoAcount = aaoAcount;
        }
    }
}
