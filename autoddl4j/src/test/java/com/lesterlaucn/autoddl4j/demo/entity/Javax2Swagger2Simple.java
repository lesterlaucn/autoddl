package com.lesterlaucn.autoddl4j.demo.entity;

import com.lesterlaucn.autoddl4j.annotations.TableExtend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "测试表1")
@Table
@TableExtend(comment = "测试表",prefix = "autodd4j")
@Data
public class Javax2Swagger2Simple {

    @Id
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "创建时间")
    private Date createTime;

    @Column
    private Boolean isTrue;

    @Column
    private int age;

    @Column
    private BigDecimal price;

    @Column
    private String identityCard;

    @Column
    private String shop;

}
