package stdev.domain.record.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import stdev.domain.dreamanalysis.domain.entity.DreamAnalysis;
import stdev.domain.dreamdiary.domain.entity.DreamDiary;
import stdev.domain.user.domain.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "dream_diary_id")
    private DreamDiary dreamDiary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "dream_analysis_id")
    private DreamAnalysis dreamAnalysis;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdAt;

    @Builder
    public Record(DreamDiary dreamDiary, User user, DreamAnalysis dreamAnalysis) {
        this.dreamDiary = dreamDiary;
        this.user = user;
        this.dreamAnalysis = dreamAnalysis;
    }
}
