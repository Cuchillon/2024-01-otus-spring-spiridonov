import { Component, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { AuthorApiService } from '../../services/author.api.service';
import { Author } from '../../models/author.model';

@Component({
  selector: 'app-author',
  standalone: true,
  imports: [NgFor, HttpClientModule],
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css', '../styles/table.css'],
  providers: [AuthorApiService]
})
export class AuthorComponent implements OnInit {
  authors: Author[] = [];

  constructor(private authorApiService: AuthorApiService) {}

  ngOnInit(): void {
    this.authorApiService.getAllAuthors().subscribe({
      next: (data: Author[]) => this.authors = data
    });
  }
}
