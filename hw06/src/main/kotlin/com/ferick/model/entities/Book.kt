package com.ferick.model.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.NamedAttributeNode
import jakarta.persistence.NamedEntityGraph
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "books")
@NamedEntityGraph(
    name = "book-author-comments-entity-graph",
    attributeNodes = [
        NamedAttributeNode("author"),
        NamedAttributeNode("bookComments")
    ]
)
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "title")
    val title: String,

    @ManyToOne(targetEntity = Author::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: Author,

    @OneToMany(targetEntity = BookComment::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    val bookComments: List<BookComment> = emptyList(),

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre::class, fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinTable(name = "books_genres",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")]
    )
    val genres: List<Genre> = emptyList()
)
