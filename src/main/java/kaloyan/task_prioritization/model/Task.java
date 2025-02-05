package kaloyan.task_prioritization.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Transient;
import kaloyan.task_prioritization.utils.Priority;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @Column
    private LocalDate dueDate;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isCompleted;

    @Column
    private String sentiment;

    @Transient
    @JsonIgnore
    private boolean isCritical;

    @JsonGetter("isCompleted")
    public boolean isCompleted() {
        return isCompleted;
    }
}
