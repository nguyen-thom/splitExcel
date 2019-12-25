package nvt.slpit.com.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class CompanyTarget implements Comparable<CompanyTarget> {

    private String sendTarget;

    private String name;

    private int code;

    private String taxCode;

    private Company company;

    private List<Invoice> invoiceList = new ArrayList<>();

    private Long totalValue;

    private String managerDepartment;

    @Override
    public int compareTo(CompanyTarget o) {
        return (int) (this.code - o.getCode());
    }
}
