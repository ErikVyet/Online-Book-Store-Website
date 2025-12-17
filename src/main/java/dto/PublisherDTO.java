package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Publisher;

public class PublisherDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String address;
	private String phone;
	private String email;
	private List<BookDTO> books;
	
	public PublisherDTO(Publisher publisher) {
		this.id = publisher.getId();
		this.name = publisher.getName();
		this.address = publisher.getAddress();
		this.phone = publisher.getPhone();
		this.email = publisher.getEmail();
		this.books = new ArrayList<BookDTO>();
		for (Book book : publisher.getBooks()) {
			this.books.add(new BookDTO(book));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}

}
