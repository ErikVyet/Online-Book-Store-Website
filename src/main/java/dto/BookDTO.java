package dto;

import java.io.Serializable;
import java.sql.Date;

import model.Book;

public class BookDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private double price;
	private int quantity;
	private String description;
	private String image;
	private Date publication;
	private int categoryId;
	private String category;
	private int authorId;
	private String author;
	private int publisherId;
	private String publisher;
	private int rating;
	
	public BookDTO() {
		super();
	}

	public BookDTO(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.quantity = book.getQuantity();
		this.description = book.getDescription();
		this.image = book.getImage();
		this.publication = book.getPublication();
		this.categoryId = book.getCategory().getId();
		this.category = book.getCategory().getName();
		this.authorId = book.getAuthor().getId();
		this.author = book.getAuthor().getName();
		this.publisherId = book.getPublisher().getId();
		this.publisher = book.getPublisher().getName();
		this.rating = book.getRating();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Date getPublication() {
		return publication;
	}
	
	public void setPublication(Date publication) {
		this.publication = publication;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
