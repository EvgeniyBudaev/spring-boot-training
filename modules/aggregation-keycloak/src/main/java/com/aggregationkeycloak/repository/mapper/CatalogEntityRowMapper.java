package com.aggregationkeycloak.repository.mapper;

import com.aggregationkeycloak.entity.CatalogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class CatalogEntityRowMapper implements RowMapper<CatalogEntity> {
    @Override
    public CatalogEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Timestamp createdAtTs = rs.getTimestamp("created_at", utcCalendar);
        Instant createdAt = createdAtTs != null ? createdAtTs.toInstant() : null;

        Timestamp updatedAtTs = rs.getTimestamp("updated_at", utcCalendar);
        Instant updatedAt = updatedAtTs != null ? updatedAtTs.toInstant() : null;

        UUID createdBy = (UUID) rs.getObject("created_by");

        return CatalogEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
