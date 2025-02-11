package org.vz.backend_bookview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.vz.backend_bookview.model.Review;
import org.vz.backend_bookview.service.ReviewService;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/review/{bookId}")
    ResponseEntity<String> addReview(@PathVariable int bookId, @RequestBody Review review){
        review.setBookId(bookId);
       return reviewService.addReview(bookId,review);
    }

    @GetMapping("/review/{bookId}")
    ResponseEntity<List<Review>> getReviewByBookId(@PathVariable int bookId){
        return reviewService.getReviewByBookId(bookId);
    }

    @PostMapping("review/update/{id}")
    ResponseEntity<String> updateReview(@PathVariable int id, @RequestBody Review review){
        return reviewService.updateReview(id,review);
    }

    @DeleteMapping("/review/delete/{id}")
    ResponseEntity<String> deleteReview(@PathVariable int id){
        return reviewService.deleteReview(id);
    }

    @GetMapping("/review/{bookId}/{id}")
    ResponseEntity<Review> getReviewById(@PathVariable int id){
        return reviewService.getReviewById(id);
    }
<<<<<<< HEAD
=======

>>>>>>> 9521db33ccf8827d3dd11dab8347d6dda7763b6d
    @GetMapping("/review/{bookId}/averageRating")
    ResponseEntity<Double> averageRating(@PathVariable int bookId){
        return reviewService.averageRating(bookId);
    }
}
