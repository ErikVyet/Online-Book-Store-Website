package model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ReviewId id;
	private int rating;
	
	@MapsId("userId")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@MapsId("bookId")
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public Review() {
		super();
	}

	public Review(ReviewId id, int rating, User user, Book book) {
		super();
		this.id = id;
		this.rating = rating;
		this.user = user;
		this.book = book;
	}

	public ReviewId getId() {
		return id;
	}

	public void setId(ReviewId id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Review [rating=" + rating + ", user=" + user.getUsername() + ", book=" + book.getTitle() + "]";
	}
	
}
