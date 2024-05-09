export class UpsertBookRequest {
    title: string
    authorId: string
    genreIds: string[]

    constructor(title: string, authorId: string, genreIds: string[]) {
        this.title = title;
        this.authorId = authorId;
        this.genreIds = genreIds;
    }
}