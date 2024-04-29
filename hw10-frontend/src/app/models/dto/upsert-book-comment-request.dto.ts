export class UpsertBookCommentRequest {
    text: string
    bookId: number

    constructor(text: string, bookId: number) {
        this.text = text;
        this.bookId = bookId;
    }
}