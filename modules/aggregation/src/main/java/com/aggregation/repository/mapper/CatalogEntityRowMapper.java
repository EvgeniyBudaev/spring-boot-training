package com.aggregation.repository.mapper;

import com.aggregation.entity.CatalogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

public class CatalogEntityRowMapper implements RowMapper<CatalogEntity> {
    @Override
    public CatalogEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Timestamp createdAtTs = rs.getTimestamp("created_at", utcCalendar);
        Instant createdAt = createdAtTs != null ? createdAtTs.toInstant() : null;

        Timestamp updatedAtTs = rs.getTimestamp("updated_at", utcCalendar);
        Instant updatedAt = updatedAtTs != null ? updatedAtTs.toInstant() : null;

        return CatalogEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
