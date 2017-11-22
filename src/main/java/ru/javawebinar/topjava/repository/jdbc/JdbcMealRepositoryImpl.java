package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {


    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER1
            = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    private DataSource dataSource;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource  dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int user_id) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", user_id)
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET  datetime=:datetime , description=:description ,calories=:calories  "
                            + " WHERE id=:id AND user_id=:user_id", map);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int user_id) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id , user_id) != 0;}


    @Override
    public Meal get(int id, int  user_id) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM  meals WHERE id=? AND user_id=? ", ROW_MAPPER1, id , user_id);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int user_id) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", ROW_MAPPER1 , user_id);
               // jdbcTemplate.query("SELECT * FROM meals ORDER BY description", ROW_MAPPER1 );
        //list  must be sorted- last eat at up   WHERE user_id=?

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}
