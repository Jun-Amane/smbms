/**
 * Package: moe.zzy040330.smbms.service
 * File: DashboardSerivce.java
 * Author: Ziyu ZHOU
 * Date: 07/01/2025
 * Time: 22:23
 * Description: calc statistic info for dashboard
 */
package moe.zzy040330.smbms.service;

import moe.zzy040330.smbms.dto.DashboardStatsDto;
import moe.zzy040330.smbms.mapper.StatMapper;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final StatMapper statMapper;

    public DashboardService(StatMapper statMapper) {
        this.statMapper = statMapper;
    }

    /**
     *  statistic info will be shown in Provider page
     *
     * @return nothing else, just the Dto
     */
    public DashboardStatsDto getStats() {
        var dto = statMapper.findDashboardStatsCountData();
        dto.setQuarterlyStats(statMapper.findQuarterlyStat());

        return dto;
    }

}
