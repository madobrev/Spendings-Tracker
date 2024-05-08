package apps.SpendingTracker.models;

import org.springframework.data.annotation.Id;

public class Category {
    @Id
    private long id;

    private String name;
}
