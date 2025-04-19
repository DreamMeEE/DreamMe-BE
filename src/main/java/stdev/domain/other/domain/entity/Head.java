package stdev.domain.other.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class Head {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headImage;

    @Column( length = 4000)
    private String headContent;

    // 꿈 종류
    private String headCategory;
}
