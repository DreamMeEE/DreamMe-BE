package stdev.domain.dreamanalysis.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import stdev.domain.record.domain.entity.Record;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class DreamAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String dreamImageUrl;

    @Column(name = "dream_comment", length = 1000)
    private String dreamComment;

    @Column(nullable = false)
    private String dreamCategory; // enum 귀찮다잉

    @Column(nullable = false)
    private boolean flag;

    @OneToOne(mappedBy = "dreamAnalysis")
    private Record record;


    @Builder
    public DreamAnalysis(String dreamImageUrl, String dreamComment, String dreamCategory, boolean flag) {
        this.dreamImageUrl = dreamImageUrl;
        this.dreamComment = dreamComment;
        this.dreamCategory = dreamCategory;
        this.flag = flag;
    }
}
