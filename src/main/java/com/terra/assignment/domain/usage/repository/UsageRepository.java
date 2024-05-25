package com.terra.assignment.domain.usage.repository;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsageRepository extends JpaRepository<Usage, Long> {

    @Query(value = "SELECT * FROM USAGE " +
            "WHERE YEAR_COLUMN = :year "+
            "AND MONTH_COLUMN=:month "+
            "AND DAY_COLUMN=:day " +
            "AND HOUR_COLUMN BETWEEN :startHour AND :endHour " +
            "ORDER BY CREATED_DATE",nativeQuery = true)
    List<Usage> findUsagesPerMin(@Param("year") Integer year,
                               @Param("month") Integer month,
                               @Param("day") Integer day,
                               @Param("startHour") Integer startHour,
                               @Param("endHour") Integer endHour);

    @Query(value = "SELECT new com.terra.assignment.domain.usage.dto.UsageHour(" +
            "u.yearColumn, u.monthColumn, u.dayColumn, u.hourColumn, " +
            "MIN(u.cpuUsage), MAX(u.cpuUsage), AVG(u.cpuUsage)) " +
            "FROM Usage u " +
            "GROUP BY u.yearColumn,u.monthColumn,u.dayColumn,u.hourColumn "+
            "HAVING u.yearColumn=:year AND u.monthColumn=:month AND u.dayColumn=:day")
    List<UsageHour> findUsagesPerHour(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);

    @Query(value = "SELECT new com.terra.assignment.domain.usage.dto.UsageDay(" +
            "u.yearColumn, u.monthColumn, u.dayColumn, " +
            "MIN(u.cpuUsage), MAX(u.cpuUsage), AVG(u.cpuUsage)) " +
            "FROM Usage u " +
            "GROUP BY u.yearColumn, u.monthColumn, u.dayColumn "+
            "HAVING u.yearColumn=:year AND u.monthColumn=:month AND u.dayColumn BETWEEN :startDay AND :endDay")
    List<UsageDay> findUsagesPerDay(@Param("year") Integer year, @Param("month") Integer month, @Param("startDay") Integer startDay, @Param("endDay") Integer endDay);
}
