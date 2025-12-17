package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Category;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String description;
	private List<BookDTO> books;
	
	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.description = category.getDescription();
		this.books = new ArrayList<BookDTO>();
		for (Book book : category.getBooks()) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}
	
}
