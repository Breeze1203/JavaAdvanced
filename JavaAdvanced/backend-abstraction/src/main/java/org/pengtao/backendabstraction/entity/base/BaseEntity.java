package org.pengtao.backendabstraction.entity.base;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

//告诉JPA，这是一个父类，字段会被子类继承
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "gmt_create", updatable = false)
    private Date gmtCreate;

    @UpdateTimestamp
    @Column(name = "gmt_modified")
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
