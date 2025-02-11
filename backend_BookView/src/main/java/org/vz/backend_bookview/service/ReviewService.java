package org.vz.backend_bookview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vz.backend_bookview.model.Book;
import org.vz.backend_bookview.model.Review;
import org.vz.backend_bookview.repo.ReviewRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;


    public ResponseEntity<String> addReview(int bookId, Review review) {
        try{
            review.setBookId(bookId);
            reviewRepo.save(review);
            return ResponseEntity.ok("Review Added!");
        }
        catch(Exception e){
            return ResponseEntity.status(500).body("Failed to add review..." + e.getMessage());
        }
    }

    public ResponseEntity<String> updateReview(int id, Review review) {
        try{
            return reviewRepo.findById(id).map(existingReview -> {
                existingReview.setComment(review.getComment());
                existingReview.setRating(review.getRating());

                reviewRepo.save(existingReview);

                return ResponseEntity.ok("Review Updated!");
            }).orElse(new ResponseEntity<>("Review not found!", HttpStatus.NOT_FOUND));
        }
        catch (Exception e){
            return new ResponseEntity("Failed to update!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    public ResponseEntity<String> deleteReview(int id) {
        try{
            if(reviewRepo.findById(id).isPresent()) {
                reviewRepo.deleteById(id);
                return ResponseEntity.ok("Review Deleted!");
            }
            else
                return new ResponseEntity("Review not present!" , HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to delete!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    public ResponseEntity<List<Review>> getReviewByBookId(int bookId) {
        try {
            List<Review> list = reviewRepo.findByBookId(bookId);
            if(list.isEmpty())
                return new ResponseEntity("No Reviews present!",HttpStatus.NO_CONTENT);
            return ResponseEntity.ok(list);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    public ResponseEntity<Review> getReviewById(int id) {
        try{
            Optional<Review> review =reviewRepo.findById(id);
            if(review.isPresent())
                return ResponseEntity.ok(review.get());
            else
                return new ResponseEntity("Review not found!" , HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }

    public ResponseEntity<Double> averageRating(int bookId) {
        try{
<<<<<<< HEAD
            double rating = reviewRepo.getSumOfRatingsByBookId(bookId) ;
=======
            double sum=reviewRepo.getSumOfRatingsByBookId(bookId);
            int count=reviewRepo.getCountOfRatingsByBookId(bookId);

            double rating = (double)sum/count ;

>>>>>>> 9521db33ccf8827d3dd11dab8347d6dda7763b6d
            return new ResponseEntity<>(rating,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!" +e.getMessage() , HttpStatus.BAD_REQUEST );
        }
    }
}
