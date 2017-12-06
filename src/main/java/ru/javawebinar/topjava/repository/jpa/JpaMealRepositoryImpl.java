package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
        //(readOnly = false)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

   // m.user.id=:userId
    @Override
    public Meal save(Meal meal, int user_id) {
        User ref = em.getReference(User.class, user_id);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        }else{
           return  em.merge(meal);

        }
    }

    @Override
    public boolean delete(int id, int user_id) {

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", user_id)
                .executeUpdate() != 0;

    }
    @Transactional(readOnly = true)
    @Override
    public Meal get(int id, int user_id) {
        return em.createNamedQuery(Meal.BY_ID, Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", user_id)
                .getSingleResult();
    }
    @Transactional(readOnly = true)
    @Override
    public List<Meal> getAll(int user_id) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", user_id)
                .getResultList();
    }
    @Transactional(readOnly = true)
    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int user_id) {
        return em.createNamedQuery(Meal.BETWEEN, Meal.class)
                .setParameter("startdate", startDate )
                .setParameter("enddate", endDate)
                .setParameter("user_id", user_id)
                .getResultList();
    }
}