import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Author } from "../models/author.model";
import { Injectable } from "@angular/core";
import { BASE_URL, PATHS } from "../config/api.config";

@Injectable()
export class AuthorApiService {
    private url = `${BASE_URL}${PATHS.AUTHOR}`

    constructor(private http: HttpClient) {}

    getAllAuthors(): Observable<Author[]> {
        return this.http.get<Author[]>(this.url);
    }
}