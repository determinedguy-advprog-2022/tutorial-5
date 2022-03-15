package id.ac.ui.cs.advprog.tutorial5.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "editor")
@Data
@NoArgsConstructor
public class Editor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @CreatedDate
    @Column(name = "registered_at")
    private Date registeredAt;

    @Column(name = "written_articles")
    private int writtenArticles;

    public Editor(String name, String email, int writtenArticles){
        this.name = name;
        this.email = email;
        this.writtenArticles = writtenArticles;
    }
}
