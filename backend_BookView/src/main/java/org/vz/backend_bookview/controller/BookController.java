package org.vz.backend_bookview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vz.backend_bookview.model.Book;
import org.vz.backend_bookview.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<String> addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping("/book")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> list= bookService.getBooks();
        return new ResponseEntity<>(list , HttpStatus.OK);
    }
}
