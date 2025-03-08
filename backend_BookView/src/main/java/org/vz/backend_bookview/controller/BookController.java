package org.vz.backend_bookview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.vz.backend_bookview.model.Book;
import org.vz.backend_bookview.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")

    public ResponseEntity<String> addBook(@RequestPart Book book , @RequestPart MultipartFile imageFile){
        return bookService.addBook(book , imageFile);
    }

    @GetMapping("/book")
    public ResponseEntity<List<Book>> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        return bookService.getBookById(id);
    }

    @PostMapping("/book/{id}")
    public  ResponseEntity<String> updateBook(@PathVariable int id,@RequestBody Book book){
        return bookService.updateBook(id,book);
    }

    @GetMapping("book/{bookId}/image")
    public  ResponseEntity<byte[]> getImageByBookId(@PathVariable int bookId){
        Book book=bookService.getBookById(bookId).getBody();

        byte[] imageFile=book.getImageData();

        return ResponseEntity.ok().contentType(MediaType
                        .valueOf(book.getImageType()))
                .body(imageFile);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id){
        return bookService.deleteBook(id);
    }
}
