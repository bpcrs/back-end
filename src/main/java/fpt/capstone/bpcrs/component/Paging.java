package fpt.capstone.bpcrs.component;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;



public class Paging extends PageRequest {

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @param sort must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */
    public Paging(int page, int size, Sort sort) {
        //Because page in JPA start count at 0
        super(page - 1, size, sort);
    }


}
