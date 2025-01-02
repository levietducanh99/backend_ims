package com.project.ims.service.impl;

import com.project.ims.service.ReportService;
import com.project.ims.model.dto.PeriodStatisticsDTO;
import com.project.ims.model.dto.ProductStatisticsDTO;
import com.project.ims.model.dto.PeriodDataDTO;
import com.project.ims.model.dto.ProductPeriodDataDTO;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PeriodStatisticsDTO getStatisticsByPeriod(String type, String period, int year, Integer month) {
        String sql = buildPeriodQuery(type, period);
        List<Object[]> results = executeQuery(sql, type, period, year, month);
        
        PeriodStatisticsDTO dto = new PeriodStatisticsDTO();
        dto.setType(type);
        dto.setPeriod(period);
        dto.setYear(year);
        dto.setMonth(month);
        dto.setData(mapPeriodData(results, period));
        
        return dto;
    }

    @Override
    public ProductStatisticsDTO getStatisticsByProduct(String type, int productId, String period, int year, Integer month) {
        String sql = buildProductQuery(type, period);
        List<Object[]> results = executeProductQuery(sql, type, productId, period, year, month);
        
        ProductStatisticsDTO dto = new ProductStatisticsDTO();
        dto.setType(type);
        dto.setProductId(productId);
        dto.setPeriod(period);
        dto.setYear(year);
        dto.setMonth(month);
        dto.setData(mapProductData(results, period));
        
        return dto;
    }

    private String buildPeriodQuery(String type, String period) {
        String tableName = type.equals("import") ? "import" : "export";
        
        switch (period) {
            case "week":
                return "SELECT EXTRACT(WEEK FROM \"createDate\") as period_num, COUNT(*) as total " +
                       "FROM \"" + tableName + "\" " +
                       "WHERE EXTRACT(YEAR FROM \"createDate\") = ?1 " +
                       "AND EXTRACT(MONTH FROM \"createDate\") = ?2 " +
                       "GROUP BY EXTRACT(WEEK FROM \"createDate\") " +
                       "ORDER BY period_num";
            case "month":
                return "SELECT EXTRACT(MONTH FROM \"createDate\") as period_num, COUNT(*) as total " +
                       "FROM \"" + tableName + "\" " +
                       "WHERE EXTRACT(YEAR FROM \"createDate\") = ?1 " +
                       "GROUP BY EXTRACT(MONTH FROM \"createDate\") " +
                       "ORDER BY period_num";
            case "year":
                return "SELECT EXTRACT(YEAR FROM \"createDate\") as period_num, COUNT(*) as total " +
                       "FROM \"" + tableName + "\" " +
                       "GROUP BY EXTRACT(YEAR FROM \"createDate\") " +
                       "ORDER BY period_num";
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }

    private String buildProductQuery(String type, String period) {
        String tableName = type.equals("import") ? "product_import" : "product_export";
        String joinTable = type.equals("import") ? "import" : "export";
        String joinColumn = type.equals("import") ? "importid" : "exportid";
        
        switch (period) {
            case "week":
                return "SELECT EXTRACT(WEEK FROM i.\"createDate\") as period_num, SUM(id.quantity) as quantity " +
                       "FROM \"" + tableName + "\" id " +
                       "JOIN \"" + joinTable + "\" i ON id.\"" + joinColumn + "\" = i.\"" + joinColumn + "\" " +
                       "WHERE id.\"productid\" = ?1 " +
                       "AND EXTRACT(YEAR FROM i.\"createDate\") = ?2 " +
                       "AND EXTRACT(MONTH FROM i.\"createDate\") = ?3 " +
                       "GROUP BY EXTRACT(WEEK FROM i.\"createDate\") " +
                       "ORDER BY period_num";
            case "month":
                return "SELECT EXTRACT(MONTH FROM i.\"createDate\") as period_num, SUM(id.quantity) as quantity " +
                       "FROM \"" + tableName + "\" id " +
                       "JOIN \"" + joinTable + "\" i ON id.\"" + joinColumn + "\" = i.\"" + joinColumn + "\" " +
                       "WHERE id.\"productid\" = ?1 " +
                       "AND EXTRACT(YEAR FROM i.\"createDate\") = ?2 " +
                       "GROUP BY EXTRACT(MONTH FROM i.\"createDate\") " +
                       "ORDER BY period_num";
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }


    private List<Object[]> executeQuery(String sql, String type, String period, int year, Integer month) {
        var query = entityManager.createNativeQuery(sql);
        
        if (period.equals("week")) {
            query.setParameter(1, year);
            query.setParameter(2, month);
        } else if (period.equals("month")) {
            query.setParameter(1, year);
        }
        
        return query.getResultList();
    }

    private List<Object[]> executeProductQuery(String sql, String type, int productId, String period, int year, Integer month) {
        var query = entityManager.createNativeQuery(sql)
            .setParameter(1, productId)
            .setParameter(2, year);
            
        if (period.equals("week")) {
            query.setParameter(3, month);
        }
        
        return query.getResultList();
    }

    private List<PeriodDataDTO> mapPeriodData(List<Object[]> results, String period) {
        List<PeriodDataDTO> data = new ArrayList<>();
        
        for (Object[] result : results) {
            PeriodDataDTO dto = new PeriodDataDTO();
            int periodNum = ((Number) result[0]).intValue();
            int total = ((Number) result[1]).intValue();
            
            if (period.equals("week")) {
                dto.setWeek(periodNum);
            } else if (period.equals("month")) {
                dto.setMonth(periodNum);
            }
            dto.setTotal(total);
            data.add(dto);
        }
        
        return data;
    }

    private List<ProductPeriodDataDTO> mapProductData(List<Object[]> results, String period) {
        List<ProductPeriodDataDTO> data = new ArrayList<>();
        
        for (Object[] result : results) {
            ProductPeriodDataDTO dto = new ProductPeriodDataDTO();
            int periodNum = ((Number) result[0]).intValue();
            int quantity = ((Number) result[1]).intValue();
            
            if (period.equals("week")) {
                dto.setWeek(periodNum);
            } else if (period.equals("month")) {
                dto.setMonth(periodNum);
            }
            dto.setQuantity(quantity);
            data.add(dto);
        }
        
        return data;
    }
}