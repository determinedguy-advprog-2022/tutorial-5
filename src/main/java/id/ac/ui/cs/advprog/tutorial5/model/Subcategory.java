package id.ac.ui.cs.advprog.tutorial5.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Subcategory")
@Data
@NoArgsConstructor
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "subcategory_name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "main_category")
    private Category mainCategory;

    @Column(name = "num_articles")
    private int numArticles;

    public Subcategory(String name, Category category, int numArticles){
        this.name = name;
        this.mainCategory = category;
        this.numArticles = numArticles;
    }

}
