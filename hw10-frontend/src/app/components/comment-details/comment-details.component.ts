import { Component, OnInit } from '@angular/core';
import { BookComment } from '../../models/book-comment.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { BookCommentApiService } from '../../services/book-comment.api.service';
import { UpsertBookCommentRequest } from '../../models/dto/upsert-book-comment-request.dto';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-comment-details',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './comment-details.component.html',
  styleUrls: ['./comment-details.component.css', '../styles/table.css', '../styles/button.css'],
  providers: [BookCommentApiService]
})
export class CommentDetailsComponent implements OnInit {
  bookComment: BookComment|undefined;

  commentText: string = '';

  constructor(
    private route: ActivatedRoute, 
    private modalService: NgbModal, 
    private router: Router, 
    private bookCommentApiService: BookCommentApiService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => 
      this.bookCommentApiService.getBookCommentById(params["id"]).subscribe(comment => this.bookComment = comment)
    );
  }

  openModalFunction(content:any){
    this.modalService.open(content, { size: 'lg' });
    this.prefillInputs();
  }

  closeModalFunction(){
    this.modalService.dismissAll();
    this.clearInputs();
  }

  updateComment() {
    let request = new UpsertBookCommentRequest(this.commentText, this.bookComment!.book.id);
    this.bookCommentApiService.updateBookComment(this.bookComment!.id, request).subscribe(comment => this.bookComment = comment);
    this.closeModalFunction();
    this.clearInputs();
  }

  deleteComment() {
    let bookId = this.bookComment!.book.id;
    this.bookCommentApiService.deleteBookCommentById(
      this.bookComment!.id).subscribe(() => console.log(`Comment ${this.bookComment?.id} deleted`)
    );
    this.closeModalFunction();
    this.router.navigate(['/book-details', bookId]);
  }

  private prefillInputs() {
    this.commentText = this.bookComment?.text ?? '';
  }

  private clearInputs() {
    this.commentText = '';
  }
}
