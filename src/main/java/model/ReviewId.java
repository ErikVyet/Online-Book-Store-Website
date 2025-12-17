package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class ReviewId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int userId;
	private int bookId;
	
	public ReviewId() {
		super();
	}

	public ReviewId(int userId, int bookId) {
		super();
		this.userId = userId;
		this.bookId = bookId;
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

	@Override
	public String toString() {
		return "ReviewId [userId=" + userId + ", bookId=" + bookId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewId other = (ReviewId) obj;
		return bookId == other.bookId && userId == other.userId;
	}
	
}

