package com.aggregation.repository;

import com.aggregation.entity.ProfileEntity;
import com.aggregation.controller.dto.request.RequestProfileCreateDto;
import com.aggregation.repository.mapper.ProfileEntityRowMapper;
import com.aggregation.shared.utils.Utils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_PROFILE =
            "INSERT INTO profiles (session_id, display_name, birthday, description,\n"
                    + "created_at, updated_at, last_online)\n"
                    + "VALUES (:sessionId, :displayName, :birthday, :description,\n"
                    + ":createdAt, :updatedAt, :lastOnline)";

    private static final String GET_PROFILE_BY_SESSION_ID =
            "SELECT id, session_id, display_name, birthday, description,\n"
                    + "is_deleted, created_at, updated_at, last_online\n"
                    + "FROM profiles\n"
                    + "WHERE session_id = :sessionId";

    public ProfileRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public ProfileEntity createProfile(RequestProfileCreateDto requestProfileCreateDto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("sessionId", requestProfileCreateDto.getSessionId())
                .addValue("displayName", requestProfileCreateDto.getDisplayName())
                .addValue("birthday", requestProfileCreateDto.getBirthday())
                .addValue("description", requestProfileCreateDto.getDescription())
                .addValue("isDeleted", false)
                .addValue("createdAt", Utils.getNowUtc())
                .addValue("updatedAt", null)
                .addValue("lastOnline", Utils.getNowUtc());
        namedParameterJdbcTemplate.update(CREATE_PROFILE, parameters);
        return namedParameterJdbcTemplate.queryForObject(
                GET_PROFILE_BY_SESSION_ID,
                parameters,
                new ProfileEntityRowMapper()
        );
    }

    @Override
    public ProfileEntity findBySessionID(String sessionId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("sessionId", sessionId);
        return namedParameterJdbcTemplate.queryForObject(
                GET_PROFILE_BY_SESSION_ID,
                parameters,
                new ProfileEntityRowMapper()
        );
    }
}
