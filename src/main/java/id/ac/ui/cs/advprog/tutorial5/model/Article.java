package id.ac.ui.cs.advprog.tutorial5.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
public class Article {

    // Id penulis sebagai salah satu field JSON request
    // Sebuah artikel berisikan judul, isi, waktu artikel tersebut dibuat dan waktu di-update.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "author_id")
    private int authorId;

    // TODO: Category

    @Column(name = "title")
    private String title;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;

    @Column(name = "content")
    private String content;

    public Article(String title, int authorId, String content){
        this.title = title;
        this.authorId = authorId;
        this.content = content;
    }

}
