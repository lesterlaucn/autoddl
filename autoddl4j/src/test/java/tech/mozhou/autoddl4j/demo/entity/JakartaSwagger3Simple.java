package tech.mozhou.autoddl4j.demo.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 全部采用actable自有的注解
 */
@Table
@Data
public class JakartaSwagger3Simple {


    private Long id;

    @Column
    private String name;

    @Column
    private Date createTime;

    @Column
    private Boolean isTrue;

    @Column
    private Integer age;

    @Column
    private BigDecimal price;

    @Column
    private String identitycard;

}
