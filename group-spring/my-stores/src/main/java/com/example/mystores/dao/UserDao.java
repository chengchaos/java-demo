package com.example.mystores.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public UserDao(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 
     * @param userId
     * @return
     */
    public Map<String, Object> getUser(Integer userId) {

        String sql = "select id, name, pwd1 pwd from my_user a " +
                "where a.id = ?";

        LOGGER.debug("SQL: {}", sql);
        Map<String, Object> result = null;

        try {
            result = this.jdbcTemplate.queryForMap(sql, userId);
        } catch (DataAccessException dae) {
            LOGGER.warn("DataAccessException: {}", dae.getMessage());
        } catch (Exception e) {
            LOGGER.error("exception:" , e);
        }

        return Objects.isNull(result) ? Collections.emptyMap() : result;
    }


    /**
     *
     * @param name
     * @return
     */
    public Map<String, Object> getUserByName(String name) {

        String sql = "select id, name, pwd1 pwd from my_user a " +
                "where a.name = ?";
        LOGGER.debug("SQL: {}", sql);

        Map<String, Object> result = null;

        try {
            result = this.jdbcTemplate.queryForMap(sql, name);
        } catch (DataAccessException dae) {
            LOGGER.warn("DataAccessException: {}", dae.getMessage());
        } catch (Exception e) {
            LOGGER.error("exception:" , e);
        }

        return Objects.isNull(result) ? Collections.emptyMap() : result;
    }
}
