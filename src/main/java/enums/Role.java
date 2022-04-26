package enums;

public enum Role {
    ADMIN(1L, "Admin"),
    WORKER(2L, "Worker"),
    CUSTOMER(3L, "Customer");

    private Long id;
    private String name;

    Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}