import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { BASE_URL, PATHS } from "../config/api.config";
import { BookComment } from "../models/book-comment.model";
import { UpsertBookCommentRequest } from "../models/dto/upsert-book-comment-request.dto";

@Injectable()
export class BookCommentApiService {
    private url = `${BASE_URL}${PATHS.BOOK_COMMENT}`

    constructor(private http: HttpClient) {}

    getBookCommentsByBookId(bookId: string): Observable<BookComment[]> {
        return this.http.get<BookComment[]>(`${this.url}${PATHS.BOOK}/${bookId}`);
    }

    getBookCommentById(id: string): Observable<BookComment> {
        return this.http.get<BookComment>(`${this.url}/${id}`);
    }

    createBookComment(request: UpsertBookCommentRequest): Observable<BookComment> {
        return this.http.post<BookComment>(this.url, request);
    }

    updateBookComment(id: string, request: UpsertBookCommentRequest): Observable<BookComment> {
        return this.http.put<BookComment>(`${this.url}/${id}`, request);
    }

    deleteBookCommentById(id: string): Observable<void> {
        return this.http.delete<void>(`${this.url}/${id}`);
    }
}