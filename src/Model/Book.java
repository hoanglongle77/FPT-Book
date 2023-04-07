package Model;

import java.util.Date;
import java.util.Objects;

public class Book {
	private int id;
	private String title;
	private String author;
	private int quantity;
	private int price;
	private String description;
	private Date createdAt;
	
	
	public Book() {
	}
	
	public Book(int id, String title, String author, int quantity, int price, String description, Date createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
		this.createdAt = createdAt;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", quantity=" + quantity + ", price="
				+ price + ", description=" + description + ", createdAt=" + createdAt + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(author, createdAt, description, id, price, quantity, title);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(description, other.description) && id == other.id && price == other.price
				&& quantity == other.quantity && Objects.equals(title, other.title);
	}
}
