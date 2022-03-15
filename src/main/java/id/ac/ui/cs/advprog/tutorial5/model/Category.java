package id.ac.ui.cs.advprog.tutorial5.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "num_articles")
    private int numArticles;

    public Category(String name, int numArticles){
        this.name = name;
        this.numArticles = numArticles;
    }

}
