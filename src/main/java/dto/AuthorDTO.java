package dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Book;

public class AuthorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String bio;
	private Date birth;
	private String country;
	private List<BookDTO> books;
	
	public AuthorDTO(Author author) {
		this.id = author.getId();
		this.name = author.getName();
		this.bio = author.getBio();
		this.birth = author.getBirth();
		this.country = author.getCountry();
		this.books = new ArrayList<BookDTO>();
		for (Book book : author.getBooks()) {
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}

}
