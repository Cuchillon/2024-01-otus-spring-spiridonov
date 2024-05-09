export class UpsertBookCommentRequest {
    text: string
    bookId: string

    constructor(text: string, bookId: string) {
        this.text = text;
        this.bookId = bookId;
    }
}