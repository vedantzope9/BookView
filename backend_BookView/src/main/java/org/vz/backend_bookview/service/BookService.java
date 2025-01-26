package org.vz.backend_bookview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vz.backend_bookview.model.Book;
import org.vz.backend_bookview.repo.BookRepo;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public ResponseEntity<String> addBook(Book book) {
        bookRepo.save(book);
        return new ResponseEntity<>("Book Added!" , HttpStatus.OK);
    }

    public List<Book> getBooks() {
        return bookRepo.findAll();
    }
}
