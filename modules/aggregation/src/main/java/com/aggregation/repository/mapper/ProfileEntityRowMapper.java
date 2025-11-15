package com.aggregation.repository.mapper;

import com.aggregation.entity.ProfileEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

public class ProfileEntityRowMapper implements RowMapper<ProfileEntity> {
    @Override
    public ProfileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProfileEntity(
                rs.getLong("id"),
                rs.getString("session_id"),
                rs.getString("display_name"),
                Optional.ofNullable(rs.getTimestamp("birthday"))
                        .map(Timestamp::toLocalDateTime).map(LocalDate::from).orElse(null),
                rs.getString("description"),
                rs.getBoolean("is_deleted"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                Optional.ofNullable(rs.getTimestamp("updated_at"))
                        .map(Timestamp::toLocalDateTime).orElse(null),
                rs.getTimestamp("last_online").toLocalDateTime()
        );
    }
}
