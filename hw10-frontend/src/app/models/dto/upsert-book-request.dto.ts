export class UpsertBookRequest {
    title: string
    authorId: number
    genreIds: number[]

    constructor(title: string, authorId: number, genreIds: number[]) {
        this.title = title;
        this.authorId = authorId;
        this.genreIds = genreIds;
    }
}