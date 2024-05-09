import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book.model';
import { NgbModule, NgbModal, NgbTooltipModule, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BookApiService } from '../../services/book.api.service';
import { UpsertBookRequest } from '../../models/dto/upsert-book-request.dto';
import { AuthorApiService } from '../../services/author.api.service';
import { GenreApiService } from '../../services/genre.api.service';
import { Author } from '../../models/author.model';
import { Genre } from '../../models/genre.model';

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [NgFor, NgIf, NgbModule, FormsModule, NgbTooltipModule, NgbAlertModule, HttpClientModule],
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css', '../styles/table.css', '../styles/button.css'],
  providers: [BookApiService, AuthorApiService, GenreApiService]
})
export class BookComponent implements OnInit {
  books: Book[] = [];
  authors: Author[] = [];
  genres: Genre[] = [];
  title: string = '';
  authorId: string = '';
  genreIds: string[] = [];
  errorMessage: string|undefined;


  constructor(
    private modalService: NgbModal, 
    private router: Router, 
    private bookApiService: BookApiService,
    private authorApiService: AuthorApiService,
    private genreApiService: GenreApiService
  ) {}

  ngOnInit(): void {
    this.bookApiService.getAllBooks().subscribe({
      next: (data: Book[]) => this.books = data
    });
    this.authorApiService.getAllAuthors().subscribe({
      next: (data: Author[]) => this.authors = data
    });
    this.genreApiService.getAllGenres().subscribe({
      next: (data: Genre[]) => this.genres = data
    });
  }

  openModalFunction(content:any){
    this.modalService.open(content, { size: 'lg' });
  }

  closeModalFunction(){
    this.modalService.dismissAll();
    this.clearInputs();
  }

  addBook() {
    let request = new UpsertBookRequest(this.title, this.authorId, this.genreIds);
    this.bookApiService.createBook(request).subscribe(
      book => this.books.push(book), 
      err => this.errorMessage = err
    );
    this.closeModalFunction();
    this.clearInputs();
  }

  goToBook(id: string) {
    this.router.navigate(['/book-details', id])
  }

  private clearInputs() {
    this.title = '';
    this.authorId = '';
    this.genreIds = [];
  }
}
