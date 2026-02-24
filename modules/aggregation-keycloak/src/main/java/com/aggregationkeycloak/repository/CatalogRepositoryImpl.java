package com.aggregationkeycloak.repository;

import com.aggregationkeycloak.controller.dto.request.RequestCatalogCreateDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogListGetDto;
import com.aggregationkeycloak.controller.dto.request.RequestCatalogUpdateDto;
import com.aggregationkeycloak.entity.CatalogEntity;
import com.aggregationkeycloak.entity.PaginationEntity;
import com.aggregationkeycloak.repository.mapper.CatalogEntityRowMapper;
import com.aggregationkeycloak.shared.exception.InternalServerException;
import com.aggregationkeycloak.shared.exception.NotFoundException;
import com.aggregationkeycloak.shared.utils.Utils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CatalogRepositoryImpl implements CatalogRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Запрос для создания каталога
    private static final String CREATE_CATALOG =
            "INSERT INTO catalogs (name, description, created_at)\n"
                    + "VALUES (:name, :description, :createdAt)\n"
                    + "RETURNING id, name, description, created_at, updated_at";

    // Запрос для обновления каталога
    private static final String UPDATE_CATALOG =
            "UPDATE catalogs\n"
                    + "SET name = :name,\n"
                    + "    description = :description,\n"
                    + "    updated_at = :updatedAt\n"
                    + "WHERE id = :id\n"
                    + "RETURNING id, name, description, created_at, updated_at";

    // Запрос для получения каталога по ID
    private static final String GET_CATALOG_BY_ID =
            "SELECT id, name, description, created_at, updated_at\n"
                    + "FROM catalogs\n"
                    + "WHERE id = :id";

    // Запрос для получения общего количества записей
    private static final String COUNT_CATALOGS =
            "SELECT COUNT(*) FROM catalogs";

    // Запрос для получения списка каталогов с пагинацией
    private static final String GET_CATALOG_LIST =
            "SELECT id, name, description, created_at, updated_at\n"
                    + "FROM catalogs\n"
                    + "ORDER BY id\n"
                    + "LIMIT :limit OFFSET :offset";

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
    public CatalogEntity updateCatalog(RequestCatalogUpdateDto dto) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id", dto.id())
                    .addValue("name", dto.name())
                    .addValue("description", dto.description())
                    .addValue("updatedAt", Utils.getNowUtc());

            return namedParameterJdbcTemplate.queryForObject(
                    UPDATE_CATALOG,
                    parameters,
                    new CatalogEntityRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(
                    "catalog not found",
                    "catalog with id=" + dto.id() + " does not exist. " + e.getMessage()
            );
        } catch (Exception e) {
            throw new InternalServerException(
                    "internal server error",
                    "internal server error. " + e.getMessage()
            );
        }
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

    @Override
    public PaginationEntity<List<CatalogEntity>> findCatalogList(RequestCatalogListGetDto dto) {
        int page = dto.page();
        int size = dto.size();
        int offset = (page - 1) * size;
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("limit", size)
                .addValue("offset", offset);
        List<CatalogEntity> content = namedParameterJdbcTemplate.query(
                GET_CATALOG_LIST,
                parameters,
                new CatalogEntityRowMapper()
        );
        MapSqlParameterSource countParams = new MapSqlParameterSource();
        Integer numberEntities = namedParameterJdbcTemplate.queryForObject(
                COUNT_CATALOGS,
                countParams,
                Integer.class
        );
        PaginationEntity<List<CatalogEntity>> paginationEntity =
                new PaginationEntity<>(page, size, numberEntities);
        paginationEntity.setContent(content);
        return paginationEntity;
    }
}
