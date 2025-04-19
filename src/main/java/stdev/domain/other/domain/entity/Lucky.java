package stdev.domain.other.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter

@Entity
@NoArgsConstructor
@DynamicUpdate
public class Lucky {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String luckyImage;

    private String keyword;


    @Column(length = 1000)
    private String comment;

    @Builder
    public Lucky(String luckyImage, String keyword, String comment){
        this.luckyImage=luckyImage;
        this.keyword=keyword;
        this.comment=comment;
    }
}


