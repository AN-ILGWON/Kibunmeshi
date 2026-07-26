package com.kibunmeshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FullSchemaCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/full-schema-check")
    @ResponseBody
    public Map<String, Object> fullSchemaCheck() {
        try {
            // 모든 테이블 확인
            List<Map<String, Object>> tables = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'kibunmeshi' AND TABLE_TYPE = 'BASE TABLE' ORDER BY TABLE_NAME"
            );
            
            Map<String, List<Map<String, Object>>> allColumns = new HashMap<>();
            
            for (Map<String, Object> table : tables) {
                String tableName = (String) table.get("TABLE_NAME");
                List<Map<String, Object>> columns = jdbcTemplate.queryForList(
                    "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, COLUMN_DEFAULT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'kibunmeshi' AND TABLE_NAME = '" + tableName + "' ORDER BY ORDINAL_POSITION"
                );
                allColumns.put(tableName, columns);
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("tables", tables);
            result.put("all_columns", allColumns);
            return result;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "스키마 확인 실패: " + e.getMessage());
            error.put("stack_trace", e.getClass().getName() + ": " + e.getMessage());
            return error;
        }
    }
}
