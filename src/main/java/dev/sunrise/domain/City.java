package dev.sunrise.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints={@UniqueConstraint(name = "latitude_longitude", columnNames = {"latitude", "longitude"})},
        indexes={@Index(name = "name", columnList = "name")}
        )
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private GeoCoordinates coordinates;

    City() {}

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.coordinates = new GeoCoordinates(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public GeoCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id.equals(city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
