package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE  FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
       @NamedQuery(name = Meal.BY_ID, query = "SELECT u FROM Meal u   WHERE u.id=:id  AND u.user.id=:user_id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT  m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC "),
        @NamedQuery(name = Meal.BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id AND m.dateTime >=:startdate AND m.dateTime<=:enddate ORDER BY m.dateTime DESC"),
})



//,columnNames= "date_time",
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id" , name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {


    public static final String DELETE = "Meal.delete";
   public static final String BY_ID = "Meal.getById";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String BETWEEN= "Meal.between";

    @Column(name = "date_time", columnDefinition = "timestamp")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name= "description" , nullable = false, unique = true)
    @NotBlank
    @Size(max=100)
    private String description;

    @Column(name = "calories", columnDefinition = "int")
    @Range(min = 10, max = 10000)
    private int calories;

//      @Column(s) not allowed on a @ManyToOne property: ru.javawebinar.topjava.model.Meal.user
    //     User ref = em.getReference(User.class, id);
  //     em.remove(ref);
    //   @JoinColumn(name = "user_id")
    //  User ref = em.getReference(User.class, user_id);
//      meal.setUser(ref);
    @ManyToOne(fetch = FetchType.LAZY)
//.EAGER
    @JoinColumn(name = "user_id")
    @CollectionTable(name = "users" , joinColumns = @JoinColumn(name = "user_id"))
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
