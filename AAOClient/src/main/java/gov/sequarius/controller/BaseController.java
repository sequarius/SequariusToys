package gov.sequarius.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import gov.sequarius.domain.OKHttpJar;
import gov.sequarius.service.TeachingEvaluationService;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Sequarius on 2016/4/14.
 */
@RestController
@RequestMapping("/api")
@EnableSwagger2
public class BaseController {
    @Autowired
    TeachingEvaluationService service;


    @RequestMapping("/")
    public String apiIndex() {
        return "testok";
    }

    @RequestMapping(value = "/evaluation_list/{username}/{token}",method = RequestMethod.GET)
    public JSONObject evaluationList(@PathVariable String username, @PathVariable String token) {
        TeachingEvaluationService.AAOAccountResult aaoAccountResult = service.getUserInfoFromRemote(username, token);
        if (aaoAccountResult.getAaoAcount() == null) {
            return aaoAccountResult.getMessage();
        }
        OKHttpJar OKHttpJar = service.login(aaoAccountResult.getAaoAcount().getUsername(), aaoAccountResult.getAaoAcount().getPassword());
        if (OKHttpJar.getResultCode() != 0) {
            return OKHttpJar.getJsonObject();
        }
        JSONObject object = new JSONObject();
        object.put("result", true);
        object.put("evaluation_list", service.getEvaluationList(OKHttpJar));
        return object;
    }

    @RequestMapping(value = "/evaluation_teaching", method = RequestMethod.POST)
    public JSONObject evaluation(@RequestBody JSONObject jsonObject) {

        TeachingEvaluationService.AAOAccountResult aaoAccountResult = service.getUserInfoFromRemote(jsonObject.getString("username"), jsonObject.getString("token"));
        if (aaoAccountResult.getAaoAcount() == null) {
            return aaoAccountResult.getMessage();
        }
        JSONArray jsonArray = jsonObject.getJSONArray("evaluation_list");
        OKHttpJar OKHttpJar = service.login(aaoAccountResult.getAaoAcount().getUsername(), aaoAccountResult.getAaoAcount().getPassword());
        if (OKHttpJar.getResultCode() != 0) {
            return OKHttpJar.getJsonObject();
        }
        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            OKHttpJar evaluation = service.evaluation(OKHttpJar, object.getString("classId"), object.getString("id"), jsonObject.getString("level"));
            JSONObject tempJson = new JSONObject();
            tempJson.put("id", object.getString("id"));
            tempJson.put("result", evaluation.getResultCode() == -1 ? false : true);
            resultArray.add(tempJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("result", true);
        resultJson.put("results", resultArray);
        return resultJson;
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiInfo apiInfo = new ApiInfo("sample of springboot", "sample of springboot", null, null, null, null, null);
        Docket docket = new Docket(DocumentationType.SWAGGER_2).select().build()
                .apiInfo(apiInfo).useDefaultResponseMessages(false);
        return docket;
    }

}
