import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

/**
 * @Author jiangyao
 * @Date 2020/4/9 16:52
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class redis {
    @Autowired
    StringRedisTemplate redisTemplate;

    ValueOperations<String, String> stringRedis;

    @PostConstruct
    public void init(){
        stringRedis=redisTemplate.opsForValue();
    }


    @Test
    public void testString (){
        stringRedis.set("name", "丁洁");
        System.out.println(stringRedis.get("name"));
    }
}
