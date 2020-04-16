package com.dt.utils.kafka;

import com.dt.utils.util.RedisUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author jiangyao
 * @Date 2020/4/14 9:08
 **/
@Component
public class KafkaLogsConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaLogsConsumer.class);

    public static final String CONSUMER_ENABLE_AUTO_COMMIT = "false";       //是否自动提交（消费者）

    public static final String TOPIC_PREFIX = "LOG_TOPIC";
    public static final String TEST_TOPIC = "TEST_TOPIC";
    public static final String TOPIC_DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    RedisUtil redisUtil;

//    @KafkaListener(topics = TOPIC_PREFIX)
    public void listenMsg (ConsumerRecord<?,String> record) {
        String value = record.value();
        LOGGER.info(">>>>>>>>>>>>>>>>>>"+value);
        String format = DateFormatUtils.format(new Date(), TOPIC_DATE_PATTERN);
        redisUtil.lSet(KafkaLogsConsumer.TOPIC_PREFIX+"_"+format,value);
    }

//    @KafkaListener(topics = TEST_TOPIC)
    public void testMsg (ConsumerRecord<?,String> record) {
        LOGGER.info(">>>>>>>>>>>>>>>>>>{}-{}-{}",record.value(),record.partition(),record.offset());
    }

    @KafkaListener(groupId = "logs-consumer-group",
//            topics = {TEST_TOPIC},
            containerFactory = "batchContainerFactory" //,
//            topicPartitions = {
//                    @TopicPartition(topic = TEST_TOPIC,partitions = {"1","3"}),
//                    @TopicPartition(topic = TEST_TOPIC,partitions = {"0","2"}
//                            partitionOffsets = @PartitionOffset(partition = "2",initialOffset = "100")
//                    )}
            )
    public void batchListener(/* @Payload */  List<ConsumerRecord<String,String>> data //,
//                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
//                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
//                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                              @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
//                              @Header(KafkaHeaders.GROUP_ID) String groupId
                            ) {
        LOGGER.info("test review:{}",data.size());
//        LOGGER.info("topic.quick.anno receive : \n"+
//                "data : "+data+"\n"+
//                "key : "+key+"\n"+
//                "partitionId : "+partition+"\n"+
//                "topic : "+topic+"\n"+
//                "groupId : "+groupId+"\n"+
//                "timestamp : "+ts+"\n"
//        );
        for (ConsumerRecord<String,String> record : data) {
            LOGGER.info(">>>>>>>>>>>>>>>>>>{}-partition:{}-offset{}",record.value(),record.partition(),record.offset());
        }
    }

}
