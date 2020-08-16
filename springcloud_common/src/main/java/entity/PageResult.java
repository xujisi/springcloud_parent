package entity;

import java.util.List;

/**
 * 存放Page的实体
 * @author: 许集思
 * @date:  2020/8/16 16:40
 */
public class PageResult<T> {

    private Long total;
    private List<T> row;

    public PageResult() {
    }

    public PageResult(Long total, List<T> row) {
        this.total = total;
        this.row = row;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRow() {
        return row;
    }

    public void setRow(List<T> row) {
        this.row = row;
    }
}
