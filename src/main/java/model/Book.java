package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity

public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private double price;
	private int quantity;
	private String description;
	private String image;
	private Date publication;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	
	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;
	
	@OneToMany(mappedBy = "book")
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "book")
	private List<CartItem> items;
	
	@OneToMany(mappedBy = "book")
	private List<OrderDetail> details;
	
	public Book() {
		super();
		this.title = null;
		this.price = 0;
		this.quantity = 0;
		this.description = null;
		this.image = null;
		this.publication = null;
		this.category = null;
		this.author = null;
		this.publisher = null;
		this.reviews = new ArrayList<Review>();
		this.items = new ArrayList<CartItem>();
		this.details = new ArrayList<OrderDetail>();
	}

	public Book(int id, String title, double price, int quantity, String description, String image, Date publication,
			Category category, Author author, Publisher publisher, List<Review> reviews, List<CartItem> items, List<OrderDetail> details) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.image = image;
		this.publication = publication;
		this.category = category;
		this.author = author;
		this.publisher = publisher;
		this.reviews = reviews;
		this.items = items;
		this.details = details;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	
	public int getRating() {
		int rating = 0;
		for (Review review : this.reviews) {
			rating += review.getRating();
		}
		if (!this.reviews.isEmpty()) {
			rating /= this.reviews.size();
		}
		return rating;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", price=" + price + ", quantity=" + quantity + ", description="
				+ description + ", image=" + image + ", publication=" + publication + ", category=" + category.getName()
				+ ", author=" + author.getName() + ", publisher=" + publisher.getName() + "\n\treviews=" + reviews
				+ "\n\titems=" + items + "\n\tdetails=" + details + "\n]";
	}
   
}
