import { NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Genre } from '../../models/genre.model';
import { GenreApiService } from '../../services/genre.api.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-genre',
  standalone: true,
  imports: [NgFor, HttpClientModule],
  templateUrl: './genre.component.html',
  styleUrls: ['./genre.component.css', '../styles/table.css'],
  providers: [GenreApiService]
})
export class GenreComponent implements OnInit {
  genres: Genre[] = [];

  constructor(private genreApiService: GenreApiService) {}

  ngOnInit(): void {
    this.genreApiService.getAllGenres().subscribe({
      next: (data: Genre[]) => this.genres = data
    });
  }
}
