import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, catchError, throwError } from "rxjs";
import { Injectable } from "@angular/core";
import { Book } from "../models/book.model";
import { BASE_URL, PATHS } from "../config/api.config";
import { UpsertBookRequest } from "../models/dto/upsert-book-request.dto";
import { RestApiError } from "../models/dto/rest-api-error";

@Injectable()
export class BookApiService {
    private url = `${BASE_URL}${PATHS.BOOK}`

    constructor(private http: HttpClient) {}

    getAllBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(this.url);
    }

    getBookById(id: string): Observable<Book> {
        return this.http.get<Book>(`${this.url}/${id}`);
    }

    createBook(request: UpsertBookRequest): Observable<Book> {
        return this.http.post<Book>(this.url, request).pipe(
            catchError(error => throwError(() => {
                const restApiError = ((error as HttpErrorResponse).error) as RestApiError;
                console.log(restApiError);
                return restApiError;
            }))
        );
    }

    updateBook(id: string, request: UpsertBookRequest): Observable<Book> {
        return this.http.put<Book>(`${this.url}/${id}`, request).pipe(
            catchError(error => throwError(() => {
                const restApiError = ((error as HttpErrorResponse).error) as RestApiError;
                console.log(restApiError);
                return restApiError;
            }))
        );
    }

    deleteBookById(id: string): Observable<void> {
        return this.http.delete<void>(`${this.url}/${id}`);
    }
}