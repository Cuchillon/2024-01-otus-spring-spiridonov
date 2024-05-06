import { Author } from "../models/author.model";
import { BookComment } from "../models/book-comment.model";
import { Book } from "../models/book.model";
import { Genre } from "../models/genre.model";

export const authors: Author[] = [
    new Author(1, 'Conan Quasi-Doyle'),
    new Author(2, 'Emmanuelle Quasi-Arsan'),
    new Author(3, 'Bram Quasi-Stoker')
  ]

export const genres: Genre[] = [
    new Genre(1, 'Science fiction'),
    new Genre(2, 'Detective fiction'),
    new Genre(3, 'Thriller'),
    new Genre(4, 'Horror'),
    new Genre(5, 'Erotic'),
    new Genre(6, 'Porn')
  ]

export const books: Book[] = [
    new Book(1, 'Sherlock Holmes in Space', new Author(1, 'Conan Quasi-Doyle'), 
    [new Genre(1, 'Science fiction'), new Genre(2, 'Detective fiction'),]),
    new Book(2, 'Emmanuelle and the Night Chase', new Author(2, 'Emmanuelle Quasi-Arsan'), 
    [new Genre(3, 'Thriller'), new Genre(5, 'Erotic'),]),
    new Book(3, 'Dracula and the Big Dildo', new Author(3, 'Bram Quasi-Stoker'), 
    [new Genre(4, 'Horror'), new Genre(6, 'Porn'),]),
  ]

// export const comments: BookComment[] = [
//     new BookComment(1, 'Awesome book, like it', 1),
//     new BookComment(2, 'Holmes in Space is something new', 1),
//     new BookComment(3, 'Emmanuelle is very sexy as always', 2),
//     new BookComment(4, 'Waiting for a new book with Emmanuelle and Emmanuel Macron', 2),
//     new BookComment(5, 'The dildo is OK', 3),
//     new BookComment(6, 'It is a good fiction enough to fall asleep', 3),
//   ]

export function remove<T>(array: T[], index: number): void {
  array.splice(index, 1)
}
