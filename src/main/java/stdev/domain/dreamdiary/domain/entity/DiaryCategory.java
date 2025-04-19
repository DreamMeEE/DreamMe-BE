package stdev.domain.dreamdiary.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import stdev.domain.record.domain.entity.Record;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class DiaryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "dream_diary_id")
    private DreamDiary dreamDiary;

    @Builder
    public DiaryCategory(String name, DreamDiary dreamDiary) {

        this.name = name;
        this.dreamDiary = dreamDiary;
    }
}
