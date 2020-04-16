package com.dt.utils.web;

import com.dt.utils.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author jiangyao
 * @Date 2019/6/27 13:35
 **/
@Controller
@RequestMapping("/test")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    public final static String TEST_TOPIC = "TEST_TOPIC";
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

//    @RequestMapping("k")
//    @ResponseBody
//    public String testString (String msg){
//        kafkaTemplate.send(TEST_TOPIC,msg);
//        return "success";
//    }

}
