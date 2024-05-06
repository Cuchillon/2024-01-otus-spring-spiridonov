import { HttpClient } from "@angular/common/http";
import { Observable, map } from "rxjs";
import { Injectable } from "@angular/core";
import { Genre } from "../models/genre.model";
import { BASE_URL, PATHS } from "../config/api.config";

@Injectable()
export class GenreApiService {
    private url = `${BASE_URL}${PATHS.GENRE}`

    constructor(private http: HttpClient) {}

    getAllGenres(): Observable<Genre[]> {
        return this.http.get<Genre[]>(this.url);
    }
}