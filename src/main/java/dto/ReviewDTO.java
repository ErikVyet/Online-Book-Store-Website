package dto;

import java.io.Serializable;

import model.Review;

public class ReviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int userId;
	private int bookId;
	private int rating;
	
	public ReviewDTO(Review review) {
		this.userId = review.getUser().getId();
		this.bookId = review.getBook().getId();
		this.rating = review.getRating();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
