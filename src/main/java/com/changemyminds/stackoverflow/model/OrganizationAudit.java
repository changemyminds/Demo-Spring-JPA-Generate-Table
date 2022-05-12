package com.changemyminds.stackoverflow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: changemyminds.
 * Date: 2022/5/12.
 * Description:
 * Reference:
 */
@Entity
public class OrganizationAudit extends AuditableEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String organizationNumber;

    @Column(nullable = false)
    private String name;

    public OrganizationAudit() {
    }

    public OrganizationAudit(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + " " + this.organizationNumber;
    }
}
