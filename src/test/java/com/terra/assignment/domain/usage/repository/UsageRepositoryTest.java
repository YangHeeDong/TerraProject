package com.terra.assignment.domain.usage.repository;

import com.terra.assignment.domain.usage.entity.Usage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class UsageRepositoryTest {

    @Autowired
    private UsageRepository usageRepository;

    @Test
    public void saveUsage() {

        // given
        Usage usage = Usage.builder()
                .cpuUsage(0.12)
                .build();

        // when
        Usage savedUsage = usageRepository.save(usage);

        // then
        assertThat(savedUsage).isNotNull();
        assertThat(savedUsage.getId()).isNotNull();
        assertThat(savedUsage.getCpuUsage()).isEqualTo(0.12);

    }

}
