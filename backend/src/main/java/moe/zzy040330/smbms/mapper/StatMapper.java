/**
 * Package: moe.zzy040330.smbms.mapper
 * File: StatMapper.java
 * Author: Ziyu ZHOU
 * Date: 07/01/2025
 * Time: 21:51
 * Description: Mappers to query statics
 */
package moe.zzy040330.smbms.mapper;

import moe.zzy040330.smbms.dto.BillStatsDto;
import moe.zzy040330.smbms.dto.DashboardStatsDto;
import moe.zzy040330.smbms.dto.ProviderStatsDto;

import java.util.List;

public interface StatMapper {

    DashboardStatsDto findDashboardStatsCountData();

    List<DashboardStatsDto.QuarterlyStat> findQuarterlyStat();

    List<BillStatsDto.PaymentStatus> findPaymentStatus();

    List<BillStatsDto.ProductSale> findProductSale();

    List<ProviderStatsDto.ProviderOrderContribution> findProviderOrderContribution();

    List<ProviderStatsDto.ProviderSale> findProviderSale();


}
