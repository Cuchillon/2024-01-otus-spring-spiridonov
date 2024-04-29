import { Book } from "./book.model"

export class BookComment {
    id: number
    text: string
    book: Book

    constructor(id: number, text: string, book: Book) {
        this.id = id
        this.text = text
        this.book = book
    }
}