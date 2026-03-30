package com.example.angula.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "videos")
public class AngulaYt {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long Id;
    @Column(unique = true, name = "video_id", length = 15, nullable = false)
    String videoId;
    @Column(name = "video_title")
    String videoTitle;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AngulaYt)) {
            return false;
        }
        return Id != null && Id.equals(((AngulaYt) o).Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
