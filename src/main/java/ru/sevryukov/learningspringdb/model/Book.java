package ru.sevryukov.learningspringdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "\"name\"")
    private String name;

    @BatchSize(size = 5)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "book_comment", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @BatchSize(size = 5)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Author> authors;

    @BatchSize(size = 5)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ManyToMany(targetEntity = Genre.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Genre> genres;

    public Book(String name, List<Author> authors, List<Genre> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }
}
