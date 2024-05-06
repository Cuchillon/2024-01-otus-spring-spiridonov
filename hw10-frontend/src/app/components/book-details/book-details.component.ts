import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from '../../models/book.model';
import { NgFor } from '@angular/common';
import { BookComment } from '../../models/book-comment.model';
import { FormsModule } from '@angular/forms';
import { NgbModal, NgbModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { BookApiService } from '../../services/book.api.service';
import { BookCommentApiService } from '../../services/book-comment.api.service';
import { UpsertBookRequest } from '../../models/dto/upsert-book-request.dto';
import { UpsertBookCommentRequest } from '../../models/dto/upsert-book-comment-request.dto';
import { HttpClientModule } from '@angular/common/http';
import { AuthorApiService } from '../../services/author.api.service';
import { GenreApiService } from '../../services/genre.api.service';
import { Author } from '../../models/author.model';
import { Genre } from '../../models/genre.model';

@Component({
  selector: 'app-book-details',
  standalone: true,
  imports: [NgFor, NgbModule, FormsModule, NgbTooltipModule, HttpClientModule],
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css', '../styles/table.css', '../styles/button.css'],
  providers: [BookApiService, BookCommentApiService, AuthorApiService, GenreApiService]
})
export class BookDetailsComponent implements OnInit {
  book: Book|undefined;
  bookComments: BookComment[] = [];
  authors: Author[] = [];
  genres: Genre[] = [];

  title: string = '';
  authorId: string = '';
  genreIds: string[] = [];
  commentText: string = '';

  constructor(
    private route: ActivatedRoute, 
    private modalService: NgbModal, 
    private router: Router, 
    private bookApiService: BookApiService,
    private bookCommentApiService: BookCommentApiService,
    private authorApiService: AuthorApiService,
    private genreApiService: GenreApiService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => 
      this.bookApiService.getBookById(params["id"]).subscribe(book => this.book = book)
    );
    this.route.params.subscribe(params => 
      this.bookCommentApiService.getBookCommentsByBookId(params["id"]).subscribe({
        next: (data: BookComment[]) => this.bookComments = data
      })
    );
    this.authorApiService.getAllAuthors().subscribe({
      next: (data: Author[]) => this.authors = data
    });
    this.genreApiService.getAllGenres().subscribe({
      next: (data: Genre[]) => this.genres = data
    });
  }

  openModalFunction(content:any){
    this.modalService.open(content, { size: 'lg' });
    this.prefillInputs();
  }

  closeModalFunction(){
    this.modalService.dismissAll();
    this.clearInputs();
  }

  updateBook() {
    let request = new UpsertBookRequest(
      this.title, Number.parseInt(this.authorId), this.genreIds.map(id => Number.parseInt(id))
    );
    this.bookApiService.updateBook(this.book!.id, request).subscribe(book => this.book = book);
    this.closeModalFunction();
    this.clearInputs();
  }

  deleteBook() {
    this.bookApiService.deleteBookById(this.book!.id).subscribe(() => console.log(`Book with id ${this.book?.id} deleted`));
    this.closeModalFunction();
    this.router.navigate(['/books']);
  }

  addBookComment() {
    let request = new UpsertBookCommentRequest(this.commentText, this.book!.id);
    this.bookCommentApiService.createBookComment(request).subscribe(comment => this.bookComments.push(comment));
    this.closeModalFunction();
    this.clearInputs();
  }

  goToBookComment(id: number) {
    this.router.navigate(['/comment-details', id]);
  }

  private prefillInputs() {
    if (this.book) {
      this.title = this.book!.title;
      this.authorId = this.book!.author.id.toString();
      this.genreIds = this.book.genres.map(genre => genre.id.toString());
    }
  }

  private clearInputs() {
    this.title = '';
    this.authorId = '';
    this.genreIds = [];
    this.commentText = '';
  }
}
