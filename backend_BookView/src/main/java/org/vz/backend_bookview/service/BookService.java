package org.vz.backend_bookview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.vz.backend_bookview.model.Book;
import org.vz.backend_bookview.repo.BookRepo;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addBook(Book book) {
        try {
            bookRepo.save(book);
            return new ResponseEntity<>("Book Added!", HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.status(500).body("Failed to save book: " + e.getMessage());
        }
    }

    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> list = bookRepo.findAll();
            return ResponseEntity.ok(list);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    public ResponseEntity<Book> getBookById(int id) {
        try{
            Optional<Book> book =bookRepo.findById(id);
            if(book.isPresent())
                return ResponseEntity.ok(book.get());
            else
                return new ResponseEntity("Book not found!" , HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBook(int id, Book book) {
        try{
            return bookRepo.findById(id).map(existingBook -> {
                // Update the fields of the existing book
                existingBook.setTitle(book.getTitle());
                existingBook.setAuthor(book.getAuthor());
                existingBook.setDescription(book.getDescription());
                existingBook.setAverageRating(book.getAverageRating());
                existingBook.setPublishedDate(book.getPublishedDate());
                existingBook.setImageName(book.getImageName());
                existingBook.setImageType(book.getImageType());
                existingBook.setImageData(book.getImageData());

                bookRepo.save(existingBook);

                return ResponseEntity.ok("Book Updated!");
            }).orElse(new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND));
        }
        catch (Exception e){
            return new ResponseEntity("Failed to update!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(int id) {
        try{
            if(bookRepo.findById(id).isPresent()) {
                bookRepo.deleteById(id);
                return ResponseEntity.ok("Book Deleted!");
            }
            else
                return new ResponseEntity("Book not present!" , HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to delete!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }
}
