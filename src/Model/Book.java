package Model;

import java.util.Objects;

public class Book {
	private int id;
	private String title;
	private String author;
	private int quantity;
	private int price;
	private String category;
	private String description;
	
	public Book() {
	}
	
	public Book(int id, String title, String author, int quantity, int price, String category, String description) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.quantity = quantity;
		this.price = price;
		this.category = category;
		this.description = description;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	@Override
	public String toString() {
		return id + ", " + title + ", " + author + ", " + quantity + ", " + price + ", " + category + ", " + description + "\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, category, description, id, price, quantity, title);
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
		return Objects.equals(author, other.author) && Objects.equals(category, other.category)
				&& Objects.equals(description, other.description) && id == other.id && price == other.price
				&& quantity == other.quantity && Objects.equals(title, other.title);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
