import { Routes } from '@angular/router';
import { AuthorComponent } from './components/author/author.component';
import { GenreComponent } from './components/genre/genre.component';
import { BookComponent } from './components/book/book.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { CommentDetailsComponent } from './components/comment-details/comment-details.component';

export const routes: Routes = [
    {
        path: 'authors',
        component: AuthorComponent
    },
    {
        path: 'genres',
        component: GenreComponent
    },
    {
        path: 'books',
        component: BookComponent
    },
    {
        path: 'book-details/:id',
        component: BookDetailsComponent
    },
    {
        path: 'comment-details/:id',
        component: CommentDetailsComponent
    }
];
