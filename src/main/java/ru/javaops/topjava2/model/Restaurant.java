package ru.javaops.topjava2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.topjava2.HasId;

import java.util.List;

@Entity
@Table(name = "restaurant")
@ToString(exclude = "dishes")
@Getter
@Setter
public class Restaurant extends NamedEntity implements HasId {

    @OneToMany(mappedBy = "restaurant")
    public List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name) {
        this(null, name);
    }
}
