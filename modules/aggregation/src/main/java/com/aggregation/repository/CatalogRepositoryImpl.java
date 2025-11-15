package com.aggregation.repository;

import com.aggregation.repository.mapper.CatalogEntityRowMapper;
import com.aggregation.shared.exception.InternalServerException;
import com.aggregation.shared.exception.NotFoundException;
import com.aggregation.shared.utils.Utils;
import com.aggregation.controller.dto.request.RequestCatalogCreateDto;
import com.aggregation.entity.CatalogEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CatalogRepositoryImpl implements CatalogRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_CATALOG =
            "INSERT INTO catalogs (name, description, created_at)\n"
                    + "VALUES (:name, :description, :createdAt)\n"
                    + "RETURNING id, name, description, created_at, updated_at";

    private static final String GET_CATALOG_BY_ID =
            "SELECT id, name, description, created_at, updated_at\n"
                    + "FROM catalogs\n"
                    + "WHERE id = :id";

    public CatalogRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public CatalogEntity createCatalog(RequestCatalogCreateDto dto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", dto.name())
                .addValue("description", dto.description())
                .addValue("createdAt", Utils.getNowUtc())
                .addValue("updatedAt", null)
                .addValue("lastOnline", Utils.getNowUtc());
        return namedParameterJdbcTemplate.queryForObject(
                CREATE_CATALOG,
                parameters,
                new CatalogEntityRowMapper()
        );
    }

    @Override
    public CatalogEntity findByID(Long id) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id", id);
            return namedParameterJdbcTemplate.queryForObject(
                    GET_CATALOG_BY_ID,
                    parameters,
                    new CatalogEntityRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                    "catalog not found",
                    "catalog with id=" + id + " does not exist. " + e.getMessage()
            );
        } catch (Exception e) {
            throw new InternalServerException("internal server error", "internal server error. " + e.getMessage());
        }
    }
}
