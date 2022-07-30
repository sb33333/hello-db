package hello.entity;

public class BakedBean {
	private String name;
	private long id;

	public String getName() {
		return name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BakedBean [name=" + name + ", id=" + id + "]";
	}
	
}
