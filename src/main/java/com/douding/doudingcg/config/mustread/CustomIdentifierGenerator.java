package com.douding.doudingcg.config.mustread;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自定义生成器
 */
@Slf4j
@Component
public class CustomIdentifierGenerator implements IdentifierGenerator {

    private final CustomSequence sequence;

    public CustomIdentifierGenerator() {
        this.sequence = new CustomSequence();
    }

    public CustomIdentifierGenerator(long workerId, long dataCenterId) {
        this.sequence = new CustomSequence(workerId, dataCenterId);
    }

    public CustomIdentifierGenerator(CustomSequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public Long nextId(Object entity) {
        return sequence.nextId();
    }
}
