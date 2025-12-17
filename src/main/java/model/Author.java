package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author implements Serializable {

	private static final long serialVersionUID = 956670310786473285L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String bio;
	private Date birth;
	private String country;
	
	@OneToMany(mappedBy = "author")
	private List<Book> books;
	
	public Author() {
		super();
		this.name = null;
		this.bio = null;
		this.birth = null;
		this.country = null;
		this.books = new ArrayList<Book>();
	}
	
	public Author(int id, String name, String bio, Date birth, String country, List<Book> books) {
		super();
		this.id = id;
		this.name = name;
		this.bio = bio;
		this.birth = birth;
		this.country = country;
		this.books = books;
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", bio=" + bio + ", birth=" + birth + ", country=" + country
				+ "\n\tbooks=" + books + "\n]";
	}
	
}