package com.dt.utils.config;

import com.dt.utils.kafka.KafkaLogsConsumer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 批量消费
 * @Author jiangyao
 * @Date 2020/4/16 10:37
 **/
@Configuration
public class KafakaBatchConfig {

    private static final Logger log= LoggerFactory.getLogger(KafakaBatchConfig.class);

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093,127.0.0.1:9094,127.0.0.1:9095");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true); //自动提交
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");  //提交时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        //一次拉取消息数量
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean(name="batchContainerFactory")
    public ConcurrentKafkaListenerContainerFactory listenerContainer() {
        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
        container.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        //设置并发量，小于或等于Topic的分区数
        container.setConcurrency(1);
        //设置为批量监听
        container.setBatchListener(true);
        return container;
    }

    @Bean
    public NewTopic batchTopic() {
        return new NewTopic(KafkaLogsConsumer.TEST_TOPIC, 4, (short) 1);
    }

}
